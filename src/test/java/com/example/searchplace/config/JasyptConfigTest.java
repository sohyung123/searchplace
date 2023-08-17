package com.example.searchplace.config;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;


class JasyptConfigTest {

    @Test
    void 암호화복호화테스트() {

        JasyptConfig jasyptConfig = new JasyptConfig("my_jasypt_key12@#");
        StringEncryptor encryptor = jasyptConfig.stringEncryptor();

        String encKakao = "ONWMWajeg0t6JoNu5Yn9sMsziRZmcLcH4QFRVxnV3J1nzB2Q33EZyUpVCZvbbpFoAP7B328o9b0=";
        String encNaverId = "Dj6/805wlH4U22sqLqM1UXbF2urG8JWoxRBzgyOKjUA=";
        String encNaverSecret = "qhE5zePYojJc9XBLGbqqmbxJp5Pl2evD";

        String decKakao = "KakaoAK 51fd6e1b5fb862ffa1f71098737702e4";
        String decNaverId = "CwNB3MspiqwJBWYMuUYT";
        String decNaverSecret = "iuGJFBep9H";

        Assertions.assertThat(decKakao.equals(encryptor.decrypt(encKakao)));
        Assertions.assertThat(decNaverId.equals(encryptor.decrypt(encNaverId)));
        Assertions.assertThat(decNaverSecret.equals(encryptor.decrypt(encNaverSecret)));

    }
}