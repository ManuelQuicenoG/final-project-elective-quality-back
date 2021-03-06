DROP TABLE IF EXISTS COURSES_BY_EMPLOYEE;
DROP TABLE IF EXISTS EMPLOYEE;
DROP TABLE IF EXISTS COURSE;

CREATE SEQUENCE IF NOT EXISTS EMPLOYEE_SEQ START 2;
CREATE SEQUENCE IF NOT EXISTS COURSE_SEQ START 1;

CREATE TABLE IF NOT EXISTS EMPLOYEE
(
    id bigint PRIMARY KEY,
    rol_position VARCHAR(255),
    gender VARCHAR(255),
    name VARCHAR(100),
    has_courses boolean NOT NULL,
    UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS COURSE
(
    id bigint PRIMARY KEY,
    name VARCHAR(255),
    UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS COURSES_BY_EMPLOYEE
(
    employee_id bigint NOT NULL,
    course_id bigint NOT NULL,
    CONSTRAINT fk_employee FOREIGN KEY (employee_id)
        REFERENCES employee (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_course FOREIGN KEY (course_id)
        REFERENCES public.course (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);