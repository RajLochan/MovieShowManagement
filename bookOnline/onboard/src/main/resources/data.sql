insert into `country` (`id`,`name`,`code`) values(1,'india','+91');
insert into `city` (`id`,`name`,`district`,`state`,`country_id`) values(1,'Bengaluru','Gautam budh nagar','Karnataka',1);
insert into `city` (`id`,`name`,`district`,`state`,`country_id`) values(2,'Bengaluru rural','Jayanagar','Karnataka',1);
insert into `theatre` (`id`,`name`,`city_id`,`lat`,`lng`,`area`) values(1,'Wave cinema',1,28.579329,77.391029,'wave mall,sector 18');
insert into `screen` (`id`,`name`,`front_seats`,`middle_seats`,`back_seats`,`theatre_id`) values(1,'Audi 1',50,30,10,1);
insert into `screen` (`id`,`name`,`front_seats`,`middle_seats`,`back_seats`,`theatre_id`) values(2,'Audi 2',50,40,10,1);
insert into `screen` (`id`,`name`,`front_seats`,`middle_seats`,`back_seats`,`theatre_id`) values(3,'Audi 3',50,30,10,1);
insert into `screen` (`id`,`name`,`front_seats`,`middle_seats`,`back_seats`,`theatre_id`) values(4,'Audi 4',50,40,10,1);
insert into `movie_variant` (`id`,`type`) values (1,'Hindi');
insert into `movie_variant` (`id`,`type`) values (2,'English');
insert into `movie_variant` (`id`,`type`) values (3,'Tamil');
insert into `movie_variant` (`id`,`type`) values (4,'Punjabi');
insert into `movie_variant` (`id`,`type`) values (5,'3D');
insert into `movie_variant` (`id`,`type`) values (6,'Animation');
insert into `movie` (`id`,`name`,`variant_id`,`description`,`duration_hour`,`duration_mint`) values (1,'Pathaan',1,'life of spy',2,300);
insert into `movie` (`id`,`name`,`variant_id`,`description`,`duration_hour`,`duration_mint`) values (2,'Pathaan',2,'life of spy',2,300);
insert into `movie` (`id`,`name`,`variant_id`,`description`,`duration_hour`,`duration_mint`) values (3,'Gadar 2',1,'ek prem katha',3,300);
insert into `movie` (`id`,`name`,`variant_id`,`description`,`duration_hour`,`duration_mint`) values (4,'Heart of stone',1,'action, thriller',2,300);
insert into `movie_shows` (`id`,`movie_id`,`audi_id`,`show_time`,`show_date`,`blocked_seats`,`booked_seats`) values (1,'1',1,'10:30:00','2023-10-01',0,0);
insert into `movie_shows` (`id`,`movie_id`,`audi_id`,`show_time`,`show_date`,`blocked_seats`,`booked_seats`) values (2,'1',1,'14:00:00','2023-10-01',0,0);
insert into `movie_shows` (`id`,`movie_id`,`audi_id`,`show_time`,`show_date`,`blocked_seats`,`booked_seats`) values (3,'1',1,'18:30:00','2023-10-01',0,0);
insert into `movie_shows` (`id`,`movie_id`,`audi_id`,`show_time`,`show_date`,`blocked_seats`,`booked_seats`) values (4,'3',2,'10:30:00','2023-10-01',0,0);
insert into `movie_shows` (`id`,`movie_id`,`audi_id`,`show_time`,`show_date`,`blocked_seats`,`booked_seats`) values (5,'3',2,'14:00:00','2023-10-01',0,0);
insert into `movie_shows` (`id`,`movie_id`,`audi_id`,`show_time`,`show_date`,`blocked_seats`,`booked_seats`) values (6,'3',2,'18:30:00','2023-10-01',0,0);
insert into `movie_shows` (`id`,`movie_id`,`audi_id`,`show_time`,`show_date`,`blocked_seats`,`booked_seats`) values (7,'4',3,'10:30:00','2023-10-01',0,0);
insert into `movie_shows` (`id`,`movie_id`,`audi_id`,`show_time`,`show_date`,`blocked_seats`,`booked_seats`) values (8,'4',3,'18:00:00','2023-10-01',0,0);

CREATE OR REPLACE VIEW mbp_search.MovieTheaterView AS
SELECT
    m.name as movie_name,
	mv.type as movie_variant,
	t.name as theatre_name,
	c.name as city_name,
    ms.show_time as show_time,
	ms.show_date as show_date,
	ms.id as show_id,
    s.name as screen_name
FROM
    movie_shows ms
INNER JOIN
    movie m ON ms.movie_id = m.id
INNER JOIN
    movie_variant mv ON m.variant_id = mv.id
INNER JOIN
    screen s ON ms.audi_id = s.id
INNER JOIN
    theatre t ON s.theatre_id = t.id
INNER JOIN
    city c ON t.city_id = c.id;
--CREATE INDEX idx_MovieTheaterView_city_name ON mbp_search.MovieTheaterView (city_name);
--CREATE INDEX idx_MovieTheaterView_show_date ON mbp_search.MovieTheaterView (show_date);
--add show date> todaydate-1 in where clause to fetch the currently running shows