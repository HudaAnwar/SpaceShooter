package com.huda.spaceshooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.Random


class Explosion(private val context: Context, var ex:Int, var ey:Int) {
    private var explosion=arrayOfNulls<Bitmap>(9)
    var frame:Int=0
    init {
        explosion[0] = BitmapFactory.decodeResource(context.resources,R.drawable.explosion0)
        explosion[1] = BitmapFactory.decodeResource(context.resources,R.drawable.explosion1)
        explosion[2] = BitmapFactory.decodeResource(context.resources,R.drawable.explosion2)
        explosion[3] = BitmapFactory.decodeResource(context.resources,R.drawable.explosion3)
        explosion[4] = BitmapFactory.decodeResource(context.resources,R.drawable.explosion4)
        explosion[5] = BitmapFactory.decodeResource(context.resources,R.drawable.explosion5)
        explosion[6] = BitmapFactory.decodeResource(context.resources,R.drawable.explosion6)
        explosion[7] = BitmapFactory.decodeResource(context.resources,R.drawable.explosion7)
        explosion[8] = BitmapFactory.decodeResource(context.resources,R.drawable.explosion8)
    }

    fun getExplosionFrame(frame:Int):Bitmap{
        return explosion[frame]!!
    }

}