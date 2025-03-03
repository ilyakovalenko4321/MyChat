package com.IKov.MyChat_Recomendation.repository;

import com.IKov.MyChat_Recomendation.domain.vector.VectorizedUser;
import com.IKov.MyChat_Recomendation.domain.vector.VectorizedUserSql;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VectorizedUserRepositorySql extends JpaRepository<VectorizedUserSql, String> {

    VectorizedUserSql getByUserTag(String userTag);

}
