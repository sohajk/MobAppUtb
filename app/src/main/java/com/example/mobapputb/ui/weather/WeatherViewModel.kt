package com.example.mobapputb.ui.weather

import android.Manifest
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobapputb.models.WeatherDataAdapterModel
import com.example.mobapputb.repositories.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherViewModel(private val application: Application, private val repository: WeatherRepository) : ViewModel() {

    private val context: Context = application.applicationContext
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
    private val _currentLocation = MutableLiveData<Location>()

    private val _latitude = MutableLiveData<Float>().apply { value = 0f }
    private val _longitude = MutableLiveData<Float>().apply { value = 0f }
    private val _statusMsg = MutableLiveData<String>().apply { value = "" }
    private val _weatherDataAdapter = MutableLiveData<List<WeatherDataAdapterModel>>().apply { value = emptyList<WeatherDataAdapterModel>() }

    val latitude: LiveData<Float> get() = _latitude
    val longitude: LiveData<Float> get() = _longitude
    val statusMsg: LiveData<String> get() = _statusMsg
    val weatherAdapterData: LiveData<List<WeatherDataAdapterModel>> get() = _weatherDataAdapter

    fun getWeatherData() {
        if (_latitude.value == null || _longitude.value == null) {
            _statusMsg.value = "Please enter coordinates"
            return
        }

        _statusMsg.value = "Loading"
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val result = repository.getWeatherPack(_latitude.value!!, _longitude.value!!, true, true, true)

                if (result != null) {
                    val dataList = mutableListOf<WeatherDataAdapterModel>()
                    val dataCount = result.timestamp.count()
                    val formatterTs = DateTimeFormatter.ofPattern("HH:mm:ss")

                    for (i in 0 until dataCount step 1) {
                        val timestamp = LocalDateTime.parse(result.timestamp[i])
                        val temperature = result.temperature.value[i].toString() + " " + result.temperature.unit
                        val precipProb = result.precipProb.value[i].toString() + " " + result.precipProb.unit
                        val visibility = result.visibility.value[i].toString() + " " + result.visibility.unit

                        dataList.add(WeatherDataAdapterModel(timestamp.format(formatterTs), temperature, precipProb, visibility))
                    }

                    _weatherDataAdapter.postValue(dataList)
                } else {
                    _weatherDataAdapter.postValue(emptyList<WeatherDataAdapterModel>())
                }

                val now = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                _statusMsg.postValue("Updated: " + now.format(formatter))
            }
        } catch (e: Exception) {
            Log.e("WeatherError",e.message!!)
            _statusMsg.postValue("Error when requesting weather data")
        }
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