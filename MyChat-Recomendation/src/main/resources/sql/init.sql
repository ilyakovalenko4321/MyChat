CREATE TABLE recommendation_statistics (
                                 gender VARCHAR(10) PRIMARY KEY, -- 'Male' или 'Female'
                                 avg_height DOUBLE PRECISION,
                                 avg_age INT,
                                 avg_weight DOUBLE PRECISION,
                                 avg_earnings BIGINT,
                                 avg_beauty DOUBLE PRECISION,
                                 avg_personality_extraversion DOUBLE PRECISION,
                                 avg_personality_openness DOUBLE PRECISION,
                                 avg_personality_conscientiousness DOUBLE PRECISION,
                                 avg_life_value_family DOUBLE PRECISION,
                                 avg_life_value_career DOUBLE PRECISION,
                                 avg_activity_level DOUBLE PRECISION
);
ALTER TABLE recommendation_statistics ADD COLUMN number BIGINT;