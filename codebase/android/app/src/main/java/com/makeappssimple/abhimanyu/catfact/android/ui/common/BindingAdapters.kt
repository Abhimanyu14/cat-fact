package com.makeappssimple.abhimanyu.catfact.android.ui.common

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imageSrc")
fun bindImage(imgView: ImageView, drawable: Drawable?) {
    drawable?.let {
        imgView.load(drawable)
    }
}
