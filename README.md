# gamesales

### 1. You can clone it from github by running following command

```
  $ git clone https://github.com/johnnyzhong123456/gamesales.git
```
### 2. Import project into eclipse
```
  File -> Import -> Maven -> Existing Maven Projects -> Browse Project from cloned location
```
### 3. Right click on project in eclipse and then Maven -> Update Projects and Maven build the project

### 4. Import src/main/java/resources/scripts/scripts.sql into MySQL database. Alternative way just start the application . it will automatically create the tables

### 5. Update database credential and other configuration into application.properties if needed for connected your own mysql & redis db.

```
spring.application.name=gamesales
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=admin123
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
logging.level.org.springframework.web=DEBUG

#Redis 
spring.redis.host=localhost
spring.redis.port=6379

#allow the large file to upload 
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB



# HikariCP settings
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.max-lifetime=1800000


```
### 6. Right click on Application.java file and run as Java Application

### 7. Prepared the csv file with 1,000,000 rows , by run the below unit testing case 
```
@Test
	void genearteCSV() throws IOException {
		CsvUtil.generateCsv(CSV_FILE_NAME, 1000000);
	}
```


### 8.Use the following restful endpoint in postman to load the CSV with 1,000,000 rows into mysql db

```
  http://localhost:8080/api/import
```


### 9. To get list of game sales 
```
 http://localhost:8080/api/getGameSales
```


### 10. To get list of game sales during a given period

```
  http://localhost:8080/api/getTotalSales
```

### Note - check the attached test doc api_test_result.doc for testing result of above restful endpoint.

