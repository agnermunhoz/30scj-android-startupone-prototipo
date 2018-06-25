package br.agner.prototipo.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import br.agner.prototipo.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        carregar()
    }

    fun carregar() {
        val animacao = AnimationUtils.loadAnimation(this, R.anim.animacao_splash)
        ivLogo.startAnimation(animacao)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        },3000)
    }

    fun onYesClick() {
        finish()
    }

}
