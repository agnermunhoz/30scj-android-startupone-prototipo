package br.agner.prototipo.model

import java.io.Serializable

data class Item(var nome: String,
                var descricao: String,
                var tipo: String,
                var metodo: String) : Serializable