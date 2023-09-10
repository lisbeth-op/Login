package com.example.myapplication.data

data class Authentication(

    val nombreDeUsuario: String ="",
    val clave: String="",
    var visiblidadDeClave: Boolean=true,
    val loading: Boolean=false,

    ){
    fun formularioValido(): Boolean{
        return nombreDeUsuario.isNotEmpty()&& clave.isNotEmpty()
    }
    companion object{
        val EMPTY_STATE=Authentication()
    }
}
