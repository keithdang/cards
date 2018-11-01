package com.company;

public class Util {
    private static Util single_instance = null;
    // static method to create instance of Singleton class
    public static Util getInstance()
    {
        if (single_instance == null)
            single_instance = new Util();

        return single_instance;
    }
    public static int presidentValues(int num){
        if(num==1){
            return 14;
        }else if(num ==2){
            return 15;
        }else{
            return num;
        }
    }
    public static void swap(int a,int b,card[] arr){
        card temp=arr[a];
        arr[a]=arr[b];
        arr[b]=temp;
    }
    public static int[] stringArrToInt(String[] arrWord){
        int[] arr=new int[arrWord.length];
        for(int i=0;i<arrWord.length;i++){
            arr[i]=Integer.parseInt(arrWord[i]);
        }
        return arr;
    }
}
