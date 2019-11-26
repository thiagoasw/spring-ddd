create sequence hibernate_sequence start 1 increment 1;

create table business (
   id character varying(36) not null,
   version int8,
   name varchar(255),
   total_employees int4 not null,
   primary key (id)
);

create table stored_domain_event (
   id int8 not null,
   event varchar(255) not null,
   occurred_on timestamp not null,
   type varchar(255) not null,
   primary key (id)
);
