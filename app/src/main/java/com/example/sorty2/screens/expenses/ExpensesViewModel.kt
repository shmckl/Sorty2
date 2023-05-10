package com.example.sorty2.screens.expenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sorty2.data.models.Bill
import com.example.sorty2.data.repository.BillRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val billRepository: BillRepository
) : ViewModel() {

    private val _bills = MutableLiveData<List<Bill>>()
    val bills: LiveData<List<Bill>> = _bills

//    val totalBalance: Float
//        get() = bills.value?.sumBy { it.balance }?.toFloat() ?: 0f

    init {
        viewModelScope.launch {
            _bills.value = billRepository.getAllBills()
        }
    }

    fun addBill(bill: Bill) {
        viewModelScope.launch {
            billRepository.addBill(bill)
            _bills.value = billRepository.getAllBills()
        }
    }

    fun deleteBill(bill: Bill) {
        viewModelScope.launch {
            billRepository.deleteBill(bill)
            _bills.value = billRepository.getAllBills()
        }
    }
}
