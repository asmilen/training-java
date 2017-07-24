Training::Java
===============
`Training::Java` là bản mẫu (boilerplate) phục vụ cho công tác training Java.  
Bạn có thể tự do lựa chọn framework (Spring, Struts, Wicket, JSF...) cũng như bất kỳ thư viện nào cần thiết. 
Project này đơn thuần chỉ thiết lập trước một **Gialb-CI pipeline** tự động chạy *test* đồng thời *deploy* mỗi khi có commit được *push* lên `master`.

## Setup
### Setup project
* Fork this project: [dung.dm/training-java](http://git.teko.vn/dung.dm/training-java)

![Fork project](https://farm5.staticflickr.com/4320/36046430855_cdff2965f3_b.jpg)

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
#### Install requirements:
1. Java 8+
2. Maven 3+

Kiểm tra môi trường:
```shell
### Check version
$ java -version
$ mvn --version

### Check install path
$ which java
$ which mvn
...
```

Cài đặt các packages còn thiếu như sau:
```shell
### Install Java
$ sudo apt update
$ sudo apt install default-jdk  # OpenJDK

### Install mvn
$ sudo apt install maven
```

#### Install Tomcat (Optional):
```shell
$ sudo apt install tomcat8
```

## Run & test :gear:
### Run
#### 1. Run with embedded Java web server
```shell
$ php -S localhost:8000 -t public
# -- or--
$ php artisan serve [--port=8000]   # for Laravel
```

#### 2. Run using Tomcat
Copy/paste `nginx-sites.conf.j2` template vào *nginx default site*:
```shell
$ sudo cp ansible/templates/nginx-sites.conf.j2 /etc/nginx/sites-available/default
```
Sau đó, sửa lại 1 số config như sau:
* `server_name {{app_name}}.{{username}}.*;` sửa thành `server_name _;`
* `{{app_root}}` sửa thành đường dẫn **tuyệt đối** của project.

Restart nginx
```shell
$ sudo service nginx restart
```

### Test
```shell
### Unit test
$ mvn test

### Pytest with coverage
$ phpunit --coverage-text
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
