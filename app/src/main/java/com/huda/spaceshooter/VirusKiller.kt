package com.huda.spaceshooter

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_MOVE
import android.view.View
import androidx.navigation.findNavController
import java.util.Random

class VirusKiller(private val context: Context) : View(context) {
    private var background: Bitmap
    private var lifeImage: Bitmap
    private var handler: Handler? = Handler()
    private var points = 0
    private var paused = false
    private var life = 3
    private var scorePaint = Paint()

    private var virus = Virus(context)
    private var medicine = Medicine(context)

    private var random: Random = Random()

    private var virusShots: ArrayList<Shot> = arrayListOf()
    private var medicineShots: ArrayList<Shot> = arrayListOf()
    private val bundle = Bundle()
    private var virusExplosion = false
    private lateinit var explosion: Explosion
    private var explosions: ArrayList<Explosion> = arrayListOf()
    private var virusShotAction = false
    private val runnable = Runnable {
        invalidate()
    }

    init {
        setScreenSize(context)
        background = BitmapFactory.decodeResource(context.resources, R.drawable.background)
        lifeImage = BitmapFactory.decodeResource(context.resources, R.drawable.life)
        scorePaint.color = Color.RED
        scorePaint.textSize = TEXT_SIZE
        scorePaint.textAlign = Paint.Align.LEFT
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(background, 0f, 0f, null)
        canvas.drawText("Pt: $points", 0f, TEXT_SIZE, scorePaint)
        for (i in life downTo 1) {
            canvas.drawBitmap(lifeImage, screenWidth.toFloat() - (lifeImage.width * i), 0f, null)
        }
        if (life == 0) {
            paused = true
            handler = null
            bundle.putBoolean("paused", true)
            bundle.putInt("points", points)
            findNavController().navigate(R.id.game_over, bundle)
        }
        if (points>=100){

        }
        virus.vx += virus.virusVelocity
        if (virus.vx + virus.getVirusWidth() >= screenWidth || virus.vx <= 0) {
            virus.virusVelocity *= -1
        }
        if ((!virusShotAction) && (virus.vx >= 200 + random.nextInt(400))) {
            val virusShot = Shot(context, virus.vx + (virus.getVirusWidth() / 2), virus.vy)
            virusShots.add(virusShot)
            virusShotAction = true
        }
        if (!virusExplosion) {
            canvas.drawBitmap(virus.getVirusBitmap(), virus.vx.toFloat(), virus.vy.toFloat(), null)
        }
        if (medicine.isAlive) {
            if (medicine.mx > screenWidth - medicine.getMedicineWidth()) {
                medicine.mx = screenWidth - medicine.getMedicineWidth()
            } else if (medicine.mx < 0) {
                medicine.mx = 0
            }
            canvas.drawBitmap(
                medicine.getMedicineBitmap(),
                medicine.mx.toFloat(),
                medicine.my.toFloat(),
                null
            )
        }
        for (i in 0 until virusShots.size) {
            virusShots[i].shy += 15
            canvas.drawBitmap(
                virusShots[i].getShot(),
                virusShots[i].shx.toFloat(),
                virusShots[i].shy.toFloat(),
                null
            )
            if (virusShots[i].shx >= medicine.mx && virusShots[i].shx <= medicine.mx + medicine.getMedicineWidth()
                && virusShots[i].shy >= medicine.my && virusShots[i].shy <= medicine.my + medicine.getMedicineHeight()
            ) {
                life--
                virusShots.removeAt(i)
                explosion = Explosion(context, medicine.mx, medicine.my)
                explosions.add(explosion)
            } else if (virusShots[i].shy >= screenHeight) {
                virusShots.removeAt(i)
            }
            if (virusShots.size == 0) {
                virusShotAction = false
            }
        }
        var i = 0
        while (medicineShots.size > 0 && i < medicineShots.size) {
            medicineShots[i].shy -= 15
            canvas.drawBitmap(
                medicineShots[i].getShot(),
                medicineShots[i].shx.toFloat(),
                medicineShots[i].shy.toFloat(),
                null
            )
            if (medicineShots[i].shx >= virus.vx
                && medicineShots[i].shx <= virus.vx + virus.getVirusWidth()
                && medicineShots[i].shy >= virus.vy
                && medicineShots[i].shy <= virus.vy + virus.getVirusHeight()
            ) {
                points++
                medicineShots.removeAt(i)
                explosion = Explosion(context, virus.vx, virus.vy)
                explosions.add(explosion)
                i--
            } else if (medicineShots[i].shy <= 0) {
                medicineShots.removeAt(i)
                i--
            }
            i++
        }
        var j=0
        while (explosions.size>0&&j<explosions.size) {
            canvas.drawBitmap(
                explosions[j].getExplosionFrame(explosions[j].frame),
                explosions[j].ex.toFloat(), explosions[j].ey.toFloat(), null
            )
            explosions[j].frame++
            if (explosions[j].frame > 8) {
                explosions.removeAt(j)
                j--
            }
            j++
        }
        if (!paused) {
            handler?.postDelayed(runnable, UPDATE_MILLIS)
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        if (event?.action == MotionEvent.ACTION_UP) {
            if (medicineShots.size < 3) {
                val shot =
                    Shot(context, (medicine.mx + medicine.getMedicineWidth() / 2), medicine.my)
                medicineShots.add(shot)
            }
        }
        if (event?.action == MotionEvent.ACTION_DOWN || event?.action == ACTION_MOVE) {
            medicine.mx = touchX?.toInt() ?: 0
        }
        return true
    }

    companion object {
        private const val UPDATE_MILLIS = 30L
        private const val TEXT_SIZE = 80f
        var screenWidth = 0
        var screenHeight = 0
        fun setScreenSize(context: Context) {
            val display = (context as Activity).windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            screenWidth = size.x
            screenHeight = size.y
        }
    }
}
