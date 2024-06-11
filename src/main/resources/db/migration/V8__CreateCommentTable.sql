CREATE TABLE s3_comment (
                            commentID BIGINT IDENTITY(1,1) PRIMARY KEY,
                            postID INT NOT NULL,
                            userID INT NOT NULL,
                            description NVARCHAR(100) NOT NULL,
                            FOREIGN KEY (postID) REFERENCES s3_post(postID),
                            FOREIGN KEY (userID) REFERENCES s3_user(userID)
);

ALTER TABLE s3_comment
    ADD CONSTRAINT CHK_Description_Length CHECK (LEN(description) BETWEEN 1 AND 100);
