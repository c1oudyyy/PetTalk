package com.example.pettalk_a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.pettalk_a.MainActivity
import com.example.pettalk_a.R
import com.example.pettalk_a.databinding.ActivityIntroBinding
import com.example.pettalk_a.first.JoinActivity
import com.example.pettalk_a.first.LoginActivity
import com.example.pettalk_a.first.findidpwActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class IntroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)

        binding.loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.joinBtn.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
        binding.findidpwbtn.setOnClickListener{
            val intent = Intent(this, findidpwActivity::class.java)
            startActivity(intent)
        }



    }
}