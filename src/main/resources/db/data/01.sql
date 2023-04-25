-- Note: You can use flyway, liquibase; a versioning system; to manage your migrations. 
-- User

INSERT INTO users(username,created_at,deleted,first_name,last_name,locked,password,phone_number) VALUES('user1','2023-04-25 14:16:05.817',FALSE,'John','Doe',FALSE,'$2a$10$zpr8qZyg2U0Ytues/0H1.uJuY7izFWTtxFQThvxx4PIK8yA7oI.6a',NULL);
