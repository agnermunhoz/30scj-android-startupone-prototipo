package br.agner.prototipo.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.agner.prototipo.R
import br.agner.prototipo.api.FluxoAPI
import br.agner.prototipo.api.RetrofitClient
import br.agner.prototipo.model.Fluxo
import br.agner.prototipo.ui.MainActivity
import kotlinx.android.synthetic.main.item_receita.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListaFluxosAdapter(private val fluxos: List<Fluxo>,
                         private val context: Context) : RecyclerView.Adapter<ListaFluxosAdapter.ListaFluxosHolder>() {

    override fun getItemCount(): Int {
        return fluxos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaFluxosHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_receita, parent, false)
        return ListaFluxosHolder(view, context)
    }

    override fun onBindViewHolder(holder: ListaFluxosHolder, position: Int) {
        val fluxo = fluxos[position]
        holder?.let {
            it.bindView(fluxo)
        }
    }

    class ListaFluxosHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var fluxo: Fluxo? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(fluxo: Fluxo) {
            this.fluxo = fluxo
            itemView.tvFluxo.text = fluxo.descricao
            itemView.tvFluxoId.text = fluxo.id
        }

        override fun onClick(itemView: View?) {
            var id = ""+itemView?.tvFluxoId?.text;
            Log.i("TAG", "Elemento "+ itemView?.tvFluxo?.text + " clicado. ID: "+itemView?.tvFluxoId?.text)
            var api = RetrofitClient.getInstance().create(FluxoAPI::class.java)

            api.getService(id).enqueue(object : Callback<Fluxo> {
                override fun onResponse(call: Call<Fluxo>?, response: Response<Fluxo>?) {
                    if (response?.isSuccessful() == true) {
                        var fluxo = response?.body()
                        Log.i("TAG", "Fluxo "+fluxo?.nome+" - Descrição "+fluxo?.descricao);
                        fluxo?.itens?.forEach {
                            Log.i("TAG", " |- Item "+it.nome+" - Descrição "+it.descricao)
                        }
                        if (context is MainActivity) {
                            context.startFluxo(fluxo!!)
                        }
                    } else {
                        Log.i("TAG", "Erro")
                    }
                }

                override fun onFailure(call: Call<Fluxo>?, t: Throwable?) {
                    Log.i("TAG", "Erro")
                }
            });

            /*var i = Intent(context, ReceitaViewActivity::class.java)
            i.putExtra("receita", receita)
            context.startActivity(i)
            */
        }
    }
}