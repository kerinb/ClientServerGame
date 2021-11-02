package com.kerinb.clientgamechallenge;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ClientApplication {
    private static String userDisc = "";
    private static int playerID = 0;

    public static void main(String[] args) throws IOException, InterruptedException, JSONException {
        String str_response = getStringRequest("http://localhost:8090/");
        System.out.println(str_response);

        str_response = getStringRequest("http://localhost:8090/gameStatus/");
        System.out.println("GAME STATUS: " + str_response);

        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter username");  // I will need to do some sanitization here first, since enterin a new line breaks things

        userDisc = myObj.nextLine();  // Read user input
        System.out.println("Username is: " + userDisc);  // Output user input
        postRequest("http://localhost:8090/registerNewPlayer", userDisc);

        System.out.println("UserID: " + playerID);
        String gameStatus = getStringRequest("http://localhost:8090/getGameStatus");
        System.out.println("has game started?: " + gameStatus );
        while (!gameStatus.equals("GAME HAS BEGUN")) {
            Thread.sleep(1000);
            gameStatus = getStringRequest("http://localhost:8090/getGameStatus");
        }

        System.out.println("GAME STATUS: " + gameStatus);

        while (gameStatus.equals("GAME HAS BEGUN")){
            gameStatus = getStringRequest("http://localhost:8090/getGameStatus");
            int playerTurn = Integer.parseInt(getStringRequest("http://localhost:8090/playerTurn"));

            if (playerTurn == playerID){
                getBoardRequest();
                System.out.println("Enter column (0-8) to make move\nTo exit the game enter 'EXIT'");
                String move = myObj.nextLine();
                System.out.println("User has entered: " + move); // sanitize inputs here

                if (move.equals("EXIT")) {
                    System.out.println("PLAYER HAS LEFT THE GAME");
                    postRequest("http://localhost:8090/postGameStatus", String.valueOf(playerID));
                    break;
                } else if (Integer.parseInt(move) >= 0 && Integer.parseInt(move) <= 8) {
                    System.out.println("PLAYER PLACES PIECE IN COLUMN: " + move);
                    String params = String.valueOf(move) + "," + String.valueOf(playerID);
                    System.out.println(params);
                    postRequest("http://localhost:8090/makeMove",params);
                } else {
                    System.out.println("INVALID ENTRY...");
                }
            } else{
                continue;
            }
            getBoardRequest();
        }
        gameFinalOutput(gameStatus);
    }

    private static void gameFinalOutput(String gameStatus) {
        if (gameStatus.equals("TIE")){
            System.out.println("\n\n###################################");
            System.out.println("########### ITS A TIE #############");
            System.out.println("########### NO PLAYER #############");
            System.out.println("###########  HAS WON  #############");
            System.out.println("###################################\n\n");
        } else if (gameStatus.contains("HAS WON")){
            String winner = String.valueOf(gameStatus.charAt(7));
            System.out.println("\n\n###################################");
            System.out.println("######## CONGRATULATIONS ##########");
            System.out.println("########     PLAYER " + winner + "    ##########");
            System.out.println("########     HAS WON     ##########");
            System.out.println("###################################\n\n");
        } else if (gameStatus.contains("QUIT")){
            String left = String.valueOf(gameStatus.charAt(7));
            System.out.println("\n\n###################################");
            System.out.println("########     PLAYER " + left + "    ##########");
            System.out.println("########     HAS LEFT    ##########");
            System.out.println("########     THE GAME    ###########");
            System.out.println("###################################\n\n");
        }
    }


    private static void getBoardRequest() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8090/boardStatus").openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        InputStream inputStream;
        if (200 <= responseCode && responseCode <= 299) {
            inputStream = connection.getInputStream();
        } else {
            inputStream = connection.getErrorStream();
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        inputStream));

        StringBuilder response = new StringBuilder();
        String currentLine;

        while ((currentLine = in.readLine()) != null)
            response.append(currentLine);

        System.out.println();
        String board = response.toString();
        board = board.replaceAll("\\[", "");
        String[] rows = board.split("],");
        for (String row : rows){
            System.out.println(row.replaceAll("]", "").replaceAll(",", "|"));
            System.out.println("-----------------------------------");
        }
    }


    private static String getStringRequest(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        InputStream inputStream;
        if (200 <= responseCode && responseCode <= 299) {
            inputStream = connection.getInputStream();
        } else {
            inputStream = connection.getErrorStream();
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        inputStream));

        StringBuilder response = new StringBuilder();
        String currentLine;

        while ((currentLine = in.readLine()) != null)
            response.append(currentLine);

        in.close();
        return response.toString();
    }


    private static void postRequest(String url, String post_params) throws IOException, JSONException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");

        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        os.write(post_params.getBytes());
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = connection.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();

            // print result
            if (url.contains("registerNewPlayer")){
                JSONObject jsonResponse = new JSONObject(response.toString());
                playerID = Integer.parseInt(jsonResponse.get("id").toString());
            }
//            System.out.println("POST RESPONSE" + response.toString());
        } else {
            System.out.println("POST request not worked");
        }

    }
}

