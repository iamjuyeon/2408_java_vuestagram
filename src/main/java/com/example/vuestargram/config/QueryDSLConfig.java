package com.example.vuestargram.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDSLConfig {
    @PersistenceContext // jpa에서 db와 상호작용하기 위한 객체(entity 매니져)
    // spring context에 자동으로 주입을 해주는 annotation
    private EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em); //bean 으로 등록해서 언제든지 사용할 수 있도록 함
    }
}
