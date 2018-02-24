package es.elb4t.llacereu3.messengerservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import es.elb4t.llacereu3.R
import java.lang.ref.WeakReference


class Messenger : AppCompatActivity() {

    private var mBoundServiceMessenger: android.os.Messenger? = null
    private var mServiceConnected = false
    private var mTimestampText: TextView? = null
    private val mActivityMessenger = Messenger(
            ActivityHandler(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cronometro)
        mTimestampText = findViewById(R.id.timestamptext)
        val printTimestampButton = findViewById<View>(R.id.btnPrintTimeStamp) as Button
        val stopServiceButon = findViewById<View>(R.id.btnStopService) as Button
        printTimestampButton.setOnClickListener {
            if (mServiceConnected) {
                try {
                    val msg = Message.obtain(null,
                            MessengerService.MSG_GET_TIMESTAMP, 0, 0)
                    msg.replyTo = mActivityMessenger
                    mBoundServiceMessenger!!.send(msg)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }

            }
        }


        stopServiceButon.setOnClickListener {
            if (mServiceConnected) {
                unbindService(mServiceConnection)
                mServiceConnected = false
            }
            val intent = Intent(this@Messenger,
                    MessengerService::class.java)
            stopService(intent)
        }


    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MessengerService::class.java)
        startService(intent)
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (mServiceConnected) {
            unbindService(mServiceConnection)
            mServiceConnected = false
        }
    }

    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName) {
            mBoundServiceMessenger = null
            mServiceConnected = false
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBoundServiceMessenger = Messenger(service)
            mServiceConnected = true
        }
    }

    internal class ActivityHandler(activity: Messenger) : Handler() {
        private val mActivity: WeakReference<Messenger> = WeakReference(activity)

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MessengerService.MSG_GET_TIMESTAMP -> {
                    mActivity.get()!!.mTimestampText!!.text = msg.data.getString("timestamp")
                }
            }
        }

    }
}
