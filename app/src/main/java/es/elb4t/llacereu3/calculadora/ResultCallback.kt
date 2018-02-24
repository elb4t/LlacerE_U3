package es.elb4t.llacereu3.calculadora

/**
 * Created by eloy on 21/2/18.
 */
interface ResultCallback<T> {
    fun onResult(data: T)
}