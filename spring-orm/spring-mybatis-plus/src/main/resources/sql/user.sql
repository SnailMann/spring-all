-- test.`user` definition

CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(100) DEFAULT NULL,
                        `email` varchar(100) DEFAULT NULL,
                        `age` int DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

