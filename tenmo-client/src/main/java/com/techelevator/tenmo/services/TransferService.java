package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

public class TransferService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_BASE_URL = "http://localhost:8080";

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    public Transfer createSendBucks() {
        Transfer transfer = new Transfer();

        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "/tenmo_transfer",
                    HttpMethod.POST, makeAuthEntity(), Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }


    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    public String askForUserId () {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter ID of user you are sending to (0 to cancel):");
        String selectedId = userInput.nextLine();

        return selectedId;
    }

    public String askForAmountToSend () {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter amount:  ");
        String selectedAmount  =  userInput.nextLine();

        return selectedAmount;
    }

    public void printSendBucks(Transfer transfer) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("You are sending: $" + askForAmountToSend() + " To userId " + askForUserId());
        System.out.println("Are you Sure? (Y/N)");
        String userSelection = userInput.nextLine();
        if (userSelection == "Y" || userSelection == "y") {
            //call  create Transfer
        } else if (userSelection == "N" || userSelection == "n"){
            System.out.println("Cancelling Transaction");
        }

    }
}

