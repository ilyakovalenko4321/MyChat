package com.IKov.MyChat_Recomendation.domain.vector;

import com.IKov.MyChat_Recomendation.domain.user.GENDER;
import com.IKov.MyChat_Recomendation.domain.user.UserTemporalData;
import jakarta.persistence.Transient;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.transaction.annotation.Transactional;

@Data
public class VectorizedUser {

    private String userTag;
    private GENDER gender;
    private double[] vector;

}
