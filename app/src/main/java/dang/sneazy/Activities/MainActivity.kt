package dang.sneazy.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dang.sneazy.Fragments.AccountFragment
import dang.sneazy.Fragments.HomeFragment
import dang.sneazy.Fragments.NotificationFragment
import dang.sneazy.Fragments.SearchFragment
import dang.sneazy.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.navigation_home -> {
                println("Home pressed")
                replaceFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                println("Search pressed")
                replaceFragment(SearchFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notification -> {
                println("Notification pressed")
                replaceFragment(NotificationFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                println("Account pressed")
                replaceFragment(AccountFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //recyclerView_main.setBackgroundColor(Color.GRAY)
        replaceFragment(HomeFragment())

        bottomNavigationView_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }





}
