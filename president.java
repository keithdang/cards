package com.company;
import java.util.InputMismatchException;
import java.util.Scanner;

public class president extends deck{
    int activeCardIndex=-1;
    Util util=Util.getInstance();
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
    public int binarySearch(int l,int r, int x){
        int mid=l+(r-l)/2;
        int midCard=util.presidentValues(deckArr[mid].numValue);
        int searchCard=util.presidentValues(deckArr[x].numValue);
        if(util.presidentValues( deckArr[r].numValue)<searchCard){
            System.out.println("No cards available");
            return -1;
        }else{
            return binaryCompare(l,r,x,mid,midCard,searchCard);
        }
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
            }else if(n==99){
                System.out.println("Skip");
            } else{
                System.out.println("Not in range, try again");
                searchCard();
            }
        }
        catch(InputMismatchException a){
            System.out.println("You did not input a number");
            searchCard();
        }
        //reader.close();
    }
    public void opponentTurn(int oppNum){
        int start=(oppNum-1)*13;
        int end=(oppNum)*13;
        System.out.println("\nActive:"+activeCardIndex);
        printHand(start,end);
        if(activeCardIndex>=start && activeCardIndex<=end){
            System.out.print("Other players could not beat that card");
        }else{
            int index=findBestCard(start,end,activeCardIndex);
            if(index!=-1){
                System.out.print("Found:");
                deckArr[index].printCard();
                activateCard(index);
            }else{
                System.out.print("No cards available");
            }
        }
    }
    private int findBestCard(int start,int end,int activeIndex){
        int index=binarySearch(start,end-1,activeIndex);
        while(index != -1){
            if(deckArr[index].active){
                break;
            }else{
                if(index<end-1){
                    index++;
                    System.out.println("kdawg increasing index to"+index);
                }else{
                    System.out.println("kdawg No cards available");
                    index=-1;
                    break;
                }
            }
        }
        return index;
    }
    public void activateCard(int n){
//        if(activeCardIndex==-1){
            activeCardIndex=n;
            System.out.print("Active card:");
            deckArr[activeCardIndex].printCard();
            deckArr[activeCardIndex].setActive(false);
//        }
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
