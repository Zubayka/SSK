package com.example.sskplatform.ui.appeals

data class ViolationModel (
    val title: String,
    val message: String,
    val image: String,
    val date: String
        ) {
    constructor():this(
        "",
        "",
        "",
        ""
    )
}