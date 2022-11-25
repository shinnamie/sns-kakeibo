-- ユーザーテーブル作成												
-- DROP TABLE IF EXISTS users CASCADE;												
												
-- CREATE TABLE users (
--   id           serial       PRIMARY KEY
--   , mail_address VARCHAR(50)  NOT NULL UNIQUE
--   , password     VARCHAR(50)  NOT NULL
-- );

-- INSERT INTO users
--   (mail_address, password)
-- VALUES
--   ('nao@co.jp', 'password')
--   , ('nage@co.jp', 'password')
--   , ('sao3@co.jp', 'password');

-- プロフィールテーブル作成
DROP TABLE IF EXISTS profile CASCADE;

CREATE TABLE profile (
  id              serial   PRIMARY KEY
  , user_id       INTEGER   REFERENCES users
  , name          NOT NULL DEFAULT '未設定'
  , gender        INTEGER
  , date_of_birth DATE
  , remarks       VARCHAR(100)
);

-- SELECT * FROM users;