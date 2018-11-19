package com.kubiakdev.safely.ui.password

import androidx.lifecycle.MutableLiveData
import com.kubiakdev.safely.base.BaseViewModel
import com.kubiakdev.safely.data.DataManager
import com.kubiakdev.safely.data.db.entity.PasswordEntity
import com.kubiakdev.safely.util.provider.CoroutineContextProvider
import kotlinx.coroutines.NonCancellable
import javax.inject.Inject

class PasswordViewModel @Inject constructor(
        override val coroutineContextProvider: CoroutineContextProvider,
        private val dataManager: DataManager
) : BaseViewModel(coroutineContextProvider) {

    var isInDeleteMode = MutableLiveData<Boolean>()

    init {
        isInDeleteMode.value = false
    }

    fun addPassword(passwordEntity: PasswordEntity, response: () -> Unit) = launchCatching(
            executionContext = coroutineContextProvider.io + NonCancellable,
            action = {
                val tempList = dataManager.allPasswordEntities.toMutableList()
                tempList.add(0, passwordEntity)
                dataManager.allPasswordEntities = tempList
            },
            onSuccess = { response() }
    )
}