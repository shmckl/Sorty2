package com.example.sorty2.data.repository

import com.example.sorty2.data.models.Bill
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillRepository @Inject constructor() {

    private val bills = mutableListOf<Bill>()

    suspend fun getAllBills(): List<Bill> {
        // Replace this with Firebase Realtime Database implementation
        return bills
    }

    suspend fun addBill(bill: Bill) {
        // Replace this with Firebase Realtime Database implementation
        bills.add(bill)
    }

    suspend fun deleteBill(bill: Bill) {
        // Replace this with Firebase Realtime Database implementation
        bills.remove(bill)
    }
}
