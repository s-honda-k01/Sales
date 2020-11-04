DROP TABLE TR_MEMO_SHARE_MEMBER CASCADE CONSTRAINTS;
DROP TABLE TR_MEMO CASCADE CONSTRAINTS;
DROP TABLE TR_SALES_DETAIL CASCADE CONSTRAINTS;
DROP TABLE TR_SALES_OUTLINE CASCADE CONSTRAINTS;
DROP TABLE MT_CUSTOMER CASCADE CONSTRAINTS;
DROP TABLE MT_ITEM CASCADE CONSTRAINTS;
DROP TABLE MT_ITEM_GENRE CASCADE CONSTRAINTS;
DROP TABLE MT_USER CASCADE CONSTRAINTS;
DROP TABLE MT_ROLE CASCADE CONSTRAINTS;

CREATE TABLE MT_ROLE(
		role_code                     		VARCHAR2(2)		 NOT NULL		 PRIMARY KEY,
		role_name                     		VARCHAR2(50)		 NOT NULL,
		explain                       		VARCHAR2(64)		 NULL 
);


CREATE TABLE MT_USER(
		user_code                     		VARCHAR2(8)		 NOT NULL		 PRIMARY KEY,
		user_name                     		VARCHAR2(100)		 NOT NULL,
		password                      		VARCHAR2(100)		 NOT NULL,
		role_code                     		VARCHAR2(2)		 NOT NULL,
		validity                      		NUMBER(1)		 NOT NULL,
  FOREIGN KEY (role_code) REFERENCES MT_ROLE (role_code)
);


CREATE TABLE MT_ITEM_GENRE(
		item_genre_code               		VARCHAR2(8)		 NOT NULL		 PRIMARY KEY,
		item_genre_name               		VARCHAR2(50)		 NOT NULL
);


CREATE TABLE MT_ITEM(
		item_code                     		VARCHAR2(8)		 NOT NULL		 PRIMARY KEY,
		item_name                     		VARCHAR2(100)		 NOT NULL,
		item_genre_code               		VARCHAR2(8)		 NOT NULL,
		spec                          		VARCHAR2(300)		 NULL ,
		price                         		NUMBER(7)		 NOT NULL,
		validity                      		NUMBER(1)		 NOT NULL,
  FOREIGN KEY (item_genre_code) REFERENCES MT_ITEM_GENRE (item_genre_code)
);


CREATE TABLE MT_CUSTOMER(
		customer_code                 		VARCHAR2(8)		 NOT NULL		 PRIMARY KEY,
		customer_name                 		VARCHAR2(100)		 NOT NULL,
		validity                      		NUMBER(1)		 NOT NULL
);


CREATE TABLE TR_SALES_OUTLINE(
		sales_id                      		NUMBER(8)		 DEFAULT 1		 NOT NULL		 PRIMARY KEY,
		customer_code                 		VARCHAR2(8)		 NOT NULL,
		user_code                     		VARCHAR2(8)		 NOT NULL,
		sale_date                     		DATE		 NOT NULL,
  FOREIGN KEY (user_code) REFERENCES MT_USER (user_code),
  FOREIGN KEY (customer_code) REFERENCES MT_CUSTOMER (customer_code)
);

DROP SEQUENCE TR_SALES_O_sales_id_SEQ;
CREATE SEQUENCE TR_SALES_O_sales_id_SEQ NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TRIGGER TR_SALES_O_sales_id_TRG
BEFORE INSERT ON TR_SALES_OUTLINE
FOR EACH ROW
BEGIN
IF :NEW.sales_id IS NULL THEN
  SELECT TR_SALES_O_sales_id_SEQ.NEXTVAL INTO :NEW.sales_id FROM DUAL;
END IF;
END;
/

CREATE TABLE TR_SALES_DETAIL(
		detail_id                     		NUMBER(10)		 DEFAULT 1		 NOT NULL		 PRIMARY KEY,
		sales_id                      		NUMBER(8)		 NOT NULL,
		item_code                     		VARCHAR2(8)		 NOT NULL,
		quantity                      		NUMBER(4)		 NOT NULL,
		sales_price                   		NUMBER(7)		 NOT NULL,
  FOREIGN KEY (sales_id) REFERENCES TR_SALES_OUTLINE (sales_id),
  FOREIGN KEY (item_code) REFERENCES MT_ITEM (item_code)
);

DROP SEQUENCE TR_SALES_detail_id_SEQ;
CREATE SEQUENCE TR_SALES_detail_id_SEQ NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TRIGGER TR_SALES_detail_id_TRG
BEFORE INSERT ON TR_SALES_DETAIL
FOR EACH ROW
BEGIN
IF :NEW.detail_id IS NULL THEN
  SELECT TR_SALES_detail_id_SEQ.NEXTVAL INTO :NEW.detail_id FROM DUAL;
END IF;
END;
/

CREATE TABLE TR_MEMO(
		memo_id                       		NUMBER(8)		 DEFAULT 1		 NOT NULL		 PRIMARY KEY,
		user_code                     		VARCHAR2(8)		 NOT NULL,
		title                         		VARCHAR2(300)		 NULL ,
		content                       		VARCHAR2(1000)		 NOT NULL,
		edit_date                     		DATE		 NOT NULL,
		parent_memo_id                		NUMBER(8)		 NULL ,
		validity                      		NUMBER(1)		 NOT NULL,
  FOREIGN KEY (user_code) REFERENCES MT_USER (user_code)
);

DROP SEQUENCE TR_MEMO_memo_id_SEQ;

CREATE SEQUENCE TR_MEMO_memo_id_SEQ NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TRIGGER TR_MEMO_memo_id_TRG
BEFORE INSERT ON TR_MEMO
FOR EACH ROW
BEGIN
IF :NEW.memo_id IS NULL THEN
  SELECT TR_MEMO_memo_id_SEQ.NEXTVAL INTO :NEW.memo_id FROM DUAL;
END IF;
END;
/

CREATE TABLE TR_MEMO_SHARE_MEMBER(
		share_member_id               		NUMBER(8)		 DEFAULT 1		 NOT NULL		 PRIMARY KEY,
		memo_id                       		NUMBER(8)		 NOT NULL,
		user_code                     		VARCHAR2(8)		 NOT NULL,
  FOREIGN KEY (memo_id) REFERENCES TR_MEMO (memo_id),
  FOREIGN KEY (user_code) REFERENCES MT_USER (user_code)
);

DROP SEQUENCE TR_MSM_share_member_id_SEQ;

CREATE SEQUENCE TR_MSM_share_member_id_SEQ NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TRIGGER TR_MSM_share_member_id_TRG
BEFORE INSERT ON TR_MEMO_SHARE_MEMBER
FOR EACH ROW
BEGIN
IF :NEW.share_member_id IS NULL THEN
  SELECT TR_MSM_share_member_id_SEQ.NEXTVAL INTO :NEW.share_member_id FROM DUAL;
END IF;
END;
/

commit;




