package org.example.Configuration.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
