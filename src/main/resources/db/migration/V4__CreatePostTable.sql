CREATE TABLE s3_post
(
    postID INT IDENTITY(1,1) PRIMARY KEY,
    description NVARCHAR(200),
    userID INT,
    FOREIGN KEY (userID) REFERENCES  s3_user(userID)
)