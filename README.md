Java期末作业，客户管理系统。

添加外部库：
![image](https://github.com/LazyHaha1023/-/assets/154957574/e5ec1d2c-cbd4-43c8-be79-16a5670a93a4)

数据库表结构：

![image](https://github.com/LazyHaha1023/-/assets/154957574/c5031b48-67da-4c27-9633-af886a454165)

![image](https://github.com/LazyHaha1023/-/assets/154957574/eaf86248-9109-424a-ac66-e1705fa8548d)

SQL建表语句：

CREATE TABLE customers (
    customer_id INT NOT NULL PRIMARY KEY,
    name VARCHAR(100),
    contact_info VARCHAR(100),
    contact_address VARCHAR(255),
    credit_rating INT,
    purchased_product VARCHAR(100),
    purchase_date DATETIME,
    contact_person_id INT
);

CREATE TABLE employees (
    employee_id INT NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    department VARCHAR(50) NOT NULL,
    product_id INT NOT NULL,
    task_plan_id INT NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    employee_type VARCHAR(20) NOT NULL,
    performance DECIMAL(10, 2) NOT NULL
);

CREATE TABLE products (
    product_id INT PRIMARY KEY,
    product_name NVARCHAR(100) NOT NULL,
    production_date DATE,
    product_type VARCHAR(50),
    price DECIMAL(10, 2)
);

CREATE TABLE roles (
    role_id INT PRIMARY KEY,
    role_name NVARCHAR(100) NOT NULL
);

CREATE TABLE task_plans (
    TaskPlanID INT PRIMARY KEY,
    ContactID INT,
    CustomerName NVARCHAR(100) NOT NULL,
    PlannedProfit DECIMAL(10, 2),
    PlannedTime DATE,
    IsCompleted TINYINT(1),
    Implementation NVARCHAR(255),
    FOREIGN KEY (ContactID) REFERENCES contacts(ContactID) -- 假设ContactID是外键，引用contacts表的ContactID字段
);

CREATE TABLE users (
    user_id INT PRIMARY KEY,
    username NVARCHAR(100) NOT NULL,
    password NVARCHAR(100) NOT NULL,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) -- 假设role_id是外键，引用roles表的role_id字段
);

