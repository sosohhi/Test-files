-- 고객 테이블
CREATE TABLE Customers(
	CustomerID  	VARCHAR2(20)	PRIMARY KEY,
	CustomerPW		VARCHAR2(40)	NOT NULL,
	CustomerName	VARCHAR2(20)	NOT NULL,
	Phone			VARCHAR2(20)	NOT NULL,
	Email			VARCHAR2(100),
	Address			VARCHAR2(200),
	City			VARCHAR2(50),
	SignUpdate		DATE			DEFAULT SYSDATE
);
--DROP TABLE Customers;
COMMENT ON COLUMN Customers.CustomerID IS '고객 아이디';
COMMENT ON COLUMN Customers.CustomerPW IS '고객 비밀번호';
COMMENT ON COLUMN Customers.CustomerName IS '고객 이름';
COMMENT ON COLUMN Customers.Phone IS '핸드폰번호';
COMMENT ON COLUMN Customers.Email IS 'Email주소';
COMMENT ON COLUMN Customers.Address IS '주소';
COMMENT ON COLUMN Customers.City IS '도시';
COMMENT ON COLUMN Customers.SignUpdate IS '가입날짜';

-- 카테고리 테이블
 CREATE TABLE Categories (
 	CategoryID		VARCHAR2(50)	PRIMARY KEY,
 	CategoryName	VARCHAR2(100)
 );
COMMENT ON COLUMN Categories.CategoryID IS '카테고리 아이디';
COMMENT ON COLUMN Categories.CategoryName IS '카테고리 이름';
--DROP TABLE  Categories;

-- 상품 테이블
CREATE TABLE Products(
	ProductID		VARCHAR2(200)	PRIMARY KEY,
	ProductName		varchar2(50)	NOT NULL,
	ProductOption	varchar2(100),
	Price			NUMBER			NOT NULL,
	Stock			NUMBER			DEFAULT 0,
	CategoryID 		VARCHAR2(50),
	FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);

COMMENT ON COLUMN Products.ProductID IS '상품 아이디';
COMMENT ON COLUMN Products.ProductName IS '상품 이름';
COMMENT ON COLUMN Products.ProductOption IS '상품 옵션';
COMMENT ON COLUMN Products.Price IS '상품 가격';
COMMENT ON COLUMN Products.Stock IS '재고 수량';
COMMENT ON COLUMN Products.CategoryID IS '카테고리 아이디';

-- 주문 테이블
CREATE TABLE Orders(
	OrderID			VARCHAR2(200)	PRIMARY KEY,
	CustomerID		VARCHAR2(20)	NOT NULL,
	OrderDate		DATE			NOT NULL,
	Status			VARCHAR2(50)	NOT NULL,
	TotalAmount		NUMBER,
	ShippingAddress VARCHAR2(200),
	ShippingCity    VARCHAR2(50),
	FOREIGN KEY (CustomerID)	REFERENCES Customers(CustomerID) 
);

COMMENT ON COLUMN Orders.OrderID IS '주문 아이디';
COMMENT ON COLUMN Orders.CustomerID IS '고객 아이디';
COMMENT ON COLUMN Orders.OrderDate IS '주문 날짜';
COMMENT ON COLUMN Orders.Status IS '주문 상태';
COMMENT ON COLUMN Orders.TotalAmount IS '총 금액';
COMMENT ON COLUMN Orders.ShippingAddress IS '배송 주소';
COMMENT ON COLUMN Orders.ShippingCity IS '배송 도시';

-- 상세주문 테이블
CREATE TABLE OrderDetails(
	OrderDetailID	NUMBER			PRIMARY KEY,
	OrderID			VARCHAR2(200)	NOT NULL,
	ProductID		VARCHAR2(200)	NOT NULL,
	Quantity		NUMBER			NOT NULL,
	Price			NUMBER			NOT NULL,
	FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

COMMENT ON COLUMN OrderDetails.OrderDetailID IS '상세 주문 아이디';
COMMENT ON COLUMN OrderDetails.OrderID IS '주문 아이디';
COMMENT ON COLUMN OrderDetails.ProductID IS '상품 아이디';
COMMENT ON COLUMN OrderDetails.Quantity IS '주문 수량';
COMMENT ON COLUMN OrderDetails.Price IS '상품 가격';

CREATE TABLE Payments(
	PaymentID		VARCHAR2(50)	PRIMARY KEY,
	OrderID			VARCHAR2(200)	NOT NULL,
	PaymentDate		DATE			NOT NULL,
	Amount			NUMBER			NOT NULL,
	PaymentMethod	VARCHAR2(50)	NOT NULL,
	FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
);
--DROP TABLE Payments;

COMMENT ON COLUMN Payments.PaymentID IS '결제 아이디';
COMMENT ON COLUMN Payments.OrderID IS '주문 아이디';
COMMENT ON COLUMN Payments.PaymentDate IS '결제 날짜';
COMMENT ON COLUMN Payments.Amount IS '결제 금액';
COMMENT ON COLUMN Payments.PaymentMethod IS '결제 방법';

CREATE TABLE Shipments(
	ShipmentID		VARCHAR2(50)	PRIMARY KEY,
	OrderID		    VARCHAR2(200)	NOT NULL,
	ShipmentDate	DATE			NOT NULL,
	Carrier			VARCHAR2(100)	NOT NULL,
	TrackingNumber 	VARCHAR2(100)	NOT NULL,
	FOREIGN KEY (OrderID)	REFERENCES Orders(OrderID)
);

COMMENT ON COLUMN Shipments.ShipmentID IS '배송 아이디';
COMMENT ON COLUMN Shipments.OrderID IS '주문 아이디';
COMMENT ON COLUMN Shipments.ShipmentDate IS '배송 날짜';
COMMENT ON COLUMN Shipments.Carrier IS '운송 업체';
COMMENT ON COLUMN Shipments.TrackingNumber IS '운송장 번호';

CREATE TABLE Reviews(
	ReviewID		NUMBER			PRIMARY KEY,
	ProductID		VARCHAR2(200)	NOT NULL,
	CustomerID		VARCHAR2(20)	NOT NULL,	
	Rating			NUMBER CHECK (Rating >=1 AND Rating <=5),
	Comments		CLOB,
	ReviewDate		DATE			DEFAULT SYSDATE,
	FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

COMMENT ON COLUMN Reviews.ReviewID IS '리뷰 아이디';
COMMENT ON COLUMN Reviews.ProductID IS '상품 아이디';
COMMENT ON COLUMN Reviews.CustomerID IS '고객 아이디';
COMMENT ON COLUMN Reviews.Rating IS '평점';
COMMENT ON COLUMN Reviews.Comments IS '리뷰 내용';
COMMENT ON COLUMN Reviews.ReviewDate IS '리뷰 날짜';

CREATE OR REPLACE TRIGGER trg_update_total_amount
AFTER INSERT OR UPDATE OR DELETE ON OrderDetails
FOR EACH ROW
BEGIN
    DECLARE
        v_total_amount NUMBER;
    BEGIN
        -- 총 금액 계산 및 업데이트
        SELECT SUM(Quantity * Price)
        INTO v_total_amount
        FROM OrderDetails
        WHERE OrderID = :NEW.OrderID;

        UPDATE Orders
        SET TotalAmount = v_total_amount
        WHERE OrderID = :NEW.OrderID;
    END;
END;

CREATE OR REPLACE TRIGGER trg_set_default_address
BEFORE INSERT ON Orders
FOR EACH ROW
BEGIN
    IF :NEW.ShippingAddress IS NULL THEN
        SELECT Address, City INTO :NEW.ShippingAddress, :NEW.ShippingCity
        FROM Customers
        WHERE CustomerID = :NEW.CustomerID;
    END IF;
END;

CREATE SEQUENCE seq_order_detail_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_review_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_shipment_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_payment_id START WITH 1 INCREMENT BY 1;