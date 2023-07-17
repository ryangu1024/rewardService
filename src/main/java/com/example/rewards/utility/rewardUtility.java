package com.example.rewards.utility;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class rewardUtility {

    public static Integer computeReward(Float price){
        Integer reward = 0;
        if(price >= 100){
            reward = ((int)(price-100))*2;
            reward += 50;
        }
        else{
            if(price >= 50){
                reward = (int)(price-50);
            }
        }
        return reward;
    }

    public static Map<String, Integer> populateResult(Map<String, Integer> queryResult, Integer totalReward, YearMonth startMonth, Integer months){
        Map<String, Integer> result = new HashMap<>(queryResult);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        for(int i = 0; i < months; i++){
            YearMonth month = startMonth.plusMonths(i);
            String monthString = month.format(dateFormatter);
            if(!result.containsKey(monthString)){
                result.put(monthString, 0);
            }
        }
        result.put("total", totalReward);
        return result;
    }
}
