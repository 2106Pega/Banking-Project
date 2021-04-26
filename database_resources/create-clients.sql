-- public.clients definition

-- Drop table

-- DROP TABLE public.clients;

CREATE TABLE public.clients (
	id int8 NOT NULL,
	username varchar NOT NULL,
	"password" varchar NOT NULL,
	firstname varchar NULL,
	lastname varchar NULL,
	balance float8 NULL
);