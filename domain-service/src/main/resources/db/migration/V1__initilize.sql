create sequence hibernate_sequence start 1 increment 1;

create table business (
   id character varying(36) not null,
   name varchar(255),
   total_employees int4 not null,
   version int8,
   primary key (id)
);

create table account (
    id character varying(36) not null,
    business character varying(36) not null,
    limit_currency varchar(3),
    limit_value decimal(19,2),
    balance_currency varchar(3),
    balance_value decimal(19,2),
    version int8,
    primary key (id)
);

create table stored_domain_event (
   id int8 not null,
   type varchar(255) not null,
   occurred_on timestamp not null,
   event varchar(255) not null,
   primary key (id)
);

alter table account 
   add constraint UK_a1qbp4t2lyoij3qyfjr1at32d unique (business);
   
alter table account 
   add constraint FKfr15xahd586maerwk1eridk7d 
   foreign key (business) 
   references business;