CREATE TABLE employeesManager(
	seq				NUMBER 				PRIMARY KEY,		-- 등록번호
	name			varchar2(60)		NOT NULL,			-- 성명
	age				NUMBER,									-- 나이
	address			varchar2(100)		NOT NULL,			-- 거주지
	phoneNum		char(11)			NOT NULL,			-- 연락처
	registrasionNo	varchar2(60)		NOT NULL,			-- 주민/외국인 등록번호
	country			varchar2(60)		NOT NULL,			-- 국적
	passportNo		varchar2(60),							-- 여권번호
	status			varchar2(30),							-- 체류자격
	stayin			varchar2(30),							-- 체류기간
	startdate		DATE,									-- 고용일자
	enddate			DATE,									-- 고용만료일자
	hourlywave		NUMBER,									-- 시급
	career			CLOB									-- 관련경력
);




--DROP TABLE employeesManager;

-- comment 등록 sql
COMMENT ON COLUMN employeesManager.seq IS '등록번호';
COMMENT ON COLUMN employeesManager.name IS '성명';
COMMENT ON COLUMN employeesManager.age IS '나이';
COMMENT ON COLUMN employeesManager.address IS '거주지';
COMMENT ON COLUMN employeesManager.phoneNum IS '연락처';
COMMENT ON COLUMN employeesManager.registrasionNO IS '주민/외국인 등록번호';
COMMENT ON COLUMN employeesManager.country IS '국적';
COMMENT ON COLUMN employeesManager.passportNo IS '여권번호';
COMMENT ON COLUMN employeesManager.status IS '체류자격';
COMMENT ON COLUMN employeesManager.stayin IS '체류기간';
COMMENT ON COLUMN employeesManager.startdate IS '고용일자';
COMMENT ON COLUMN employeesManager.enddate IS '고용만료일자';
COMMENT ON COLUMN employeesManager.hourlywave IS '시급';
COMMENT ON COLUMN employeesManager.career IS '경력';



--DROP SEQUENCE seq_Regidence_employees_no;
-- sequence생성(자동순서생성)
CREATE SEQUENCE seq_employeesManager_no 
	   increment BY 1 
	   START WITH 1
;

--DELETE board;
--INSERT INTO employeesManager(seq,name,address,phone_Num,registrasionNo,country, status,start_date,houly_wave) 
--VALUES (seq_employeesManager_no.NEXTVAL,'비버','한국어딘가',12345678
--,'12345-12345', '일본','D-8',sysdate,15000);






CREATE TABLE monthlySalary
(	
	seq						NUMBER PRIMARY KEY,
	employeesManager_seq	NUMBER,
	year					NUMBER,
	month					NUMBER,
	totalSalary				NUMBER,
	CONSTRAINT fk_monthly_emp FOREIGN KEY(employeesManager_seq) REFERENCES employeesManager(seq)
);

--DROP TABLE monthlySalary;


COMMENT ON COLUMN monthlySalary.seq IS '등록번호';
COMMENT ON COLUMN monthlySalary.employeesManager_seq IS 'employeesManager_seq';
COMMENT ON COLUMN monthlySalary.year IS '연도';
COMMENT ON COLUMN monthlySalary.month IS '달';
COMMENT ON COLUMN monthlySalary.totalSalary IS '급여';

--DROP SEQUENCE seq_calculator_no;

CREATE SEQUENCE seq_month_no 
	   increment BY 1 
	   START WITH 1
;



CREATE TABLE Supervisor(
	adminID		varchar2(20),	
	adminPW		varchar2(30)
);
