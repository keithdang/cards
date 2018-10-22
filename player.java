package com.company;

import java.util.ArrayList;

public class player {
    int id;
    ArrayList<card> hand=new ArrayList<card>();
    public player(int n, ArrayList<card> hand){
        this.id=n;
        this.hand=hand;
    }
    public void printPlayer(){
        System.out.print("\nPlayer:"+id);
    }
}
