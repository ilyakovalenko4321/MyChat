package com.IKov.MyChat_Recomendation.repository;

import com.IKov.MyChat_Recomendation.domain.user.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    // Данные для подключения к базе данных (они могут быть заменены на параметры из application.properties)
    private static final String URL = "jdbc:postgresql://localhost:5432/mychat";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Rts28022007";

    // Метод для получения профиля по тегу
    public Profile getProfileByTag(String tag) {
        Profile profile = null;

        // SQL запрос
        String sql = "SELECT * FROM user_profiles WHERE tag = ?";

        // Подключение к базе данных
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Установка параметра для запроса
            statement.setString(1, tag);

            // Выполнение запроса
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Если профиль найден, создаем объект Profile
                    profile = mapResultSetToProfile(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Логирование ошибки подключения и запроса
        }

        return profile;
    }

    public List<Profile> getProfilesByTags(List<String> tags){
        List<Profile> profiles = new ArrayList<>();

        // SQL запрос
        String sql = "SELECT * FROM user_profiles WHERE tag = ?";

        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql)){

            for(int i = 0; i < tags.size(); i++){
                statement.setString(1, tags.get(i));

                try(ResultSet resultSet = statement.executeQuery()){
                    if(resultSet.next()){
                        profiles.add(mapResultSetToProfile(resultSet));
                    }
                }
            }

        }catch (Exception e){
        }
        return profiles;
    }

    // Метод для преобразования строки данных из ResultSet в объект Profile
    private Profile mapResultSetToProfile(ResultSet resultSet) throws SQLException {
        Profile profile = new Profile();
        profile.setId(resultSet.getLong("id"));
        profile.setTag(resultSet.getString("tag"));
        profile.setName(resultSet.getString("name"));
        profile.setSurname(resultSet.getString("surname"));
        profile.setEmail(resultSet.getString("email"));
        profile.setPhoneNumber(resultSet.getString("phone_number"));
        profile.setWeight(resultSet.getInt("weight"));
        profile.setHeight(resultSet.getDouble("height"));
        profile.setEarnings(resultSet.getLong("earnings"));
        profile.setAge(resultSet.getInt("age"));
        profile.setGender(Profile.GENDER.valueOf(resultSet.getString("gender")));
        profile.setAboutMe(resultSet.getString("about_me"));
        profile.setCity(resultSet.getString("city"));
        profile.setCountry(resultSet.getString("country"));
        profile.setPersonalityExtraversion(resultSet.getDouble("personality_extraversion"));
        profile.setPersonalityOpenness(resultSet.getDouble("personality_openness"));
        profile.setPersonalityConscientiousness(resultSet.getDouble("personality_conscientiousness"));
        profile.setLifeValueFamily(resultSet.getDouble("life_value_family"));
        profile.setLifeValueCareer(resultSet.getDouble("life_value_career"));
        profile.setActivityLevel(resultSet.getDouble("activity_level"));
        profile.setBeauty(resultSet.getInt("beauty"));
        return profile;
    }
}
