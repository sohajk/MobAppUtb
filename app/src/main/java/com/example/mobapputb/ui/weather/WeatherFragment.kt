package com.example.mobapputb.ui.weather

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobapputb.MainActivity
import com.example.mobapputb.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainActivity = requireActivity() as MainActivity
        val repository = mainActivity.MyApp.weatherRepository

        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, WeatherViewModelFactory(requireActivity().application, repository))[WeatherViewModel::class.java]

        // Bind the ViewModel to the layout
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        checkLocationPermission()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        binding = null
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permission
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    // Handle permission result in onRequestPermissionsResult callback
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, fetch location
                viewModel.getLocation()
            } else {
                // Location permission denied, handle accordingly
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}