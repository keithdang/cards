package com.company;
import java.util.Collections;
import java.util.Arrays;
public class Main {

    public static void main(String[] args) {
	// write your code here
        deck deckOb=new deck();
//        deckOb.printDeck();
        deckOb.shuffleDeck();
        //deckOb.reorderDeck();
        //deckOb.printDeck();
        deckOb.reorderHand(0,13);
        splitCards(deckOb,4);
    }
    public static void splitCards(deck deckOb,int splitNum){
        System.out.println("Your hand");
        for(int i=0;i<deckOb.deckArr.length/splitNum;i++){
            System.out.println(deckOb.deckArr[i].cardValue +" of "+deckOb.deckArr[i].suit);
        }
    }
}
