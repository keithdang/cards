package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class presidentArraylist extends deck {
    card activeCard;
    int turnCount=1;
    int maxTurn;
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
        int pivot=util.presidentValues( arr[right].numValue);
        int index=left;
        for(int i=left;i<right;i++){
            if(util.presidentValues(arr[i].numValue)<=pivot){
                swap(index,i,arr);
                index++;
            }
        }
        swap(index,right,arr);
        return index;
    }
    private void swap(int a,int b,card[] arr){
        card temp=arr[a];
        arr[a]=arr[b];
        arr[b]=temp;
    }
//    private int presValues(int num){
//        if(num==1){
//            return 14;
//        }else if(num ==2){
//            return 15;
//        }else{
//            return num;
//        }
//    }
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
            if(i==0){
                printHand(hands.get(i));
            }
        }
        //printAllHands();
    }
    public void searchPlayerCard() {
        ArrayList<card> yourHand=hands.get(0);
        restartTurn();
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter index:");
        try {
            int n = reader.nextInt();
            System.out.println("You chose:" + n);
            if (n >= 0 && n < yourHand.size()) {
                selectCard(yourHand,n);
            }
            else if(n==99){
                System.out.println("Skip");
            } else{
                System.out.println("Not in range, try again");
                searchPlayerCard();
           }
        }
        catch(InputMismatchException a){
            System.out.println("You did not input a number");
            searchPlayerCard();
        }
    }
    public void printHand(ArrayList<card> yo){
        System.out.println("Hand");
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
            activeCard=null;
            return true;
        }
    }
    public void oppoonentTurn(int oppNum){
        ArrayList<card> oppHand=hands.get(oppNum-1);
        int index=0;
        if(restartTurn()){
            selectCard(oppHand,index);
        }else{
            index=findBestCard(oppHand);
            if(index!=-1){
                selectCard(oppHand,index);
            }else{
                System.out.print("No cards available");
            }
        }
    }
    private void selectCard(ArrayList<card> hand,int index){
        System.out.print("Found:");
        activeCard=hand.get(index);
        activeCard.printCard();
        hand.remove(index);
        turnCount=1;
        printHand(hand);
    }
    private int findBestCard(ArrayList<card> oppHand){
        int start=0;
        int end=oppHand.size()-1;
        printHand(oppHand);
        int index=-1;
        if(util.presidentValues(oppHand.get(end).numValue)>=util.presidentValues(activeCard.numValue)){
            index=binarySearch(start,end, oppHand);
        }
        return index;
    }
    public int binarySearch(int l,int r, ArrayList<card> hand){
        int mid=l+(r-l)/2;
        int midCard=util.presidentValues(hand.get(mid).numValue);
        int searchCard=util.presidentValues(activeCard.numValue);
        if(util.presidentValues(hand.get(r).numValue)<searchCard){
            System.out.println("No cards available");
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
