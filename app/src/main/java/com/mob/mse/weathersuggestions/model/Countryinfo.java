package com.mob.mse.weathersuggestions.model;

import java.util.ArrayList;

/**
 * Created by Imed on 04-Jan-18.
 */
//https://restcountries.eu/rest/v2/alpha?codes=ch


public class Countryinfo {
     public String name;

    public  ArrayList<String> topLevelDomains ;
    public String alpha2Code ;
    public   String  alpha3Code ;

    public  ArrayList<String> callingCodes ;


    public   String capital ;

    public   ArrayList<String> altSpellings ;
    public    String region ;
    public    String subregion ;
    public    long population ;
    public   ArrayList<Double> latlng ;
    public  String demonym ;
    public  int area ;
    public double gini ;
    public   ArrayList<String> timezones ;
    public   ArrayList<String>borders ;
    public   String nativeName;
    public   String numericCode ;
    public class currency {
        public   String code;
        public  String name ;
        public   String symbol ;
    }
    public  ArrayList<currency> currencies ;
    public class language {

      public  String iso639_1 ;
        public   String iso639_2 ;
        public  String name ;
        public   String nativeName ;
    }
    public  ArrayList<language> languages ;
    public class translation {
        String de ;
        String es;
        String fr;
        String ja;
        String it;
        String br;
        String pt;
        String nl;
        String hr;
        String fa;

    }

    public translation translation ;
    public String flag ;


public  class regionalBlocs {


    public  String acronym ;
    public String name ;
    public  ArrayList<String>otherAcronyms;
    public  ArrayList<String> otherNames ;
}
    public ArrayList<regionalBlocs> regionalBlocs ;
    public String cioc ;


}
