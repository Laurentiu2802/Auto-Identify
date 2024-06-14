package org.example.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class CountPosts {
    private Long userID;
    private String username;
    private Long postCount;

    public CountPosts(Long userID, String username, Long postCount){
        this.userID = userID;
        this.username = username;
        this.postCount = postCount;
    }
}
