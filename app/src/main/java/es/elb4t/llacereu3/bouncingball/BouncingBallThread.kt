package es.elb4t.llacereu3.bouncingball

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView


/**
 * Created by eloy on 24/2/18.
 */
class BouncingBallThread(view: SurfaceView) : Thread() {
    val FPS: Long = 10
    private var superfView: SurfaceView? = null
    private var width: Int = 100
    private var height: Int = 100
    private var running = false
    private var pos_x = -1
    private var pos_y = -1
    private var xVelocidad = 10
    private var yVelocidad = 5
    //private var pelota: BitmapDrawable? = null
    var touched_x: Int = 0
    var touched_y: Int = 0
    var touched: Boolean = false
    var canvas: Canvas? = null
    val paint: Paint = Paint()
    val radioCirculo: Float = 50f

    init {
        this.superfView = view
        // Coloca una imagen de tu elección
        //pelota = view.context.resources.getDrawable(R.drawable.pelota) as BitmapDrawable?
    }

    fun setRunning(run: Boolean) {
        running = run
    }

    override fun run() {
        val ticksPS = (1000 / this.FPS)
        var startTime: Long
        var sleepTime: Long
        while (running) {

            startTime = System.currentTimeMillis()

            try {
                // Bloqueamos el canvas de la superficie para dibujarlo
                canvas = superfView!!.holder.lockCanvas()
                // Sincronizamos el método draw() de la superficie para
                // que se ejecute como un bloque
                synchronized(superfView!!.holder) {
                    if (canvas != null) doDraw(canvas!!)
                }
            } finally {
                // Liberamos el canvas de la superficie desbloqueándolo
                if (canvas != null) {
                    superfView!!.holder.unlockCanvasAndPost(canvas)
                }
            }
            // Tiempo que debemos parar la ejecución del hilo
            sleepTime = ticksPS - System.currentTimeMillis() - startTime
            // Paramos la ejecución del hilo
            try {
                if (sleepTime > 0)
                    Thread.sleep(sleepTime)
                else
                    Thread.sleep(10)
            } catch (e: Exception) {
            }
        }
    }

    protected fun doDraw(canvas: Canvas) {
        if (pos_x < 0 && pos_y < 0) {
            pos_x = this.width / 2
            pos_y = this.height / 2
        } else {
            pos_x += xVelocidad
            pos_y += yVelocidad
            if (touched && touched_x > (pos_x - radioCirculo * 2)
                    && touched_x < (pos_x + radioCirculo * 2)
                    && touched_y > (pos_y - radioCirculo * 2)
                    && touched_y < (pos_y + radioCirculo * 2)) {
                touched = false
                xVelocidad *= -1
                yVelocidad *= -1
            }
            if (pos_x > this.width - radioCirculo || pos_x < 0) {
                xVelocidad *= -1
            }
            if (pos_y > this.height - radioCirculo*2 || pos_y < 0) {
                yVelocidad *= -1
            }
        }
        canvas.drawColor(Color.LTGRAY)
        //canvas.drawBitmap(pelota!!.bitmap, pos_x.toFloat(), pos_y.toFloat(), null)
        paint.color = Color.BLUE
        canvas.drawCircle(pos_x.toFloat(), pos_y.toFloat(), radioCirculo, paint)
    }

    fun setSurfaceSize(width: Int, heigth: Int) {
        synchronized(superfView!!) {
            this.width = width
            this.height = height
        }
    }

    fun onTouch(event: MotionEvent): Boolean {
        touched_x = event.x.toInt()
        touched_y = event.y.toInt()
        val action = event.action
        Log.e("TOUCH", "--------------------------")
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                touched = true
                Log.e("TOUCH", "DOWN------")
            }
            MotionEvent.ACTION_MOVE -> {
                touched = true
                Log.e("TOUCH", "MOVE------")
            }
            MotionEvent.ACTION_UP -> {
                touched = false
                Log.e("TOUCH", "UP------")
            }
            MotionEvent.ACTION_CANCEL -> {
                touched = false
                Log.e("TouchEven ACTION_CANCEL", " ")
            }
            MotionEvent.ACTION_OUTSIDE -> touched = false
        }
        return true
    }

}