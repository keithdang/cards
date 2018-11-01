package com.company;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class presidentObjectList extends deck{

    ArrayList<card> activeCards=new ArrayList<card>();
    int turnCount=1;
    int maxTurn;
    int playerTurn=0;
    boolean game=true;
    Util util=Util.getInstance();
    ArrayList<player> players=new ArrayList<player>();
    public void initialize(){
        printPlayers();
        markDoubles();
    }
    public void printPlayers(){
        for(int i=0;i<players.size();i++){
            players.get(i).printPlayer();
            printHand(players.get(i).hand);
        }
    }
    public void markDoubles(){
        for(int i=0;i<players.size();i++){
            players.get(i).initDoubles();
            players.get(i).printPairs();
        }
    }
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
            //hands.add(hand);
            players.add(new player(i,hand));
            while(count<end){
                //hands.get(hands.size()-1).add(deckArr[count]);
                players.get(players.size()-1).hand.add(deckArr[count]);
                count++;
            }
        }
    }
    public void searchPlayerCard() {
        playerTurn=0;
        ArrayList<card> yourHand=players.get(playerTurn).hand;
        printHand(yourHand);
        restartTurn();
        Scanner reader = new Scanner(System.in);
        System.out.println("\nEnter index:");
        try {
            String word=reader.nextLine();
            System.out.println("You chose:" + word);
            String[] arrWord=word.split(",");
            int[] arrNum=util.stringArrToInt(arrWord);
            int n;
            if(arrWord.length==1){
                n=arrNum[0];
                if(n==99){
                    System.out.println("Skip");
                }else{
                    userCardInput(n,yourHand);
                    players.get(playerTurn).initDoubles();
                }
            }else if(arrWord.length>1){
                multipleInput(arrNum,yourHand);
                players.get(playerTurn).initDoubles();
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

    private void multipleInput(int[] arrNum,ArrayList<card> hand){
        if(arrNum.length==2) {
            System.out.println("User trying doubles...");
            if (handDoubles(arrNum, hand)) {
                System.out.println("VALID PAIR");
                selectDoubles(arrNum,hand);
            }
        }
    }
    private boolean handDoubles(int[] arrNum,ArrayList<card> hand){
        boolean isPair=false;
        if(hand.get(arrNum[0]).numValue==hand.get(arrNum[1]).numValue){
            isPair=true;
        }
        return  isPair;
    }
    private void selectDoubles(int[] arrNum, ArrayList<card> hand){
        if (checkValid(arrNum[0],hand) && checkValid(arrNum[1],hand)){
            System.out.print("\nFound Doubles:");
            //somehow same pair of doubles
            if(activeCards.size()==2 && hand.get(arrNum[0]).numValue==activeCards.get(0).numValue){
                System.out.println("Burn");
                printMultiple(arrNum,hand);
                activeCards.clear();
                clearMultiple(arrNum,hand);
                turnCount=1;
                //burn complete, restart turn for player who burned
                if(playerTurn==0){
                    searchPlayerCard();
                }else{
                    opponentTurn(playerTurn);
                }
            }else{//this is the first time/burned
                activeCards.clear();
                addMultipleToActive(arrNum,hand);
                printActive();
                clearMultiple(arrNum,hand);
                turnCount=1;
            }
            System.out.print("\nUpdated: ");
            printHand(hand);
        } else{
            System.out.println("Not in range, try again");
            searchPlayerCard();
        }
    }
    private void printMultiple(int[] arrNum,ArrayList<card> hand){
        for(int i=0;i<arrNum.length;i++){
            hand.get(arrNum[i]).printCard();
        }
    }
    private void addMultipleToActive(int[] arrNum, ArrayList<card> hand){
        for(int i=0;i<arrNum.length;i++){
            activeCards.add(hand.get(arrNum[i]));
        }
    }
    private void printActive(){
        for(int i=0;i<activeCards.size();i++){
            activeCards.get(i).printCard();
        }
    }
    private void clearMultiple(int[] arrNum,ArrayList<card> hand){
        for(int i=0;i<arrNum.length;i++){
            hand.remove(arrNum[i]-i);
        }
    }
    private void userCardInput(int n, ArrayList<card> hand){
        if (checkValid(n,hand)){
            selectCard(hand,n);
        } else{
            System.out.println("Not in range, try again");
            searchPlayerCard();
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
    public void opponentTurn(int oppNum){
        playerTurn=oppNum;

        ArrayList<card> oppHand=players.get(playerTurn-1).hand;//hands.get(playerTurn-1);
        int index=0;
        ArrayList<Integer> indices;
        if(game) {
            if (restartTurn() || activeCards.size()==0) {
                selectCard(oppHand, index);
                players.get(playerTurn-1).initDoubles();
            } else {
                //index = findBestCard(oppHand);
                indices = findBestCard2(oppHand);
                if(indices.size()>0){
                //if (index != -1) {
                    selectCard2(oppHand,indices);
                    players.get(playerTurn-1).initDoubles();
                    //selectCard(oppHand, index);
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
    private void selectCard2(ArrayList<card> hand,ArrayList<Integer> indices){
        System.out.print("\nFound:");
        //doubles
        if(indices.size()==2){
            int []arr={indices.get(0),indices.get(1)};
            selectDoubles(arr,hand);
        }
        //burn card
        else if(activeCards.size()>0 && hand.get(indices.get(0)).numValue==activeCards.get(0).numValue){
            System.out.println("Burn");
            hand.get(indices.get(0)).printCard();
            activeCards.clear();
            hand.remove(indices.get(0));
            turnCount=1;
            if(playerTurn==0){
                searchPlayerCard();
            }else{
                opponentTurn(playerTurn);
            }
        }
        //different card
        else{
            activeCards.clear();
            activeCards.add(hand.get(indices.get(0)));
            activeCards.get(0).printCard();
            hand.remove(indices.get(0));
            turnCount=1;
        }
    }
    private void selectCard(ArrayList<card> hand,int index){
        System.out.print("\nFound2:");
        if(activeCards.size()>0 && hand.get(index).numValue==activeCards.get(0).numValue){
            System.out.println("Burn");
            hand.get(index).printCard();
            activeCards.clear();
            hand.remove(index);
            turnCount=1;
            if(playerTurn==0){
                searchPlayerCard();
            }else{
                opponentTurn(playerTurn);
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
        if(singleCase(end,oppHand)){
            index=binarySearch(start,end, oppHand);
        }else if(doubleCase(end,oppHand)){
            System.out.println("\nDetected Doubles");
        }
        return index;
    }
    private ArrayList<Integer> findBestCard2(ArrayList<card> oppHand){
        int start=0;
        int end=oppHand.size()-1;
        printHand(oppHand);
        ArrayList<Integer> indices=new ArrayList<Integer>();
        if(singleCase(end,oppHand)){
            indices.add(binarySearch(start,end, oppHand));
        }else if(doubleCase(end,oppHand)){
            System.out.println("\nDetected Doubles");
            ArrayList<ArrayList<card>> cardPairs=players.get(playerTurn-1).cardPairs;
            end=cardPairs.size()-1;
            int pairIndex=binarySearchPair(start,end, cardPairs);
            if(pairIndex!=-1){
                activeCards.clear();
                activeCards.add(cardPairs.get(pairIndex).get(0));
                int index= binarySearch(start,end,oppHand);
                if(index!=-1){
                    if(index!=0 && oppHand.get(index).numValue==oppHand.get(index-1).numValue){
                        indices.add(index-1);
                        indices.add(index);
                    }else{
                        indices.add(index);
                        indices.add(index+1);
                    }
                }else{
                    index=bruteForceHandSearch(cardPairs,oppHand,pairIndex);
                    indices.add(index);
                    indices.add(index+1);
                }
                activeCards.clear();
            }
        }
        return indices;
    }
    private int bruteForceHandSearch(ArrayList<ArrayList<card>> cardPairs,ArrayList<card> oppHand, int pairIndex){
        int index=-1;
        int cardVal=cardPairs.get(pairIndex).get(0).numValue;
        for(int i=0;i<oppHand.size();i++){
            if(cardVal==oppHand.get(i).numValue){
                index=i;
                break;
            }
        }
        return index;
    }
    private int binarySearchPair(int l,int r,ArrayList<ArrayList<card>> cardPairs){
        int mid=l+(r-l)/2;
        int midCard=util.presidentValues(cardPairs.get(mid).get(0).numValue);
        int searchCard=util.presidentValues(activeCards.get(0).numValue);
        if(util.presidentValues(cardPairs.get(r).get(0).numValue)<searchCard){
            return -1;
        }else{
            return binaryComparePair(l,r,mid,midCard,searchCard,cardPairs);
        }
    }
    private boolean singleCase(int end,ArrayList<card> oppHand){
        return activeCards.size()<=1 && util.presidentValues(oppHand.get(end).numValue)>=util.presidentValues(activeCards.get(0).numValue);
    }
    private boolean doubleCase(int end,ArrayList<card> oppHand){
        ArrayList<ArrayList<card>> cardPairs=players.get(playerTurn-1).cardPairs;
        int oppMax=util.presidentValues(cardPairs.get(cardPairs.size()-1).get(0).numValue);
        int activeMax=util.presidentValues(activeCards.get(0).numValue);
        return activeCards.size()==2 && oppMax>=activeMax;
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
    public ArrayList<Integer> binarySearch2(int l,int r, ArrayList<card> hand){
        int mid=l+(r-l)/2;
        int midCard=util.presidentValues(hand.get(mid).numValue);
        int searchCard=util.presidentValues(activeCards.get(0).numValue);
        ArrayList<Integer> indices=new ArrayList<Integer>();
        if(util.presidentValues(hand.get(r).numValue)>=searchCard){
            indices.add(binaryCompare(l,r,mid,midCard,searchCard,hand));
        }
        return indices;
    }
    public int binaryComparePair(int l,int r,int mid, int midCard,int searchCard, ArrayList<ArrayList<card>> hand){
        if(r-l==1){
            if(hand.get(l).get(0).numValue>=searchCard){
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
                return binarySearchPair(l,mid,hand);
            }
            if(midCard<searchCard) {
                return binarySearchPair(mid+1 , r,hand);
            }
        }
        return -1;
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

    private void printCard(int i){
        System.out.print(i+")");
        deckArr[i].printCard();
    }
}
