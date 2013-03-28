INSERT INTO city (id, name) 
VALUES 
  (1, 'Zürich'),
  (2, 'Bern'),
  (3, 'Chur'),
  (4, 'Luzern'),
  (5, 'Turgi'),
  (6, 'Rapperswil');

INSERT INTO category (id, name)
VALUES 
 (1, 'Software'),
 (2, 'Hardware'),
 (3, 'Movie');

INSERT INTO user (id, name, password)
VALUES
 (1, 'Ivo', '1234'),
 (2, 'Fredi', '2345'),
 (3, 'Luke', '3456');

INSERT INTO project
VALUES
 (1, 1, 5, 3, 'FH Aliens vs ETH Monsters', 'Ein Film: FH-ler gegen ETH-ler. Wer gewinnt?', 100000.00, '2013-03-28 12:00:00', '2013-06-28 12:00:00'),
 (2, 2, 6, 2, 'USB Stick ohne Akku', 'Ein USB Stick mit Solarzelle', 20000.00, '2013-03-29 13:00:00', '2013-08-28 10:00:00'),
 (3, 3, 1, 1, 'Crowd Funding Platform', 'Super Coole App für Crowd Funding', 100.00, '2013-04-1 13:00:00', '2013-04-10 10:00:00');

INSERT INTO comment
VALUES
 (1, 2, 1, 'geiler Film - freue mich', NOW()),
 (2, 3, 1, 'naja.. FH gewinnt doch so oder so', NOW()),
 (3, 1, 2, 'macht das denn Sinn?', NOW()),
 (4, 3, 2, 'naja..', NOW()), 
 (5, 2, 2, 'jo sicher, mann!', NOW()),
 (6, 1, 3, 'Get denn das en Crowd-Funding Inception?', NOW()),
 (7, 2, 3, 'coole sache!', NOW());
 
INSERT INTO funding_amount (id, project_id, amount, reward)
VALUES
 (1, 1, 1000.00, 'Special DVD Box'),
 (2, 1, 5000.00, 'Special DVD Box + Premiere-Ticket'),
 (3, 1, 10000.00, 'Special DVD Box + VIP-Premiere-Ticket'),
 (4, 2, 250.00, 'USB Stick'),
 (5, 2, 500.00, 'USB Stick in fancy box'),
 (6, 3, 0.25, '1 year free membership on crowd funding platform'),
 (7, 3, 0.50, '5 year free membership on crowd funding platform'),
 (8, 3, 1.00, 'unlimited year free membership on crowd funding platform'); 

INSERT INTO stretch_goal (id, project_id, amount, bonus)
VALUES
 (1, 1, 150000, 'Additional DVD'),
 (2, 2, 30000, 'Additional 32 GB on each Stick'),
 (3, 2, 40000, 'Additional 64 GB on each Stick'),
 (4, 3, 200, '1 year additional membership');

INSERT INTO fund (id, funding_amount_id, user_id)
VALUES
 (1, 1, 2),
 (2, 3, 2),
 (3, 5, 1),
 (4, 6, 1),
 (5, 2, 3),
 (6, 4, 3);  
