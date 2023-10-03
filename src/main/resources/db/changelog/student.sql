--liquibase formatted sql

--changeset Volkov:1
create index student_name_index on student(name)