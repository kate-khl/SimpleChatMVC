DELETE from MESSAGE;
DELETE from USERS_CHATS;
DELETE from CHAT;
DELETE from USER;

INSERT INTO USER (ID, EMAIL, NAME, PASSWORD, ROLE) VALUES 
	(1000, 'user1@test.com', 'UserName1', '123', 'user'), 
	(1001, 'user2@test.com', 'UserName2', '123', 'user'),
	(1002, 'user3@test.com', 'UserName3', '123', 'user'),
	(1003, 'user4@test.com', 'UserName4', '123', 'user');

INSERT INTO CHAT (ID, NAME, AUTHOR_ID) VALUES (1004, 'ChatName', 1000);

INSERT INTO USERS_CHATS (CHAT_ID, USER_ID) VALUES (1004, 1000), (1004, 1001);

INSERT INTO MESSAGE (ID, DATE, VALUE, AUTHOR_ID, CHAT_ID) VALUES 
	(1100, '2020-09-19 13:00:00', 'Message1', 1000, 1004),
	(1101, '2020-09-19 13:01:00', 'Message2', 1001, 1004),
	(1102, '2020-09-19 13:02:00', 'Message3', 1000, 1004);