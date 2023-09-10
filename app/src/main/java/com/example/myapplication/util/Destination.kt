package com.example.myapplication.util

sealed class Destination (val route: String){
    object LoginScreen: Destination("login_screen")
    object RegisterScreen: Destination("register_screen")
}