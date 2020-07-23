Gprc Gateway
===

## grpc-gateway

@Github: [grpc-ecosystem/grpc-gateway](https://github.com/grpc-ecosystem/grpc-gateway)  
@Offic: [grpc-gateway](https://grpc-ecosystem.github.io/grpc-gateway/)  

![grpc-gateway](https://camo.githubusercontent.com/e75a8b46b078a3c1df0ed9966a16c24add9ccb83/68747470733a2f2f646f63732e676f6f676c652e636f6d2f64726177696e67732f642f3132687034435071724e5046686174744c5f63496f4a707446766c41716d35774c513067677149356d6b43672f7075623f773d37343926683d333730)

> The grpc-gateway is a plugin of the Google protocol buffers compiler [protoc](https://github.com/protocolbuffers/protobuf). It reads protobuf service definitions and generates a reverse-proxy server which 'translates a RESTful HTTP API into gRPC. This server is generated according to the [google.api.http](https://github.com/googleapis/googleapis/blob/master/google/api/http.proto#L46) annotations in your service definitions.

### gprc-gateway + swagger2

github.com/grpc-ecosystem/grpc-gateway/protoc-gen-swagger

## 对比 grpc-web

- [Compare gRPC-Web with grpc-gateway](https://medium.com/@thinhda/compare-grpc-web-with-grpc-gateway-fa1e2acdf29f)

### 开源项目

#### 代码生成器

- [izumin5210/grapi](https://github.com/izumin5210/grapi) 可以通过指令直接生成一个grpc-gateway的go+grpc的项目

## 参考

- [grpc-gateway：grpc转换为http协议对外提供服务](https://segmentfault.com/a/1190000012389468)
- [protobuffer、gRPC、restful gRPC的相互转化](https://segmentfault.com/a/1190000013560739)
- [7大维度看国外企业为啥选择gRPC打造高性能微服务？](https://segmentfault.com/a/1190000013201741)
- [grpc-gateway的替代品--Turbo](https://www.cnblogs.com/andyidea/p/6529900.html)
- [Grpc-Gateway - Grpc兼容HTTP协议文档自动生成网关](https://my.oschina.net/wenzhenxi/blog/3023874)
- [Migrating APIs from REST to gRPC at WePay](https://wecode.wepay.com/posts/migrating-apis-from-rest-to-grpc-at-wepay)
- [Protobuf & gRPC & gRPC-Gateway](https://www.udnz.com/2019/01/25/Protobuf-gRPC-gRPC-Gateway/)