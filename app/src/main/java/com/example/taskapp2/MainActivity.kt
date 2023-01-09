package com.example.taskapp2


import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.taskapp2.databinding.ActivityMainBinding
import com.example.taskapp2.utils.Preference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().token.addOnCompleteListener{
            Log.e("ololo", "onCreate: "+it.result )
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_profile,
                R.id.newTaskFragment,
                R.id.authenticationFragment
            )
        )
        if (!Preference(applicationContext).isBoardingShowed()){
            navController.navigate(R.id.onBoardFragment)
        }else if (auth.currentUser == null){
            navController.navigate(R.id.authenticationFragment)}

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.newTaskFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.onBoardFragment, R.id.authenticationFragment -> {
                    navView.visibility = View.GONE
                    supportActionBar?.hide()
                }
                else -> {navView.visibility = View.VISIBLE
                    supportActionBar?.show()
                     }
            }
        }
    }
}
