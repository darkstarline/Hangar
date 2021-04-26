CREATE TABLE IF NOT EXISTS test(
   id INT not null auto_increment primary key,
   darkstarline VARCHAR(256)
)

select a.* from test a;

INSERT  into test (darkstarline) values('darkstarline is a loser');


drop table operator ;
CREATE TABLE IF NOT EXISTS operator(
	id INT not null auto_increment primary key,
	code VARCHAR(32),
	name VARCHAR(32),
	password VARCHAR(64),
	email VARCHAR(64),
	plaintext_password VARCHAR(64),
	is_admin VARCHAR(8),
	try_times INT,
	lock_flag BOOLEAN,
	notes VARCHAR(256),
	state VARCHAR(8),
	create_date TIMESTAMP,
	tel VARCHAR(32)
);
select a.* from operator a;
DELETE  from operator where code = 'darkstarline';
INSERT  into operator (code,name,password,email,plaintext_password,is_admin,try_times,lock_flag,notes,state,create_date,tel)
	VALUES('darkstarline','zhaosb','darkstarline','darkstarline@outlook.com','darkstarline','super',999,false,'superadmin','U',CURRENT_TIMESTAMP(),'15582892791');




DROP  table gundam;
CREATE  TABLE IF NOT EXISTS gundam(
	orgamism_id INT  auto_increment primary key,
	organism_number VARCHAR(32) unique,
	organism_code_name VARCHAR(32),
	jpn_name VARCHAR(32),
	en_name VARCHAR(32),
	cn_name VARCHAR(32),
	animation VARCHAR(64),
	organism_type VARCHAR(16),
	belong VARCHAR(16),
	manufacturer VARCHAR(16),
	organism_size VARCHAR(32),
	net_weight VARCHAR(8),
	full_weight VARCHAR(8),
	armored_structure VARCHAR(32),
	output VARCHAR(32),
	propulsion VARCHAR(32),
	acceleration VARCHAR(8),
	sensor_radius VARCHAR(8),
	fixed_armed VARCHAR(32),
	dubut VARCHAR(16),
	cockpit VARCHAR(16),
	pilot VARCHAR(16),
	choose_weapons VARCHAR(64),
	degree_time VARCHAR(8),
	ground_speed VARCHAR(8),
	water_speed VARCHAR(8),
	introduction VARCHAR(1024),
	cover VARCHAR(128),
	state VARCHAR(2),
	op_code VARCHAR(32),
	create_date TIMESTAMP,
	extend0 VARCHAR(200),
	extend1 VARCHAR(200),
	extend2 VARCHAR(200),
	extend3 VARCHAR(200),
	extend4 VARCHAR(200),
	extend5 VARCHAR(200),
	extend6 VARCHAR(200),
	extend7 VARCHAR(200)
);


select * from gundam ;

select count(*) 
from information_schema.COLUMNS 
where TABLE_SCHEMA='test' and table_name='gundam' ;



show variables like '%character%';


#SET character_set_database = utf8mb4;

commit;

DROP  table file_info;
CREATE  TABLE IF NOT EXISTS file_info(
	file_id VARCHAR(64)   primary key,
	dfs_id VARCHAR(256) unique,
	file_name VARCHAR(256),
	file_status VARCHAR(16),
	content_type VARCHAR(64),
	file_size VARCHAR(32),
	size_desc VARCHAR(16),
	create_date TIMESTAMP,
	op_code VARCHAR(32),
	state VARCHAR(8),
	router_id VARCHAR(16)
)
select * from file_info;


DROP  table fstdfs_tracker_router;
CREATE  TABLE IF NOT EXISTS fstdfs_tracker_router(
	router_id INT not null auto_increment primary key,
	router_name VARCHAR(32),
	tracker_server_url VARCHAR(64),
	descrption VARCHAR(64),
	state VARCHAR(8),
	create_date TIMESTAMP
)
select * from fstdfs_tracker_router;


DROP  table gundam_file_rel;
CREATE  TABLE IF NOT EXISTS gundam_file_rel(
	file_id VARCHAR(64) references file_info(file_id),
	dfs_id VARCHAR(256) references file_info(dfs_id),
	organism_number VARCHAR(32) references gundam(organism_number),
	file_type VARCHAR(16),
	create_date TIMESTAMP,
	state VARCHAR(8)
)
select * from gundam_file_rel;

DROP  table file_invoke;
CREATE  TABLE IF NOT EXISTS file_invoke(
	invoke_id INT not null auto_increment primary key,
	file_id VARCHAR(64),
	file_name VARCHAR(256),
	result_code VARCHAR(32),
	result_msg VARCHAR(256),
	op_type VARCHAR(16),
	create_date TIMESTAMP
)
select * from file_invoke;



