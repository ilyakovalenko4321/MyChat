CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS user_profiles (
                                             id SERIAL PRIMARY KEY,                             -- Уникальный идентификатор пользователя
                                             tag VARCHAR(50) NOT NULL UNIQUE,                   -- Имя пользователя
                                             name VARCHAR(100) NOT NULL,                        -- Имя
                                             surname VARCHAR(100) NOT NULL,                     -- Фамилия
                                             email VARCHAR(100) NOT NULL UNIQUE,                -- Электронная почта
                                             phone_number VARCHAR(20),                          -- Номер телефона (необязательный)
                                             weight INT NOT NULL,                               -- Вес пользователя
                                             height DOUBLE PRECISION NOT NULL,                  -- Рост пользователя
                                             hobby VARCHAR(50),                                 -- Хобби пользователя
                                             profession VARCHAR(50),                   -- Профессия пользователя
                                             earnings BIGINT,                                   -- Заработок пользователя
                                             age INT NOT NULL,                                  -- Возраст пользователя
                                             gender VARCHAR(10) CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')) NOT NULL,  -- Пол пользователя
                                             orientation VARCHAR(10) CHECK (orientation IN ('HETERO', 'HOMO', 'BI')) NOT NULL,  -- Ориентация
                                             about_me TEXT,                                     -- Описание профиля
                                             city VARCHAR(100) NOT NULL,                        -- Город проживания
                                             country VARCHAR(100) NOT NULL,                     -- Страна
                                             created_at TIMESTAMP DEFAULT NOW() NOT NULL       -- Дата создания профил
);

CREATE TABLE IF NOT EXISTS user_locations (
                                              id SERIAL PRIMARY KEY,
                                              user_tag VARCHAR(50) NOT NULL,                    -- Используем tag вместо user_id
                                              city VARCHAR(100),
                                              country VARCHAR(100),
                                              location GEOMETRY(Point, 4326),
                                              FOREIGN KEY (user_tag) REFERENCES user_profiles(tag) ON DELETE CASCADE  -- Внешний ключ ссылается на tag
);

CREATE TABLE IF NOT EXISTS profile_pictures (
                                                id SERIAL PRIMARY KEY,
                                                user_tag VARCHAR(50) NOT NULL,                    -- Используем tag вместо user_id
                                                picture_url VARCHAR(255) NOT NULL,
                                                FOREIGN KEY (user_tag) REFERENCES user_profiles(tag) ON DELETE CASCADE  -- Внешний ключ ссылается на tag
);

CREATE TABLE preferences (
                             id SERIAL PRIMARY KEY,
                             preferences_hobby VARCHAR(255),  -- Строка для хобби
                             preferences_professions VARCHAR(255),  -- Строка для профессий
                             min_earnings BIGINT,
                             min_bmi FLOAT8,
                             max_bmi FLOAT8,
                             min_height FLOAT8,
                             max_height FLOAT8,
                             min_age FLOAT8,
                             max_age FLOAT8,
                             distance INT,
                             rating INT DEFAULT 0,
                             user_tag VARCHAR(50) NOT NULL,                    -- Используем tag вместо id
                             FOREIGN KEY (user_tag) REFERENCES user_profiles(tag) ON DELETE CASCADE  -- Внешний ключ ссылается на tag
);
