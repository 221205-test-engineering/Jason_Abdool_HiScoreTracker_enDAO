--SQL table for HiScoreTracker

create table if not exists scores (
	id serial primary key,
	initials varchar(3) not null,
	score integer
)

insert into scores values 
(default, 'AAA', 999999),
(default, 'MLG', 420690),
(default, 'RED', 1131572119);


select * from scores;