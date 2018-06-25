package br.agner.prototipo.model

import java.io.Serializable

data class Dados(var id: String?,
                 var fluxo: Fluxo?,
                 var itens: List<String>) : Serializable