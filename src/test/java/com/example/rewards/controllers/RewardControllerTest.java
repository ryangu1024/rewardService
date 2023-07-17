package com.example.rewards.controllers;

import com.example.rewards.service.RewardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureWebMvc
@AutoConfigureMockMvc
class RewardControllerTest {

    @Autowired
    RewardController rewardController;

    @MockBean
    RewardService rewardService;

    @Autowired
    MockMvc mvc;

    @Test
    void getReward() throws Exception{
        Map<String, Integer> serviceResult = new HashMap<>();
        serviceResult.put("2023-05",100);
        serviceResult.put("2023-06",0);
        serviceResult.put("2023-07",90);
        serviceResult.put("total",190);
        Mockito.when(rewardService.getRewardPoint("Bill", YearMonth.of(2023,05),3)).thenReturn(serviceResult);
        RequestBuilder mockRequest = MockMvcRequestBuilders.get("/reward/getReward?userID=Bill&startMonth=2023-05&months=3");
        MvcResult response = mvc.perform(mockRequest).andReturn();
        String correctResponse = "{\"total\":190,\"2023-05\":100,\"2023-06\":0,\"2023-07\":90}";
        JSONAssert.assertEquals(correctResponse, response.getResponse().getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}