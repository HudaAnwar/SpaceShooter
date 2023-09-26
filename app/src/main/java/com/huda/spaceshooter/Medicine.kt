package com.huda.spaceshooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.Random


class Medicine(val context: Context) {
    private var medicine:Bitmap = BitmapFactory.decodeResource(context.resources,R.drawable.medicine1)
    private var random: Random = Random()
    var mx=0
    var my=0
    var isAlive:Boolean=true
    var medicineVelocity=0

    init {
        resetMedicine()
    }
    fun getMedicineBitmap():Bitmap{
        return medicine
    }
    fun getMedicineWidth():Int{
        return medicine.width
    }

    fun getMedicineHeight():Int{
        return medicine.height
    }
    private fun resetMedicine() {
        VirusKiller.setScreenSize(context)
        mx = 200+random.nextInt(VirusKiller.screenWidth)
        my=VirusKiller.screenHeight-medicine.height
        medicineVelocity = 10+random.nextInt(6)
        
    }
}