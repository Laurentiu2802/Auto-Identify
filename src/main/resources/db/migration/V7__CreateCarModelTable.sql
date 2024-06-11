CREATE TABLE s3_model (
                          modelID BIGINT IDENTITY(1,1) PRIMARY KEY,
                          modelName NVARCHAR(50) NOT NULL,
                          brandID BIGINT NOT NULL,
                          FOREIGN KEY (brandID) REFERENCES s3_brand(brandID)
);

ALTER TABLE s3_model
    ADD CONSTRAINT CHK_ModelName_Length CHECK (LEN(modelName) BETWEEN 1 AND 50);
