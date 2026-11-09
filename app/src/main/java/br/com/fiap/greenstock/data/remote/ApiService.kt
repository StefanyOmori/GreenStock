package br.com.fiap.greenstock.data.remote

import br.com.fiap.greenstock.data.model.ProductApiResponse
import retrofit2.http.GET

interface ApiService {

    @GET("products/category/groceries")
    suspend fun getProducts(): ProductApiResponse
}