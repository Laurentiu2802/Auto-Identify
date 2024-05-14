CREATE TABLE s3_user_role
(
    id INT IDENTITY(1,1) NOT NULL,
    userID INT NOT NULL,
    role_name NVARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT UQ_User_Role UNIQUE (userID, role_name),
    FOREIGN KEY (userID) REFERENCES s3_user (userID)
);