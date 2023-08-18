package com.example.searchplace.repository;

import com.example.searchplace.entity.Keyword;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class KeywordRepositoryTest {

    @Autowired
    KeywordRepository keywordRepository;

    @DisplayName("springdata jpa 테스트")
    @Test
    void findByKeywordNameTest() {
        //when
        Keyword keyword = keywordRepository.findByKeywordName("동작구");

        //then
        Assertions.assertThat(keyword).isNotNull();
        Assertions.assertThat(keyword.getKeywordName()).isEqualTo("동작구");
    }
}