SET @pw = '$2a$12$QNvN/tk6gimZC.9i/OqW5uxKYH7BTGc5x3B1ET2fzA4Q.Y9/foTlS'

INSERT INTO role(name) VALUES('ROLE_ADVISER');
INSERT INTO role(name) VALUES('ROLE_DEAN');
INSERT INTO role(name) VALUES('ROLE_ADMIN');

INSERT INTO faculty(name) VALUES('Công nghệ thông tin');
INSERT INTO faculty(name) VALUES('Cơ khí chế tạo máy');
INSERT INTO faculty(name) VALUES('Công nghệ may và thiết kế thời trang');

INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (1, 'tranvanc@gmail.com', @pw, 'mod1', 'Tran Van C', 1, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (2, 'nguyenvana@gmail.com', @pw, 'mod2', 'Nguyen Van A', 2, 2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `role_id`) VALUES (3, 'admin@gmail.com', @pw, 'admin', 'Luong Van Thuan', 3);

INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('jbutt@gmail.com', @pw, 'jamesbutt', 'James Butt', 1);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('josephine_darakjy@darakjy.org', @pw, 'josephine_darakjy', 'Josephine Darakjy', 2);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('art@venere.org', @pw, 'art', 'Art	Venere', 3);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('lpaprocki@hotmail.com', @pw, 'lpaprocki', 'Lenna Paprocki', 1);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('donette.foller@cox.net', @pw, 'donette.foller', 'Donette Foller', 2);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('simona@morasca.com', @pw, 'simona', 'Simona Morasca', 3);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('sage_wieser@cox.net', @pw, 'sage_wieser', 'Sage Wieser', 1);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('leota@hotmail.com', @pw, 'leota', 'Leota Dilliard', 2);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('mitsue_tollner@yahoo.com', @pw, 'mitsue_tollner', 'Mitsue Tollner', 3);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('amaclead@gmail.com', @pw, 'amaclead', 'Abel Maclead', 1);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('minna_amigon@yahoo.com', @pw, 'minna_amigon', 'Minna Amigon', 2);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('kris@gmail.com', @pw, 'kris', 'Kris Marrier', 3);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('calbares@gmail.com', @pw, 'calbares', 'Cammy Albares', 1);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('gruta@cox.net', @pw, 'gruta', 'Graciela Ruta', 2);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('kiley.caldarera@aol.com', @pw, 'kiley.caldarera', 'Kiley Caldarera', 3);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('gladys.rim@rim.org', @pw, 'gladys.rim', 'Gladys Rim', 1);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('meaghan@hotmail.com', @pw, 'meaghan', 'Meaghan Garufi', 2);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('mattie@aol.com', @pw, 'mattie', 'Mattie Poquette', 3);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('bette_nicka@cox.net', @pw, 'bette_nicka', 'Bette Nicka', 1);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('fletcher.flosi@yahoo.com', @pw, 'fletcher.flosi', 'Fletcher Flosi', 2);
INSERT INTO user(`email`, `password`, `username`, `full_name`, `faculty_id`) VALUES ('yuki_whobrey@aol.com', @pw, 'yuki_whobrey', 'Yuki Whobrey', 3);

INSERT INTO topic(name, faculty_id) VALUES ('Học tập của SV  - Đăng ký MH', 1);
INSERT INTO topic(name, faculty_id) VALUES ('Nghiên cứu khoa học sinh viên', 1);
INSERT INTO topic(name, faculty_id) VALUES ('Điểm rèn luyện - Học bổng', 1);
INSERT INTO topic(name, faculty_id) VALUES ('Công tác SV - Chế độ chính sách - K.CNTT', 1);
INSERT INTO topic(name, faculty_id) VALUES ('Các vấn đề chung trong khoa CNTT', 1);
INSERT INTO topic(name, faculty_id) VALUES ('Ngành Công nghệ Thông tin', 1);
INSERT INTO topic(name, faculty_id) VALUES ('Ngành Kỹ thuật Dữ liệu', 1);

INSERT INTO topic(name, faculty_id) VALUES ('Các vấn đề chung trong khoa CKM', 2);
INSERT INTO topic(name, faculty_id) VALUES ('Nghiên cứu khoa học sinh viên', 2);
INSERT INTO topic(name, faculty_id) VALUES ('Trợ giảng, Thỉnh giảng, đăng ký môn học CKM', 2);
INSERT INTO topic(name, faculty_id) VALUES ('Công tác SV - Chế độ chính sách - K.CKM', 2);
INSERT INTO topic(name, faculty_id) VALUES ('Ngành Công nghệ Chế biến Gỗ', 2);
INSERT INTO topic(name, faculty_id) VALUES ('Ngành CN Kỹ thuật Cơ khí', 2);
INSERT INTO topic(name, faculty_id) VALUES ('Ngành CN Kỹ thuật Cơ điện tử', 2);
INSERT INTO topic(name, faculty_id) VALUES ('Tuyển sinh', 2);

INSERT INTO topic(name, faculty_id) VALUES ('Các vấn đề chung trong khoa CN May TT', 3);
INSERT INTO topic(name, faculty_id) VALUES ('Nghiên cứu khoa học Sinh viên', 3);
INSERT INTO topic(name, faculty_id) VALUES ('Công tác SV - Chế độ chính sách - K.CNMTT', 3);
INSERT INTO topic(name, faculty_id) VALUES ('Ngành Thiết kế Thời Trang', 3);
INSERT INTO topic(name, faculty_id) VALUES ('Ngành Công nghệ May', 3);
INSERT INTO topic(name, faculty_id) VALUES ('Tuyển sinh', 3);
INSERT INTO topic(name, faculty_id) VALUES ('Trợ giảng, Thỉnh giảng, đăng ký môn học CNMTT', 3);




