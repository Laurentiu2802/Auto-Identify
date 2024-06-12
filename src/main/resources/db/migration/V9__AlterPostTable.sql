ALTER TABLE s3_post
    ADD categoryID BIGINT NOT NULL,
    brandID BIGINT NOT NULL,
    modelID BIGINT NOT NULL;


ALTER TABLE s3_post
    ADD CONSTRAINT fk_user FOREIGN KEY (userID) REFERENCES s3_user(userID);

ALTER TABLE s3_post
    ADD CONSTRAINT fk_category FOREIGN KEY (categoryID) REFERENCES s3_category(categoryID);

ALTER TABLE s3_post
    ADD CONSTRAINT fk_brand FOREIGN KEY (brandID) REFERENCES s3_brand(brandID);

ALTER TABLE s3_post
    ADD CONSTRAINT fk_model FOREIGN KEY (modelID) REFERENCES s3_model(modelID);