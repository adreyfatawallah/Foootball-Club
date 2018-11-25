package com.example.adrey.footballclub.api

import java.net.URL

class ApiRepository {

    fun doRequest(url: String): String = URL(url).readText()
}