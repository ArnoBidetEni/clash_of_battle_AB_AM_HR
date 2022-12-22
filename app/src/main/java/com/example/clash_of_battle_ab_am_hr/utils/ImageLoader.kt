package com.example.clash_of_battle_ab_am_hr.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso


/**
 * Importe une image à partir d'une URL et l'ajoute à une ImageView.
 *
 * IMPORTANT !! N'oubliez pas d'ajouter la dépendance suivante dans app/build.gradle :
 * implementation 'com.squareup.picasso:picasso:2.8'
 *
 * Et d'ajouter la permission dans AndroidManifest :
 * <uses-permission android:name="android.permission.INTERNET"/>
 */
fun loadImage(imageView: ImageView, url: String) {
    Picasso.get()
        .load(url)
        .fit()
        .into(imageView)
}