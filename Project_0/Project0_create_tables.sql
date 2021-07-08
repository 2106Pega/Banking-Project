create table customers (
	customer_id serial primary key,
	customer_first_name varchar(30) not null,
	customer_last_name varchar(30) not null,
	customer_ssn int unique not null
);

create table accounts (
	account_number serial primary key,
	account_type varchar(8) not null, --set to 8 for checking
	account_balance numeric(15,2) not null,
	c_id int references customers(customer_id)
);

create table account_applications(
	application_id serial primary key,
	account_type varchar(8) not null,
	account_balance numeric(15,2) not null,
	c_id int references customers(customer_id)
);

create table customer_transactions(
	transaction_id serial primary key,
	a_id int references accounts(account_number),
	account_type varchar(8),
	transaction_type varchar(10) not null,
	transaction_amount numeric(15,2) not null,
	transfer_into_id int null	
);

select * from customers;
select * from accounts;
select * from account_applications;
select * from customer_transactions;

delete from customers;
delete from accounts;
delete from account_applications;
delete from customer_transactions;

drop table customers;
drop table accounts;
drop table account_applications;
drop table customer_transactions;

--Junction table
--Customers can have multiple accounts, but an account COULD have multiple customers (Husband and wife for example)
--This is most likely out of scope for the project, going to just have a foreign key in accounts
--create table customer_accounts (
--	c_id int references customers(customer_id),
--	a_id int references accounts(account_number)
--);

--create table employee (
--	employee_id serial primary key,
--	employee_first_name varchar(30) not null,
--	employee_last_name varchar(30) not null
--);
