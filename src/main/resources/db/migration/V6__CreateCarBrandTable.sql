CREATE TABLE s3_brand (
                          brandID BIGINT IDENTITY(1,1) PRIMARY KEY,
                          brandName NVARCHAR(50) NOT NULL
);


ALTER TABLE s3_brand
    ADD CONSTRAINT CHK_BrandName_Length CHECK (LEN(brandName) BETWEEN 3 AND 50);
