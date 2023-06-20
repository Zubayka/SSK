package com.example.sskplatform.ui.appeals

import java.util.Date

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