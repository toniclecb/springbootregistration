# Spring Boot Registration Project

By Cleiton Balansin

## Introduction

This project was created to be used like a tutorial, you can follow the commits and see what was done in the code and the comments about writen in this file.

The main objective of this project is to bring together the various technologies accessible by SpringBoot.
The project starts as simple as possible, but over the commits new features will be added.
You will find comments and explanations of each topic in the sections "Tags and Commits" and "Tags", 

## Running

It's a Maven Project!
Commands:
- mvn clean compile - Clean and compile the project;
- mvn spring-boot:run - Run the application;
- This are related to multiple property files:
  - mvn clean package -Pdev - this will build with 'dev' profile
  - java -jar app.jar -Dspring.profiles.active=dev - this will run with 'dev' profile


### The project: Registration

This project has the functionality to store data of any type. You can store data about a "Person" or a "Car" or a "Dog". For this you must create entities that will define the data saved by each type of register ("Person", "Car" or "Dog") you want.

## Tags, Commits and comments about


- 100_initial
  - First commit - project made by spring-initializr - no changes
  - Files from spring-initializr - original zip and configuration used
    - The first two commits are related to project generated by spring-initializr (https://start.spring.io/). The original project generated, the original zip file and an image from the site with the configuration used to generate this project.
  - The readme.md file
    - Contains the firsts topics: "Introduction", "running", "the project", "commits" and "tags".
- 101_first_endpoint
  - the first endpoint returns the datetime of server
- 102_spring_actuator
  - Change /time to return an ISO-8601 representation of date
    - This is a easier way to represent datetime in a string, a javascript code, for example, can turn it into a date object very easy.
  - Added actuator dependency
  - Change the port of actuator management server
    - we change application.properties file defining the server port and the management server port
  - Enable all of actuator endpoints by properties file - Change the default discovery endpoint /actuator to /act by properties file
  - Added git informations into /act/info endpoint of actuator
  - Added maven build informations into /act/info endpoint of actuator
  - Change the exposure of actuator's endpoints to expose only info,health,metrics
- 103_h2database
  - Rename main class SpringbootRegistrationApplication and readme.md modifications
  - Create method run() with CommandLineRunner interface
  - Import H2 and make a method to access (insert and query) relational data using JDBC
- 104_Logging
  - Change the sout for Logger.info (slf4j)
  - Logging the exception in case of problem in database operations
  - Create logback configuration file logback.xml, log in console with level info
  - Adding config to log into files, SizeAndTimeBasedRollingPolicy will create files with 1mb max
  - Remove the code that generates logs
- 105_Properties_files
  - Added property to change the format of return from /time
  - Change 1 application.properties to 3 files, more dev and prod, prod is default
  - Change configuration of properties, change default profile from pom.xml to application.properties
- 106_devtools
  - Added spring dev tools dependency
- 107_Exception_handler
  - Added Exception handler and defined a class to manipulate bad requests for /date and PathVariable
  - Added handler to not found response
- 108_dependency_injection
  - Creating our first Service and using dependency injection
- 109_first_test
  - Added the first test class for method DateTimeService.date() in the class DateTimeServiceTest
  - Second test, Changing the code to a more testable code, remove the direct dependency on Date, test getStringDatetime
- 110_upload_and_download_files
  - Create the class FileStorageConfig to store the directory of upload
  - Create the file controller (mapping for upload), file exception and service (method to store file)
  - Create methods to download a file, controller and service
- 111_spring_jpa
  - Configure H2 database removing default config

## Tags

### 100_initial - Initial project

The initial project has only two dependencies: 
- "spring-boot-starter-web" (this one make your java project into a web java project),
- "spring-boot-starter-test" (and this adds into your project the possibility to use tests, and a class SpringbootRegistrationApplicationTests already exists in the initial project).

Besides the dependencies we have the plugin:
- "spring-boot-maven-plugin" (this one allows we use spring-boot commands in maven).

We need run "mvn clean compile" to compile the project and "mvn spring-boot:run" to run the application. After that, you can access the application in the browser: http://localhost:8080/. "Whitelabel Error Page" must appear because we still haven't defined anything, but the Tomcat server is running.

### 101_newtag - The first endpoint

The class **TimeController** contains two annotations: RestController (https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html)
and GetMapping (https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/GetMapping.html).

**RestController** is a specialized version of the controller. It includes the @Controller and @ResponseBody annotations, and as a result, simplifies the controller implementation (https://www.baeldung.com/spring-controller-vs-restcontroller).

**GetMapping** annotation is a specialized version of @RequestMapping annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET) (https://howtodoinjava.com/spring5/webmvc/controller-getmapping-postmapping/).

Now, after running the application, it is possible to access the address http://localhost:8080/time and see something like: _Wed Nov 16 00:38:37 BRST 2022_ (result of the line "new Date().toString()" in the method "public String time()").


### 102_spring_actuator

Spring Boot's Actuator dependency is used to monitor and manage the Spring web application.
Actuator is mainly used to expose operational information about the running application — health, metrics, info, dump, env, etc. It uses HTTP endpoints or JMX beans to enable us to interact with it (https://www.baeldung.com/spring-boot-actuators).
Now, after running the application, it is possible to access the address http://localhost:9001/actuator/health, for example, to see the status of the server ("UP" is expected).

Actuator can respond in different port than the application, for that we change application.properties file.

We change the default endpoint to http://localhost:9001/act and enable all the endpoints of actuator changing the properties "management.endpoints.web.exposure.include" and "management.endpoints.web.base-path".

Now we can access git informations in http://localhost:9001/act/info endpoint thanks to "git-commit-id-plugin" plugin.
Also we can access maven build informations thanks to "spring-boot-maven-plugin" plugin, here we must define a "goal" in the pom file (< goal>build-info</ goal>).

We also change the exposure of actuator's endpoints to expose only info,health,metrics by setting "management.endpoints.web.exposure.include" property, in the same way we can change "management.endpoints.web.exposure.exclude" property, but with the names of endpoints we want to exclude!

### 103_h2database

First we created the method run() using CommandLineRunner interface: Used to indicate that a bean should run when it is contained within a SpringApplication. (https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/CommandLineRunner.html).

Now we want the H2 database working on the project, after we will use this for something.
We added the dependencies for H2 and "spring-boot-starter-jdbc" and all configuration is automatic in Spring Boot for H2, because of that we don't need create configurations like url connection, user, password, etc. We need only inject the object JdbcTemplate.
In SpringbootRegistrationApplication.run() was implemented all tasks related to the database: drop table, create table, insert records, query.
The object Car was created to make this tasks possible, see the packages created? we try start some clean architecture here.

### 104_Logging

It's very simple to use Logger. Import org.slf4j.Logger and create the object with LoggerFactory.getLogger(), after that we can use Logger.info() and anothers methods to log. Messages must be meaningful and always remember to log the exception.
We can configurate the log, here we put the file logback.xml to log into console with info level.
RollingFileAppender and SizeAndTimeBasedRollingPolicy are used to config the logs into files, files max 1mb in size, 30 days and 1gb max storage.

### 105_Properties_files

You can create every property you want, Spring Boot keeps it simple and everything you need to do is write it in "application.properties" file, and in the code get the value with "env.getProperty()" method.

We created two more property files: application-dev.properties and application-prod.properties. We moved the content of aplication.properties to these files and we change a little to show some difference when one or another runs. Prod start the server in 8080 port. Dev start the server in 8081 port.
**Important**: For this was created the tag "profiles" in pom.xml, and we can change wich file runs in the tag "activeByDefault"! Alternatively, the profile can be directly specified in the application.properties file by adding the line: spring.profiles.active=prod

We changed the format of setting the default profile from pom.xml to application.properties. This will help during the development, but previous configuration is nicer.

### 106_devtools

By adding "spring-boot-devtools" dependency we enable some helps in develpment time: Property defaults, Automatic Restart, Live Reload, Global settings, Remote applications. The most used is automatic restart, now by only saving the files the server restart and apply the changes, we don't need restart it by ourselves.

### 107_Exception_handler

To test the exception handler we created the method TimeController.date(@PathVariable(value = "field") String field).
We created a exception handler, class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler, annoted with @ControllerAdvice and @RestController. ControllerAdvice allows you to handle exceptions across the whole application basically an interceptor of exceptions thrown by methods annotated with @RequestMapping.
Then we created class ExceptionResponse implements Serializable. This is the response when the handler is called and needs to answer something.
After that, we created class UnsuportedDateFormatException extends RuntimeException annoted with @ResponseStatus(HttpStatus.BAD_REQUEST), this way we are defining that this class will handle bad requests. This class will be used in one of CustomizedResponseEntityExceptionHandler methods, in the annotation @ExceptionHandler(UnsuportedDateFormatException.class).
Accessing an invalid address like http://localhost:8081/date/test will return this:
{"timestamp":"2022-11-30T00:50:40.004+00:00","message":"The follow field is not suported: test","details":"uri=/date/test"}

#### PathVariable

PathVariable is used to handle template variables in the request URI mapping. Here we used to define which type of response we want.
<code>@GetMapping("/date/{param}")
public String date(@PathVariable(value = "param") String param){</code>
Use {} in the GetMapping annotation, and define all fields with the same name, simple like that.
The URI is something like that: http://localhost:8081/date/DayOfWeek
"DayOfWeek" will be defined in the variable "param".

#### Added handler to not found response

To customize the not found response we need override the method handleNoHandlerFoundException in CustomizedResponseEntityExceptionHandler class to return our customized response (ExceptionResponse).
To redirect the exception to our advice we need to set a couple of properties in the the properties file: spring.mvc.throw-exception-if-no-handler-found=true and spring.web.resources.add-mappings=false.
spring.mvc.throw-exception-if-no-handler-found: Whether a "NoHandlerFoundException" should be thrown if no Handler was found to process a request.
spring.web.resources.add-mappings: Whether to enable default resource handling.
See https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html

### 108_dependency_injection

Here we export some logic from TimeController to DateTimeService and annotate it with @Service. This means that SpringBoot will manage the objects from this class.
Now we can use DateTimeService in different places, first let's use in TimeController, we need annotation @AutoWired to SpringBoot know that must instanciate and manage this object.
We'll see more about dependency injection later on.
See: https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring

### 109_first_test

We created the class DateTimeServiceTest, using dependency injection we get access to DateTimeService, then we call the method date() with possibles values in the parameter and test it's response.
In a test class the more important things are @SpringBootTest and @Test annotations and the assert comands that will check if the code runs on the right way.

In the second part of tests we did a few things:
- Created interface DateTime
- Created class DateTimeImpl
- Changed getStringDatetime() method to use DateTime interface
- In test, renamed getStringDatetimeTest() to dateTest()
- Created a new getStringDatetimeTest()
- Created a method setUp() annotated with @BeforeAll
- Created a method getDateTime() annotated with @Bean

Changes executed in DateTime, DateTimeImpl and DateTimeService were to provide a better way to structure the code, basically remove the direct dependency (Date) will allow us to inject different implementations (runtime and test runtime).
In runtime the DateTime will be created at SpringbootRegistrationApplication.getDateTime() but in test we created that in setUp() method, and here we mocked methods from Date. We did this to exemplify the use of the mock.
Spring @Bean annotation tells that a method produces a bean to be managed by the Spring container.
@BeforeAll annotation is used to signal that annotated method should be executed before all tests.

### 110_upload_and_download_files

First we added another dependency: "spring-boot-configuration-processor". This will allow us to set properties in another way, creating a prefix in ours properties files.
Some properties of Spring already have been set, "spring.servlet.multipart" properties.
Create the class FileStorageConfig to store the directory of upload.
In SpringbootRegistrationApplication class there is the call to config file and we print the value of directory.

In FileStorageController.uploadFile() we recive a MultipartFile, in the FileStorageService.saveFile() we save it and return the filename!
In FileStorageService we have the constructor that create directories.
In the request the file needs to be set in body with type "form-data" (parameter "file").
UploadFileResponseVO class was created to return some info to user in the response.

We created FileStorageService.downloadFileResource() and FileStorageController.downloadFile() to generate the file in the /download request.
MyFileNotFoundException was created to return errors when the file was not found.

### 111_spring_jpa

First, we write the datasource configuration in properties file and we don't depend on default settings for our H2 database.
