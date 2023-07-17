package com.example.rewards.controllers;

import com.example.rewards.service.RewardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

@RestController
@RequestMapping("reward")
public class RewardController {

    RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/getReward")
    public String getReward(@RequestParam String userID, @RequestParam(value = "startMonth", required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth startMonth, @RequestParam(defaultValue = "3") Integer months){
        if(startMonth == null){
            startMonth = YearMonth.now().minusMonths(months-1);
        }
        Map<String, Integer> reward = rewardService.getRewardPoint(userID, startMonth, months);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString;
        try{
            jsonString = objectMapper.writeValueAsString(reward);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        return jsonString;
    }

    @PostMapping(value = "/addTransaction")
    public void addTransaction(@RequestParam(value = "userID") String userID, @RequestParam(value = "price") Float price, @RequestParam(value = "date", required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate date){
        if(date == null){
            date = LocalDate.now();
        }
        rewardService.addTransaction(userID,price,date);
    }

}
