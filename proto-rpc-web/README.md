Proto Rpc Web
===
## 写在前面
### 技术栈
后端：

前端：
- [protobufjs/protobuf.js]

公共：
- [google.api.http](https://github.com/googleapis/googleapis/blob/master/google/api/http.proto#L46)

### 基本想法

模仿 [grpc-gateway](https://github.com/grpc-ecosystem/grpc-gateway) 利用 protocol buffer对rpc的支持进行实现。

```proto
syntax = "proto3";
package example;

import "google/api/annotations.proto";

message StringMessage {
  string value = 1;
}

service YourService {
    rpc Echo(StringMessage) returns (StringMessage) {
        option (google.api.http) = {
            post: "/v1/example/echo"
            body: "*"
        }
    }
}
```

## Web 前端

[protobufjs/protobuf.js]