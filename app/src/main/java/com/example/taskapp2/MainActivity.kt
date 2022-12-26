package com.example.taskapp2

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.taskapp2.databinding.ActivityMainBinding
import com.example.taskapp2.ui.home.AdapterTask
import com.example.taskapp2.ui.home.TaskModel
import com.example.taskapp2.utils.Preference
import java.util.Collections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskAdapter: AdapterTask
    private var tasklist= arrayListOf<TaskModel>()

    @Override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }
    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       val id = item.itemId
        if (id==R.id.action_sort){
          sortAlertdialog()
        }else if (id==R.id.action_delete){
          deleteAlertDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortAlertdialog() {
        val options = arrayOf("По времени", "По алфавиту")
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Сортировать по:").setItems(options, DialogInterface.OnClickListener { dialogInterface, i ->
        if (i==0){
           //сортировка по времени
            Toast.makeText(applicationContext,"сортировка по времени",Toast.LENGTH_SHORT).show()
        }else if (i==1){
            ///сортировка по алфавиту
            sortAlphabet()
            App.database.dao().sortedByAlphabet()
        }
        }).create().show()
    }

    private fun sortAlphabet() {
                    Collections.sort(tasklist,Comparator <TaskModel>{ t, t2 ->
            t.title.compareTo(t2.title)
        })
        taskAdapter.notifyDataSetChanged()
    }

    private fun deleteAlertDialog(){
        val options = arrayOf("Удалить все")
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Удаление").setItems(options,DialogInterface.OnClickListener{dialogInterface, i ->
            if (i==0){
                ///delete all database
               // Toast.makeText(applicationContext,"Удалить все",Toast.LENGTH_SHORT).show()
                apply {
                    App.database.dao().deleteAll()
                }
            }
        }).create().show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskAdapter = AdapterTask()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                R.id.newTaskFragment
            )
        )
        if (Preference(applicationContext).isBoardingShowed())
            navController.navigate(R.id.navigation_home)
        else  navController.navigate(R.id.onBoardFragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->  // исечазает боттом навигация при переходе на новый фрагмент
            if (destination.id == R.id.newTaskFragment||destination.id == R.id.onBoardFragment) {
                navView.visibility = View.GONE
                supportActionBar?.hide()
            } else navView.visibility = View.VISIBLE
        }
    }
}