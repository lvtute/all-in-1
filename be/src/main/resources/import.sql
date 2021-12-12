SET @pw = '$2a$12$QNvN/tk6gimZC.9i/OqW5uxKYH7BTGc5x3B1ET2fzA4Q.Y9/foTlS'

INSERT INTO role(id, name) VALUES(1, 'ROLE_ADVISER');
INSERT INTO role(id, name) VALUES(2, 'ROLE_DEAN');
INSERT INTO role(id, name) VALUES(3, 'ROLE_ADMIN');

INSERT INTO faculty(name) VALUES('Công nghệ thông tin');
INSERT INTO faculty(name) VALUES('Cơ khí chế tạo máy');
INSERT INTO faculty(name) VALUES('Công nghệ may và thiết kế thời trang');

INSERT INTO topic(id, name, faculty_id) VALUES (1, 'Học tập của SV  - Đăng ký MH', 1);
INSERT INTO topic(id, name, faculty_id) VALUES (2, 'Nghiên cứu khoa học sinh viên', 1);
INSERT INTO topic(id, name, faculty_id) VALUES (3, 'Điểm rèn luyện - Học bổng', 1);
INSERT INTO topic(id, name, faculty_id) VALUES (4, 'Công tác SV - Chế độ chính sách - K.CNTT', 1);
INSERT INTO topic(id, name, faculty_id) VALUES (5, 'Các vấn đề chung trong khoa CNTT', 1);
INSERT INTO topic(id, name, faculty_id) VALUES (6, 'Ngành Công nghệ Thông tin', 1);
INSERT INTO topic(id, name, faculty_id) VALUES (7, 'Ngành Kỹ thuật Dữ liệu', 1);

INSERT INTO topic(id, name, faculty_id) VALUES (8, 'Các vấn đề chung trong khoa CKM', 2);
INSERT INTO topic(id, name, faculty_id) VALUES (9, 'Nghiên cứu khoa học sinh viên', 2);
INSERT INTO topic(id, name, faculty_id) VALUES (10, 'Trợ giảng, Thỉnh giảng, đăng ký môn học CKM', 2);
INSERT INTO topic(id, name, faculty_id) VALUES (11, 'Công tác SV - Chế độ chính sách - K.CKM', 2);
INSERT INTO topic(id, name, faculty_id) VALUES (12, 'Ngành Công nghệ Chế biến Gỗ', 2);
INSERT INTO topic(id, name, faculty_id) VALUES (13, 'Ngành CN Kỹ thuật Cơ khí', 2);
INSERT INTO topic(id, name, faculty_id) VALUES (14, 'Ngành CN Kỹ thuật Cơ điện tử', 2);
INSERT INTO topic(id, name, faculty_id) VALUES (15, 'Tuyển sinh', 2);

INSERT INTO topic(id, name, faculty_id) VALUES (16, 'Các vấn đề chung trong khoa CN May TT', 3);
INSERT INTO topic(id, name, faculty_id) VALUES (17, 'Nghiên cứu khoa học Sinh viên', 3);
INSERT INTO topic(id, name, faculty_id) VALUES (18, 'Công tác SV - Chế độ chính sách - K.CNMTT', 3);
INSERT INTO topic(id, name, faculty_id) VALUES (19, 'Ngành Thiết kế Thời Trang', 3);
INSERT INTO topic(id, name, faculty_id) VALUES (20, 'Ngành Công nghệ May', 3);
INSERT INTO topic(id, name, faculty_id) VALUES (21, 'Tuyển sinh', 3);
INSERT INTO topic(id, name, faculty_id) VALUES (22, 'Trợ giảng, Thỉnh giảng, đăng ký môn học CNMTT', 3);

INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `role_id`) VALUES (1, 'admin@gmail.com', @pw, 'admin', 'Luong Van Thuan', 3);

-- IT Users
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (2, 'tranvanc@gmail.com', @pw, 'mod1', N'Trần Văn Nam', 1, 2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (3, 'nguyenvana@gmail.com', @pw, 'mod2', 'Nguyen Van A', 1, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (4, 'jbutt@gmail.com', @pw, 'jamesbutt', N'James Butt', 1, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (5, 'josephine_darakjy@darakjy.org', @pw, 'josephine_darakjy', 'Josephine Darakjy', 1, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (6, 'art@venere.org', @pw, 'art', 'Art	Venere', 1, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (7, 'lpaprocki@hotmail.com', @pw, 'lpaprocki', 'Lenna Paprocki', 1, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (8, 'donette.foller@cox.net', @pw, 'donette.foller', 'Donette Foller', 1, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (9, 'simona@morasca.com', @pw, 'simona', 'Simona Morasca', 1, 1);
-- CKM
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (10, 'sage_wieser@cox.net', @pw, 'sage_wieser', 'Sage Wieser', 2, 2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (11, 'leota@hotmail.com', @pw, 'leota', 'Leota Dilliard', 2, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (12, 'mitsue_tollner@yahoo.com', @pw, 'mitsue_tollner', 'Mitsue Tollner', 2, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (13, 'amaclead@gmail.com', @pw, 'amaclead', 'Abel Maclead', 2, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (14, 'minna_amigon@yahoo.com', @pw, 'minna_amigon', 'Minna Amigon', 2, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (15, 'kris@gmail.com', @pw, 'kris', 'Kris Marrier', 2, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (16, 'calbares@gmail.com', @pw, 'calbares', 'Cammy Albares', 2, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (17, 'gruta@cox.net', @pw, 'gruta', 'Graciela Ruta', 2, 1);
-- CNMTT
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (18, 'kiley.caldarera@aol.com', @pw, 'kiley.caldarera', 'Kiley Caldarera', 3, 2);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (19, 'gladys.rim@rim.org', @pw, 'gladys.rim', 'Gladys Rim', 3, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (20, 'meaghan@hotmail.com', @pw, 'meaghan', 'Meaghan Garufi', 3, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (21, 'mattie@aol.com', @pw, 'mattie', 'Mattie Poquette', 3, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (22, 'bette_nicka@cox.net', @pw, 'bette_nicka', 'Bette Nicka', 3, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (23, 'fletcher.flosi@yahoo.com', @pw, 'fletcher.flosi', 'Fletcher Flosi', 3, 1);
INSERT INTO user(`id`, `email`, `password`, `username`, `full_name`, `faculty_id`, `role_id`) VALUES (24, 'yuki_whobrey@aol.com', @pw, 'yuki_whobrey', 'Yuki Whobrey', 3, 1);

-- user (3-9)_topic(1-7)
INSERT INTO user_topic(`user_id`, topic_id) VALUES (3, 1);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (4, 2);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (5, 2);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (6, 3);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (7, 4);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (8, 5);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (9, 5);

-- user 11-17 / topic 8-15
INSERT INTO user_topic(`user_id`, topic_id) VALUES (11, 8);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (12, 9);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (13, 10);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (14, 11);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (15, 12);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (16, 13);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (17, 14);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (17, 15);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (16, 15);

-- user -19-24 / topic 16-22
INSERT INTO user_topic(`user_id`, topic_id) VALUES (19, 16);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (20, 17);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (21, 18);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (22, 19);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (23, 20);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (24, 21);
INSERT INTO user_topic(`user_id`, topic_id) VALUES (24, 22);

INSERT INTO question(title, content, name, email, faculty_id, topic_id, created_date) VALUES ('Đăng kí đề tài môn tiểu luận chuyên ngành', 'Theo thông báo của khoa thì từ 5-10 sẽ update link đăng ký đề tài trên trang dkdt.fit.hcmute.edu.vn. Nhưng hiện tại em vẫn chưa thấy ạ?','Nguyễn ĐÌnh Thượng Thượng', 'thuongndt@gmail.com', 1, 1, generateRandomDateTime());
INSERT INTO question(title, content, name, email, faculty_id, topic_id, created_date) VALUES ('Hỏi thông tin liên lạc', 'Hiện tại em không có cách liên lạc với  thầy Nguyễn Trường Hải lớp Công nghệ phần mềm . Khoa có thể cho xin sđt hoặc email của thầy được không ạ. Em xin cảm ơn.', 'Trần Thái Nguyên', 'nguyentt@gmail.com', 1, 5, generateRandomDateTime());
INSERT INTO question(title, content, name, email, faculty_id, topic_id, created_date) VALUES ('Thắc mắc', 'Hiện tại lớp Công nghệ phần mềm Sáng thứ 6 tiết 3-6 của Thầy Nguyễn Trường Hải, đến nay là 3 tuần em chưa nhận được thông báo gì. Và em đã xem không có trong danh sách lớp bị hủy. Em cũng đã có gửi tin nhắn cho Phòng Đào tạo nhưng chưa nhận được phản hồi. Kính mong Khoa CNTT giải quyết giùm em. Em xin cảm ơn', 'Trần Thái Nguyên', 'nguyentt@gmail.com', 1, 1, generateRandomDateTime());
INSERT INTO question(title, content, name, email, faculty_id, topic_id, created_date) VALUES ('cho em xin email của cô Trương Thị Khánh Dịp ạ, em ko tìm thấy trên trang fit.hcmute.edu.vn', '', 'Trần Thái Nguyên', 'nguyentt@gmail.com', 1, 6, generateRandomDateTime());
INSERT INTO question(title, content, name, email, faculty_id, topic_id, created_date) VALUES ('Cách tính điểm rèn luyện HKII năm học 2020-2021', 'Cho tôi gửi lời chào đến Ban tư vấn! Chúc các thành viên trong Ban tư vấn sức khỏe trong đại dịch Covid-19 để luôn đồng hành cùng sinh viên hoàn thành chương trình học tập đặt kết quả cao nhất. Xin Ban tư vấn giải đáp thắc mắc của tôi về điểm rèn luyện của sinh viên HKII năm học 2020-2021 được tính như thế nào khi mà thời gian sinh viên học online nhiều, không thể tham gia các hoạt động của trường. Xin cám ơn !', 'Nguyễn Thanh Quang', 'quangnt@gmail.com', 1, 3, generateRandomDateTime());
INSERT INTO question(title, content, name, email, faculty_id, topic_id, created_date) VALUES ('Thắc mắc môn tốt nghiệp và môn thực hành sư phạm', 'Thưa thầy cô, em có 2 vấn đề thắc mắc nhờ thầy cô tư vấn giúp em: sv lớp sư phạm cntt có thể học chuyên đề tốt nghiệp thay vì làm khóa luận được không ạ.hiện giờ hệ sư phạm của trường đã không còn công nhận sư phạm, vậy mấy môn thực hành sư phạm kì này của em có cần học không ạ, vì hiện giờ vẫn đang trong lịch cứng của em.cảm ơn thầy cô đã quan tâm.', 'Võ Văn Phước', 'phuocvv@gmail.com', 1, 5, generateRandomDateTime());
INSERT INTO question(title, content, name, email, faculty_id, topic_id, created_date) VALUES ('Hướng dẫn khóa luận tốt nghiệp.', 'Em là sv k15. Hiện tại em mới thực tập tốt nghiệp song. Có một số thông về giảng viên em chưa cập nhập vì em ko đi học củng khá lâu ạ. ban tư vấn có thể cho em biết hiện tại những giảo viên em có thể đăng ký dc ạ. xin qúy thầy cô tư vấn giúp em 1 số giảng viên. em xin chân thành cảm ơn.', 'Bùi Minh Tiên', 'tienbm@gmail.com', 1, 6, generateRandomDateTime());
INSERT INTO question(title, content, name, email, faculty_id, topic_id, created_date) VALUES ('Về việc làm đơn xin thôi học', 'Em muốn làm thủ tục thôi học . Mong thầy giúp đở ạ ', 'Nguyễn Thành Đạt', 'datnt@gmail.com', 1, 5, generateRandomDateTime());
INSERT INTO question(title, content, name, email, faculty_id, topic_id, created_date) VALUES ('Xin mở lớp Nhập môn lập trình', 'Dạ chào thầy, hiện tại theo như thông tin thì thầy Tú nói là hệ đại trà đã mở thêm một lớp Nhập môn lập trình học lại nhưng trong mục đăng ký thì không tìm thấy bất kỳ lớp nào ạ. Mong thầy mở lớp để cho sinh viên có thể hoàn thành môn này ạ', 'Pham Xuan Nhuan', 'nhuanpx@gmail.com', 1, 1, generateRandomDateTime());
INSERT INTO question(title, content, name, email, faculty_id, topic_id, created_date) VALUES ('Xin vào lớp Nhập môn lập trình', 'Dạ chào thầy (cô),Theo em được biết là các thầy phụ trách Nhập môn lập trình đều bận cả nên không thể xin mở lớp được. Trong khi đó môn học này không xuất hiện trong danh sách các môn được đăng ký vào học. Vậy em phải làm như thế nào ạ vì đây là năm cuối của em rồi. Em rất lo và mong thầy cô hỗ trợ em cũng như một vài bạn khác nữa ạ!', 'Bùi Lê Tấn Phi', 'phiblt@gmail.com', 1, 1, generateRandomDateTime());

INSERT INTO question(title, content, name, email, faculty_id, topic_id, created_date) VALUES ('Thắc mắc về nộp bằng anh văn', 'Em chào ad ạ. Hiện em là sinh viên năm 2 ngành Công nghệ Chế tạo Máy. Em có thắc mắc là nếu đầu năm 3 em không nộp bằng anh văn để qui đổi điểm anh văn 1 2 3( chưa học anh văn 1 2 3) thì có đăng ký làm đồ án không ạ. Em cảm ơn ạ.', 'Nguyễn Thái Hòa', 'hoant@gmail.com', 2, 8, generateRandomDateTime());
INSERT INTO question(title, content, name, email, faculty_id, topic_id, created_date) VALUES ('Xin giấy xác nhận sinh viên', 'Dạ xin chào thầy/ cô ạ.Em là sinh viên Nguyễn Hồ Anh Tân , MSSV: 17146326  Ngành:  cơ điện tửEm muốn xin giấy xác nhận sinh viên bằng file thì phải làm thế nào ạ?Mong nhận được phản hồi sớm nhất từ thầy/ cô ạ.Em xin cảm ơn ạ', 'Nguyễn Hồ Anh Tân', 'tannha@gmail.com', 2, 11, generateRandomDateTime());
INSERT INTO question(title, content, name, email, faculty_id, topic_id, created_date) VALUES ('Mail sinh viên của em đã bị xoá ko vào đc làm sao để em khôi phục lại ạ', '', 'Nguyễn Quốc Quý', 'quynq@gmail.com', 2, 11, generateRandomDateTime());
INSERT INTO question(title, content, name, email, faculty_id, topic_id, created_date) VALUES ('LỖI TRANG DẠY HỌC SỐ (HDS)', 'Kính gửi PĐT Em tên là Lâm Nguyễn Hữu Đam Mssv : 2116544 em là tân sinh viên, Hiện tại trang DHS ( dạy học số) của em chưa cập nhật lịch các môn học mặc dù đã có thời khó biểu ( trong file đính kèm) Nên em không thể vào học onl được. Em gửi mail này kính mong trường có thể giải quyết và phản hồi dùm em để em có thế theo kịp bài. Em cảm ơn.', 'Lâm Nguyễn Hữu Đam', 'damlnh@gmail.com', 2, 11, generateRandomDateTime());
INSERT INTO question(title, content, name, faculty_id, topic_id, created_date) VALUES ('Vấn đề học Anh Văn', 'Em chào thầy cô ạ, em là tân sinh viên K21 của trường mình khoa Chế Tạo Máy hệ CLC Tiếng Việt với MSSV là 21143414, em có 1 câu hỏi là : nếu em có bằng ielts 6. trở lên thì em có được miễn học AV không ạ, và bằng ielts đó có thể được dùng cho AV đầu ra được không ạ ? Em cảm ơn thầy cô đã tư vấn cho em ', 'Dương Thanh Tuấn', 2, 8, generateRandomDateTime());
INSERT INTO question(title, content, name, faculty_id, topic_id, created_date) VALUES ('Tuyển sinh', 'Phiếu điểm thi em đã gởi phiếu điểm thi cho bưu điện để nộp về trường nhưng có lẽ do dịch bệnh nên chưa tới. Em nhận được thông báo là phải cập nhật phiếu điểm trong chiều 25/9 thì phải làm sao?(vì không còn giữ phiếu điểm nên em không có thông tin, số liệu để cập nhật)', 'Nguyễn Hoàng Trung Hiếu', 2, 15, generateRandomDateTime());
INSERT INTO question(title, content, name, faculty_id, topic_id, created_date) VALUES ('em nộp học phí trễ do dịch covid nên bị đình chỉ học', 'do dịch nên em bị đình chỉ học.nay e da đóng học phí đủ xin được hoãn đình chỉ để em đc học tiếp ạ', 'nguyễn xuân tài', 2, 11, generateRandomDateTime());
INSERT INTO question(title, content, name, faculty_id, topic_id, created_date) VALUES ('Tìm kiếm thông tin của giáo viên', 'Dạ em xin chào ban tư vấn. Em mong ban tư vấn có thể tìm giúp em email của giáo viên Vương Thị Ngọc Hân ( giảng dạy môn sức bền vật liệu ( cơ khí ) ) để em liên hệ với cô ạ.. Xin trân thành cám ơn', 'Nguyễn Đức LÝ', 2, 8, generateRandomDateTime());
INSERT INTO question(title, content, name, faculty_id, topic_id, created_date) VALUES ('Đăng ký môn học', 'Con tôi học ngành CNKT cơ điện tử muốn đăng học môn điều khiển tự động mã lớp AUCO330329_03 do Thầy Võ Lâm Chương giảng dạy (không có trong mục đăng ký ngoài kế hoạch), xin được hướng dẩn cách đăng ký. Tôi xin chân thành cám ơn!', 'Nguyễn Minh Huy', 2, 10, generateRandomDateTime());
INSERT INTO question(title, content, name, faculty_id, topic_id, created_date) VALUES ('Thắc mắc về nghiên cứu khoa học', 'Em xin chào ban tư vấn ạ! Ban tư vấn cho em hỏi một chút về vấn đề nghiên cứu khoa học ạ.Em hiện đang theo học chuyên ngành công nghệ chế tạo máy và em muốn tham gia các cuộc thi nghiên cứu khoa học ạ. Ban tư vấn có thể cho em biết cần những điều kiện gì để tham gia, liên hệ ai và một team của em cần bao nhiêu thành viên là hợp lý ạ? Em xin cảm ơn ban tư vấn ạ!', 'Nguyễn Minh Huy', 2, 10, generateRandomDateTime());

INSERT INTO question(title, content, name, faculty_id, topic_id, created_date) VALUES ('Đã nghỉ học 1 năm bây giờ em muốn đi học trở lại', 'Em xin chào ban tư vấn! Em học ngành Công nghệ may, khoá 15, khoa Công nghệ may và Thời trang, em muốn hỏi  bạn tư vấn trường hợp của em là em đã hoàn thành 4 năm học nhưng vẫn còn nợ môn, vì hoàn cảnh gia đình em phải nghỉ học 1 năm, nhưng em không có làm đơn xin bảo lưu thì em có bị kỷ luật gì không a và  bây giờ em muốn đi học trở lại thì em sẽ làm như thế nào ạ? Mong bạn tư vấn trả lời thắc mắc giúp em ạ. Em xin cảm ơn!', 'Danh Thị Kim Thắm', 3, 18, generateRandomDateTime());
INSERT INTO question(title, content, name, faculty_id, topic_id, created_date) VALUES ('Thắc mắc về kết quả tổng kết năm học', 'Dạ, em muốn hỏi là em đã được nhận quyết định tạm nghỉ từ học kì II năm học 2019-2020. Nhưng tại sao kết quả tổng kết năm học của em vẫn tính cả học kì II ạ. Và nó có ảnh hưởng gì tới quá trình học tập không ạ.Em cảm ơn', 'Lữ Thị Kim Tuyến', 3, 16, generateRandomDateTime());
INSERT INTO question(title, content, name, faculty_id, topic_id, created_date) VALUES ('Hỏi đăng kí môn học ạ', 'Dạ cho em hỏi , e đã xem chương trình đào tạo của khoa công nghệ may và thời trang. Em là khóa 2019-Em thấy phần đăng kí môn học khối tự chọn : em thấy chia ra nhiều phần  tự chọn. Em không biết đăng kí sao để không phải dư ạ. Và học bao nhiêu để đủ tốt nghiệp. Cần bao nhiêu chỉ bắt buộc và tự chọn ạ.-Em mong quý thầy cô giải đáp giúp em.-Em trân thành cảm ơn.', 'Bùi Thị Ánh Ngọc', 3, 22, generateRandomDateTime());
INSERT INTO question(title, content, name, faculty_id, topic_id, created_date) VALUES ('Thắc mắc về việc chuyển ngành học', 'Em chào thầy, cô ạ Em có vài thắc mắc xin nhờ thầy, cô giải đáp cho emVấn đề của e là về việc chuyển ngành họcVậy những điều kiện gì e phải đáp ứng để được chuyển ngành học vậy ạ ? Em có ý định chuyển ngành cùng khoa Hiện giờ e có nợ môn đại cương thì e có được chuyển không Và e có cần phải hoàn tất chương trình đang học hay ngừng lại và bắt đầu học chuyên ngành khácVới cả, nếu được chuyển ngành e sẽ học học kì mới với những bạn khoá sau đúng không ạe cám ơn thầy, cô đã giải đáp cho em', 'La Lý Thuỳ Dương', 3, 16, generateRandomDateTime());
INSERT INTO question(title, content, name, faculty_id, topic_id, created_date) VALUES ('Thắc mắc về vấn đề học bổng', 'Kính thư thầy cô,Em tên là Nguyễn Thị Thanh Nga Mssv:19109146 Em có thắc mắc về các điều kiện xét học bổng của ngành công nghệ may và số lượng sinh viên được nhận học thường là bao nhiêu vậy ạ?Em rất mong nhận được sự giải đáp từ thầy cô.Em xin trân trọng cảm ơn.', 'Nguyễn Thị Thanh Nga', 3, 18, generateRandomDateTime());
INSERT INTO question(title, content, name, faculty_id, topic_id, created_date) VALUES ('Đăng kí môn học đã hết chỗ đăng kí', 'Em chào thầy cô ạ, em tên là Phạm Thị Mỹ Lộc MSSV 16109143 hiện đang là sinh viên ngành kinh tế gia đình khoa CNM & TT. Lúc đăng kí học phần vừa qua em dự định đăng kí một số môn nhưng đã hết chỗ. Em muốn xin đăng kí vào lớp học phần đó thì phải làm mẫu đơn xin đăng kí học phần của trường phải không ạ?  Em sẽ  nộp cho ai để phê duyệt và thời gian khi nào thì nộp được ạ?', 'Phạm Thị Mỹ Lộc', 3, 22, generateRandomDateTime());

INSERT INTO question(title, content, name, faculty_id, topic_id, adviser_id, answer, approved_by_dean, created_date) VALUES ('Hỏi xếp lớp chuyên ngành cho học kỳ 2', 'Chào thầy. Mong thầy tư vấn về quy trình đăng ký và phân loại về thứ tự ưu tiên chuyên ngành cho sinh viên ở năm ba ạ. Em cảm ơn', 'Phạm Xuân Nhuận', 1, 5, 8, 'Khoảng giữa học kỳ, khoa sẽ tổ chức buổi gặp gỡ SV để tư vấn chọn chuyện ngành. Mỗi bạn sẽ đăng ký nguyện vọng chọn chuyên ngành theo thứ tự ưu tiên 1,2,3. Tinh thần là sẽ khoa sẽ sắp xếp các bạn vô chuyên ngành theo đúng nguyện vọng. Tuy nhiên, thỉnh thoảng có trường hợp các bạn tập trung vào đăng ký ngành này quá nhiều, trong khi ngành kia thì rất ít bạn chọn. Khi đó khoa sẽ dựa vào kết quả học tập của các bạn để xét và sắp xếp lại theo thứ tự ưu tiên.', 1, CURRENT_TIMESTAMP);
INSERT INTO question(title, content, name, faculty_id, topic_id, adviser_id, answer, approved_by_dean, created_date) VALUES ('Chuyển số tín chỉ cho môn Kĩ thuật lập trình', 'Môn kĩ thuật lập trình của khóa 17 là 4 chỉ, nhưng lúc em học lại là 3 chỉ. Giờ em phải lên phòng đào tạo để tiến hành chuyển 3 thành 4 hay nao khoa sẽ tự chuyển cho em ạ ?', 'Ngô Minh Thu', 1, 5, 9, 'Chào em. Em làm đơn xin thay thế môn học từ 4 chỉ xuống 3 chỉ và nộp về văn phòng Khoa để BCN Khoa xem xét nhé. Thân.', 0, CURRENT_TIMESTAMP);
INSERT INTO `question` VALUES (29,'2021-12-05 19:49:29.749000',0,'2021-12-05 19:49:33.143000',_binary '\0',NULL,0,'<p><em><strong>Chào Thầy/Cô,</strong></em></p>\n<p><em><strong>Em là SV khoá 12, hiện tại e vẫn còn theo học hệ chính quy. Trên tài khoản online có ghi là thời gian học 2012-2020. Em không rõ là học kỳ mấy của năm 2020 là e sẽ hết hạn theo học ạ?</strong></em></p>\n<p><em><strong>Em cảm ơn Thầy/Cô.</strong></em>&nbsp;</p>','baonlq@gmail.com','Nguyễn Lê Quốc Bảo','Thời gian hết hạn học chính quy',1,3,1,1),(31,'2021-12-05 19:53:29.267000',0,'2021-12-05 19:53:29.267000',_binary '\0',NULL,0,'<p><br></p>','tuanth@gmail.com','Trần Hữu Tuấn','Cho em hỏi đào tạo đại học không chính quy 2019 đợt 2 đến bây giờ có kết quả chưa vậy ạ. Em cảm ơn.',0,3,1,1),(32,'2021-12-05 19:54:55.190000',0,'2021-12-05 19:54:55.190000',_binary '\0',NULL,0,'<p><em><strong>Bây giờ em có tinh thần không tốt nên bây giờ em xin rút hết toàn bộ môn học ( trừ pháp luật đại cương ) được k ạ</strong></em><strong><br>\n</strong><em><strong>em chỉ học PLĐC rồi năm 4 e đăng kí lại được k ạ. Em cảm ơn.</strong></em><br>\n&nbsp;</p>','daoqp@gmail.com','Quyết Phong Đào','Rút học phần',0,3,1,1),(33,'2021-12-05 19:56:22.746000',0,'2021-12-05 19:56:22.746000',_binary '\0',NULL,0,'<p>cho em xin thời khóa biểu hk2 :((((</p>','ducnm@gmail.com','NGUYỄN MINH ĐỨC','em xin thời khóa biểu',0,3,1,1),(34,'2021-12-05 19:57:10.475000',0,'2021-12-05 19:57:10.475000',_binary '\0',NULL,0,'<p>Em muốn tạm dừng khóa 2019-2020 và 2020-2021 thì phải làm sao ạ&nbsp;</p>','phongdq@gmail.com','Đào Quyết Phong','Tạm dừng',0,3,1,1),(35,'2021-12-05 19:58:38.235000',0,'2021-12-05 19:58:38.235000',_binary '\0',NULL,0,'<p>Kính thưa quý thầy/cô, các môn tự chọn em đã học xong hết, bây giờ các môn này đổi mã MH thì trường có tự cập nhật cho sinh viên hay phải làm đơn đổi mã môn học? Em cám ơn ạ.</p>','quanh@gmail.com','Hoàng Quân','Về việc học các môn thay thế',0,3,1,1),(36,'2021-12-05 20:00:04.325000',0,'2021-12-05 20:00:07.728000',_binary '\0',NULL,0,'<p><br></p>','khanhhq@gmail.com','Hồ Quốc Khánh ','Thắc mắc môn \"Liên hệ doanh nghiệp\"',1,3,1,1),(37,'2021-12-05 20:01:17.465000',0,'2021-12-06 08:18:59.793000',_binary '\0',NULL,0,'<p>Cho em hỏi là bắt đầu từ kì 2 là môn VB bị bỏ &nbsp;và thay thế bằng môn tin học trong kỹ thuật phải không ạ, bởi vì trên danh sách môn tương đương thì em ko thấy.</p>\n<p>Em cảm ơn!!</p>','huungpq@gmail.com','Phạm Quốc Hùng','Lập trình Visual Basic',2,3,1,1),(38,'2021-12-05 20:02:07.976000',0,'2021-12-05 20:02:07.976000',_binary '\0',NULL,0,'<p><br></p>','vutv@gmail.com','Trần Văn Vũ','ĐĂNG KÝ MÔN HỌC VISUAL BASIC',0,3,1,1),(39,'2021-12-05 20:03:00.899000',0,'2021-12-06 19:28:55.710000',_binary '\0',NULL,0,'<p>Chào thầy (cô), em hiện tại đang là sinh viên năm 4 ngành CNTT, thầy (cô) cho em hỏi em có thể xin vào 1 lớp học phần khi đã đủ sinh viên rồi được không ạ, và thủ tục như thế nào ạ.</p>\n<p>Em cám ơn.</p>','luanhs@gmail.com','Hồ Sỹ Luận ','Thủ tục xin vào lớp',3,3,1,1);
