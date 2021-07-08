# Bank

## Description
​
   The Prestigious Bank app is a console based application that allows customers to apply for an account, withdraw, deposit, and even transfer money to any of their associates that also use the Prestigious Bank app. Employees can assist customers by approving their account requests, helping supply lost passwords, and even removing a user if they end up abusing the Prestigious Bank app to help preserve the app for all users.
	
## Technologies
   The Prestigious Bank app makes use of several technologies that help us deliver the best service possible
* SQL Database
* Log4j Logging
* JUnit Testing
* DAO Design
​
## User Stories
​
* As a user, I can login.
* As a customer, I can apply for a new bank account with a starting balance.
* As a customer, I can view the balance of a specific account.
* As a customer, I can make a withdrawal or deposit to a specific account.
* As the system, I reject invalid transactions.
	* Ex:
		* A withdrawal that would result in a negative balance.
		* A deposit or withdrawal of negative money.
* As an employee, I can approve or reject an account.
* As an employee, I can view a customer's bank accounts.
* As a user, I can register for a customer account.
* As a customer, I can post a money transfer to another account.
* As a customer, I can accept a money transfer from another account.
* An employee, I can view a log of all transactions.