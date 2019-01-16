package com.kubiakdev.safely.ui.password

import androidx.lifecycle.MutableLiveData
import com.kubiakdev.safely.base.BaseSharedViewModel
import com.kubiakdev.safely.data.model.PasswordModel
import javax.inject.Inject

class PasswordSharedViewModel @Inject constructor() : BaseSharedViewModel() {

    var isInDeleteMode = MutableLiveData<Boolean>()
    var newPasswordModel = MutableLiveData<PasswordModel>()
    var editedPasswordModel = MutableLiveData<PasswordModel>()

    init {
        isInDeleteMode.value = false
    }

}