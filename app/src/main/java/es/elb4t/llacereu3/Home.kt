package es.elb4t.llacereu3

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import es.elb4t.llacereu3.bouncingball.BouncingBall
import es.elb4t.llacereu3.calculadora.CalculadoraSHA1
import es.elb4t.llacereu3.calculadora.CalculadoraSHA1Broadcast
import es.elb4t.llacereu3.messengerservice.Messenger
import es.elb4t.llacereu3.primos.Primos
import es.elb4t.llacereu3.primos.PrimosFragmentOculto
import es.elb4t.llacereu3.primos.PrimosInterface
import es.elb4t.llacereu3.serviciocronometro.ServicioCronometro
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_primos -> {
                startActivity(Intent(this,Primos::class.java))
            }
            R.id.nav_primosinterface -> {
                startActivity(Intent(this,PrimosInterface::class.java))
            }
            R.id.nav_primosfragmento -> {
                startActivity(Intent(this,PrimosFragmentOculto::class.java))
            }
            R.id.nav_cronometro -> {
                startActivity(Intent(this,ServicioCronometro::class.java))
            }
            R.id.nav_messengerservice -> {
                startActivity(Intent(this, Messenger::class.java))
            }
            R.id.nav_calculadorasha1 -> {
                startActivity(Intent(this, CalculadoraSHA1::class.java))
            }
            R.id.nav_calculadorasha1broadcast -> {
                startActivity(Intent(this, CalculadoraSHA1Broadcast::class.java))
            }
            R.id.nav_bouncingball -> {
                startActivity(Intent(this, BouncingBall::class.java))
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
