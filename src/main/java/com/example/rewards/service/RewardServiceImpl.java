package com.example.rewards.service;

import com.example.rewards.entity.TransactionDetails;
import com.example.rewards.repository.TransactionRepository;
import com.example.rewards.utility.rewardUtility;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

@Service
public class RewardServiceImpl implements RewardService{

    private TransactionRepository repository;

    public RewardServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Map<String, Integer> getRewardPoint(String userID, YearMonth startMonth, Integer months) {
        LocalDate startDate = startMonth.atDay(1);
        LocalDate endDate = startMonth.plusMonths(months-1).atEndOfMonth();
        Map<String, Integer> rewardByMonth = repository.sumRewardByUserIDAndTimestampByMonth(userID, startDate, endDate);
        Integer totalReward = repository.sumRewardByUserIDAndTimestamp(userID, startDate, endDate);
        Map<String, Integer> populatedResult = rewardUtility.populateResult(rewardByMonth, totalReward, startMonth, months);
        return populatedResult;
    }

    @Override
    public void addTransaction(String userID, Float price, LocalDate timestamp) {

        Integer reward = rewardUtility.computeReward(price);
        TransactionDetails newTransaction = new TransactionDetails();
        newTransaction.setUserID(userID);
        newTransaction.setPrice(price);
        newTransaction.setReward(reward);
        newTransaction.setTimestamp(timestamp);
        repository.save(newTransaction);
    }
}
