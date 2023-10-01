select * from student ;
select *from student where age between 12 and 13;
select name from student;
select * from student where lower("name") like '%о%';
select *from student where age < 13;
select *from student order by age ;
select s.* from  student as s , faculty as f
where s.faculty_id =f.id
and faculty_id =2;
