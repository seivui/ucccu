# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cooperative (
  cooperative_id            bigint auto_increment not null,
  cooperative_name          varchar(255),
  contact_person            varchar(255),
  phone_number              varchar(255),
  address                   varchar(255),
  email                     varchar(255),
  union_id                  bigint,
  constraint pk_cooperative primary key (cooperative_id))
;

create table failed_milk_transaction (
  transaction_id            bigint auto_increment not null,
  reason                    varchar(255),
  date                      datetime,
  farmerId                  bigint,
  cooperative_id            bigint,
  constraint pk_failed_milk_transaction primary key (transaction_id))
;

create table farmer (
  farmer_id                 bigint auto_increment not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  account_number            integer,
  sex                       varchar(255),
  phone_number              varchar(255),
  amount_cows               integer,
  cooperative_id            bigint,
  constraint pk_farmer primary key (farmer_id))
;

create table munion (
  union_id                  bigint auto_increment not null,
  union_name                varchar(255),
  contact_person            varchar(255),
  district                  varchar(255),
  address                   varchar(255),
  phone_number              varchar(255),
  email                     varchar(255),
  constraint pk_munion primary key (union_id))
;

create table milk_transaction (
  transaction_id            bigint auto_increment not null,
  amount                    double,
  fat_content               double,
  water_content             double,
  date                      datetime,
  farmer_id                 bigint,
  cooperative_id            bigint,
  constraint pk_milk_transaction primary key (transaction_id))
;

create table payment (
  payment_id                bigint auto_increment not null,
  payment                   integer,
  date                      datetime,
  total_litres              integer,
  price                     integer,
  farmer_id                 bigint,
  constraint pk_payment primary key (payment_id))
;

create table user (
  email                     varchar(255) not null,
  role                      varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (email))
;

alter table cooperative add constraint fk_cooperative_union_1 foreign key (union_id) references munion (union_id) on delete restrict on update restrict;
create index ix_cooperative_union_1 on cooperative (union_id);
alter table failed_milk_transaction add constraint fk_failed_milk_transaction_farmer_2 foreign key (farmerId) references farmer (farmer_id) on delete restrict on update restrict;
create index ix_failed_milk_transaction_farmer_2 on failed_milk_transaction (farmerId);
alter table failed_milk_transaction add constraint fk_failed_milk_transaction_cooperative_3 foreign key (cooperative_id) references cooperative (cooperative_id) on delete restrict on update restrict;
create index ix_failed_milk_transaction_cooperative_3 on failed_milk_transaction (cooperative_id);
alter table farmer add constraint fk_farmer_cooperative_4 foreign key (cooperative_id) references cooperative (cooperative_id) on delete restrict on update restrict;
create index ix_farmer_cooperative_4 on farmer (cooperative_id);
alter table milk_transaction add constraint fk_milk_transaction_farmer_5 foreign key (farmer_id) references farmer (farmer_id) on delete restrict on update restrict;
create index ix_milk_transaction_farmer_5 on milk_transaction (farmer_id);
alter table milk_transaction add constraint fk_milk_transaction_cooperative_6 foreign key (cooperative_id) references cooperative (cooperative_id) on delete restrict on update restrict;
create index ix_milk_transaction_cooperative_6 on milk_transaction (cooperative_id);
alter table payment add constraint fk_payment_farmer_7 foreign key (farmer_id) references farmer (farmer_id) on delete restrict on update restrict;
create index ix_payment_farmer_7 on payment (farmer_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table cooperative;

drop table failed_milk_transaction;

drop table farmer;

drop table munion;

drop table milk_transaction;

drop table payment;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

