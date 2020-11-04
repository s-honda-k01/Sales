DROP TABLE TR_SALES_DETAIL CASCADE CONSTRAINTS;
DROP TABLE TR_SALES_OUTLINE CASCADE CONSTRAINTS;

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

insert into TR_SALES_OUTLINE values(TR_SALES_O_sales_id_SEQ.NEXTVAL,'C0000001','u003',sysdate);
insert into TR_SALES_OUTLINE values(TR_SALES_O_sales_id_SEQ.NEXTVAL,'C0000002','u004',sysdate);

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

insert into TR_SALES_DETAIL values(TR_SALES_detail_id_SEQ.NEXTVAL,1,'00000001',1,3128);
insert into TR_SALES_DETAIL values(TR_SALES_detail_id_SEQ.NEXTVAL,1,'00000004',2,298);
insert into TR_SALES_DETAIL values(TR_SALES_detail_id_SEQ.NEXTVAL,2,'00000005',1,1478);
insert into TR_SALES_DETAIL values(TR_SALES_detail_id_SEQ.NEXTVAL,2,'00000006',10,878);

commit;


