
create table business (
    id character varying(36) not null,
    version int8,
    name varchar(255) not null,
    total_employees int4 not null,
    primary key (id)
);
