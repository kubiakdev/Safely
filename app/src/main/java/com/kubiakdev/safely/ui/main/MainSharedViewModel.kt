package com.kubiakdev.safely.ui.main

import androidx.lifecycle.MutableLiveData
import com.kubiakdev.safely.base.BaseSharedViewModel
import com.kubiakdev.safely.ui.main.adapter.item.PasswordItem
import javax.inject.Inject

class MainSharedViewModel @Inject constructor() : BaseSharedViewModel() {

    var passwordItemToEdit = MutableLiveData<PasswordItem?>()

}