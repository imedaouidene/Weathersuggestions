package com.mob.mse.weathersuggestions.model;

import java.util.ArrayList;

/**
 * Created by Imed on 04-Jan-18.
 */
// https://pixabay.com/api/?key=7593479-4b373fb7ca049dd32f5c81299&q=switzerland&image_type=photo&pretty=true
public class ImageResponse {

    int totalHits;
    public class hits {

        int previewHeight ;
        int likes ;
        int favorites ;
        String tags;
        int webformatHeight ;
        int views ;
        int webformatWidth ;
        int previewWidth ;
        int comments ;
        int downloads ;
        String pageURL ;
        String previewURL ;
        String webformatURL ;
        int imageWidth ;
        int user_id ;
        String userImageURL ;
        int imageHeight ;




    }

    ArrayList<hits> hits ;
    int total ;

}
