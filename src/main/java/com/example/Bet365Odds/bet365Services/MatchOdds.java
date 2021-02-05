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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MatchOdds implements Runnable{

    private String id;
    private String homeTeam;
    private String awayTeam;
    private MatchModel matchModel;

    public MatchOdds(String id, String homeTeam, String awayTeam) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        matchModel = new MatchModel();
        matchModel.setId(Integer.parseInt(id));
        matchModel.setHomeTeam(homeTeam);
        matchModel.setAwayTeam(awayTeam);
    }

    @Override
    public void run() {
        try {
            //TODO: promeni token kada uzmes novi
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.b365api.com/v1/bet365/event?token=73510-dKGX4Zgh2ekp0C&FI=" + id).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json: charset=UTF-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            InputStream is = conn.getInputStream();
            String jsonResult = streamReader(is);

            fullTimeOdds(jsonResult);
            doubleChanceOdds(jsonResult);
            goalsOverUnder(jsonResult);

            if(!MatchModel.getMatches().contains(matchModel)){
                MatchModel.addMatches(matchModel);
            }else {
                for(MatchModel m: MatchModel.getMatches()){
                    if(m.equals(matchModel)){
                        m.setHomeTeam(matchModel.getHomeTeam());
                        m.setAwayTeam(matchModel.getAwayTeam());
                        m.setOdd1(matchModel.getOdd1());
                        m.setOddX(matchModel.getOddX());
                        m.setOdd2(matchModel.getOdd2());
                        m.setOdd1X(matchModel.getOdd1X());
                        m.setOddX2(matchModel.getOddX2());
                        m.setOdd12(matchModel.getOdd12());
                        m.setOddUnder(matchModel.getOddUnder());
                        m.setOddOver(matchModel.getOddOver());
                    }
                }
            }

            is.close();
            conn.disconnect();

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void goalsOverUnder(String jsonString){
        JSONArray array = new JSONObject(jsonString).getJSONArray("results").getJSONArray(0);
        for(int i=0;i<array.length();i++){
            JSONObject mgObject = array.getJSONObject(i);
            if(mgObject.getString("type").equals("MG") && mgObject.getString("ID").equals("421")){
                String bound = array.getJSONObject(i + 2).getString("NA");
                String over = array.getJSONObject(i + 4).getString("OD");
                String under = array.getJSONObject(i + 6).getString("OD");

                matchModel.setOverUnderOddName(bound);
                matchModel.setOddUnder(getOddFromString(under));
                matchModel.setOddOver(getOddFromString(over));

            }
        }
    }

    private void doubleChanceOdds(String jsonString){
        JSONArray array = new JSONObject(jsonString).getJSONArray("results").getJSONArray(0);
        for(int i=0;i<array.length();i++){
            JSONObject mgObject = array.getJSONObject(i);
            if(mgObject.getString("type").equals("MG") && mgObject.getString("ID").equals("10115")){
                String odd1X = array.getJSONObject(i + 2).getString("OD");
                String oddX2 = array.getJSONObject(i + 3).getString("OD");
                String odd12 = array.getJSONObject(i + 4).getString("OD");

                matchModel.setOdd1X(getOddFromString(odd1X));
                matchModel.setOddX2(getOddFromString(oddX2));
                matchModel.setOdd12(getOddFromString(odd12));
            }
        }
    }

    private void fullTimeOdds(String jsonString){
        JSONArray array = new JSONObject(jsonString).getJSONArray("results").getJSONArray(0);
        for(int i=0;i<array.length();i++){
            JSONObject mgObject = array.getJSONObject(i);
            if(mgObject.getString("type").equals("MG") && mgObject.getString("ID").equals("1777")){
                String odd1 = array.getJSONObject(i + 2).getString("OD");
                String oddX = array.getJSONObject(i + 3).getString("OD");
                String odd2 = array.getJSONObject(i + 4).getString("OD");

                matchModel.setOdd1(getOddFromString(odd1));
                matchModel.setOddX(getOddFromString(oddX));
                matchModel.setOdd2(getOddFromString(odd2));
            }
        }
        String started = array.getJSONObject(0).getString("TT");
        String mins = array.getJSONObject(0).getString("TM");
        String sec = array.getJSONObject(0).getString("TS");
        if(started.equals("0") && mins.equals("0") && sec.equals("0")){
            matchModel.setStarted(0);
        }else{
            matchModel.setStarted(1);
        }

    }

    private double getOddFromString(String odd){
        String[] array = odd.split("/");
        double d = 1 + Double.parseDouble(array[0]) / Double.parseDouble(array[1]);
        DecimalFormat df = new DecimalFormat("#.##");
        return  Double.parseDouble(df.format(d));
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
