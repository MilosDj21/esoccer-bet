package com.example.Bet365Odds.controllers;

import com.example.Bet365Odds.bet365Services.MatchModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class OddsController {

    @GetMapping("/odds")
    public String getOdds(){
        List<MatchModel> list = MatchModel.getMatches();
        String json = "[";
        if(list.isEmpty()){
            json += "]";
        }else {
            for(MatchModel m: list){
                json += "{\"id\":" + m.getId() + "," +
                        "\"homeTeam\":\"" + m.getHomeTeam() + "\"," +
                        "\"awayTeam\":\"" + m.getAwayTeam() + "\"," +
                        "\"odd1\":" + m.getOdd1() + "," +
                        "\"oddX\":" + m.getOddX() + "," +
                        "\"odd2\":" + m.getOdd2() + "," +
                        "\"odd1X\":" + m.getOdd1X() + "," +
                        "\"oddX2\":" + m.getOddX2() + "," +
                        "\"odd12\":" + m.getOdd12() + "," +
                        "\"oddUnder\":" + m.getOddUnder() + "," +
                        "\"oddOver\":" + m.getOddOver() + "," +
                        "\"overUnderOddName\":\"" + m.getOverUnderOddName() + "\"," +
                        "\"started\":" + m.getStarted() + "}";
                if(!list.get(list.size()-1).equals(m)){
                    json += ",";
                }else {
                    json += "]";
                }
            }
        }
        return json;
    }
}
