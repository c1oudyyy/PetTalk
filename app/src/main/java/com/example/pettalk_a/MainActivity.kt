package com.example.pettalk_a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.pettalk_a.banner.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

    internal lateinit var viewpager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)

        viewpager = findViewById(R.id.viewpager) as ViewPager

        val adapter = ViewPagerAdapter(this)
        viewpager.adapter = adapter
    }
}