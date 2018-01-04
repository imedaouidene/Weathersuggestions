package com.mob.mse.weathersuggestions.model;

import java.util.ArrayList;

/**
 * Created by Imed on 04-Jan-18.
 */
//https://restcountries.eu/rest/v2/alpha?codes=ch


public class Countryinfo {
    String name;

    ArrayList<String> topLevelDomains ;
    String alpha2Code ;
    String  alpha3Code ;

    ArrayList<String> callingCodes ;


    String capital ;

    ArrayList<String> altSpellings ;
    String region ;
    String subregion ;
    long population ;
    ArrayList<Integer> latlng ;
    String demonym ;
    int area ;
    double gini ;
    ArrayList<String> timezones ;
    ArrayList<String>borders ;
    String nativeName;
    String numericCode ;
    public class currency {
        String code;
        String name ;
        String symbol ;
    }
    ArrayList<currency> currencies ;
    public class language {

        String iso639_1 ;
        String iso639_2 ;
        String name ;
        String nativeName ;
    }
    ArrayList<language> languages ;
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

translation translation ;
    String flag ;


public  class regionalBlocs {


    String acronym ;
    String name ;
    ArrayList<String>otherAcronyms;
    ArrayList<String> otherNames ;
}
ArrayList<regionalBlocs> regionalBlocs ;
    String cioc ;


}
