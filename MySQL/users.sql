/*
	This is experimental to test the database connection to the app.
	Key columns are added.
*/
CREATE TABLE users 
(
    user_id int NOT NULL AUTO_INCREMENT,
    username varchar(30) NOT NULL,
    email varchar(50) NOT NULL,
    password varchar(100) NOT NULL,
    PRIMARY KEY(user_id)
)