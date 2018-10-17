package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class presidentArraylist extends deck {
//    card activeCard;
    ArrayList<card> activeCards=new ArrayList<card>();
    int turnCount=1;
    int maxTurn;
    int playerTurn=0;
    boolean game=true;
    Util util=Util.getInstance();
    ArrayList<ArrayList<card>> hands=new ArrayList<ArrayList<card>>();
    public void reorderHand(int start, int end){
        quickSort(start,end-1,deckArr);
    }

    private void quickSort(int left, int right,card []arr){
        if(left<right){
            int partition=partition(left,right,arr);
            quickSort(left,partition-1,arr);
            quickSort(partition+1,right,arr);
        }
    }
    private int partition(int left, int right, card [] arr){
        int pivot=util.presidentValues(arr[right].numValue);
        int index=left;
        for(int i=left;i<right;i++){
            if(util.presidentValues(arr[i].numValue)<=pivot){
                util.swap(index,i,arr);
                index++;
            }
        }
        util.swap(index,right,arr);
        return index;
    }
    public void splitAndOrderCards(int splitNum){
        int ratio=deckArr.length/splitNum;
        maxTurn=splitNum;
        int start,end,count;
        for(int i=0;i<splitNum;i++){
            start=ratio*i;
            end=ratio*(i+1);
            reorderHand(start,end);
            count=start;
            ArrayList<card> hand=new ArrayList<card>();
            hands.add(hand);
            while(count<end){
                hands.get(hands.size()-1).add(deckArr[count]);
                count++;
            }
        }
    }
    public void searchPlayerCard() {
        playerTurn=0;
        ArrayList<card> yourHand=hands.get(playerTurn);
        printHand(yourHand);
        restartTurn();
        Scanner reader = new Scanner(System.in);
        System.out.println("\nEnter index:");
        try {
            int n = reader.nextInt();
            System.out.println("You chose:" + n);
            if(n==99){
                System.out.println("Skip");
            }else{
                if (checkValid(n,yourHand)){
                    selectCard(yourHand,n);
                } else{
                    System.out.println("Not in range, try again");
                    searchPlayerCard();
                }
            }
        }
        catch(InputMismatchException a){
            System.out.println("You did not input a number");
            searchPlayerCard();
        }
        if(yourHand.size()<1){
            System.out.println("You win!");
            game=false;
        }
    }
    private boolean checkValid(int n, ArrayList<card> hand){
        int cardVal=util.presidentValues(hand.get(n).numValue);
        return n >= 0 && n < hand.size() && (activeCards.size()==0 || cardVal>=util.presidentValues(activeCards.get(0).numValue));
//        return n >= 0 && n < hand.size() && (activeCard==null || cardVal>=util.presidentValues(activeCard.numValue));
    }
    public void printHand(ArrayList<card> yo){
        System.out.println("\nHand");
        for(int i=0;i<yo.size();i++){
            System.out.print(i+")");
            yo.get(i).printCard();

        }
    }
    public boolean restartTurn(){
        if(turnCount<maxTurn){
            turnCount++;
            return false;
        }else{
            turnCount=1;
            activeCards.clear();
            return true;
        }
    }
    public void oppoonentTurn(int oppNum){
        playerTurn=oppNum;
        ArrayList<card> oppHand=hands.get(playerTurn-1);
        int index=0;
        if(game) {
            if (restartTurn() || activeCards.size()==0) {
                selectCard(oppHand, index);
            } else {
                index = findBestCard(oppHand);
                if (index != -1) {
                    selectCard(oppHand, index);
                } else {
                    System.out.print("No cards available");
                }
            }
            if (oppHand.size() < 1) {
                System.out.println("You lose!");
                game = false;
            }
        }
    }
    private void selectCard(ArrayList<card> hand,int index){
        System.out.print("\nFound:");
        if(activeCards.size()>0 && hand.get(index).numValue==activeCards.get(0).numValue){
            System.out.println("Burn");
            hand.get(index).printCard();
            activeCards.clear();
            hand.remove(index);
            turnCount=1;
            if(playerTurn==0){
                searchPlayerCard();
            }else{
                oppoonentTurn(playerTurn);
            }
        }else{
            activeCards.clear();
            activeCards.add(hand.get(index));
            activeCards.get(0).printCard();
            hand.remove(index);
            turnCount=1;
        }

    }
    private int findBestCard(ArrayList<card> oppHand){
        int start=0;
        int end=oppHand.size()-1;
        printHand(oppHand);
        int index=-1;
        if(util.presidentValues(oppHand.get(end).numValue)>=util.presidentValues(activeCards.get(0).numValue)){
            index=binarySearch(start,end, oppHand);
        }
        return index;
    }
    public int binarySearch(int l,int r, ArrayList<card> hand){
        int mid=l+(r-l)/2;
        int midCard=util.presidentValues(hand.get(mid).numValue);
        int searchCard=util.presidentValues(activeCards.get(0).numValue);
        if(util.presidentValues(hand.get(r).numValue)<searchCard){
            return -1;
        }else{
            return binaryCompare(l,r,mid,midCard,searchCard,hand);
        }
    }
    public int binaryCompare(int l,int r,int mid, int midCard,int searchCard, ArrayList<card> hand){
        if(r-l==1){
            if(hand.get(l).numValue>=searchCard){
                return l;
            }else{
                return r;
            }
        }else if(r==l){
            return l;
        }
        if(r>=1){
            if(midCard==searchCard){
                return mid;
            }
            if(midCard>searchCard){
                return binarySearch(l,mid,hand);
            }
            if(midCard<searchCard) {
                return binarySearch(mid+1 , r,hand);
            }
        }
        return -1;
    }
    public void hello(){
        ArrayList<Integer> yo=new ArrayList<Integer>();
        int []arr={5,1,2,3};
        yo.add(arr[0]);
        yo.add(arr[1]);
        yo.add(arr[2]);
        yo.add(arr[3]);
        yo.remove(2);
        for(int i=0;i<yo.size();i++){
            System.out.println(yo.get(i));
        }
    }
    public void printAllHands(){
        for(int i=0;i<hands.size();i++){
            printHand(hands.get(i));
        }
    }
    private void printCard(int i){
        System.out.print(i+")");
        deckArr[i].printCard();
    }
}
