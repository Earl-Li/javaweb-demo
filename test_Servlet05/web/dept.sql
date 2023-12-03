drop table if exists t_Dept;
create table t_Dept(
	deptno int primary key,
    	dname varchar(255),
    	loc varchar(255)
);
insert into t_Dept(deptno, dname, loc) values(10, 'XiaoShouBu', 'BEIJING');
insert into t_Dept(deptno, dname, loc) values(20, 'YanFaBu', 'SHANGHAI');
insert into t_Dept(deptno, dname, loc) values(30, 'JiShuBu', 'GUANGZHOU');
insert into t_Dept(deptno, dname, loc) values(40, 'MeiTiBu', 'SHENZHEN');
commit;
select * from t_Dept;