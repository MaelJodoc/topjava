DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
  (100000, DATE '2018-03-15' + TIME '07:00', 'завтрак', 700),
  (100000, DATE '2018-03-15' + TIME '14:00', 'обед', 1200),
  (100000, DATE '2018-03-15' + TIME '20:00', 'ужин', 500),

  (100000, DATE '2018-03-16' + TIME '07:00', 'завтрак', 500),
  (100000, DATE '2018-03-16' + TIME '14:00', 'обед', 1000),
  (100000, DATE '2018-03-16' + TIME '19:00', 'ужин', 500),

  (100000, DATE '2018-03-17' + TIME '07:34', 'завтрак', 400),
  (100000, DATE '2018-03-17' + TIME '12:10', 'обед', 1000),
  (100000, DATE '2018-03-17' + TIME '19:25', 'ужин', 400),

  (100001, DATE '2018-03-15' + TIME '07:00', 'завтрак админа', 700),
  (100001, DATE '2018-03-15' + TIME '14:00', 'обед админа', 1200),
  (100001, DATE '2018-03-15' + TIME '20:00', 'ужин админа', 500);

