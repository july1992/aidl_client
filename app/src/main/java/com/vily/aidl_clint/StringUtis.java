package com.vily.aidl_clint;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-11-11
 *  
 **/
public class StringUtis {

    public static int toInt(String a){

        int value=0;
        try {

            value=Integer.parseInt(a);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;

    }
}
