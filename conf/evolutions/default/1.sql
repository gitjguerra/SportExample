
create table Customers (
  id                    bigint auto_increment not null,
  dni                 	bigint,
  nombre			  	varchar(255),
  apellido 				varchar(255),
  telefono				varchar(255),
  email					varchar(255),
  constraint pk_page_retrieval primary key (id))
;

SET FOREIGN_KEY_CHECKS=0;

drop table Customers;

SET FOREIGN_KEY_CHECKS=1;

