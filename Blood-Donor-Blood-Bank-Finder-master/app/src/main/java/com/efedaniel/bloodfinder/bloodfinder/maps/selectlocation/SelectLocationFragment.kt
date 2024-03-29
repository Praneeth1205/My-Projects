package com.efedaniel.bloodfinder.bloodfinder.maps.selectlocation

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.efedaniel.bloodfinder.App
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.databinding.FragmentSelectLocationBinding
import com.efedaniel.bloodfinder.utils.Misc
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class SelectLocationFragment : BaseFragment(), OnMapReadyCallback {

    companion object {
        const val DEFAULT_ZOOM = 15
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSelectLocationBinding
    private lateinit var viewModel: SelectLocationViewModel
    private lateinit var map: GoogleMap
    private var mapsLocationButton: View? = null

    private var locationPermissionGranted = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectLocationBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelectLocationViewModel::class.java)
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)

        //Initialise Places
//        if (!Places.isInitialized()) {
//            //TODO Insert your Google API key to run
//         //  Places.initialize(mainActivity.applicationContext, Secret.BILLING_API_KEY)
//           // Places.initialize(context!!, getString(R.string.api_key), Locale.US);
//            Places.createClient(mainActivity)
//        }

        binding.selectLocationButton.setOnClickListener {
            Timber.d("Latitude: %s", map.cameraPosition.target.latitude.toString())
            Timber.d("Longitude: %s", map.cameraPosition.target.longitude.toString())
            showDialogWithAction(
                title = getString(R.string.save_selected_location),
                body = getString(R.string.save_selected_location_message),
                positiveRes = R.string.proceed,
                positiveAction = {
                    viewModel.saveUserLocation(
                        map.cameraPosition.target.latitude.toString(),
                        map.cameraPosition.target.longitude.toString())
                },
                negativeRes = R.string.cancel
            )
        }
        viewModel.locationSavedAction.observe(this, Observer {
            if (it) {
                findNavController().navigate(SelectLocationFragmentDirections.actionSelectLocationFragmentToDashboardFragment())
                viewModel.locationSavedActionCompleted()
            }
        })
    }

    private fun initiateGettingLocation() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
        val client = LocationServices.getSettingsClient(mainActivity)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            getLocationPermission()
        }

        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    it.startResolutionForResult(mainActivity, Misc.LOCATION_DEVICE_ACCESS_REQUEST)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // ignore the error
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.select_location_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_my_location -> {
                getLocationPermission()
                initiateGettingLocation()
                mapsLocationButton?.callOnClick()
                true
            }
            R.id.action_search -> {
                val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
                val intent = Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields).setCountry("NG")
                    .build(mainActivity)
                startActivityForResult(intent, Misc.LOCATION_AUTOCOMPLETE_CODE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Misc.LOCATION_AUTOCOMPLETE_CODE) {
            when (resultCode) {
                RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data!!)
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                place.latLng!!.latitude,
                                place.latLng!!.longitude
                            ), DEFAULT_ZOOM.toFloat()
                        )
                    )
                }
            }
        }
    }

    private fun getLocationPermission() {
        locationPermissionGranted = false
        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
            // Setup current location shii
            setupCurrentLocation()
        } else {
            requestPermissions(arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ), Misc.LOCATION_PERMISSION_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            Misc.LOCATION_PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                    setupCurrentLocation()
                }
            }
        }
    }

    private fun setupCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        map.isMyLocationEnabled = true
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapsLocationButton = (mapFragment.view!!.findViewById<View>(Integer.parseInt("1")).parent as View).findViewById(Integer.parseInt("2"))
        mapsLocationButton?.visibility = View.GONE
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Show Dialog to alert users to select their location
        showDialogWithAction(body = getString(R.string.move_map_around_message), positiveRes = R.string.close, cancelOnTouchOutside = true)

        // Add a marker in Nigeria and move the camera
        val nigeria = LatLng(9.08, 8.67)
        map.moveCamera(CameraUpdateFactory.newLatLng(nigeria))

        // Enable Selecting Location Button
        binding.selectLocationButton.isEnabled = true

        //Get Location Permission
        getLocationPermission()

        // Send a current Location Request
        initiateGettingLocation()
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.select_location), false)
        invalidateToolbarElevation(100)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
