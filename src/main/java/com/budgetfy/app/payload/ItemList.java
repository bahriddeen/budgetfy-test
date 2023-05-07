package com.budgetfy.app.payload;

import java.util.List;

public record ItemList(
        Integer id,
        String name,
        List<Object> items,
        List<Integer> integerList,
        List<String> stringList,
        List<Integer> accountIds,
        List<Integer> categoryIds,
        List<String> transactionType,
        List<String> paymentTypes,
        List<String> paymentStatus

) {

}
