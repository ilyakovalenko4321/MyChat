CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS user_profiles (
                                             id SERIAL PRIMARY KEY,
                                             tag VARCHAR(50) NOT NULL UNIQUE,
                                             name VARCHAR(100) NOT NULL,
                                             surname VARCHAR(100) NOT NULL,
                                             email VARCHAR(100) NOT NULL UNIQUE,
                                             password VARCHAR(255) NOT NULL,
                                             phone_number VARCHAR(20),
                                             date_of_birth DATE,
                                             gender VARCHAR(10) CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),
                                             orientation VARCHAR(10) CHECK (orientation IN ('HETERO', 'HOMO', 'BI')),
                                             about_me TEXT,
                                             created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS user_locations (
                                              id SERIAL PRIMARY KEY,
                                              user_id INT NOT NULL UNIQUE,
                                              city VARCHAR(100),
                                              country VARCHAR(100),
                                              location GEOMETRY(Point, 4326),
                                              FOREIGN KEY (user_id) REFERENCES user_profiles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS profile_pictures (
                                                id SERIAL PRIMARY KEY,
                                                user_id INT NOT NULL,
                                                picture_url VARCHAR(255) NOT NULL,
                                                FOREIGN KEY (user_id) REFERENCES user_profiles(id) ON DELETE CASCADE
);
