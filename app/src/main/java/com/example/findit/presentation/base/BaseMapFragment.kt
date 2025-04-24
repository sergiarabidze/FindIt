package com.example.findit.presentation.base

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.example.findit.presentation.extension.showSnackBar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


abstract class BaseMapFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : BaseFragment<VB>(inflate) {
    protected lateinit var fusedLocationClient: FusedLocationProviderClient

    private fun requestPermissions() {
        val locationPermissionRequest =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                if (permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)) {
                    setupLocation()
                } else {
                    binding.root.showSnackBar("Location permission denied")
                }
            }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }


    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            locationManager.isLocationEnabled
        } else {
            val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            gpsEnabled || networkEnabled
        }
    }

    private fun promptForLocationSettings() {
        if (!isLocationEnabled(requireContext())) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }


    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun setupLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled(requireContext())) {
                onLocationSetupSuccess()
            } else {
                promptForLocationSettings()
            }
        } else {
            requestPermissions()
        }
    }

   open fun onLocationSetupSuccess() {
       fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
   }

    override fun onResume() {
        super.onResume()
        if (!::fusedLocationClient.isInitialized) {
            setupLocation()
        }
    }
}

