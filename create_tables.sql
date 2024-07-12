DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS transactions;


CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(40) UNIQUE NOT NULL,
    password VARCHAR(40) NOT NULL,
    email VARCHAR(100) NOT NULL,
    first_name VARCHAR(40),
    last_name VARCHAR(40)
);

CREATE TABLE accounts (
	account_id SERIAL PRIMARY KEY,
	balance NUMERIC(15, 2) default 0.00,
	account_name VARCHAR(50) NOT NULL,
	account_type VARCHAR(50) NOT NULL,
	user_id INTEGER,
	CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE transactions(
	transaction_id SERIAL primary key,
	transaction_type VARCHAR(50) not null, --Deposit, Withdraw, Transfer
	amount NUMERIC(15, 2) not null,
	date VARCHAR(8) not null,
	user_id INTEGER,
	account_id INTEGER,
	CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES accounts (account_id),
	CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);

select * from accounts order by account_id;

select * from users order by user_id;

select * from transactions order by account_id, transaction_id desc;

