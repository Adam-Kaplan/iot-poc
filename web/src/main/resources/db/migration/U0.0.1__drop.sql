alter table Item
	drop constraint FK17riagh2psc1ye4dbr9bsmpv6;

drop table Item cascade;

drop table Type cascade;

drop sequence hibernate_sequence;
