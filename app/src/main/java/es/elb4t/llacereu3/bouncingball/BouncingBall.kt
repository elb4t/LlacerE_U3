package es.elb4t.llacereu3.bouncingball

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by eloy on 24/2/18.
 */
class BouncingBall : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(BouncingBallView(this))
    }
}