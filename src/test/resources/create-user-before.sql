 delete from user_role;
delete from usr;

insert into usr(id, active, password, username) values
(1, true , '$2a$08$Evuq.SAmNBMA5owqxl4WHuR0SENynsU1nPCsh9e6kUrgcioo6skTu', 'admin'),
(2, true , '$2a$08$Evuq.SAmNBMA5owqxl4WHuR0SENynsU1nPCsh9e6kUrgcioo6skTu', 'user');

insert into user_role(user_id, roles) values
(1, 'ADMIN'), (1, 'USER'),
(2, 'USER');