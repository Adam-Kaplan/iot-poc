create sequence hibernate_sequence start 1 increment 1;

create table Item (
	id uuid not null,
	fill float4,
	name varchar(255),
	type_id int8,
	primary key (id)
);

create table Type (
	id int8 not null,
	name varchar(255),
	primary key (id)
);

alter table Item
	add constraint FK17riagh2psc1ye4dbr9bsmpv6 foreign key (type_id)references Type;
