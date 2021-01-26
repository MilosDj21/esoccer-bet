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
    private double odd1X;
    private double oddX2;
    private double odd12;
    private double oddUnder;
    private double oddOver;
    private String overUnderOddName;
    private int started;

    public MatchModel() {
    }

    public static synchronized void addMatches(MatchModel matchModel){
        matches.add(matchModel);
    }

    public static synchronized List<MatchModel> getMatches(){
        return matches;
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

    public double getOdd1X() {
        return odd1X;
    }

    public void setOdd1X(double odd1X) {
        this.odd1X = odd1X;
    }

    public double getOddX2() {
        return oddX2;
    }

    public void setOddX2(double oddX2) {
        this.oddX2 = oddX2;
    }

    public double getOdd12() {
        return odd12;
    }

    public void setOdd12(double odd12) {
        this.odd12 = odd12;
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

    public String getOverUnderOddName() {
        return overUnderOddName;
    }

    public void setOverUnderOddName(String overUnderOddName) {
        this.overUnderOddName = overUnderOddName;
    }

    public int getStarted() {
        return started;
    }

    public void setStarted(int started) {
        this.started = started;
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
