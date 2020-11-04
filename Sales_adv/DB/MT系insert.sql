insert into MT_ROLE values('1','admin','システム管理者');
insert into MT_ROLE values('2','manager','課長');
insert into MT_ROLE values('3','staff','課員');

insert into MT_ITEM_GENRE values('00000001','コピー用紙');
insert into MT_ITEM_GENRE values('00000002','クリアファイル');
insert into MT_ITEM_GENRE values('00000003','付箋');


insert into MT_ITEM values('00000001','コピー用紙A4_01','00000001','A4',3128,1);
insert into MT_ITEM values('00000002','コピー用紙A3_01','00000001','A3',3364,1);
insert into MT_ITEM values('00000003','コピー用紙B5_01','00000001','B5',2901,1);
insert into MT_ITEM values('00000004','クリアファイル_010','00000002','10枚パック',298,1);
insert into MT_ITEM values('00000005','クリアファイル_100','00000002','100枚パック',1478,1);
insert into MT_ITEM values('00000006','付箋_大_010','00000003','大',878,1);
insert into MT_ITEM values('00000007','付箋_中_010','00000003','中',678,1);
insert into MT_ITEM values('00000008','付箋_小_010','00000003','小',568,1);

insert into MT_CUSTOMER values('C0000001','新橋商店',1);
insert into MT_CUSTOMER values('C0000002','虎ノ門商事',1);
insert into MT_CUSTOMER values('C0000003','霞が関産業',1);

commit;