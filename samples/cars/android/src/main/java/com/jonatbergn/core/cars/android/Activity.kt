package com.jonatbergn.core.cars.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.jonatbergn.cars.car.Car
import com.jonatbergn.core.cars.android.App.Companion.store
import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDateTime

class Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Ui(
                    onEditReservationDateTimeRequested = {
                        val datePicker = datePickerForReservation(it)
                        val timePicker = timePickerForReservation(it)
                        datePicker.addOnPositiveButtonClickListener { timePicker.show(supportFragmentManager, "time") }
                        datePicker.show(supportFragmentManager, "date")
                    },
                )
            }
        }
    }

    private fun datePickerForReservation(
        car: Entity.Pointer<Car>,
    ) = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Select date")
        .apply { store.state.value.reservation(car).start?.let { setSelection(it.toEpochMilliseconds()) } }
        .build()
        .apply {
            addOnPositiveButtonClickListener {
                val instant = Instant.fromEpochMilliseconds(it)
                lifecycleScope.launch {
                    store.editReservation(car) { copy(startDate = instant.toLocalDateTime(timeZone).date) }
                }
                dismiss()
            }
            addOnNegativeButtonClickListener { dismiss() }
        }

    private fun timePickerForReservation(
        car: Entity.Pointer<Car>,
    ) = MaterialTimePicker.Builder()
        .setTitleText("Select date")
        .apply { store.state.value.reservation(car).startTime?.let { setHour(it.hour); setMinute(it.minute) } }
        .build()
        .apply {
            addOnPositiveButtonClickListener {
                lifecycleScope.launch {
                    store.editReservation(car) { copy(startTime = LocalTime(hour = hour, minute = minute)) }
                }
                dismiss()
            }
            addOnNegativeButtonClickListener { dismiss() }
        }

}
