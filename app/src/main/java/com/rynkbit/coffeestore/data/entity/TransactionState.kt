package com.rynkbit.coffeestore.data.entity

import com.rynkbit.coffeestore.R

enum class TransactionState(val resourceStringId: Int){
    OPEN(R.string.opened),
    CLOSED(R.string.closeed),
    REVOKED(R.string.revoked)
}