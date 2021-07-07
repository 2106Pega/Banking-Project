drop table if exists bank_accounts;

drop table if exists users;



create table users (
	user_name		varchar(30)		primary key,
	pass_word		varchar(30)		not null,
	first_name		varchar(30)		not null,
	last_name		varchar(30)		not null,
	is_employee		boolean			default false
);

create table bank_accounts (
	account_id		serial			primary key,
	u_user_name		varchar(30)		references users(user_name),
	account_name	varchar(30)		not null,
	balance			decimal			default 0.00,
	is_approved		boolean			default false
);



-- Create java_user and initialize privileges.
--create role java_user LOGIN password 'p4ssword';
grant all privileges on table users to java_user;
grant all privileges on table bank_accounts to java_user;
grant all privileges on sequence bank_accounts_account_id_seq to java_user;



-- Insert sample users/bank accounts into DB.
insert into users values
	('employee 1', 'i am an employee', 'bobby', 'bob', true),
	('employee 2', 'i am a better employee', 'susie', 'sue', true),
	('employee 3', 'i am the best employee', 'ronny', 'ron', true);

insert into users values 
	('newcustomer1', 'good p4ssword', 'first', 'last'), 
	('newcustomer2', 'good p4ssword', 'first', 'last');

insert into bank_accounts(u_user_name, account_name, balance, is_approved) values
	('newcustomer1', 'acc1', 0.00, false),
	('newcustomer1', 'acc2', 0.00, false),
	('newcustomer1', 'acc3', 50.00, true);
	


-- Insert sample users/bank accounts for JUnit testing.
insert into users values
	('testcustomer1', 'good p4ssword', 'tester', 'one', false),
	('testcustomer2', 'good p4ssword', 'tester', 'two', false),
	('testemployee', 'good p4ssword', 'tester', 'three', true);

insert into bank_accounts(u_user_name, account_name, balance, is_approved) values
	('testcustomer1', 'testacc1', 50.00, false),
	('testcustomer1', 'testacc2', 20.00, false);
	

