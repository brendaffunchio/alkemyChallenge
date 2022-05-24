/*antes de correr el proyecto:*/
create database disneyDB;
use  disneyDB;

/*despues de correr el proyecto:*/

insert into Rol (id,name) values (1,"USER");

/*para usar postman con los datos que estan cargados ahi :*/
insert into Genero values (7,"fantasia.jpg","suspenso y aventura"),(15,"fantasia.jpg","suspenso");
insert into Pelicula values (10,5,'2016-04-03 00:00:00.000000','malefica.jpg','Malefica 2',7),
(11,5,'2014-04-03 00:00:00.000000','aliciaEnElPaisDeLasMaravillas.jpg','Alicia En El Pais De Las Maravillas 2',7),
(20,5,'2018-04-03 00:00:00.000000','malefica.jpg','La Bella Durmiente',7);

insert into pelicula_personaje VALUES (11,5),(10,18),(10,4),(20,3);

insert into Personaje values (2,17,'Harry James Potter es el protagonista de la serie de libros Harry Potter de J. K. Rowling.','harryPotter.jpg','Harry Potter',57,0),
(3,45,'Maléfica es una bruja y hada malvada que hechiza a la protagonista, la princesa Aurora, la Bella Durmiente tras no ser invitada por su madre y su padre, el Rey Estéfano y la Reina Flor, a su bautizo. Es una de las villanas más malvadas de Disney.','maleficaPersonaje.jpg','Malefica',56,0),
(4,35,'El Sombrerero es un personaje de la novela Las aventuras de Alicia en el país de las maravillas, del escritor inglés Lewis Carroll.','sombrero.jpg','Sombrero Loco',70,0),(5,20,'Alicia es un personaje ficticio, protagonista de la novela infantil de Lewis Carroll, Las aventuras de Alicia en el país de las maravillas y de su secuela, A través del espejo','alicia.jpg','Alicia',54,1),(16,20,'Alicia es un personaje ficticio, protagonista de la novela infantil de Lewis Carroll, Las aventuras de Alicia en el país de las maravillas y de su secuela, A través del espejo','alicia.jpg','Alicia',54,1),(17,20,'Alicia es un personaje ficticio, protagonista de la novela infantil de Lewis Carroll, Las aventuras de Alicia en el país de las maravillas y de su secuela, A través del espejo','alicia.jpg','Alicia',54,0),
(18,20,'Alicia es un personaje ficticio, protagonista de la novela infantil de Lewis Carroll, Las aventuras de Alicia en el país de las maravillas y de su secuela, A través del espejo nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnbnnnnnnnnnnnnnnnnnnnnnnnn','alicia.jpg','Alicia',54,0),(19,20,'Alicia es un personaje ficticio, protagonista de la novela infantil de Lewis Carroll, Las aventuras de Alicia en el país de las maravillas y de su secuela, A través del espejo','alicia.jpg','Alicia',54,1),(26,20,'Alicia es un personaje ficticio, protagonista de la novela infantil de Lewis Carroll, Las aventuras de Alicia en el país de las maravillas y de su secuela, A través del espejo','alicia.jpg','Alicia',54,1);

insert into Usuario values (6,'$2a$10$DRBFW5uj1kAKL10YIImuV.aq3Jgls7nRma1HhQPW8TJPVaPyUdeCi','brendaffun@gmail.com',1);
