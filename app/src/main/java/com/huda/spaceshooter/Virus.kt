package com.huda.spaceshooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.Random


class Virus(private val context: Context) {
    private var virus:Bitmap = BitmapFactory.decodeResource(context.resources,R.drawable.virus)
    private var random: Random = Random()

    var vx=0
    var vy=0
    var virusVelocity=0

    init {
        resetVirus()
    }
    fun getVirusBitmap():Bitmap{
        return virus
    }
    fun getVirusWidth():Int{
        return virus.width
    }

    fun getVirusHeight():Int{
        return virus.height
    }
    private fun resetVirus() {
        vx = 200+random.nextInt(400)
        vy=0
        virusVelocity = 14+random.nextInt(10)

    }
}