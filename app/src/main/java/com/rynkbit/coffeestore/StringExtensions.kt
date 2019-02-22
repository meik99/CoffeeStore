package com.rynkbit.coffeestore



fun String.toInternationalDouble(): Double{
    return this.replace(",", ".").trim().toDouble()
}