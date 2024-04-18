CREATE TABLE s3_user (
                         userID INT IDENTITY(1,1) PRIMARY KEY,
                         username VARCHAR(255),
                         password VARCHAR(5000),
                         description VARCHAR(255)
);