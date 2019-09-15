delete from message;

insert into message(id, text, tag, user_id) values
(1, 'first', 'my-tag', 1),
(2, 'second', 'some', 1),
(3, 'third', 'my-tag', 2),
(4, 'fourth', 'some', 1);

alter sequence hibernate_sequence restart with 10;