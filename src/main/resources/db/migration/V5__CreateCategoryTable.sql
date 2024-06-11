CREATE TABLE s3_category (
                             categoryID BIGINT IDENTITY(1,1) PRIMARY KEY,
                             categoryName NVARCHAR(100) NOT NULL
);


-- Adding a constraint to ensure the length of categoryName is between 5 and 100
ALTER TABLE s3_category
    ADD CONSTRAINT CHK_CategoryName_Length CHECK (LEN(categoryName) BETWEEN 5 AND 100);
