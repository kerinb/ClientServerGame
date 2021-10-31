package com.kerinb.clientgamechallenge;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class ClientApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        String str_response = getRequest("http://localhost:8090/");
        System.out.println(str_response);

        str_response = getRequest("http://localhost:8090/gameStatus/");
        System.out.println("GAME STATUS: " + str_response);

        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter username");

        String userName = myObj.nextLine();  // Read user input
        System.out.println("Username is: " + userName);  // Output user input
        postRequest("http://localhost:8090/registerNewPlayer", userName);

        str_response = getRequest("http://localhost:8090/gameStatus");
        System.out.println("has game started?: " + str_response );
        while (!str_response.equals("GAME CAN BEGIN")) {
            Thread.sleep(1000);
            str_response = getRequest("http://localhost:8090/gameStatus");
            System.out.println("has game started?: " + str_response );
        }

        str_response = getRequest("http://localhost:8090/gameStatus/");
        System.out.println("GAME STATUS: " + str_response);
    }

    private static String getRequest(String url) throws IOException {
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


    private static void postRequest(String url, String post_params) throws IOException {
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
            StringBuffer response = new StringBuffer();

            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request not worked");
        }

    }
}
