package com.kerinb.servergamechallenge;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ControllerTests {
    private static int playerID = 0;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void testGetIndex() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Welcome to 5 in a row! please register your player");
    }

    @Test
    void testEndGameExit() throws  URISyntaxException {
        URI uri = new URI("http://localhost:" + port + "/postGameStatus/");
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        HttpEntity<String> request = new HttpEntity<>("1", headers);


        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        String gameStatus = this.restTemplate.getForObject("http://localhost:" + port + "/getGameStatus", String.class);

        assertEquals(gameStatus,
                "PLAYER 1 QUIT");
    }

    @Test
    void testInitialiseDb()  {
        String playerCount = this.restTemplate.getForObject("http://localhost:" + port + "/getPlayerCount", String.class);
        assertEquals("2", playerCount);
    }

    @Test
    void testRegisterOneNewPlayer() throws URISyntaxException {
        URI uri = new URI("http://localhost:" + port + "/registerNewPlayer/");
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        HttpEntity<String> request = new HttpEntity<>("player1", headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        String playerCount = this.restTemplate.getForObject("http://localhost:" + port + "/getPlayerCount", String.class);
        assertEquals("2", playerCount);
    }

    @Test
    void testRegisterTwoNewPlayer() throws  URISyntaxException {
        URI uriA = new URI("http://localhost:" + port + "/registerNewPlayer/");
        HttpHeaders headersA = new HttpHeaders();
        headersA.set("X-COM-PERSIST", "true");
        HttpEntity<String> requestA = new HttpEntity<>("player1", headersA);

        URI uriB = new URI("http://localhost:" + port + "/registerNewPlayer/");
        HttpHeaders headersB = new HttpHeaders();
        headersB.set("X-COM-PERSIST", "true");
        HttpEntity<String> requestB = new HttpEntity<>("player2", headersB);

        ResponseEntity<String> resultA = this.restTemplate.postForEntity(uriA, requestA, String.class);
        ResponseEntity<String> resultB = this.restTemplate.postForEntity(uriB, requestB, String.class);

        String playerCount = this.restTemplate.getForObject("http://localhost:" + port + "/getPlayerCount", String.class);
        assertEquals("4", playerCount);
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


    private static void postRequest(String url, String post_params) throws IOException, JSONException, JSONException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");

        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        os.write(post_params.getBytes());
        os.flush();
        os.close();

        int responseCode = connection.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();

            if (url.contains("registerNewPlayer")){
                JSONObject jsonResponse = new JSONObject(response.toString());
                playerID = Integer.parseInt(jsonResponse.get("id").toString());
            }
        } else {
            System.out.println("POST request not worked");
        }
    }
}



