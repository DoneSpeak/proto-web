Proto Web
===

## proto-service

该方式会将方法名定义为url，使用post作为请求的方法，从而使得所有的请求都是protbuf的。

### 定义接口

api/account/api/list_account.proto
```proto
message ListAccountRequest {
    common.Pagination page = 1 [(valid.validate) = true];
}

message ListAccountResponse {
    repeated account.data.Account accounts = 1;
    common.Pagination page = 2;
}

/**
 * 获取账号列表
 */
message ListAccount {
    ListAccountRequest request = 1;
    ListAccountResponse response = 2;
}
```

api/account/api/data/account.proto
```proto
message Account {
    int64 account_id = 1;
    string first_name = 2 [(valid.pattern) = "[a-zA-Z]{5,20}"];
    string last_name = 3 [(valid.pattern) = "[a-zA-Z]{5,20}"];
    string email = 4 [(valid.email) = true];
    string phone = 5 [(valid.pattern) = "[a-zA-Z]{5,20}"];
}
```

规定定义请求的类名以Request作为结尾，响应类型的类以Response作为返回类型，定义接口的message则需要制定request和response。此外，Account类中的数据校验参数也是自定义实现的。

指定模块提供的所有接口  

api/account/api/account_api.proto
```proto
package account;

import "account/api/list_account.proto";
import "account/api/get_account.proto";

message AccountApi {
    // POST /account-api/list-account
    account.api.ListAccount listAccount = 1;
    // POST /account-api/get-account
    account.api.GetAccount getAccount = 2;
}
```

指定模块聚合项目的接口  

web/api.proto  
```proto
package web;

import "account/account_api.proto";
import "image/image_api.proto";

message Api {
    account.AccountApi account_api = 1;
    image.ImageApi image_api = 2;
}
```

### 后端实现

#### 标准实现  
  
```java
@RestController
@RequestMapping("/std/AccountApi")
public class AccountStandardController {

    @RequestMapping(
        path = "ListAccount",
        method = RequestMethod.POST,
        consumes = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"},
        produces = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"})
    public ListAccountResponse listAccount(ListAccountRequest request) {
        // do something
        ListAccountResponse response = ListAccountResponse.newBuilder().build();
        return response;
    }
}
```

#### 自定义标签实现  

```java
@ProtoApi(AccountApi.class)
public class AccountCustomizedController {

    // POST /AccountApi/ListAccount
    @ProtoEndpoint(ListAccount.class)
    public ListAccountResponse listAccount(@RequestBody ListAccountRequest request) {
        // do something
        ListAccountResponse response = ListAccountResponse.newBuilder()
            .addAccounts(Account.newBuilder().setEmail("gr@gr.com").build())
            .setPage(request.getPage())
            .build();
        return response;
    }
}
```  

在自定义实现中，可以制定一个`ProtoEndpointConverter`来将驼峰的路径转化为中划线等方式。

## proto-rest

该方式是一个RESTful风格的实现，具体的请求方法和请求路径会通过option在protobuf中定义清楚。该方式会按照REST的方式使用请求方法，当使用GET方法时候，如果要拼接路径参数则无法使用protobuf，需要将字段解析出来拼接到url中进行请求。

### 定义接口

api/account/rest/api/list_account.proto
```proto
package account.rest.api;

import "core/rest.proto";
import "account/rest/data/account.proto";

message ListAccountRequest {
    string email = 1;
    // 按照创建时间查询 (created_at_from, created_at_end]
    int64 created_at_from = 2;
    int64 created_at_end = 3;
}

message ListAccountResponse {
    repeated account.rest.data.Account accounts = 1;
}

message ListAccount {
    option (api.endpoint) = {method: "GET" path:"/account"};

    ListAccountRequest request = 1;
    ListAccountResponse response = 2;
}
```

api/account/rest/api/get_account.proto
```proto
package account.rest.api;

import "core/rest.proto";
import "account/rest/data/account.proto";

message GetAccountRequest {
}

message GetAccountResponse {
    account.rest.data.Account account = 1;
}

message GetAccount {

    option (api.endpoint) = {method: "GET" path:"/account/{accountId}"};

    GetAccountRequest request = 1;
    GetAccountResponse response = 2;

    // 约定：path_variable 开头的命名均为路径参数
    int64 path_variable_account_id = 3;
}
```

模块api全局定义
```proto
import "core/rest.proto";

package account.rest;

message AccountApi {
    
    // 所有api路径前缀
    option (api.global).path = "/account";

}
```

### 后端实现

#### 标准实现

```java
@RestController
@RequestMapping("/account")
public class AccountStandardController {

    @GetMapping(
        path = "account",
        consumes = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"},
        produces = {"application/x-protobuf", "application/x-protobuf;charset=UTF-8"})
    public ListAccountResponse listAccount(ListAccountRequest request) {
        ListAccountResponse response = ListAccountResponse.newBuilder().build();
        return response;
    }
}
```

#### 自定义注解实现

```java
@ProtoApi(AccountApi.class)
public class AccountCustomizedController {

    @ProtoEndpoint(ListAccount.class)
    public ListAccountResponse listAccount(@RequestBody ListAccountRequest request) {
        ListAccountResponse response = ListAccountResponse.newBuilder().build();
        return response;
    }

    @ProtoEndpoint(GetAccount.class)
    public GetAccountResponse listAccount(@PathVariable long accountId, @RequestBody GetAccountRequest request) {
        GetAccountResponse response = GetAccountResponse.newBuilder().build();
        return response;
    }
}
```

## proto-grpc-envoy

## proto-grpc-gateway

## proto-rpc

## proto-validation

## 前端技术栈

- []()

## 规范

接口由提供方声明，只需要定义自己对外提供的接口，不定义自己需要调用的接口。

## 测试工具

### proto + web

**postman app**  

还不支持，见 issue [Feature Request : Support for google protobuf #2801](https://github.com/postmanlabs/postman-app-support/issues/2801)

**spluxx/Protoman(推荐)**  

> A Postman-like API client for protobuf-based messages.  

@Github: [spluxx/Protoman](https://github.com/spluxx/Protoman/issues/13)  

目前`v0.3.3`是可以正常使用的，`v0.3.2`还有一些问题。

### proto + grpc

**uw-labs/BloomRPC(推荐)**  

> Inspired by Postman and GraphQL Playground  
> BloomRPC aim to give the simplest and efficient developer experience for exploring and querying your GRPC services.

@Github: [uw-labs/bloomrpc](https://github.com/uw-labs/bloomrpc)    

![BloomRPC](https://github.com/uw-labs/bloomrpc/blob/master/resources/editor-preview.gif)  

- [How To Test GRPC Services Using GUI client](https://www.youtube.com/watch?v=27HLPHLOwEQ&list=PLI5t0u6ye3FGXJMh5kU2RvN0xrul67p7R&index=5)

**njpatel/grpcc**    

> grpcc is a flexible command-line client for any gRPC server for quick and easy testing of APIs. grpcc is written in nodejs but can talk to a gRPC service written in any language.  

repo: [njpatel/grpcc](https://github.com/njpatel/grpcc)

**jnewmano/grpc-json-proxy**  

> GRPC JSON is a proxy which allows HTTP API tools like Postman to interact with gRPC servers.  

repo: [jnewmano/grpc-json-proxy](https://github.com/jnewmano/grpc-json-proxy)  
ref: [gRPC + Postman = 😍](https://medium.com/@jnewmano/grpc-postman-173b62a64341)  

**postman app**  

还不支持，见 issue [gRPC support in Postman #5194](https://github.com/postmanlabs/postman-app-support/issues/5194)

### Api设计规则

- [API Design Guide @cloud.google.com](https://cloud.google.com/apis/design/)

## TODO

- 如果一个接口可以返回rest或者protobuf message，我建议在controller就返回protobuf，如果用户发来的请求是Accept: application/json，protobuf的httpmessageconverter会自动将protobuf转化成json返回

## 参考
- [Third-Party Add-ons for Protocol Buffers](https://github.com/protocolbuffers/protobuf/blob/master/docs/third_party.md)
- [Protocol Buffers @developers.google.com](https://developers.google.com/protocol-buffers/docs/overview)
- [gRPC with REST and Open APIs](https://grpc.io/blog/coreos/) #readlater