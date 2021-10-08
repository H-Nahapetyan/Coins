package com.example.coinstats.utils

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import com.example.coinstats.BuildConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

open class BaseViewModel : ObservableViewModel(), KoinComponent {

    private var loading : MutableLiveData<Boolean> = MutableLiveData(false)

    private val errorEvent = SingleLiveEvent<Exception>()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(this::class.java.simpleName, "", throwable)
        notifyErrorOccurred(throwable as Exception)
    }

    fun runAsync(tryFunction: suspend () -> Unit) {
        viewModelScope.launch(errorHandler) {
            try {
                loading.postValue(true)
                tryFunction()
            } catch (ex: Exception) {
                if(BuildConfig.DEBUG) {
                    Log.e(BaseViewModel::class.java.toString(), ex.message.toString())
                }
                notifyErrorOccurred(ex)
            } finally {
                loading.postValue(false)
            }
        }
    }

    fun observe(lifecycleOwner: LifecycleOwner, onDataChanged: () -> Unit, onError: (exception: Exception) -> Unit) {
        observe(lifecycleOwner, onDataChanged)
        errorEvent.observe(lifecycleOwner, onError)
    }

    private fun notifyErrorOccurred(exception: Exception) {
        errorEvent.postValue(exception)
    }

}

fun Fragment.observe(
    baseViewModel: BaseViewModel,
    onDataChanged: () -> Unit,
    onError: (exception: Exception) -> Unit
) = baseViewModel.observe(viewLifecycleOwner, onDataChanged, onError)

fun AppCompatActivity.observe(
    baseViewModel: BaseViewModel,
    onDataChanged: () -> Unit,
    onError: (exception: Exception) -> Unit
) = baseViewModel.observe(this, onDataChanged, onError)