package com.example.pettalk_a.first

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.AlteredCharSequence
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pettalk_a.IntroActivity
import com.example.pettalk_a.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

class findidpwActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database
    private val myRef = database.getReference("User")
    private val TAG = findidpwActivity::class.java.simpleName


    fun sendToast(idorpwd : String){
        Toast.makeText(this, idorpwd, Toast.LENGTH_LONG).show()
    }

    /*@SuppressLint("QueryPermissionsNeeded")
    private fun sendEmail(title : String, content: String, email : String) {
        val title = title
        val content = content
        val emailAddress = email

        val intent = Intent(Intent.ACTION_SENDTO) // 메일 전송 설정
            .apply {
                type = "text/plain" // 데이터 타입 설정
                data = Uri.parse("mailto:") // 이메일 앱에서만 인텐트 처리되도록 설정

                putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress)) // 메일 수신 주소 목록
                putExtra(Intent.EXTRA_SUBJECT, title) // 메일 제목 설정
                putExtra(Intent.EXTRA_TEXT, content) // 메일 본문 설정
            }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "메일 전송하기"))
        } else {
            Toast.makeText(this, "메일을 전송할 수 없습니다", Toast.LENGTH_LONG).show()
        }
    }*/
    fun goIntro(){

        val intent = Intent(this, IntroActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findidpw)

        val findId_btn = findViewById<Button>(R.id.findidbtn)
        val findPw_btn = findViewById<Button>(R.id.findpwbtn)

        findId_btn.setOnClickListener{

            /*val email = Intent(Intent.ACTION_SEND)
            email.type = "plain/Text"
            email.putExtra(Intent.EXTRA_EMAIL, "ddi1235@naver.com")
            email.putExtra(
                Intent.EXTRA_SUBJECT,"ID찾기 기능입니다."
            )*/

            var checkEmaildata = UserModel()
            var checkId = false

            val idfindName = findViewById<EditText>(R.id.idfindName).text.toString()
            val idfindEmail = findViewById<EditText>(R.id.idfindemail).text.toString()

            var ad = AlertDialog.Builder(this)
            ad.setIcon(R.drawable.logo)
            ad.setTitle("ID 확인")
            var listener = object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    goIntro()
                }
            }
            ad.setPositiveButton("확인", listener)

            var ad2 = AlertDialog.Builder(this)
            ad2.setIcon(R.drawable.logo)
            ad2.setTitle("PASSWORD 확인")
            var listener2 = object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                }
            }
            ad2.setPositiveButton("확인", listener2)

            runBlocking {
                val userListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // Get Post object and use the values to update the UI
                        for (dataModel in dataSnapshot.children) {
                            Log.d("UserActivity", dataModel.toString())
                            var item = dataModel.getValue(UserModel::class.java)
                            if (item != null) {
                                if (item.useremail.equals(idfindEmail)) {
                                    checkEmaildata = item.copy()
                                    checkId = true
                                }
                            }
                        }
                        if(checkId){
                            if(checkEmaildata.userName.equals(idfindName)){

                                /*email.putExtra(
                                    Intent.EXTRA_TEXT, checkEmaildata.userid
                                )
                                email.type = "message/rfc822"
                                startActivity(email)*/

                                ad.setMessage(checkEmaildata.userid)
                                ad.show()
                                //sendEmail("ID 정보 메일입니다", checkEmaildata.userid, checkEmaildata.useremail)
                                //sendToast(checkEmaildata.userid)
                            }
                            else{
                                ad2.setMessage("일치하는 회원정보가 없습니다.")
                                ad2.show()
                            }
                        }
                        else{
                            ad2.setMessage("일치하는 회원정보가 없습니다.")
                            ad2.show()
                            //이메일 중복이 있을 경우 중복을 알리는 메시지 출력 함수 실행
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

        findPw_btn.setOnClickListener{

            var checkEmaildata = UserModel()
            var checkId = false

            val idfindName = findViewById<EditText>(R.id.idfindName).text.toString()
            val idfindEmail = findViewById<EditText>(R.id.idfindemail).text.toString()
            val pwfindId = findViewById<EditText>(R.id.pwfindeid).text.toString()

            var ad = AlertDialog.Builder(this)
            ad.setIcon(R.drawable.logo)
            ad.setTitle("PASSWORD 확인")

            var listener = object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    goIntro()
                }
            }
            ad.setPositiveButton("확인", listener)

            var ad2 = AlertDialog.Builder(this)
            ad2.setIcon(R.drawable.logo)
            ad2.setTitle("PASSWORD 확인")
            var listener2 = object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                }
            }
            ad2.setPositiveButton("확인", listener2)


            runBlocking {
                val userListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // Get Post object and use the values to update the UI
                        for (dataModel in dataSnapshot.children) {
                            Log.d("UserActivity", dataModel.toString())
                            var item = dataModel.getValue(UserModel::class.java)
                            if (item != null) {
                                if (item.useremail.equals(idfindEmail)) {
                                    checkEmaildata = item.copy()
                                    checkId = true
                                }
                            }
                        }
                        if(checkId){
                            if(checkEmaildata.userName.equals(idfindName)){
                                if(checkEmaildata.userid.equals(pwfindId)){
                                    ad.setMessage(checkEmaildata.userpassword)
                                    ad.show()
                                    //sendEmail("PASSWORD 정보 메일입니다", checkEmaildata.userpassword, checkEmaildata.useremail)
                                    //sendToast(checkEmaildata.userpassword)
                                }
                                else{
                                    ad2.setMessage("일치하는 회원정보가 없습니다.")
                                    ad2.show()
                                }
                            }
                            else{
                                ad2.setMessage("일치하는 회원정보가 없습니다.")
                                ad2.show()
                            }
                        }
                        else{
                            ad2.setMessage("일치하는 회원정보가 없습니다.")
                            ad2.show()
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
