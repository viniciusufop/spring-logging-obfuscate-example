# Sprint Boot Logging Stater example

I build this starter to help developers generate print in their kotlin/java applications respect law's as LGPD. 
In this case, I propose generate a json data where you use annotations in your class for decide what data need to obfuscate or don't.

## Structure

This project has two modules:
* the first is a spring starter with obfuscate logic and some automatic loggers' generate in resource flow.
* the second is a basic web spring application where I created a resource and demonstrate how you can use this library.

## Building the project

You need to build a library before the demo application. So, for this execute the command bellow:
```shell
./gradlew -p spring-boot-logging-starter clean build publishToMavenLocal
```

After this, execute the command for start demo application:
```shell
 ./gradlew -p app-demo bootRun
```
> Important: Your application wil run on port 8080 by default

## Execute test in your application

Demo application has two tests. One with ResponseEntity class as a return and other without him. In both cases you will see a print of your request, response and other important data.
Request Example:

> Important: The data bellow was generated on the website https://www.4devs.com.br/gerador_de_pessoas

```shell
curl --location --request POST 'http://localhost:8080/test/response-entity' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Theo Gabriel Ferreira",
    "document": "820.438.170-00",
    "phone": "(69) 99857-1473",
    "password": ">e%p2/DB{{aT",
    "description": "First test using the library"
}'
```
OR
```shell
curl --location --request POST 'http://localhost:8080/test' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Theo Gabriel Ferreira",
    "document": "820.438.170-00",
    "phone": "(69) 99857-1473",
    "password": ">e%p2/DB{{aT",
    "description": "First test using the library"
}'
```

Application Print Example:
```shell
2022-05-19 15:33:42.743  INFO 22892 --- [nio-8080-exec-6] c.v.l.r.print.ResourceLoggingComponent   : "RequestInfo" {
    "URL":"/test/response-entity",  
    "HTTPMethod":"POST", 
    "KotlinClass":"com.vfs.appdemo.TestResource", 
    "kotlinMethod":"testWithResponseEntity", 
    "body":{"name"="Th** Ga***** Fe******","document"="***438170**","phone"="***********1473","password"="************","description"="First test using the library"} 
} 
2022-05-19 15:33:42.744  INFO 22892 --- [nio-8080-exec-6] com.vfs.appdemo.TestResource             : M=testWithResponseEntity generates log with obfuscate data. DTO={"name"="Th** Ga***** Fe******","document"="***438170**","phone"="***********1473","password"="************","description"="First test using the library"}
2022-05-19 15:33:42.745  INFO 22892 --- [nio-8080-exec-6] c.v.l.r.print.ResourceLoggingComponent   : "ResponseInfo" {
    "URL":"/test/response-entity",  
    "HTTPMethod":"POST",
    "HTTPStatus":"201",
    "Body":{"name"="Th** Ga***** Fe******","document"="***438170**","phone"="***********1473","password"="************","description"="First test using the library","uuid"="270bba6d-5542-4183-9385-881fb8b58638"} 
} 

```
