package com.practice.chatbot.repository.chat;

import com.practice.chatbot.model.chat.QThreadEntity;
import com.practice.chatbot.model.chat.ThreadEntity;
import com.practice.chatbot.repository.QuerydslSupportUtils;
import com.practice.chatbot.web.controller.chat.response.ThreadResponse;
import com.practice.chatbot.web.vo.PageResponse;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ThreadRepositoryImpl implements ThreadRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public PageResponse<ThreadResponse> findByUserIdWithPagination(String userId, Pageable pageable) {
        QThreadEntity thread = QThreadEntity.threadEntity;

        // 데이터 조회
        List<ThreadEntity> threadList = queryFactory
            .selectFrom(thread)
            .where(thread.user.id.eq(userId))
            .orderBy(QuerydslSupportUtils.getOrder(pageable.getSort(), this::getOrderPath))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // 전체 데이터 개수 조회
        long total = queryFactory
            .select(thread.count())
            .from(thread)
            .where(thread.user.id.eq(userId))
            .fetchOne();


        // QueryDSL 결과를 Spring Data의 Page 객체로 변환
        Page<ThreadResponse> pageResult = new PageImpl<>(threadList.stream().map(ThreadResponse::of).toList(), pageable, total);

        // PageResponse로 변환하여 반환
        return new PageResponse<ThreadResponse>().of(pageResult);
    }

    private SimpleExpression<?> getOrderPath(final String fieldName) {
        return switch (fieldName) {
            default -> QThreadEntity.threadEntity.createdAt;
        };
    }
}