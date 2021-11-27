SET @pw = '$2a$12$QNvN/tk6gimZC.9i/OqW5uxKYH7BTGc5x3B1ET2fzA4Q.Y9/foTlS';
INSERT INTO role(name) VALUES('ROLE_ADVISER');
INSERT INTO role(name) VALUES('ROLE_DEAN');
INSERT INTO role(name) VALUES('ROLE_ADMIN');

INSERT INTO faculty(name) VALUES('Công nghệ thông tin');
INSERT INTO faculty(name) VALUES('Cơ khí chế tạo máy');
INSERT INTO faculty(name) VALUES('Công nghệ may và thiết kế thời trang');

INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (1, 'tranvanc@gmail.com', @pw, 'mod1', 'Tran Van C', 1, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (2, 'nguyenvana@gmail.com', @pw, 'mod2', 'Nguyen Van A', 2, 2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (3, 'admin@gmail.com', @pw, 'admin', 'Luong Van Thuan', 3,3);

INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (4,'jbutt@gmail.com', @pw, 'jamesbutt', 'James Butt', 1,2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (5,'josephine_darakjy@darakjy.org', @pw, 'josephine_darakjy', 'Josephine Darakjy', 2,3);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (6,'art@venere.org', @pw, 'art', 'Art	Venere', 3,2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (7,'lpaprocki@hotmail.com', @pw, 'lpaprocki', 'Lenna Paprocki', 1,2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (8,'donette.foller@cox.net', @pw, 'donette.foller', 'Donette Foller', 2,1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (9,'simona@morasca.com', @pw, 'simona', 'Simona Morasca', 3,2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (10,'sage_wieser@cox.net', @pw, 'sage_wieser', 'Sage Wieser', 1,3);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (11,'leota@hotmail.com', @pw, 'leota', 'Leota Dilliard', 2,2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (12,'mitsue_tollner@yahoo.com', @pw, 'mitsue_tollner', 'Mitsue Tollner', 3,2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (13,'amaclead@gmail.com', @pw, 'amaclead', 'Abel Maclead', 1,1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (14,'minna_amigon@yahoo.com', @pw, 'minna_amigon', 'Minna Amigon', 2,2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (15,'kris@gmail.com', @pw, 'kris', 'Kris Marrier', 3,1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (16,'calbares@gmail.com', @pw, 'calbares', 'Cammy Albares', 1,2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (17,'gruta@cox.net', @pw, 'gruta', 'Graciela Ruta', 2,3);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (18,'kiley.caldarera@aol.com', @pw, 'kiley.caldarera', 'Kiley Caldarera', 3,2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (19,'gladys.rim@rim.org', @pw, 'gladys.rim', 'Gladys Rim', 1,2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (20,'meaghan@hotmail.com', @pw, 'meaghan', 'Meaghan Garufi', 2,1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (21,'mattie@aol.com', @pw, 'mattie', 'Mattie Poquette', 3,2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (22,'bette_nicka@cox.net', @pw, 'bette_nicka', 'Bette Nicka', 1,3);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (23,'fletcher.flosi@yahoo.com', @pw, 'fletcher.flosi', 'Fletcher Flosi', 2,2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (24,'yuki_whobrey@aol.com', @pw, 'yuki_whobrey', 'Yuki Whobrey', 3,1);



INSERT INTO question(`id`, `content`,`email`, `title`,`user_name`,`faculty_id`,`write_date`,`modify_date`) VALUES (1, N'dạ em muốn hỏi ngày tổ chức tuyển sinh của khoa là ngày nào ạ','yuki_whobrey@aol.com',N'Ngày tuyển sinh',N'vui vẻ',1,'2012-05-06 21:10:17','2012-05-06 21:10:17');
SET @dt =  NOW();
INSERT INTO question(`id`, `content`,`email`, `title`,`user_name`,`faculty_id`,`write_date`,`modify_date`) VALUES (2, N'tôi muốn hỏi về tình hình học tập của con tôi thì làm như thế nào vậy','yuki12_whobrey@aol.com', N'Tình hình học tập',N'Nam Nguyễn',2,@dt,@dt);
INSERT INTO question(`id`, `content`,`email`, `title`,`user_name`,`faculty_id`,`write_date`,`modify_date`) VALUES (3, N'dạ em muốn hỏi ngày tổ chức tuyển sinh của khoa là ngày nào ạ','yuki11_whobrey@aol.com', N'Ngày tuyển sinh',N'Cao Nguyễn',3,@dt,@dt);
INSERT INTO question(`id`, `content`,`email`, `title`,`user_name`,`faculty_id`,`write_date`,`modify_date`) VALUES (4, N'dbao lâu mới hết hạn đóng học phí vậy ạ','yuki13_whobrey@aol.com', N'Học phí',N'Đô Thống',3,@dt,@dt);

INSERT INTO answer(`id`, `content`,  `question_id`, `user_id`) VALUES (1, N'20-9 nhé', 1,  1);
INSERT INTO answer(`id`, `content`,  `question_id`, `user_id`) VALUES (2, N'15-10 nhé', 4,  2);
INSERT INTO answer(`id`, `content`,  `question_id`, `user_id`) VALUES (3, N'đã cập thay đổi thành 25-10 nhé', 4,  3);
INSERT INTO answer(`id`, `content`,  `question_id`, `user_id`) VALUES (4, N'Bạn có thể liên hệ phòng đào tạo để biết thêm thông tin học tập con nhé', 2,  1);
INSERT INTO answer(`id`, `content`, `question_id`, `user_id`) VALUES (5, N'20-9 nhé', 3,  2);


INSERT INTO topic(`id`, `name_topic`, `faculty_id`) VALUES (1, N'Học phí',    1);
INSERT INTO topic(`id`, `name_topic`, `faculty_id`) VALUES (2, N'Điểm số',    2);
INSERT INTO topic(`id`, `name_topic`, `faculty_id`) VALUES (3, N'Tuyển sinh',   3);
INSERT INTO topic(`id`, `name_topic`, `faculty_id`) VALUES (4, N'20-9 nhé',   1);
INSERT INTO topic(`id`, `name_topic`, `faculty_id`) VALUES (5, N'20-9 nhé',   2);

INSERT INTO ques_topic(`question_id`, `topic_id`) VALUES (1,3);
INSERT INTO ques_topic(`question_id`, `topic_id`) VALUES (2,2);
INSERT INTO ques_topic(`question_id`, `topic_id`) VALUES (3,4);




