package com.huda.spaceshooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.Random


class Shot(private val context: Context, var shx:Int, var shy:Int) {
    private var shot:Bitmap = BitmapFactory.decodeResource(context.resources,R.drawable.shot)

    fun getShot():Bitmap{
        return shot
    }

}