package br.agner.prototipo.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.agner.prototipo.R
import kotlinx.android.synthetic.main.activity_text_field.*

class TextFieldActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_field)
        var descricao = intent.extras.getString("descricao")
        tvDescricao.setText(descricao)
        etData.text.clear()

        btnSend.setOnClickListener {
            var resultIntent = Intent()
            resultIntent.putExtra("RESULT", etData.text.toString());
            setResult(1, resultIntent);
            finish();
        }
    }
}
