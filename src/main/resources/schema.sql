-- -----------------------------------------------------
-- Table country
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS country (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(45) NOT NULL UNIQUE,
  country_code VARCHAR(5) NOT NULL UNIQUE
);

-- -----------------------------------------------------
-- Table state
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS state (
  id INT AUTO_INCREMENT PRIMARY KEY,
  country_id INT NOT NULL,
  name VARCHAR(45) NOT NULL,
  CONSTRAINT fk_state_country_id FOREIGN KEY (country_id) REFERENCES country(id)
);

-- -----------------------------------------------------
-- Table city
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS city (
  id INT AUTO_INCREMENT PRIMARY KEY,
  state_id INT NOT NULL,
  name VARCHAR(45) NOT NULL,
  CONSTRAINT fk_city_state_id FOREIGN KEY (state_id) REFERENCES state(id)
);

-- -----------------------------------------------------
-- Table users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR_IGNORECASE(50) NOT NULL UNIQUE,
  password VARCHAR_IGNORECASE(500) NOT NULL,
  city_id INT,
  name VARCHAR(45),
  mobile_number VARCHAR(15)  UNIQUE,
  email VARCHAR(45) UNIQUE,
  gst_number VARCHAR(45) UNIQUE,
  address VARCHAR(255) DEFAULT NULL,
  create_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  otp VARCHAR(6),
  otp_creation_time TIMESTAMP,
  enabled boolean not null,
  CONSTRAINT fk_users_city_id FOREIGN KEY (city_id) REFERENCES city(id)
);


-- -----------------------------------------------------
-- Table authorities
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS authorities (
    username VARCHAR_IGNORECASE(50) NOT NULL,
    authority VARCHAR_IGNORECASE(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);


-- -----------------------------------------------------
-- Table item
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS item (
  id INT AUTO_INCREMENT PRIMARY KEY,
  users_id INT NOT NULL,
  name VARCHAR(45) NOT NULL,
  current_price DECIMAL(10,2) NOT NULL,
  description VARCHAR(255),
  create_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  status BOOLEAN NOT NULL,
  CONSTRAINT fk_item_users_id FOREIGN KEY (users_id) REFERENCES users(id)
);

-- -----------------------------------------------------
-- Table order_detail
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS order_detail (
  id INT AUTO_INCREMENT PRIMARY KEY,
  total_amt DOUBLE NOT NULL,
  status VARCHAR(45) NOT NULL,
  customer_name VARCHAR(45) NOT NULL,
  customer_number VARCHAR(45),
  create_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  order_number VARCHAR(50) UNIQUE
);

-- -----------------------------------------------------
-- Table order_item
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS order_item (
  id INT AUTO_INCREMENT PRIMARY KEY,
  order_detail_id INT NOT NULL,
  item_id INT NOT NULL,
  price_at_order DECIMAL(10,2) NOT NULL,
  create_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  quantity INT,
  CONSTRAINT fk_order_item_order_detail_id FOREIGN KEY (order_detail_id) REFERENCES order_detail(id),
  CONSTRAINT fk_order_item_item_id FOREIGN KEY (item_id) REFERENCES item(id)
);

-- -----------------------------------------------------
-- Table query
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS query (
  id INT AUTO_INCREMENT PRIMARY KEY,
  users_id INT NOT NULL,
  name VARCHAR(45) NOT NULL,
  mobile_number VARCHAR(15) NOT NULL,
  description VARCHAR(255) NOT NULL,
  create_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_query_users_id FOREIGN KEY (users_id) REFERENCES users(id)
);

-- -----------------------------------------------------
-- Insert Data
-- -----------------------------------------------------
INSERT INTO country (id, name, country_code) VALUES (1, 'India', '+91');

INSERT INTO state (id, country_id, name) VALUES
(1, 1, 'Madhya Pradesh'),
(2, 1, 'Delhi'),
(3, 1, 'Maharashtra');

INSERT INTO city (id, state_id, name) VALUES
(1, 1, 'Indore'),
(2, 1, 'Bhopal'),
(3, 1, 'Jabalpur'),
(4, 2, 'Delhi'),
(5, 3, 'Mumbai');
