--liquibase formatted sql

--changeset Volkov:2
create index faculty_name_color_index on faculty(name,color)