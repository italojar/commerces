package website.italojar.klikincommerces.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.databinding.ActivityMainBinding
import android.view.WindowManager
import website.italojar.klikincommerces.presentation.interfaces.IFragmentsListener


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IFragmentsListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeStatusBarColor()
        with(binding) {
            toolbar = toolbarContainer.toolbar
            setToolbarTitle(getString(R.string.fragment_commerces_list))
            val navHostFragment =
                supportFragmentManager.findFragmentById(fragmentContainerView.id) as NavHostFragment
            val navController  = navHostFragment.navController
            setToolbarNavigation(navController, toolbar)
        }
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
}