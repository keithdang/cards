package com.company;

public class president extends deck{
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
    public void splitAndOrderCards(int splitNum){
        int ratio=deckArr.length/splitNum;
        for(int i=0;i<splitNum;i++){
            reorderHand(ratio*i,(ratio)*(i+1));
            if(i==0){
                System.out.println("Your hand");
            }else{
                System.out.println("Opponents hand");
            }
            printHand(ratio*i,(ratio)*(i+1));
        }

    }
    private void printHand(int start,int end){
        for(int i=start;i<end;i++){
            System.out.println(deckArr[i].cardValue + " of " +deckArr[i].suit);
        }
    }
}
