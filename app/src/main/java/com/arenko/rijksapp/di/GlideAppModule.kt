package com.arenko.rijksapp.di

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import com.arenko.rijksapp.R
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import dagger.Provides
import javax.inject.Singleton

@GlideModule
class GlideAppModule : AppGlideModule() {

    @Singleton
    @Provides
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.apply {
            RequestOptions()
                .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.light_gray)))
                .error(ColorDrawable(ContextCompat.getColor(context, R.color.light_gray)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(300)
                .timeout(30000)
        }
    }

}