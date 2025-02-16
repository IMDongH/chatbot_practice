package com.practice.chatbot.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.SimpleExpression;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class QuerydslSupportUtils {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static OrderSpecifier<?>[] getOrder(Sort sort, OrderPathResolver resolver) {
        if (sort.isEmpty()) {
            return null;
        }

        List<OrderSpecifier<?>> orders = new ArrayList<>();
        for (Sort.Order order : sort) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            SimpleExpression orderPath = resolver.getOrderPath(order.getProperty());
            orders.add(new OrderSpecifier<>(direction, orderPath));
        }
        return orders.toArray(OrderSpecifier[]::new);
    }

    @FunctionalInterface
    public interface OrderPathResolver {

        SimpleExpression<?> getOrderPath(String property);
    }
}
