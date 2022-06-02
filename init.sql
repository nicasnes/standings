CREATE TABLE TEAMS (
	teamid serial,
	teamname varchar(30) NOT NULL,
	conference int NOT NULL,
	division int,
	wins int,
	losses int,
	gd int,
	PRIMARY KEY (teamid),
	FOREIGN KEY (conference) references CONFERENCES(num),
	FOREIGN KEY (division) references DIVISIONS(num)
);

CREATE TABLE CONFERENCES (
	num int,
	name varchar(30),
	PRIMARY KEY (num)
);

CREATE TABLE DIVISIONS (
	num int,
	name varchar(30),
	PRIMARY KEY (num)
);

CREATE TABLE MATCHES(
	matchid serial,
	tid1 int,
	tid2 int,
	winnerid int,
	PRIMARY KEY (matchid),
	FOREIGN KEY (tid1) references TEAMS(teamid),
	FOREIGN KEY (tid2) references TEAMS(teamid)
);