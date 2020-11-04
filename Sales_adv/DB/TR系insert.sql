insert into TR_MEMO values(TR_MEMO_memo_id_SEQ.NEXTVAL,'u002','ManagerTest','ManagerTest',sysdate,null,1);
insert into TR_MEMO values(TR_MEMO_memo_id_SEQ.NEXTVAL,'u003','StaffTest','StaffTest',sysdate,null,1);
insert into TR_MEMO values(TR_MEMO_memo_id_SEQ.NEXTVAL,'u003','Re:ManagerTest','Re:ManagerTest',sysdate,1,1);


insert into TR_MEMO_SHARE_MEMBER values(TR_MSM_share_member_id_SEQ.NEXTVAL,1,'u003');
insert into TR_MEMO_SHARE_MEMBER values(TR_MSM_share_member_id_SEQ.NEXTVAL,1,'u004');
insert into TR_MEMO_SHARE_MEMBER values(TR_MSM_share_member_id_SEQ.NEXTVAL,3,'u002');
insert into TR_MEMO_SHARE_MEMBER values(TR_MSM_share_member_id_SEQ.NEXTVAL,3,'u004');


insert into TR_SALES_OUTLINE values(TR_SALES_O_sales_id_SEQ.NEXTVAL,'C0000001','u003',sysdate);
insert into TR_SALES_DETAIL values(TR_SALES_detail_id_SEQ.NEXTVAL,TR_SALES_O_sales_id_SEQ.CURRVAL,'00000001',1,3128);
insert into TR_SALES_DETAIL values(TR_SALES_detail_id_SEQ.NEXTVAL,TR_SALES_O_sales_id_SEQ.CURRVAL,'00000004',2,298);

insert into TR_SALES_OUTLINE values(TR_SALES_O_sales_id_SEQ.NEXTVAL,'C0000002','u004',sysdate);
insert into TR_SALES_DETAIL values(TR_SALES_detail_id_SEQ.NEXTVAL,TR_SALES_O_sales_id_SEQ.CURRVAL,'00000005',1,1478);
insert into TR_SALES_DETAIL values(TR_SALES_detail_id_SEQ.NEXTVAL,TR_SALES_O_sales_id_SEQ.CURRVAL,'00000006',10,878);


commit;