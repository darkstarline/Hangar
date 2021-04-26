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
	plaintextPassword VARCHAR(64),
	isAdmin VARCHAR(8),
	tryTimes INT,
	lockFlag BOOLEAN,
	notes VARCHAR(256),
	state VARCHAR(8),
	createDate TIMESTAMP,
	tel VARCHAR(32)
);
select a.* from operator a;
DELETE  from operator where code = 'darkstarline';
INSERT  into operator (code,name,password,email,plaintextPassword,isAdmin,tryTimes,lockFlag,notes,state,createDate,tel)
	VALUES('darkstarline','zhaosb','darkstarline','darkstarline@outlook.com','darkstarline','super',999,false,'superadmin','U',CURRENT_TIMESTAMP(),'15582892791');

DROP  table gundam;
CREATE  TABLE IF NOT EXISTS gundam(
	orgamismId INT not null auto_increment primary key,
	organismNumber VARCHAR(32),
	organismCodeName VARCHAR(32),
	jpnName VARCHAR(32),
	enName VARCHAR(32),
	cnName VARCHAR(32),
	animation VARCHAR(64),
	organismType VARCHAR(16),
	belong VARCHAR(16),
	manufacturer VARCHAR(16),
	organismSieze VARCHAR(32),
	netWeight VARCHAR(8),
	fullWeight VARCHAR(8),
	armoredStructure VARCHAR(32),
	output VARCHAR(32),
	propulsion VARCHAR(32),
	acceleration VARCHAR(8),
	sensorRadius VARCHAR(8),
	fixedArmed VARCHAR(32),
	dubut VARCHAR(16),
	cockpit VARCHAR(16),
	pilot VARCHAR(16),
	chooseWeapons VARCHAR(64),
	degreeTime VARCHAR(8),
	groundSpeed VARCHAR(8),
	waterSpeed VARCHAR(8),
	introduction VARCHAR(1024),
	cover VARCHAR(128),
	state VARCHAR(2),
	opCode VARCHAR(32),
	createDate TIMESTAMP,
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


