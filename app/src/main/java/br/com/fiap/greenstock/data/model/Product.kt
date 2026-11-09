package br.com.fiap.greenstock.data.model

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val quantity: Int,
    val unit: String,
    val expirationDate: String,
    val status: String,
    val image: String? = null
)

data class ProductApiResponse(
    val products: List<ProductApi>
)

data class ProductApi(
    val id: Int,
    val title: String,
    val category: String,
    val stock: Int,
    val thumbnail: String
)