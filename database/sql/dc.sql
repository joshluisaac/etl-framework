


create table kvdataconnector_config(
id bigint primary key not null,
global_config_id bigint not null,
column_name varchar(30)
);
alter table kvdataconnector_config add column_default_value varchar(30);
alter table kvdataconnector_config add column_start_position smallint;
alter table kvdataconnector_config add column_end_position smallint, add column_data_handler smallint;
alter table kvdataconnector_config add column_is_key boolean, add column_is_iexternal boolean, add column_is_optional boolean, add column_is_cached boolean, add lookup_query text;

create table kvdataconnector_config_global(
id bigint primary key not null,
destination_table varchar(30),
table_sequence_name varchar(30),
generated_key varchar(30),
column_delimiter smallint,
date_format smallint
);

create sequence kvdataconnector_config_id;
create sequence kvdataconnector_config_global_id;



CREATE TABLE kvsftp_settings (
server varchar(200),
port smallint,
userName varchar(50),
pass varchar(50),
localDir varchar(50),
remoteDir varchar(50)
);

CREATE TABLE kvemail_settings (
sendEmail boolean,
userAuthentication varchar(50),
userName varchar(50),
pass varchar(50),
host varchar(50),
recipient text,
port smallint,
subject varchar(50),
msg text,
subjectErr varchar(50),
msgErr text
);

CREATE TABLE kvdatabase_settings (
driver varchar(50),
url varchar(50),
port smallint,
additionalArgs varchar(50),
userName varchar(50),
password varchar(50)
);

create table kvhost(
id bigint primary key not null,
name varchar(30),
fqdn varchar(30),
username varchar(30),
host varchar(30),
port smallint,
publicKey text,
createdAt varchar(100),
updatedAt varchar(100)
);


alter table kvhost alter column createdAt type timestamp using createdat::timestamp without time zone;
alter table kvhost alter column updatedAt type timestamp using updatedAt::timestamp without time zone;

create sequence kvhost_id;

alter table kvdataconnector_config_global add sourceFileName varchar(100);
alter table kvdataconnector_config_global add description varchar(100);

alter table kvdataconnector_config_global add descriptorFileName varchar(100);
alter table kvdataconnector_config_global add includeLoadExecution boolean;

CREATE TABLE kvuser (
  id bigint primary key not null,
  email varchar(255) UNIQUE NOT NULL,
  lastName varchar(255) NOT NULL,
  firstName varchar(255) NOT NULL,
  password varchar(255) NOT NULL
);

alter table kvuser add enabled boolean NOT NULL DEFAULT FALSE;
alter table kvuser add role varchar(20) NOT NULL;
alter table kvuser add tenant_id bigint NOT NULL;

create sequence kvuser_id;

ALTER TABLE kvdataconnector_config_global ADD disable boolean
ALTER TABLE kvdataconnector_config ADD look_insert_query text
ALTER TABLE kvdataconnector_config ADD look_insert_key_query text

ALTER TABLE kvdataconnector_config ADD disable boolean

select md5('hashim');
ALTER TABLE kvdataconnector_config ADD disable boolean
ALTER TABLE kvdataconnector_config ADD remark text

ALTER TABLE kvdataconnector_config ALTER COLUMN column_name TYPE varchar(40);


select invoices.id as invoice_id, sum(transactions.credit) as total_transactions, (invoices.invoice_amount+invoices.gst) as invoice_plus_gst, 
invoices.invoice_outstanding as invoice_outstanding
from invoices 
inner join transactions on invoices.id = transactions.invoice_id
where invoices.tenant_id = 63 and transactions.initial_debit = false group by invoices.id, invoices.invoice_outstanding;

update invoices set invoice_outstanding = invoice_plus_gst - total_transactions;

create table kvbatch(
  id bigint primary key not null,
  code text,
  name text,
  description text,
  disable boolean
);

create table kvbatch_history(
  id bigint primary key not null,
  batch_id bigint,
  number_of_records_updated bigint,
  updated_date timestamp(0)
);

create sequence kvbatch_id;
create SEQUENCE kvbatch_history_id;

create UNIQUE INDEX kv_vendor_invoices_idx1 on kv_vendor_invoices (load_id);

alter table transaction_load add line_of_business varchar(50);
alter table transaction_load add invoice_no varchar(255);
alter table transaction_load add invoice_reference varchar(255);

CREATE TABLE kvdata_profiler (
  id BIGINT primary key not null,
  name VARCHAR(30),
  base_path text,
  prefix VARCHAR(50),
  cloneAs VARCHAR(50),
  cloneBeforeUnique BOOLEAN,
  cloneFile BOOLEAN,
  uniqueKeyFields text,
  uniqueKeyIndex text,
  generateHash BOOLEAN,
  regex text,
  replacement text,
  expectedLength BIGINT
);

CREATE SEQUENCE kvdata_profiler_id;

alter table kv_data_date add tenant_id bigint;
update kv_data_date set tenant_id = 63;

alter table kvbatch_history add data_source text;

CREATE TABLE kvemail_update(
  id bigint primary key not null,
  last_run_time BIGINT,
  update_type text
);

INSERT into kvemail_update(id,last_run_time,update_type)
values(1,(extract(epoch from now()) * 1000),'Daily batch email update');

INSERT into kvemail_update(id,last_run_time,update_type)
values(2,(extract(epoch from now()) * 1000),'Test email update');

CREATE table kvemail_log(
  id BIGINT PRIMARY KEY NOT NULL,
  subject text,
  recipient text,
  time_sent TIMESTAMP,
  status VARCHAR(20)
);

create SEQUENCE kvemail_log_id;