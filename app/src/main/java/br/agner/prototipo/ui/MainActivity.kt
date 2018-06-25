package br.agner.prototipo.ui

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import br.agner.prototipo.R
import br.agner.prototipo.api.FluxoAPI
import br.agner.prototipo.api.RetrofitClient
import br.agner.prototipo.model.Dados
import br.agner.prototipo.model.Fluxo
import br.agner.prototipo.ui.adapter.ListaFluxosAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.loading.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import kotlin.math.log

class MainActivity : AppCompatActivity(), Serializable {

    private val reqCodeForm = 1
    private var fluxos : List<Fluxo>? = null
    private var results: MutableList<String> = mutableListOf()
    private var fluxo: Fluxo? = null
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == reqCodeForm) {
            results.add(data!!.getStringExtra("RESULT"))
            stepFluxo()
        }
    }

    override fun onResume() {
        super.onResume()

        carregarDados()
    }

    fun carregarDados() {
        var api = RetrofitClient.getInstance().create(FluxoAPI::class.java)

        loading.visibility = View.VISIBLE

        api.getServices().enqueue(object : Callback<List<Fluxo>> {

            override fun onResponse(call: Call<List<Fluxo>>?, response: Response<List<Fluxo>>?) {
                if (response?.isSuccessful() == true) {
                    setReceitas(response?.body())
                } else {
                    error.visibility = View.VISIBLE
                    tvMensagemErro.text = response?.errorBody()?.charStream()?.readText();
                }

                loading.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<Fluxo>>?, t: Throwable?) {
                Log.i("TAG", t?.message)
                error.visibility = View.VISIBLE
                tvMensagemErro.text = t?.message
                loading.visibility = View.GONE
            }
        })

    }

    fun setReceitas(fluxos: List<Fluxo>?) {

        this.fluxos = fluxos
        loadReciclyView(this.fluxos)
    }

    fun loadReciclyView(receitas: List<Fluxo>?) {
        receitas.let {
            rvReceitas.adapter = ListaFluxosAdapter(fluxos!!, this)
            val layoutManager = LinearLayoutManager(this)
            rvReceitas.layoutManager = layoutManager
        }
    }

    fun startFluxo(fluxo: Fluxo) {
        this.results = mutableListOf()
        this.fluxo = fluxo
        this.position = 0
        stepFluxo()
    }

    fun stepFluxo() {
        if (fluxo!!.itens!!.size > position) {
            var i = Intent(this, TextFieldActivity::class.java)
            var descricao = fluxo!!.itens!![position].descricao
            i.putExtra("descricao", descricao)
            startActivityForResult(i, reqCodeForm)
            position++
        } else {
            //fim
            Log.i("TAG", "  Fim do fluxo")
            results.forEach{
                Log.i("TAG", "  |- "+it)
            }
            finishFluxo()
        }
    }

    fun finishFluxo() {
        var api = RetrofitClient.getInstance().create(FluxoAPI::class.java)
        var dados = Dados(null, null, results)
        api.postService(fluxo!!.id, dados).enqueue(object : Callback<Dados> {

            override fun onResponse(call: Call<Dados>?, response: Response<Dados>?) {
                if (response?.isSuccessful() == true) {
                    var dados = response.body()
                    Log.i("TAG", "Dados id "+dados?.id)
                } else {
                    Log.i("TAG", "Erro")
                }
            }

            override fun onFailure(call: Call<Dados>?, t: Throwable?) {
                Log.i("TAG", "Erro")
            }
        })
    }
}
