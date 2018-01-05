package com.mob.mse.weathersuggestions.model;

import java.util.ArrayList;

/**
 * Created by Imed on 04-Jan-18.
 */
// https://pixabay.com/api/?key=7593479-4b373fb7ca049dd32f5c81299&q=switzerland&image_type=photo&pretty=true
public class ImageResponse {

    public  int totalHits;
    public class hits {

       public int previewHeight ;
       public int likes ;
        public   int favorites ;
        public     String tags;
        public     int webformatHeight ;
        public      int views ;
        public      int webformatWidth ;
        public      int previewWidth ;
        public      int comments ;
        public      int downloads ;
        public       String pageURL ;
        public        String previewURL ;
        public        String webformatURL ;
        public      int imageWidth ;
        public      int user_id ;
        public      String userImageURL ;
        public       int imageHeight ;




    }

    public   ArrayList<hits> hits ;
    public  int total ;

}
