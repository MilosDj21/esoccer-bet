package com.example.Bet365Odds.bet365Services;

import java.util.ArrayList;
import java.util.List;

public class MatchModel {

    private static List<MatchModel> matches = new ArrayList<>();

    private int id;
    private String homeTeam;
    private String awayTeam;
    private double odd1;
    private double oddX;
    private double odd2;
    private double oddUnder;
    private double oddOver;

    public MatchModel(int id, String homeTeam, String awayTeam, double odd1, double oddX, double odd2, double oddUnder, double oddOver) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.odd1 = odd1;
        this.oddX = oddX;
        this.odd2 = odd2;
        this.oddUnder = oddUnder;
        this.oddOver = oddOver;
    }

    public static synchronized void addMatches(MatchModel matchModel){
        matches.add(matchModel);
    }

    public static synchronized List<MatchModel> getMatches(){
        return matches;
    }

    public static synchronized void clearMatchList(){
        matches.clear();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public double getOdd1() {
        return odd1;
    }

    public void setOdd1(double odd1) {
        this.odd1 = odd1;
    }

    public double getOddX() {
        return oddX;
    }

    public void setOddX(double oddX) {
        this.oddX = oddX;
    }

    public double getOdd2() {
        return odd2;
    }

    public void setOdd2(double odd2) {
        this.odd2 = odd2;
    }

    public double getOddUnder() {
        return oddUnder;
    }

    public void setOddUnder(double oddUnder) {
        this.oddUnder = oddUnder;
    }

    public double getOddOver() {
        return oddOver;
    }

    public void setOddOver(double oddOver) {
        this.oddOver = oddOver;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MatchModel){
            if(id == ((MatchModel) obj).id){
                return true;
            }
        }
        return false;
    }
}
