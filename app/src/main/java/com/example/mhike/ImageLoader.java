package com.example.mhike;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
public class ImageLoader {
    // Function to load and display an image from a URL into an ImageView
    public static void loadImageFromUrl(Context context, String imageUrl, ImageView imageView) {
        // Use Picasso to load and display the image
        Picasso.get()
                .load(imageUrl)
                .into(imageView);
    }
}
