package com.example.Bet365Odds.bet365Services;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UpcomingMatches implements Runnable{

    @Override
    public void run() {

        HttpURLConnection conn = null;
        InputStream is = null;

        while(true){
            try {
                //TODO: promeni token kada uzmes novi
                conn = (HttpURLConnection) new URL("https://api.b365api.com/v1/bet365/inplay_filter?sport_id=1&token=TOKEN").openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                is = conn.getInputStream();
                String jsonResult = streamReader(is);

                List<JSONObject> eSportGames = getAllGames(jsonResult);
                MatchModel.getMatches().clear();
                for (JSONObject o : eSportGames) {
                    String gameId = o.getString("id");
                    String homeTeam = o.getJSONObject("home").getString("name");
                    String awayTeam = o.getJSONObject("away").getString("name");
                    Thread t = new Thread(new MatchOdds(gameId, homeTeam, awayTeam));
                    t.start();
                    Thread t1 = new Thread(new MatchResultApi(gameId, homeTeam, awayTeam));
                    t1.start();
                }
                Thread.sleep(10000);
            }
            catch (IOException | InterruptedException e) {
                e.printStackTrace();
                try {
                    is.close();
                    conn.disconnect();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private List<JSONObject> getAllGames(String jsonString){
        List<JSONObject> list = new ArrayList<>();
        JSONArray array = new JSONObject(jsonString).getJSONArray("results");
        JSONObject object;
        for(int i=0;i<array.length();i++){
            object = array.getJSONObject(i);
            String leagueName = object.getJSONObject("league").getString("name");
            if(leagueName.contains("Esoccer") && leagueName.contains("8 mins play")){
                list.add(object);
            }
        }
        return list;
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
