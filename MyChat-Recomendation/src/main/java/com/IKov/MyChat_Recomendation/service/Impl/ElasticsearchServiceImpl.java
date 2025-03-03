package com.IKov.MyChat_Recomendation.service.Impl;

import com.IKov.MyChat_Recomendation.domain.user.GENDER;
import com.IKov.MyChat_Recomendation.domain.user.UserTemporalData;
import com.IKov.MyChat_Recomendation.domain.vector.VectorizedUser;
import com.IKov.MyChat_Recomendation.repository.VectorizedUserRepository;
import com.IKov.MyChat_Recomendation.service.ElasticsearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private final VectorizedUserRepository vectorizedUserRepository;

    @Override
    public void saveUser(VectorizedUser user, UserTemporalData temporalData) {
        vectorizedUserRepository.save(user, temporalData);
        log.info("Пользователь: {}успешно coхранен в elasticsearch", user.getUserTag());
    }

    @Override
    public Optional<VectorizedUser> findByUserTag(String userTag) {
        return Optional.empty();
    }

    @Override
    public List<VectorizedUser> findByGender(GENDER gender) {
        // TODO: Реализовать поиск пользователей по полу
        return List.of();
    }

    @Override
    public List<VectorizedUser> findByVector(double[] vector) {
        // TODO: Реализовать поиск пользователей по вектору
        return List.of();
    }

    @Override
    public void deleteUser(String userTag) {
        // TODO: Реализовать удаление пользователя
    }

    @Override
    public void dropAll() {
        for(int i = 1; i < 6; i++){
            vectorizedUserRepository.dropIndex(GENDER.MALE, i);
        }
        for(int i = 1; i < 6; i++){
            vectorizedUserRepository.dropIndex(GENDER.FEMALE, i);
        }
    }

    @Override
    public void saveAllUsers(List<VectorizedUser> users, List<UserTemporalData> temporalDataList) {
        vectorizedUserRepository.saveAll(users, temporalDataList);
        log.info("Сохранено {} пользователей в Elasticsearch", users.size());
    }

}
