# hui-api
This is the server side REST API made for the HUI Proyect. Built with Spring Boot and Hibernate.


### Stack of technologies used:

![Java](https://img.shields.io/badge/java-red.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/hibernate-brown.svg?style=for-the-badge&logo=hibernate&logoColor=white)
![Postman](https://img.shields.io/badge/postman-orange.svg?style=for-the-badge&logo=postman&logoColor=white)
![Jira](https://img.shields.io/badge/jira-blue.svg?style=for-the-badge&logo=jira&logoColor=white)
![Confluence](https://img.shields.io/badge/confluence-grey.svg?style=for-the-badge&logo=confluence&logoColor=white)
![PHPmyadmin](https://img.shields.io/badge/PHPmyadmin-FF00FF.svg?style=for-the-badge&logo=phpmyadmin&logoColor=white)


Postman API Reference: https://www.postman.com/hui-app

---

## Development Instalation
1. Copy the file `application.properties.sample` in the same folder *(app/src/main/resources)* and name it `application.properties`

2. Change the following values for your enviroment ones.
- server.port
-  spring.datasource.url
-  spring.datasource.username
-  spring.datasource.password
```properties
server.port=<port>
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/<database>?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.username=<user>
spring.datasource.password=<password>
server.error.include-message=always
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jackson.serialization.FAIL_ON_EMPTY_BEANS=false

```
3. Run the application with the property `spring.jpa.hibernate.ddl-auto=create-drop` to create and populate the database, then change the value to `update` if you dont want to create the database each time it runs.
4. Also comment this three lines in the file `app/src/main/java/com/grupo5/huiapi/config/DBConfig.java` so it doesn't cause a conflict inserting the initial data.
```java
 @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            //categoryRepository.saveAll( getInitialCategories() );
            //userRepository.saveAll( getInitialUsers() );
            //eventRepository.saveAll( getInitialEvents() );
        };
    }
```

