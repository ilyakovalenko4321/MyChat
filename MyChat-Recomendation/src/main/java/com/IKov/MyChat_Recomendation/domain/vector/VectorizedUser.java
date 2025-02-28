package com.IKov.MyChat_Recomendation.domain.vector;

import com.IKov.MyChat_Recomendation.domain.user.GENDER;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "#{T(com.IKov.MyChat_Recomendation.domain.user.GENDER).MALE.toString() == 'MALE' ? 'users_male' : 'users_female'}")
public class VectorizedUser {

    private String userTag;
    private GENDER gender;
    private double[] vector;

}
