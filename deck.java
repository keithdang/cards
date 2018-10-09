package com.company;

import java.util.Arrays;
import java.util.Collections;

public class deck {
    public card [] deckArr=new card[52];
    private int count=0;
    public deck(){
        String [] suits={"Spades","Hearts","Clubs","Diamonds"};
        fillDeck(deckArr,suits);
    }
    public void shuffleDeck(){
        Collections.shuffle(Arrays.asList(deckArr));
    }
    public void reorderHand(int start, int end){
        quickSort(start,end-1,deckArr);
    }
    public void reorderDeck(){
        quickSort(0,deckArr.length-1,deckArr);
        System.out.println("swaps:"+count);
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
    private void quickSort(int left, int right,card []arr){
        if(left<right){
            int partition=partition(left,right,arr);
            quickSort(left,partition-1,arr);
            quickSort(partition+1,right,arr);
        }
    }
    private int partition(int left, int right, card [] arr){
        int pivot=arr[right].id;
        int index=left;
        for(int i=left;i<right;i++){
            if(arr[i].id<pivot){
                count++;
//                System.out.printf("swapping:arr[%s]=%s <=> arr[%s]=arr[%s]\n",i,arr[i].id,index,arr[index].id);
                swap(index,i,arr);
                index++;
            }
        }
        swap(index,right,arr);
        return index;
    }
    public int binarySearch(int l,int r, int x){
        int mid=l+(r-l)/2;
        int midCard=deckArr[mid].numValue;
        int searchCard=deckArr[x].numValue;
        return binaryCompare(l,r,x,mid,midCard,searchCard);
    }
    public int binaryCompare(int l,int r,int x,int mid, int midCard,int searchCard){
//        System.out.println("mid:"+mid+"\tl:"+l+"\tr:"+r);
//        System.out.print("Search\t"+searchCard+"\t");
//        deckArr[x].printCard();
//        System.out.print("Mid\t\t"+midCard+"\t");
//        deckArr[mid].printCard();
        if(r-l==1){
            if(deckArr[l].numValue>=searchCard){
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
                return binarySearch(l,mid,x);
            }
            if(midCard<searchCard) {
                return binarySearch(mid+1 , r,x);
            }
        }
        return -1;
    }
    private void swap(int a,int b,card[] arr){
        card temp=arr[a];
        arr[a]=arr[b];
        arr[b]=temp;
    }
}
