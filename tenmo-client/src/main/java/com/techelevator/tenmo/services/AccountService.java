package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountService {


    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_BASE_URL = "http://localhost:8080";

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public double getCurrentBalance() {

        double balance = 0;

        try {
            ResponseEntity<Double> response = restTemplate.exchange(API_BASE_URL + "/balance",
                    HttpMethod.GET, makeAuthEntity(), double.class);
            balance = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return balance;

    }

    public double withdrawAmountToSend(double withdrawAmount) {
            return getCurrentBalance() - withdrawAmount;

    }

    public double depositAmountToSend(double depositAmount) {
        return getCurrentBalance() +  depositAmount;
    }

//    public Account accountWithdrawingFrom(Account accounToWithdraw) {
//
//    }



    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    public void printBalance(double balance) {
        System.out.println("Your current balance is: $" + balance);

    }
}