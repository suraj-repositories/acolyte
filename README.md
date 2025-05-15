# Acolyte
A command line tool for spring boot application, which make your effort a lot easier by writing boilerplate code for you and also maintain the standard spring package structure.
<br/><br/>
<p align="center">Under development...</p>
<br/><br/>

## Commands

#### Make Entity
```shell
acoltye make:entity <Entity-Name>
```
```shell
acoltye make:entity User
```

#### Make View
```shell
acolyte make:view <View-Name>
```
```shell
acolyte make:view user-list
```

#### Make Controller
```shell
acolyte make:controller <Controller-Name> --resource <function-names>
```
```shell
acolyte make:controller UserController 
```
```shell
acolyte make:controller UserController --resource
```
```shell
acolyte make:controller UserController --resource index save destroy
```
#### Make DTO or POJO
```shell
acolyte make:dto <dto-name>
```
```shell
acolyte make:dto UserDto
```
#### Make Service
```shell
acolyte make:service <service-name>
```
```shell
acolyte make:service UserSerivce
```
#### Make Repository
```shell
acolyte make:repository <repository-name>
// OR
acolyte make:repo <repository-name>
```
```shell
acolyte make:repo UserRepository
```
#### Make Configuration
```shell
acolyte make:configuration <config-file-name>
// OR
acolyte make:config <config-file-name>
```
```shell
acolyte make:config WebConfig
```