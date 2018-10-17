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
}
