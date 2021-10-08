package com.example.coinstats.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class ObservableViewModel : ViewModel() {

    private val dataChangedEvent = SingleLiveEvent<Unit>()

    fun notifyDataChanged() {
        dataChangedEvent.post()
    }

    open fun observe(lifecycleOwner: LifecycleOwner, onDataChanged: () -> Unit) {
        dataChangedEvent.observe(lifecycleOwner) {
            onDataChanged()
        }
    }

    companion object
}

fun Fragment.observe(
    observableViewModel: ObservableViewModel,
    onDataChanged: () -> Unit
) = observableViewModel.observe(viewLifecycleOwner, onDataChanged)

fun AppCompatActivity.observe(
    observableViewModel: ObservableViewModel,
    onDataChanged: () -> Unit
) = observableViewModel.observe(this, onDataChanged)

class ViewModelState<T>(initialValue: T) : ReadWriteProperty<ObservableViewModel, T> {

    private var value: T = initialValue

    override fun getValue(thisRef: ObservableViewModel, property: KProperty<*>) = value

    override fun setValue(thisRef: ObservableViewModel, property: KProperty<*>, value: T) {
        this.value = value
        thisRef.notifyDataChanged()
    }
}

fun <T> ObservableViewModel.Companion.state(initialValue: T) = ViewModelState(initialValue)