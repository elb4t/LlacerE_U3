package es.elb4t.llacereu3.primos

/**
 * Created by eloy on 19/2/18.
 */
interface TaskListener {
    fun onPreExecute()
    fun onProgressUpdate(progreso: Double?)
    fun onPostExecute(resultado: Boolean)
    fun onCancelled()
}