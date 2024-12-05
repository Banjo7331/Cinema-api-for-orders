CREATE TABLE `role` (
                        `id` CHAR(36) NOT NULL,
                        `name` VARCHAR(15) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
                        `id` CHAR(36) NOT NULL,
                        `email` VARCHAR(255) NOT NULL UNIQUE,
                        `password` VARCHAR(255) NOT NULL,
                        `phone_number` VARCHAR(15) NOT NULL,
                        `username` VARCHAR(15) NOT NULL UNIQUE,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `movie` (
                         `id` CHAR(36) NOT NULL,
                         `description` VARCHAR(255) NOT NULL,
                         `end_of_playing_date` DATE NOT NULL,
                         `length_in_minutes` INT NOT NULL CHECK (`length_in_minutes` >= 1 AND `length_in_minutes` <= 240),
                         `minimum_age_to_watch` INT NOT NULL CHECK (`minimum_age_to_watch` >= 3),
                         `movie_category` TINYINT NOT NULL CHECK (`movie_category` BETWEEN 0 AND 20),
                         `name` VARCHAR(100) NOT NULL,
                         `premiere_date` DATE NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `products` (
                            `id` CHAR(36) NOT NULL,
                            `code` VARCHAR(255) NOT NULL UNIQUE,
                            `name` VARCHAR(100) NOT NULL,
                            `price` DECIMAL(15, 2) NOT NULL,
                            `product_type` TINYINT NOT NULL CHECK (`product_type` BETWEEN 0 AND 1),
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `repertoir_full_day` (
                                      `id` CHAR(36) NOT NULL,
                                      `date` DATE NOT NULL UNIQUE,
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `room` (
                        `id` CHAR(36) NOT NULL,
                        `available` BOOLEAN NOT NULL,
                        `number` INT NOT NULL CHECK (`number` BETWEEN 1 AND 10),
                        `number_of_rows` INT NOT NULL CHECK (`number_of_rows` >= 1),
                        `number_of_seats` INT NOT NULL,
                        `special` BOOLEAN NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `seat` (
                        `id` CHAR(36) NOT NULL,
                        `broken` BOOLEAN NOT NULL,
                        `number` BIGINT NOT NULL,
                        `seat_type` TINYINT NOT NULL CHECK (`seat_type` BETWEEN 0 AND 2),
                        `room_id` CHAR(36) NOT NULL,
                        PRIMARY KEY (`id`),
                        CONSTRAINT `FK_seat_room` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `orders` (
                          `id` CHAR(36) NOT NULL,
                          `coupon_code` VARCHAR(255) DEFAULT NULL,
                          `is_reservation_for_movie` BOOLEAN NOT NULL,
                          `total_price` DECIMAL(15, 2) NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `seance` (
                          `id` CHAR(36) NOT NULL,
                          `hour_of_start` TIME(6) NOT NULL,
                          `taken_seats` INT NOT NULL,
                          `movie_id` CHAR(36) NOT NULL,
                          `repertoire_id` CHAR(36) NOT NULL,
                          `room_id` CHAR(36) NOT NULL,
                          PRIMARY KEY (`id`),
                          CONSTRAINT `FK_seance_movie` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`),
                          CONSTRAINT `FK_seance_repertoire` FOREIGN KEY (`repertoire_id`) REFERENCES `repertoir_full_day` (`id`),
                          CONSTRAINT `FK_seance_room` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `reservation` (
                               `id` CHAR(36) NOT NULL,
                               `attendance` BOOLEAN NOT NULL,
                               `date_created` DATETIME(6) DEFAULT NULL,
                               `email` VARCHAR(255) NOT NULL,
                               `last_updated` DATETIME(6) DEFAULT NULL,
                               `number_of_viewers` INT NOT NULL CHECK (`number_of_viewers` >= 1),
                               `phone_number` VARCHAR(15) NOT NULL,
                               `order_id` CHAR(36) DEFAULT NULL,
                               `seance_id` CHAR(36) NOT NULL,
                               `user_id` CHAR(36) DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `UK_order_id` (`order_id`),
                               CONSTRAINT `FK_reservation_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
                               CONSTRAINT `FK_reservation_seance` FOREIGN KEY (`seance_id`) REFERENCES `seance` (`id`),
                               CONSTRAINT `FK_reservation_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users_roles` (
                               `user_id` CHAR(36) NOT NULL,
                               `role_id` CHAR(36) NOT NULL,
                               PRIMARY KEY (`user_id`, `role_id`),
                               CONSTRAINT `FK_users_roles_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                               CONSTRAINT `FK_users_roles_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `orders_order_items` (
                                      `order_id` CHAR(36) NOT NULL,
                                      `order_items_id` CHAR(36) NOT NULL,
                                      PRIMARY KEY (`order_id`, `order_items_id`),
                                      CONSTRAINT `FK_order_items_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
                                      CONSTRAINT `FK_order_items_product` FOREIGN KEY (`order_items_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `reservation_seat` (
                                    `reservation_id` CHAR(36) NOT NULL,
                                    `seat_id` CHAR(36) NOT NULL,
                                    PRIMARY KEY (`reservation_id`, `seat_id`),
                                    CONSTRAINT `FK_reservation_seat` FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`id`),
                                    CONSTRAINT `FK_seat_reservation` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

