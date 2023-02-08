package com.example.pettalk_a.first

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import com.example.pettalk_a.IntroActivity
import com.example.pettalk_a.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.regex.Pattern
import com.example.pettalk_a.databinding.ActivityJoinBinding as ActivityJoinBinding
import kotlinx.coroutines.delay as delay

class JoinActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database
    private val myRef = database.getReference("User")
    private val TAG = JoinActivity::class.java.simpleName


    fun createUser(username: String, userid: String, password1: String, email: String) {
        auth.createUserWithEmailAndPassword(userid, password1)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "성공", Toast.LENGTH_LONG).show()
                    myRef.push().setValue(UserModel(username, userid, password1, email))
                    val intent = Intent(this, IntroActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "실패", Toast.LENGTH_LONG).show()
                }
            }


    }
    val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    fun texttoast(content:String){
        Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    }

    fun checkEmail(email:String):Boolean{
        var email = email.trim() //공백제거
        val p = Pattern.matches(emailValidation, email) // 서로 패턴이 맞닝?
        return p
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        //binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        val join_btn = findViewById<Button>(R.id.userJoin)

        auth = Firebase.auth

        join_btn.setOnClickListener {

            var isGoToJoin = true
            var checkEmail = true

            //val username = binding.joinName.text.toString()
            //val userid = binding.joinId.text.toString()
            //val password1 = binding.joinPwd.text.toString()
            //val email = binding.joinEmail.text.toString()

            val username = findViewById<EditText>(R.id.joinName).text.toString()
            val userid = findViewById<EditText>(R.id.joinId).text.toString()
            val password1 = findViewById<EditText>(R.id.joinPwd).text.toString()
            val email = findViewById<EditText>(R.id.joinEmail).text.toString()


            // 값이 비어있는지 확인
            if (username.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if (userid.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if (password1.isEmpty()) {
                Toast.makeText(this, "Password1을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if (email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if (!checkEmail(email)){
                Toast.makeText(this, "이메일 형식이 틀렸습니다.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            // 비밀번호가 6자 이상인지
            if (password1.length < 6) {
                Toast.makeText(this, "비밀번호를 6자리 이상으로 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            var checkEmaildata = UserModel()

            runBlocking {
                val userListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // Get Post object and use the values to update the UI
                        for (dataModel in dataSnapshot.children) {
                            Log.d("UserActivity", dataModel.toString())
                            var item = dataModel.getValue(UserModel::class.java)
                            if (item != null) {
                                if (item.useremail.equals(email)) {
                                    checkEmaildata = item.copy()
                                    checkEmail = false
                                }
                            }
                        }
                        if(checkEmail){
                            if(isGoToJoin){
                                createUser(username, userid, password1, email)
                            }
                        }
                        else{
                            //이메일 중복이 있을 경우 중복을 알리는 메시지 출력 함수 실행
                            texttoast("중복가입된 이메일 정보가 있습니다")
                        }
                        Log.d("UserActivity", checkEmaildata.toString())
                        // ...
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Getting Post failed, log a message
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                    }
                }
                myRef.addValueEventListener(userListener)
            }
        }
    }


}