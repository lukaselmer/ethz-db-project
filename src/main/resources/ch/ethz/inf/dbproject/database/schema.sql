CREATE TABLE city
   (id        INTEGER PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL);
 
CREATE TABLE category
   (id        INTEGER PRIMARY KEY AUTO_INCREMENT,
   name       VARCHAR(255) NOT NULL);

CREATE TABLE user
   (id         INTEGER PRIMARY KEY AUTO_INCREMENT,
   name        VARCHAR(255) NOT NULL UNIQUE,
   password    VARCHAR(255) NOT NULL);

CREATE TABLE project
   (id         INTEGER PRIMARY KEY AUTO_INCREMENT,
   user_id     INTEGER NOT NULL,
   city_id     INTEGER NOT NULL,
   category_id INTEGER NOT NULL,
   title       VARCHAR(255) NOT NULL UNIQUE,
   description TEXT,
   goal        DECIMAL(10,2) NOT NULL CHECK (goal > 0.0),
   start       DATETIME NOT NULL,
   end         DATETIME NOT NULL,
   FOREIGN KEY (user_id) REFERENCES user(id),
   FOREIGN KEY (city_id) REFERENCES city(id),
   FOREIGN KEY (category_id) REFERENCES category(id));

CREATE TABLE stretch_goal
   (id        INTEGER PRIMARY KEY AUTO_INCREMENT,
   amount     DECIMAL(10,2) NOT NULL CHECK (amount > 0.0),
   bonus      TEXT NOT NULL,
   project_id INTEGER NOT NULL,
   FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE);
   
CREATE TABLE funding_amount
   (id         INTEGER PRIMARY KEY AUTO_INCREMENT,
   project_id  INTEGER NOT NULL,
   amount      DECIMAL(10,2) NOT NULL CHECK (amount > 0.0),
   reward      TEXT,
   FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE);

CREATE TABLE fund
   (id                INTEGER PRIMARY KEY AUTO_INCREMENT,
   funding_amount_id  INTEGER NOT NULL,
   user_id            INTEGER NOT NULL,
   FOREIGN KEY (funding_amount_id) REFERENCES funding_amount(id) ON DELETE CASCADE,
   FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE);

CREATE TABLE comment
   (id        INTEGER PRIMARY KEY AUTO_INCREMENT,
   user_id    INTEGER NOT NULL,
   project_id INTEGER NOT NULL,
   text       VARCHAR(255) NOT NULL,
   date       DATETIME NOT NULL,
   FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE CASCADE,
   FOREIGN KEY(project_id) REFERENCES project(id) ON DELETE CASCADE);