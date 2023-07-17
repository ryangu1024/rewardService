package com.example.rewards.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

public interface RewardService {

    public Map<String, Integer> getRewardPoint(String userID, YearMonth startMonth, Integer months);

    public void addTransaction(String userID, Float price, LocalDate timestamp);

}
