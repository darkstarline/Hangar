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
	propulsion VARCHAR(128),
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
select * from file_info a where a.file_id = '20210518e7fd6b9a198f454a8abbb9dc3fa28cb9';


DROP  table fstdfs_tracker_router;
CREATE  TABLE IF NOT EXISTS fstdfs_tracker_router(
	router_id INT not null auto_increment primary key,
	router_name VARCHAR(32),
	tracker_server_url VARCHAR(64),
	description VARCHAR(64),
	state VARCHAR(8),
	create_date TIMESTAMP
)
select * from fstdfs_tracker_router;
INSERT into fstdfs_tracker_router (router_name,tracker_server_url,
	description,state,create_date) VALUES('笔记本VM虚拟机地址','192.168.79.213:22133',
	'测试用的，之后另搭，只做记录','U',CURRENT_TIMESTAMP());

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
commit;

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

DROP table bs_static_data;
CREATE TABLE IF NOT EXISTS bs_static_data(
	code_type VARCHAR(64),
	code_value VARCHAR(64),
	code_name VARCHAR(64),
	code_desc VARCHAR(128),
	code_type_alias VARCHAR(64),
	state VARCHAR(8),
	extern_code_type VARCHAR(256)
)
select * from bs_static_data;
insert into bs_static_data(code_type,code_value,code_name) values ('testType','testValue','testName');
insert into bs_static_data(code_type,code_value,code_name,state) values ('testType','testValue','testName','U');
 sb.append("{");
                sb.append("\"organismNumber\" : \"").append(gundamBean.getOrganismNumber()).append("\",");
                sb.append("\"organismCodeName\" : \"").append(gundamBean.getOrganismCodeName()).append("\",");
                sb.append("\"jpnName\" : \"").append(gundamBean.getJpnName()).append("\",");
                sb.append("\"enName\" : \"").append(gundamBean.getEnName()).append("\",");
                sb.append("\"cnName\" : \"").append(gundamBean.getCnName()).append("\",");
                sb.append("\"animation\" : \"").append(gundamBean.getAnimation()).append("\",");
                sb.append("\"organismType\" : \"").append(gundamBean.getOrganismType()).append("\",");
                sb.append("\"belong\" : \"").append(gundamBean.getBelong()).append("\",");
                sb.append("\"manufacturer\" : \"").append(gundamBean.getManufacturer()).append("\",");
                sb.append("\"organismSieze\" : \"").append(gundamBean.getOrganismSieze()).append("\",");
                sb.append("\"netWeight\" : \"").append(gundamBean.getNetWeight()).append("\",");
                sb.append("\"fullWeight\" : \"").append(gundamBean.getFullWeight()).append("\",");
                sb.append("\"armoredStructure\" : \"").append(gundamBean.getArmoredStructure()).append("\",");
                sb.append("\"output\" : \"").append(gundamBean.getOutput()).append("\",");
                sb.append("\"propulsion\" : \"").append(gundamBean.getPropulsion()).append("\",");
                sb.append("\"acceleration\" : \"").append(gundamBean.getAcceleration()).append("\",");
                sb.append("\"sensorRadius\" : \"").append(gundamBean.getSensorRadius()).append("\",");
                sb.append("\"fixedArmed\" : \"").append(gundamBean.getFixedArmed()).append("\",");
                sb.append("\"dubut\" : \"").append(gundamBean.getDubut()).append("\",");
                sb.append("\"cockpit\" : \"").append(gundamBean.getCockpit()).append("\",");
                sb.append("\"pilot\" : \"").append(gundamBean.getPilot()).append("\",");
                sb.append("\"chooseWeapons\" : \"").append(gundamBean.getChooseWeapons()).append("\",");
                sb.append("\"degreeTime\" : \"").append(gundamBean.getDegreeTime()).append("\",");
                sb.append("\"groundSpeed\" : \"").append(gundamBean.getGroundSpeed()).append("\",");
                sb.append("\"waterSpeed\" : \"").append(gundamBean.getWaterSpeed()).append("\",");
                sb.append("\"introduction\" : \"").append(gundamBean.getIntroduction()).append("\",");
               
               
               

 SELECT   id,code,name,password,email,plaintext_password,is_admin,try_times,lock_flag,notes,state,create_date,tel           FROM file_info          WHERE  file_id = 123;                                                                     and file_status = ?                                                                                  and create_date = ?                                           and op_code = ?                                           and state = ?

