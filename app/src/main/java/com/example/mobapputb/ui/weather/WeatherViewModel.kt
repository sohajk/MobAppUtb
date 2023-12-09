package com.example.mobapputb.ui.weather

import android.Manifest
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class WeatherViewModel(application: Application) : ViewModel() {

    private val context: Context = application.applicationContext
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
    private val _currentLocation = MutableLiveData<Location>()
    private val _latitude = MutableLiveData<Float>().apply { value = 0f }
    private val _longitude = MutableLiveData<Float>().apply { value = 0f }

    private val _gps = MutableLiveData<String>().apply { value = "---" }

    val latitude: LiveData<Float> get() = _latitude
    val longitude: LiveData<Float> get() = _longitude

    val gps: LiveData<String> get() = _gps

    fun updateTextViewValue() {
        _gps.value = _latitude.value.toString() + ", " + _longitude.value.toString()
    }

    fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            fun hasLocationPermission(): Boolean {
                return ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            }
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    _latitude.value = location.latitude.toFloat()
                    _longitude.value = location.longitude.toFloat()
                    _currentLocation.value = it
                }
            }
            .addOnFailureListener {e ->
                showFailureDialog(e.message ?: "Location retrieval failed")
            }
    }

    private fun showFailureDialog(message: String) {
        AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                // You can add additional logic here if needed
                dialog.dismiss()
            })
            .show()
    }
}