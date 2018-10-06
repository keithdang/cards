package com.company;

import java.util.Arrays;
import java.util.Collections;

public class deck {
    private card [] deckArr=new card[52];
    public deck(){
        String [] suits={"Spades","Hearts","Clubs","Diamonds"};
        fillDeck(deckArr,suits);
    }
    public void shuffleDeck(){
        Collections.shuffle(Arrays.asList(deckArr));
    }
    public void printDeck(){
        for(int i=0;i<deckArr.length;i++){
            System.out.println(deckArr[i].cardValue + " of " +deckArr[i].suit);
        }
    }
    private void fillDeck(card[] deck, String [] suits){
        int x=0;
        for(int i=0;i<suits.length;i++){
            for(int j=1;j<=13;j++){
                deck[x]=new card(j,suits[i],x+1);
                x++;
            }
        }
    }
}
