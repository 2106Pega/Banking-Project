
-- bankApp project 0 




create table if not exists users(
	user_id serial primary key,
	user_name varchar(30) unique not NULL,
    first_name VARCHAR(40) NOT NULL,
    last_name VARCHAR(20) NOT null
);

create table if not exists employees(
	user_id serial primary key,
	user_name varchar(30) unique not NULL,
    passwords VARCHAR(40) NOT NULL
);

select * from employees;
select * from employees where user_name = 'Ben1' and passwords = 'yy1';
insert into employees (user_name, passwords) values('Ben1', 'yy1');

create table if not exists accounts(
	accounts_id serial primary key,
	account_name varchar(30) not null,
	account_banlance Decimal,
	account_approved boolean not null,
	user_id int references users(user_id)
);

create table if not exists transactions(
	trans_id serial primary key,
	trans_date date not null,
	trans_description varchar(30),
	trans_type varchar(10) not null,
	trans_amount Decimal not null,
	trans_banlance Decimal not null,
	account_id int references accounts(accounts_id)
); 



drop table users;
drop table accounts;
drop table transactions;

drop table accounts;


select * from accounts;

select * from users;

select * from users where user_name = 'bang123';


insert into users (user_name, first_name,last_name) 
values('james123','Benjamin', 'Yuan');

insert into users (user_name, first_name,last_name) 
values('bang123','Benjamin', 'Yuan' );

update accounts set account_approved = true where accounts_id = 16 ;

insert into accounts (account_name,account_banlance,account_approved,user_id) 
values('savings123','0',false, 1),
		('svings124','100',false,2);
	
	insert into accounts (account_name,account_banlance,account_approved,user_id) 
values('checking123','0',false, 1),
		('checking','100',false,1);
delete from accounts;





