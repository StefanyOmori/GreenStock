package br.com.fiap.greenstock.data.repository

import br.com.fiap.greenstock.data.model.Product
import br.com.fiap.greenstock.data.remote.ApiService

class ProductRepository(
    private val api: ApiService
) {

    suspend fun getProducts(): List<Product> {

        val response = api.getProducts()

        return response.products.map { item ->
            Product(
                id = item.id,
                name = item.title,
                category = item.category,
                quantity = item.stock,
                unit = "unidades",
                expirationDate = "Não informada",
                status = "Sem validade cadastrada",
                image = item.thumbnail
            )
        }
    }
}