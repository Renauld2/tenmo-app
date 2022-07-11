package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

public class TransferService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_BASE_URL = "http://localhost:8080";




    private AccountService accountService = new AccountService();
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    public Transfer createSendBucks(Transfer transfer) {

        System.out.println("********");
        System.out.println(transfer);

        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "/tenmo_transfer",
                    HttpMethod.POST, makeTransferEntity(transfer), Transfer.class);
            if (response.hasBody()) {
                transfer = response.getBody();
            }
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    public User[] getAllAccounts() {
        User[] accountsList = null;

        try {
            ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL + "/tenmo_account",
                    HttpMethod.GET, makeAuthEntity(), User[].class);
            accountsList = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accountsList;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers  = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }


    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    public String askForUserId () {
        User[] accountList = getAllAccounts();
       for (User users : accountList) {
           System.out.println(users.getId());
           System.out.println(users.getUsername());;
       }

        System.out.println();
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter ID of user you are sending to (0 to cancel):");
        //System.out.println(accountService.getAllAccounts());
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
        String userId = askForUserId();
        String sendingAmount = askForAmountToSend();

        int userIdToSendTo = Integer.parseInt(askForUserId());
        double amountToSend = Double.parseDouble(askForAmountToSend());

        Scanner userInput = new Scanner(System.in);
        System.out.println("You are sending: $" + sendingAmount + " To userId " + userId);
        System.out.println("Are you Sure? (Y/N)");
        String userSelection = userInput.nextLine();

        if (userSelection.equals("Y") || userSelection.equals("y")) {

//            accountService.withdrawAmountToSend(amountToSend);
//            accountService.depositAmountToSend(amountToSend);

            transfer.setAccountTo(userIdToSendTo);
            transfer.setAmount(amountToSend);

            createSendBucks(transfer);

        } else if (userSelection == "N" || userSelection == "n"){
            System.out.println("Cancelling Transaction");
        }

    }
}

