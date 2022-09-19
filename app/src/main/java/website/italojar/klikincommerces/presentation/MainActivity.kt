package website.italojar.klikincommerces.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.databinding.ActivityMainBinding
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import website.italojar.klikincommerces.presentation.interfaces.IDialogListener
import website.italojar.klikincommerces.presentation.sharedViewModel.SharedCommerceViewModel
import website.italojar.klikincommerces.presentation.interfaces.IFragmentsListener


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IFragmentsListener, IDialogListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: MaterialToolbar
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val sharedViewModel: SharedCommerceViewModel by viewModels()
    private var cont: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeStatusBarColor()
        with(binding) {
            toolbar = toolbarContainer.toolbar
            setToolbarTitle(getString(R.string.list_fragment_commerces))
            val navHostFragment =
                supportFragmentManager.findFragmentById(fragmentContainerView.id) as NavHostFragment
            val navController = navHostFragment.navController
            setToolbarNavigation(navController, toolbar)
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStart() {
        super.onStart()
        getCurrentLocation()
    }

    private fun changeStatusBarColor() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAGS_CHANGED)
    }

    private fun setToolbarNavigation(navController: NavController, toolbar: MaterialToolbar) {
        setSupportActionBar(toolbar)
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.commercesListFragment),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        toolbar.setupWithNavController(navController, appBarConfiguration)
        navInEachDestination(navController, toolbar)
    }

    private fun navInEachDestination(navController: NavController, toolbar: MaterialToolbar) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.commerceDetailFragment -> {
                    toolbar.setNavigationIcon(R.drawable.arrow_left)
                }
            }
        }
    }

    override fun setToolbarTitle(name: String) {
        toolbar.title = name
    }

    // Maps
    private fun isPermissionsGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (isPermissionsGranted()) {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                //create bundle instance
                val location: Location? = task.result
                if (location != null) {
                    sharedViewModel
                        .getCurrentLocation(LatLng(location.latitude, location.longitude))
                }
            }
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            cont++
            if (cont < 3) {
                Toast.makeText(this, getString(R.string.go_to_settings), Toast.LENGTH_SHORT).show()
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode == 0) {
            true -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            else -> {
                Toast.makeText(
                    this,
                    getString(R.string.go_to_settings_location_permissions),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!isPermissionsGranted()) {
            requestLocationPermission()
        }
    }

    override fun onDialogPositiveClick(distance: Int) {
        sharedViewModel.getDistance(distance)
    }
}