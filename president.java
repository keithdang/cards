package com.company;
import java.util.InputMismatchException;
import java.util.Scanner;
public class president extends deck{
    int activeCardIndex=-1;

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
        int pivot=presValues( arr[right].numValue);
        int index=left;
        for(int i=left;i<right;i++){
            if(presValues(arr[i].numValue)<=pivot){
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
    private int presValues(int num){
        if(num==1){
            return 14;
        }else if(num ==2){
            return 15;
        }else{
            return num;
        }
    }
    public int binarySearch(int l,int r, int x){
        int mid=l+(r-l)/2;
        int midCard=presValues(deckArr[mid].numValue);
        int searchCard=presValues(deckArr[x].numValue);
        return binaryCompare(l,r,x,mid,midCard,searchCard);
    }
    public void splitAndOrderCards(int splitNum){
        int ratio=deckArr.length/splitNum;
        for(int i=0;i<splitNum;i++){
            reorderHand(ratio*i,(ratio)*(i+1));
            if(i==0){
                System.out.println("Your hand");
                printHand(ratio*i,(ratio)*(i+1));
            }
//             else{
//                System.out.println("Opponents hand");
//            }
//            printHand(ratio*i,(ratio)*(i+1));
        }
    }
    public void searchCard(){
        Scanner reader=new Scanner(System.in);
        System.out.println("Enter index:");
        try{
            int n=reader.nextInt();
            System.out.println("You chose:"+n);
            if(n>=0 && n<13 && deckArr[n].active){
                printCard(n);
                deckArr[n].setActive(false);

                System.out.println("Your hand");
                printHand(0,12);
                activateCard(n);
                //searchCard();
            }else{
                System.out.println("Not in range, try again");
                searchCard();
            }
        }
        catch(InputMismatchException a){
            System.out.println("You did not input a number");
            searchCard();
        }
        reader.close();
    }
    public void search(){
        System.out.println("RANDO:"+activeCardIndex);
        printHand(13,26);
        int index=binarySearch(13,26,activeCardIndex);
        System.out.print("Found:");
        deckArr[index].printCard();
    }
    public void activateCard(int n){
        if(activeCardIndex==-1){
            activeCardIndex=n;
            System.out.print("Active card:");
            deckArr[activeCardIndex].printCard();
        }
    }
    private void printHand(int start,int end){
        for(int i=start;i<end;i++){
            if(deckArr[i].active){
                printCard(i);
            }
        }
    }
    private void printCard(int i){
        System.out.print(i+")");
        deckArr[i].printCard();
    }
}
