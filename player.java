package com.company;

import java.util.ArrayList;

public class player {
    int id;
    ArrayList<card> hand=new ArrayList<card>();
    ArrayList<ArrayList<card>> cardPairs=new ArrayList<ArrayList<card>>();
    public player(int n, ArrayList<card> hand){
        this.id=n;
        this.hand=hand;
    }
    public void initDoubles(){
        int temp=0;

        for(int i=1;i<hand.size();i++){
            if(hand.get(temp).numValue==hand.get(i).numValue){
                System.out.print("\nDouble verified\t");
                ArrayList<card> pair=new ArrayList<>();
                pair.add(hand.get(temp));
                pair.add(hand.get(i));
                cardPairs.add(pair);
                hand.get(temp).printCard();
                hand.get(i).printCard();
            }
            temp=i;
        }
    }
    public void printPairs(){
        System.out.println("\nPrinting Pairs");
        for(int i=0;i<cardPairs.size();i++){
            cardPairs.get(i).get(0).printCard();
            cardPairs.get(i).get(1).printCard();
            System.out.println();
        }
    }
    public void printPlayer(){
        System.out.print("\nPlayer:"+id);
    }
}
