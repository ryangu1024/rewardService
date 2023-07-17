package com.example.rewards.service;

import com.example.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RewardServiceTest {

    @Autowired
    RewardService rewardService;

    @MockBean
    TransactionRepository repository;

    @Test
    void getRewardPoint() {

        Map<String, Integer> repositoryResult = new HashMap<>();
        repositoryResult.put("2023-05",100);
        repositoryResult.put("2023-07",90);
        Mockito.when(repository.sumRewardByUserIDAndTimestamp("Bill", LocalDate.of(2023, 05, 01), LocalDate.of(2023, 07, 31))).thenReturn(190);
        Mockito.when(repository.sumRewardByUserIDAndTimestampByMonth("Bill", LocalDate.of(2023, 05, 01), LocalDate.of(2023, 07, 31))).thenReturn(repositoryResult);
        Map<String, Integer> result = rewardService.getRewardPoint("Bill", YearMonth.of(2023,05),3);
        assertTrue(result.containsKey("total"));
        assertTrue(result.get("total") == 190);
        assertTrue(result.containsKey("2023-05"));
        assertTrue(result.containsKey("2023-06"));
        assertTrue(result.get("2023-07") == 90);
        assertTrue(result.get("2023-05") == 100);
    }

}