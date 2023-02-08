package com.example.pettalk_a.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.pettalk_a.R
import com.example.pettalk_a.databinding.ActivityBoardEditBinding
import com.example.pettalk_a.utils.FBAuth

import com.example.pettalk_a.utils.FBRef
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.lang.Exception

class BoardEditActivity : AppCompatActivity() {

    private lateinit var key:String

    private lateinit var binding : ActivityBoardEditBinding

    private val TAG = BoardEditActivity::class.java.simpleName

    private lateinit var writerUid : String

    private var selected = " "

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_edit)

        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)

        var category = listOf(" ","판매게시판", "질문게시판", "자유게시판")

        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,category)

        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selected = category.get(position)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        binding.editBtn.setOnClickListener {
            editBoardData(key)
        }



    }

    private fun editBoardData(key : String){

        FBRef.boardRef
            .child(key)
            .setValue(
                BoardModel(binding.titleArea.text.toString(),
                    binding.contentArea.text.toString(),
                    writerUid,
                    FBAuth.getTime())
            )

        Toast.makeText(this, "수정완료", Toast.LENGTH_LONG).show()

        finish()

    }

    private fun getImageData(key : String){

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.imageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if(task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

            } else {

            }
        })


    }

    private fun getBoardData(key : String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataModel = dataSnapshot.getValue(BoardModel::class.java)
//                Log.d(TAG, dataModel.toString())
//                Log.d(TAG, dataModel!!.title)
//                Log.d(TAG, dataModel!!.time)

                binding.titleArea.setText(dataModel?.title)
                binding.contentArea.setText(dataModel?.content)
                writerUid = dataModel!!.uid


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        FBRef.boardRef.child(key).addValueEventListener(postListener)


    }






}