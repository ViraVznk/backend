--DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Store_Product;
DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS Customer_Card;
DROP TABLE IF EXISTS "Check";
DROP TABLE IF EXISTS Sale;

CREATE TABLE IF NOT EXISTS Category (
    category_number INT          NOT NULL AUTO_INCREMENT,
    category_name   VARCHAR(50)  NOT NULL,
    CONSTRAINT PK_Category PRIMARY KEY (category_number)
    );

CREATE TABLE Product (
    id_product      INT           NOT NULL AUTO_INCREMENT,
    category_number INT           NOT NULL,
    product_name    VARCHAR(50)   NOT NULL,
    manufacturer    VARCHAR(50)   NOT NULL,
    characteristics VARCHAR(100)  NOT NULL,
    CONSTRAINT PK_Product      PRIMARY KEY (id_product),
    CONSTRAINT FK_Prod_Cat     FOREIGN KEY (category_number)
        REFERENCES Category(category_number)
        ON UPDATE CASCADE
        ON DELETE NO ACTION
    );

CREATE TABLE Store_Product (
    UPC                 VARCHAR(12)    NOT NULL,
    UPC_prom            VARCHAR(12)    NULL,
    id_product          INT            NOT NULL,
    selling_price       DECIMAL(13, 4) NOT NULL CHECK (selling_price >= 0),
    products_number     INT            NOT NULL CHECK (products_number >= 0),
    promotional_product BOOLEAN        NOT NULL DEFAULT FALSE,
    CONSTRAINT PK_StoreProduct  PRIMARY KEY (UPC),
    CONSTRAINT FK_SP_Prom       FOREIGN KEY (UPC_prom)
        REFERENCES Store_Product(UPC)
        ON UPDATE CASCADE
        ON DELETE SET NULL,

    CONSTRAINT FK_SP_Product    FOREIGN KEY (id_product)
        REFERENCES Product(id_product)
        ON UPDATE CASCADE
        ON DELETE NO ACTION
    );

CREATE TABLE Employee (
    id_employee      VARCHAR(10)    NOT NULL,
    empl_surname     VARCHAR(50)    NOT NULL,
    empl_name        VARCHAR(50)    NOT NULL,
    empl_patronymic  VARCHAR(50)    NULL,
    empl_role        VARCHAR(10)    NOT NULL,   -- 'Manager' | 'Cashier'
    salary           DECIMAL(13, 4) NOT NULL CHECK (salary >= 0),
    date_of_birth    DATE           NOT NULL,
    date_of_start    DATE           NOT NULL,
    phone_number     VARCHAR(13)    NOT NULL,
    city             VARCHAR(50)    NOT NULL,
    street           VARCHAR(50)    NOT NULL,
    zip_code         VARCHAR(9)     NOT NULL,
    CONSTRAINT PK_Employee PRIMARY KEY (id_employee),
    CONSTRAINT CHK_Emp_Age CHECK (DATEDIFF('YEAR', date_of_birth, CURRENT_DATE) >= 18)
    );

CREATE TABLE Customer_Card (
    card_number     VARCHAR(13)    NOT NULL,
    cust_surname    VARCHAR(50)    NOT NULL,
    cust_name       VARCHAR(50)    NOT NULL,
    cust_patronymic VARCHAR(50)    NULL,
    phone_number    VARCHAR(13)    NOT NULL,
    city            VARCHAR(50)    NULL,
    street          VARCHAR(50)    NULL,
    zip_code        VARCHAR(9)     NULL,
    percent         INT            NOT NULL CHECK (percent >= 0),
    CONSTRAINT PK_CustomerCard PRIMARY KEY (card_number)
    );

CREATE TABLE "Check" (
    check_number VARCHAR(10)    NOT NULL,
    id_employee  VARCHAR(10)    NOT NULL,
    card_number  VARCHAR(13)    NULL,
    print_date   TIMESTAMP      NOT NULL,
    sum_total    DECIMAL(13, 4) NOT NULL CHECK (sum_total >= 0),
    vat          DECIMAL(13, 4) NOT NULL CHECK (vat >= 0),
    CONSTRAINT PK_Check      PRIMARY KEY (check_number),
    CONSTRAINT FK_Chk_Emp    FOREIGN KEY (id_employee)
    REFERENCES Employee(id_employee)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,
    CONSTRAINT FK_Chk_Card   FOREIGN KEY (card_number)
    REFERENCES Customer_Card(card_number)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
    );

CREATE TABLE Sale (
    UPC          VARCHAR(12)    NOT NULL,
    check_number VARCHAR(10)    NOT NULL,
    product_number INT          NOT NULL CHECK (product_number > 0), --кількість
    selling_price  DECIMAL(13, 4) NOT NULL CHECK (selling_price >= 0),
    CONSTRAINT PPK_Sale   PRIMARY KEY (UPC, check_number),
    CONSTRAINT FK_Sale_SP FOREIGN KEY (UPC)
    REFERENCES Store_Product(UPC)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,
    CONSTRAINT FK_Sale_Chk FOREIGN KEY (check_number)
    REFERENCES "Check"(check_number)
    ON UPDATE CASCADE
    ON DELETE CASCADE
    );