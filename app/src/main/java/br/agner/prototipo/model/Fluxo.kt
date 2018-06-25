package br.agner.prototipo.model

import java.io.Serializable


data class Fluxo(var id: String?,
                 var nome: String,
                 var descricao: String,
                 var itens: List<Item>?) : Serializable