# spring-security-rest-redis-tokenstore


## Description ##

This project will create an authentication and authorization service. REST endpoints will be secured and tokens are kept in the Redis server instead of in memory or jdbc token store.

By using this, Authentication and Authorization service can be scalled very easily as token verification is done from the Redis server.
  

## Project Details ##

### Pre-requisite ###

1. Java 8 installed
2. Gradle installed 
3. Redis installed and running

### How to build ###

gradle clean build

### How to Run ###

gradle bootRun

### How to Test ###

#### To get the authorization token #### 

http://localhost:8080/oauth/token?username=joe&password=joe&grant_type=password&scope=read%20write

##### Set http Header as #####

```key --> Authorization & value --> Basic Y2xpZW50YXBwOjEyMzQ1Ng==```


``` 
Response

{
  "access_token": "52711040-a6f0-4288-b529-939404f25d97",
  "token_type": "bearer",
  "refresh_token": "07c91f86-fccb-48c5-a121-9a8ed5a35bf8",
  "expires_in": 299,
  "scope": "read write"
}
```


http://localhost:8080/greeting?name=Saumit

##### Set http Header as #####


```key --> Authorization and value --> Bearer 52711040-a6f0-4288-b529-939404f25d97```


```
Response 

{
  "id": 4,
  "content": "Hello, SaM!"
}
```

## Contribution List ##
```Saumit Mandal - mr.saumit.mandal@gmail.com```