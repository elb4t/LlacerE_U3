package es.elb4t.llacereu3.primos

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import es.elb4t.llacereu3.R
import kotlinx.android.synthetic.main.activity_primos.*

/**
 * Created by eloy on 24/2/18.
 */
class PrimosFragmentOculto : AppCompatActivity(), FragmentOculto.TaskListener {
    companion object {
        private val TAG = PrimosFragmentOculto::class.java.name
    }

    private var inputField: EditText? = null
    private lateinit var resultField: EditText
    private lateinit var primecheckbutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primos)
        inputField = findViewById(R.id.inputField)
        resultField = findViewById(R.id.resultField)
        primecheckbutton = findViewById(R.id.primecheckbutton)

        primecheckbutton.setOnClickListener {
            val parameter = inputField!!.text.toString().toLong()
            val parametros = Bundle()
            parametros.putLong("numComprobar", parameter)
            val fragment = FragmentOculto().newInstance(parametros)
            val ft = fragmentManager.beginTransaction()
            ft.replace(android.R.id.content, fragment, FragmentOculto.TAG)
            ft.commit()
        }
    }

    override fun onPreExecute() {
        resultField.setText("")
        primecheckbutton.isEnabled = false
    }

    override fun onProgressUpdate(progreso: Double) {
        resultField.setText(String.format("%.1f%% completado", progreso * 100))
        progressResult.progress = (progreso * 100).toInt()
    }

    override fun onPostExecute(resultado: Boolean) {
        resultField.setText(resultado.toString() + "")
        progressResult.progress = 100
        primecheckbutton.text = "¿ES PRIMO?"
        primecheckbutton.isEnabled = true
    }

    override fun onCancelled() {
        resultField.setText("Proceso cancelado")
        primecheckbutton.isEnabled = true
    }
}