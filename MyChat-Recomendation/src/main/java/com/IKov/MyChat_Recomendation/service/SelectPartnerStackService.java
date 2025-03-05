package com.IKov.MyChat_Recomendation.service;

import com.IKov.MyChat_Recomendation.domain.user.Profile;
import com.IKov.MyChat_Recomendation.domain.user.UserPropertiesToVectorize;
import com.IKov.MyChat_Recomendation.domain.vector.VectorizedUser;

import java.util.List;

public interface SelectPartnerStackService {

    List<Profile> formPartnerStack(String RequestUserTag);

}
