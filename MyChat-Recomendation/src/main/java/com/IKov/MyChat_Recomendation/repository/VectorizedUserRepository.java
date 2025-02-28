package com.IKov.MyChat_Recomendation.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.KnnSearch;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.IKov.MyChat_Recomendation.domain.user.GENDER;
import com.IKov.MyChat_Recomendation.domain.vector.VectorizedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Repository
@RequiredArgsConstructor
@Slf4j
public class VectorizedUserRepository {

    private final ElasticsearchClient client;

    private static final String INDEX_MALE = "vectorized_users_male";
    private static final String INDEX_FEMALE = "vectorized_users_female";
    private static final String INDEX_OTHER = "vectorized_users_other";

    public void save(VectorizedUser user) {
        try {
            String indexName = getIndexName(user.getGender(), false);
            IndexRequest<VectorizedUser> request = IndexRequest.of(i -> i
                    .index(indexName)
                    .id(user.getUserTag())
                    .document(user));
            client.index(request);
        } catch (IOException e) {
            log.error("Ошибка при сохранении пользователя с тегом {}: {}", user.getUserTag(), e.getMessage());
            throw new RuntimeException("Ошибка при сохранении данных", e);
        }
    }

    /**
     * @param gender Пол пользователя
     * @param opposite Если true, возвращает индекс для противоположного пола
     */
    private String getIndexName(GENDER gender, boolean opposite) {
        if (!opposite) {
            if (gender == GENDER.MALE) {
                return INDEX_MALE;
            } else if (gender == GENDER.FEMALE) {
                return INDEX_FEMALE;
            } else {
                return INDEX_OTHER;
            }
        } else {
            // При поиске противоположного пола: если не MALE, то ищем MALE, иначе FEMALE
            return (gender != GENDER.MALE) ? INDEX_MALE : INDEX_FEMALE;
        }
    }

    public List<VectorizedUser> findSimilarUsers(VectorizedUser vectorizedUser, Integer neighborsNumber) {
        try {
            String index = getIndexName(vectorizedUser.getGender(), true);

            List<Float> queryVectorList = DoubleStream.of(vectorizedUser.getVector())
                    .mapToObj(d -> (float) d)
                    .toList();

            SearchRequest searchRequest = SearchRequest.of(s -> s
                    .index(index)
                    .knn(List.of(KnnSearch.of(knn -> knn
                            .field("vector")
                            .queryVector(queryVectorList)
                            .k(neighborsNumber)
                            .numCandidates(neighborsNumber * 2)
                    )))
            );

            SearchResponse<VectorizedUser> response = client.search(searchRequest, VectorizedUser.class);
            return response.hits().hits().stream().map(Hit::source).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Ошибка при поиске схожих пользователей: {}", e.getMessage());
            throw new RuntimeException("Ошибка при поиске схожих пользователей", e);
        }
    }
}
