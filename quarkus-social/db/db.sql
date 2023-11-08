CREATE TABLE quarkus-social;

CREATE TABLE USERS (
	id bigserial not null primary key,
	name varchar(100) not null,
	age integer not null
);

create table POSTS (
	id bigserial not null primary key,
	post_text varchar(150) not null,
	dataTime timestamp,
	user_id bigserial not null references USERS(id)
)