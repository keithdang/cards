package com.company;
import sun.util.locale.provider.SPILocaleProviderAdapter;

import java.util.Collections;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        president2();
        presidentObjects();
    }
    public static void normal(){
        deck deckOb=new deck();
//        deckOb.printDeck();
        deckOb.shuffleDeck();
        //deckOb.reorderDeck();
        //deckOb.printDeck();
        deckOb.reorderHand(0,13);
        splitCards(deckOb,4);
    }
    public static void presidentObjects(){
        presidentObjectList pres=new presidentObjectList();
        pres.shuffleDeck();
        pres.splitAndOrderCards(4);
        pres.initialize();
        while(pres.game){
            pres.searchPlayerCard();
            pres.opponentTurn(2);
            pres.opponentTurn(3);
            pres.opponentTurn(4);
        }
    }
    public static void president2(){
        presidentArraylist pres=new presidentArraylist();
        pres.shuffleDeck();
        pres.splitAndOrderCards(4);
        while(pres.game){
            pres.searchPlayerCard();
            pres.oppoonentTurn(2);
            pres.oppoonentTurn(3);
            pres.oppoonentTurn(4);
        }
    }
    public static void president(){
        president pres=new president();
        pres.shuffleDeck();
        pres.splitAndOrderCards(4);
        for(int i=0;i<5;i++){
            pres.searchCard();
            pres.opponentTurn(2);
            pres.opponentTurn(3);
            pres.opponentTurn(4);
        }
    }
    public static void splitCards(deck deckOb,int splitNum){
        System.out.println("Your hand");
        for(int i=0;i<deckOb.deckArr.length/splitNum;i++){
            System.out.println(deckOb.deckArr[i].cardValue +" of "+deckOb.deckArr[i].suit);
        }
    }
}
