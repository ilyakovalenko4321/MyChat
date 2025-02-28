package com.IKov.MyChat_Recomendation.service;

import com.IKov.MyChat_Recomendation.domain.user.UserPropertiesToVectorize;

public interface UserVectorizeService {

    void vectorize(UserPropertiesToVectorize properties);

}
