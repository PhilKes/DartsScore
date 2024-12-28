package com.philkes.dartsscore;

import android.os.Parcel;
import android.os.Parcelable;

import com.philkes.dartsscore.activites.PlayerActivity;

public class Player implements Parcelable{
    public Player(int id, String name, int score, int legs, int sets, double avg, double doubles, int bestFinish, int matches, int won, int highestScore, int legsPlayed, int legsWon) {
        this.id= id;
        this.name = name;
        this.score = score;
        this.legs = legs;
        this.sets = sets;
        this.avg=avg;
        this.doubles=doubles;
        this.bestFinish=bestFinish;
        this.matches = matches;
        this.won=won;
        this.highestScore=highestScore;
        this.legsPlayed = legsPlayed;
        this.legsWon=legsWon;
    }
    public Player(int score) {
        this.id=PlayerActivity._ID++;
        this.name="New Player...";
        this.score=score;
    }
    public Player(int score,String name) {
        this.id=PlayerActivity._ID++;
        this.name=name;
        this.score=score;
    }
    public String name="";
    public int score=0;
    public int legs=0;
    public int sets=0;
    public int id=0;
    public double avg=0.0;
    public double doubles=0.0;
    public int bestFinish=0, matches =0, won=0,darts=0,gameAvg=0,highestScore=0,doubleDarts=0,doubleGame =0,legsWon=0, legsPlayed =0;


    protected Player(Parcel in) {
        name = in.readString();
        score = in.readInt();
        legs = in.readInt();
        sets = in.readInt();
        id = in.readInt();
        avg = in.readDouble();
        doubles = in.readDouble();
        bestFinish = in.readInt();
        matches = in.readInt();
        won = in.readInt();
        darts = in.readInt();
        gameAvg = in.readInt();
        highestScore = in.readInt();
        doubleDarts = in.readInt();
        doubleGame = in.readInt();
        legsWon = in.readInt();
        legsPlayed = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(score);
        dest.writeInt(legs);
        dest.writeInt(sets);
        dest.writeInt(id);
        dest.writeDouble(avg);
        dest.writeDouble(doubles);
        dest.writeInt(bestFinish);
        dest.writeInt(matches);
        dest.writeInt(won);
        dest.writeInt(darts);
        dest.writeInt(gameAvg);
        dest.writeInt(highestScore);
        dest.writeInt(doubleDarts);
        dest.writeInt(doubleGame);
        dest.writeInt(legsWon);
        dest.writeInt(legsPlayed);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public String toString() {
        return name;
    }
}
