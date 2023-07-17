package com.example.rewards.utility;

import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class rewardUtilityTest {

    @Test
    void computeReward() {
        assertEquals(rewardUtility.computeReward(20f),0);
        assertEquals(rewardUtility.computeReward(50f),0);
        assertEquals(rewardUtility.computeReward(60f),10);
        assertEquals(rewardUtility.computeReward(100f),50);
        assertEquals(rewardUtility.computeReward(120f),90);
        assertEquals(rewardUtility.computeReward(150f),150);
    }

    @Test
    void populateResult() {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("2023-07", 100);
        Map<String, Integer> resultMap = rewardUtility.populateResult(testMap, 100, YearMonth.of(2023,05), 3);
        assertTrue(resultMap.containsKey("total"));
        assertTrue(resultMap.get("total") == 100);
        assertTrue(resultMap.containsKey("2023-05"));
        assertTrue(resultMap.containsKey("2023-06"));
        assertTrue(resultMap.get("2023-07") == 100);
        assertTrue(resultMap.get("2023-05") == 0);
    }
}