package com.example.nearbyeats_app.utils

import java.util.Locale

fun Double.toCurrency(): String = "€%.2f".format(this)

fun Float.toStarDisplay(): String = "%.1f".format(this)

fun String.capitalize(): String =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
