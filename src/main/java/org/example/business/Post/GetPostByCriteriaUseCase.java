package org.example.business.Post;

import org.example.business.dto.postDTO.GetPostsByCriteriaRequest;
import org.example.business.dto.postDTO.GetPostsByCriteriaResponse;
import org.springframework.context.annotation.Bean;


public interface GetPostByCriteriaUseCase {
    GetPostsByCriteriaResponse getPosts(GetPostsByCriteriaRequest request);
}
