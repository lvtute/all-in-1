INSERT INTO role(name) VALUES('ROLE_ADVISER');
INSERT INTO role(name) VALUES('ROLE_DEAN');
INSERT INTO role(name) VALUES('ROLE_ADMIN');

INSERT INTO faculty(name) VALUES('Công nghệ thông tin');
INSERT INTO faculty(name) VALUES('Cơ khí chế tạo máy');
INSERT INTO faculty(name) VALUES('Công nghệ may và thiết kế thời trang');

INSERT INTO user(`id`, `email`, `password`, `username`, `faculty_id`) VALUES (1, 'tranvanc@gmail.com', '$2a$12$QNvN/tk6gimZC.9i/OqW5uxKYH7BTGc5x3B1ET2fzA4Q.Y9/foTlS', 'mod1', 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `faculty_id`) VALUES (2, 'nguyenvana@gmail.com', '$2a$12$QNvN/tk6gimZC.9i/OqW5uxKYH7BTGc5x3B1ET2fzA4Q.Y9/foTlS', 'mod2', 2);
INSERT INTO user(`id`, `email`, `password`, `username`) VALUES (3, 'admin@gmail.com', '$2a$12$QNvN/tk6gimZC.9i/OqW5uxKYH7BTGc5x3B1ET2fzA4Q.Y9/foTlS', 'admin');

INSERT INTO user_roles(`user_id`, `role_id`) VALUES (1, 1);
INSERT INTO user_roles(`user_id`, `role_id`) VALUES (2, 2);
INSERT INTO user_roles(`user_id`, `role_id`) VALUES (3, 3);


