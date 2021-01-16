package com.example.Bet365Odds.service;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Bet365ApiService implements Runnable{

    @Override
    public void run() {

        try {
            //TODO: promeni url na bet365
            HttpURLConnection conn = (HttpURLConnection) new URL("https://betsapi.com/docs/samples/bet365_prematch_odds.json").openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json: charset=UTF-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            while(!Thread.currentThread().isInterrupted()){

                //TODO: dodaj za upcoming metode da bi nasao event id (FI)

                InputStream is = conn.getInputStream();
                String jsonResult = streamReader(is);
                fullTimeOdds(jsonResult);
                doubleChanceOdds(jsonResult);
                goalsOverUnder(jsonResult);

                //TODO: skloni interrupt kada se zavrsi testiranje
                Thread.currentThread().interrupt();

                Thread.sleep(1000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private JSONArray goalsOverUnder(String jsonString){
        JSONObject obj = new JSONObject(jsonString);
        JSONArray results = obj.getJSONArray("results").getJSONObject(0).getJSONObject("main").getJSONObject("sp").getJSONObject("goals_over_under").getJSONArray("odds");
        JSONObject oddOver = results.getJSONObject(0);
        JSONObject oddUnder = results.getJSONObject(1);
        System.out.println(oddOver.getString("header") + " " + oddOver.getString("name") + " " + oddOver.getString("odds"));
        System.out.println(oddUnder.getString("header") + " " + oddUnder.getString("name") + " " + oddUnder.getString("odds"));

        return results;
    }

    private JSONArray doubleChanceOdds(String jsonString){
        JSONObject obj = new JSONObject(jsonString);
        JSONArray results = obj.getJSONArray("results").getJSONObject(0).getJSONObject("main").getJSONObject("sp").getJSONObject("double_chance").getJSONArray("odds");
        JSONObject odd1X = results.getJSONObject(0);
        JSONObject oddX2 = results.getJSONObject(1);
        JSONObject odd12 = results.getJSONObject(2);
        System.out.println(odd1X.getString("name") + " " + odd1X.getString("odds"));
        System.out.println(oddX2.getString("name") + " " + oddX2.getString("odds"));
        System.out.println(odd12.getString("name") + " " + odd12.getString("odds"));

        return results;
    }

    private JSONArray fullTimeOdds(String jsonString){
        JSONObject obj = new JSONObject(jsonString);
        JSONArray results = obj.getJSONArray("results").getJSONObject(0).getJSONObject("main").getJSONObject("sp").getJSONObject("full_time_result").getJSONArray("odds");
        JSONObject odd1 = results.getJSONObject(0);
        JSONObject oddX = results.getJSONObject(1);
        JSONObject odd2 = results.getJSONObject(2);
        System.out.println(odd1.getString("name") + " " + odd1.getString("odds"));
        System.out.println(oddX.getString("name") + " " + oddX.getString("odds"));
        System.out.println(odd2.getString("name") + " " + odd2.getString("odds"));
        return results;
    }

    private String streamReader(InputStream stream){
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        InputStreamReader in = new InputStreamReader(stream, StandardCharsets.UTF_8);
        int charsRead;
        try{
            while((charsRead = in.read(buffer, 0, buffer.length)) > 0) {
                out.append(buffer, 0, charsRead);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return out.toString();
    }
}
