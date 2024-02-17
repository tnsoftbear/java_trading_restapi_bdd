CREATE TABLE IF NOT EXISTS stored_user (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           username VARCHAR(255),
                                           password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS security (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS stored_order (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            price INT,
                                            quantity INT,
                                            type INT,
                                            fulfilled BOOLEAN DEFAULT FALSE NOT NULL,
                                            security_id BIGINT,
                                            user_id BIGINT,
                                            FOREIGN KEY (security_id) REFERENCES security(id),
                                            FOREIGN KEY (user_id) REFERENCES stored_user(id)
);
