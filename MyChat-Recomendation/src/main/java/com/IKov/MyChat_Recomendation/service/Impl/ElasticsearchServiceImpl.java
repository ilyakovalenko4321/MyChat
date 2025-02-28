package com.IKov.MyChat_Recomendation.service.Impl;

import com.IKov.MyChat_Recomendation.domain.user.GENDER;
import com.IKov.MyChat_Recomendation.domain.vector.VectorizedUser;
import com.IKov.MyChat_Recomendation.repository.VectorizedUserRepository;
import com.IKov.MyChat_Recomendation.service.ElasticsearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private final VectorizedUserRepository vectorizedUserRepository;

    @Override
    public void saveUser(VectorizedUser user) {
        vectorizedUserRepository.save(user);
    }

    @Override
    public Optional<VectorizedUser> findByUserTag(String userTag) {
        // TODO: Реализовать поиск пользователя по тегу
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
}
