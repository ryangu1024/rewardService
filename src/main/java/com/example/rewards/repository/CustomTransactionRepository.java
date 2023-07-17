package com.example.rewards.repository;

import java.time.LocalDate;
import java.util.Map;

public interface CustomTransactionRepository {

    Integer sumRewardByUserIDAndTimestamp(String userID, LocalDate startDate, LocalDate endDate);
    Map<String, Integer> sumRewardByUserIDAndTimestampByMonth(String userID, LocalDate startDate, LocalDate endDate);

}
