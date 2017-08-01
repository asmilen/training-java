Training::Java
===============
`Training::Java` là bản mẫu (boilerplate) phục vụ cho công tác training Java.  
Bạn có thể tự do lựa chọn framework (Spring, Struts, Wicket, JSF...) cũng như bất kỳ thư viện nào cần thiết. 
Project này đơn thuần chỉ thiết lập trước một **Gialb-CI pipeline** tự động chạy *test* đồng thời *deploy* mỗi khi có commit được *push* lên `master`.

## Setup
### Setup project
* Fork this project: [dung.dm/training-java](http://git.teko.vn/dung.dm/training-java)

![Fork project](https://farm5.staticflickr.com/4292/36111959386_235ab6dbd5_b.jpg)

Sau khi fork, bạn có thể đổi tên project & repo URL nếu muốn:

  1. Vào **Settings >  General**  
  2. Coi mục **Project settings** hoặc **Rename repository**

* Clone repo

```shell
$ git clone git@git.teko.vn:<username>/training-java.git
$ cd training-java
```

* Add *dung.dm*'s remote: (for keeping things up-to-date)

```shell
$ git remote add dung.dm http://git.teko.vn/dung.dm/training-java
```

### Setup development environment
Requirements:
1. Java 8+
2. Maven 3+
3. Tomcat 8+

#### Kiểm tra môi trường
```shell
### Check version
$ java -version
$ mvn --version

### Check install path
##### Linux
$ which java
$ which mvn
...

##### Windows (PowerShell)
$ Get-Command java
$ Get-Command mvn
...
```

Cài đặt các packages còn thiếu như sau:
#### Linux

```shell
### Install Java
$ sudo apt update
$ sudo apt install default-jdk  # OpenJDK

### Install mvn
$ sudo apt install maven
```

#### Windows
* *Java*:  Download Oracle Java [here](http://oracle.com/technetwork/java/javase/downloads/) & install
* *Maven*: Download Apache Maven [here](https://maven.apache.org/download.cgi) & extract
* Set environment variables như sau:
![Add environment variable](https://farm5.staticflickr.com/4308/36111377126_da1f60ea15_b.jpg)

Ví dụ:  
```
JAVA_HOME:  C:\Program Files\Java\jdk1.8.0_121
MAVEN_HOME: C:\Program Files\Apache\apache-maven-3.5.0
PATH:       %PATH%;%JAVA_HOME%\bin;%MAVEN_HOME%\bin
```

#### Install Tomcat
Download Tomcat [here](https://tomcat.apache.org/download-80.cgi) & extract

## Run & test :gear:
### Run
#### 1. Run using command line
```shell
$ mvn jetty:run
--or--
$ mvn jetty:run -Djetty.http.port=9999
```

**Cách khác**: Download [webapp runner](https://mvnrepository.com/artifact/com.github.jsimone/webapp-runner) (tomcat base) 
hoặc [jetty runner](https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-runner)
rồi làm theo hướng dẫn tại [1](https://devcenter.heroku.com/articles/java-webapp-runner)
hoăc [2](https://devcenter.heroku.com/articles/deploy-a-java-web-application-that-launches-with-jetty-runner)

#### 2. Run in your IDE
* JetBrains IntelliJ:  
Tạo Run/Debug Configurations như sau:
![Setup Tomcat in IntelliJ](https://farm5.staticflickr.com/4329/36111376946_1ba5fc35bd_b.jpg)

#### 3. Run inside Tomcat
```
$ mvn package

# Copy your war file into Tomcat's webapps folder 
$ cp ./target/application.war ${TOMCAT_HOME}/webapps

# Start server
$ ${TOMCAT_HOME}/bin/startup

# Open this URL in your browser
$ localhost:8080/application
```

### Test
```shell
### JUnit test
$ mvn test
```

## Folder structure
Project này đã được config sẵn cho việc auto test & auto deploy, nên có những file bạn ko nên tự ý sửa/xóa nếu chưa hiểu rõ bản chất của nó.
* `.gitlab-ci.yml`:  
File định nghĩa pipeline, sử dụng bởi Gitlab-CI. KHÔNG nên động vào :smirk:
* `ansible/`:  
Folder chứa script deploy app lên production. Để nguyên nó đấy :sunglasses:
* `pom.xml`, folders `src/`:  
Bạn thêm/sửa/xóa file thoải mái, song nên cần thận khi đổi tên (rename/move).  

**Important**: Contact [DungDM](https://teko.facebook.com/profile.php?id=100015907001998) để biết thêm thông tin chi tiết :cowboy:

## App Config and Database
### App Config
Trên production, app config nên được load từ file `.env`. 
File này sẽ auto-generated trong quá trình deploy dựa vào biến môi trường (`environment variables`) và template file `.env.j2`.  
Vậy để thêm một config ta cần làm 2 việc như sau:

### 1. Define environment variables
Có một số chỗ ta có thể định nghĩa environment variables như sau:
* Secret Variables: Định nghĩa các variables cần tính bảo mật cao như password, secret keys,...  
  **Settings** > **CI/CD Pipelines** > Coi mục "**Secret Variables**"
* Global variables: Các variables sẽ share giữa các CI jobs.  
  Top-level session `variables` trong file `.gitlab-ci.yml` (Giống như `HOST_URL`)
* Job variables: Những variables chỉ xuất hiện trong job tương ứng.  
  Job-level session `variables` trong file `.gitlab-ci.yml` (Giống như `MYSQL_DATABASE` của job `test:phpunit`)

### 2. Write `.env.j2` template
Template được viết dựa trên [Jinja2](http://jinja.pocoo.org/) template engine.
Tóm tắt:
* `{% ... %}` for [Statements](http://jinja.pocoo.org/docs/2.9/templates/#list-of-control-structures), VD:
    ```
    {% for user in users %}
        ...
    {% endfor %}
    
    {% if users is admin %}
        ...
    {% endif %}
    ```

* `{{ ... }}` for [Expressions](http://jinja.pocoo.org/docs/2.9/templates/#expressions), VD:
    ```
    MYSQL_USERNAME="{{ db_user }}"
    app_name="{{ lookup('env', 'CI_PROJECT_NAME') }}"          # Environment variables
    {{ foo.bar }}
    {{ foo['bar'] }}
    ```

* [Filters](http://jinja.pocoo.org/docs/2.9/templates/#builtin-filters) use pipe `|` annotation, VD:
    ```
    {{ name | striptags | title }}
    {{ listx | join(', ') }}
    ```

Tham khảo [Jinja2 Designer Documentation](http://jinja.pocoo.org/docs/2.9/templates/)

### Database
#### 1. Database for Test
Sử dụng Gitlab-CI services:
```
# .gitlab-ci.yml
test:phpunit:
  services:
    - mysql:5.7
  variables:
    MYSQL_HOST: "mysql"
    MYSQL_PORT: "3306"
    MYSQL_DATABASE:      "app"
    MYSQL_ROOT_PASSWORD: "supersecret"
  ...
```

#### 2. Database for Live
Database sẽ tự động cấp phát khi variable `REQUIRE_DB = <db_type>` được assign.
Hiện tại `db_type` mới chỉ support `MySQL` (chú ý hoa thường).
```
# .gitlab-ci.yml
deploy:production:
  variables:
    REQUIRE_DB: MySQL
    DATABASE_PASSWORD: foobar     # default qwerty
  ...
```

Sau deploy, các configs sẽ được assign vào file `.env`, VD:
```
# .env
DB_CONNECTION=mysql
DB_HOST=localhost
DB_PORT=3306
DB_USERNAME=dung.dm
DB_PASSWORD=foobar
DB_DATABASE=dungdm_trainingphp
```

Xem `.env.j2` for template

## Others
### Get Deploy URL
Sau khi deploy xong bạn có thể tận hưởng thành quả của mình bằng cách:  
**Pipelines** > **Environments** > Click "**Open**" button bên cạnh *Environment name*.

![Get Deploy URL](https://farm5.staticflickr.com/4049/35727650805_894d01af99_z.jpg)

### Connect to Live Database

```shell
$ mysql -h '35.186.148.164' -u <username> -p <dbname>
```

Trong đó:
* `<username>`: tên tài khoản GitLab (VD: `dung.dm`)
* `<dbname>`: tên database, theo quy tắc `dbname = username.replace('\W+','')_projectname.replace('\W+','')`. Tức:
  * Bỏ tất cả các ký tự đặc biệt trong `username` và `projectname`
  * `username` và `projectname` ghép với nhau bởi dấu gạch dưới `_`  

VD: `username = dung.dm`, `projectname = training-php` ⮕ `dbname = dungdm_trainingphp`
