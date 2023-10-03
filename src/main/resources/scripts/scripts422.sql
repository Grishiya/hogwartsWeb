create table car (id serial primary key,
make varchar(30),
model varchar(30),
price numeric(10,2)
);
create table people(
id serial primary key,
name varchar not null,
age serial check(age>=18),
has_license boolean default false,
car_id serial references car(id)
);
select student.name, student.age ,faculty.name
from student
inner join faculty on student.faculty_id = faculty.id;
select avatar.student_id ,student.name
from student
inner join avatar on avatar.student_id =student.id;