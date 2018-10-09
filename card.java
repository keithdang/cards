package com.company;

public class card {
    int id;
    String suit;
    int numValue;
    String cardValue;
    boolean active=true;
    public card(int n,String suit,int id){
        this.suit=suit;
        this.id=id;
        numValue=n;
        cardValue=assignVal(n);
    }
    public void setActive(boolean bool){
        active=bool;
    }
    private String assignVal(int n){
        switch (n){
            case 13:
                return "King";
            case 12:
                return "Queen";
            case 11:
                return "Jack";
            case 1:
                return "Ace";
            default:
                return Integer.toString(n);
        }
    }
    public void printCard(){
        System.out.print(cardValue + " of "+suit+"\n");
    }
}
