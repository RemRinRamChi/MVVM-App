package com.yawjenn.mvvmpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.yawjenn.mvvmpractice.home.HomeFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(tToolbar)

        supportFragmentManager.commit {
            add(R.id.container, HomeFragment.newInstance())
        }
    }
}
