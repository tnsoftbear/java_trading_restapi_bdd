INSERT INTO stored_user (username, password) VALUES
('user1', 'password1'),
('user2', 'password2'),
('user3', 'password3'),
('user4', 'password4');

INSERT INTO security (name) VALUES
('Apple'),
('Google');

INSERT INTO stored_order (price, quantity, type, security_id, user_id) VALUES
(100, 50, 0, 1, 1),
(200, 100, 1, 2, 2);
