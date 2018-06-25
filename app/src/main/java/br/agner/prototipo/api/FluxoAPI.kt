package br.agner.prototipo.api

import br.agner.prototipo.model.Dados
import br.agner.prototipo.model.Fluxo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FluxoAPI {

    @GET("/services")
    fun getServices(): Call<List<Fluxo>>

    @GET("/service/{id}")
    fun getService(@Path("id") id: String?): Call<Fluxo>

    @POST("/service/{id}")
    fun postService(@Path("id") id: String?, @Body dados: Dados): Call<Dados>

}