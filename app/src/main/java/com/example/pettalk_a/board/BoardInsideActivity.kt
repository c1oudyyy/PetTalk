package com.example.pettalk_a.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.pettalk_a.R
import com.example.pettalk_a.board.CommentLVAdapter
import com.example.pettalk_a.board.CommentModel
import com.example.pettalk_a.databinding.ActivityBoardInsideBinding
import com.example.pettalk_a.utils.FBAuth
import com.example.pettalk_a.utils.FBRef
import com.bumptech.glide.Glide
import com.example.pettalk_a.databinding.CommentListItemBinding
import com.example.pettalk_a.utils.FBRef.Companion.commentRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.NonCancellable.children
import java.lang.Exception

class BoardInsideActivity : AppCompatActivity() {

    private val TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var binding : ActivityBoardInsideBinding

    private lateinit var commentBinding : CommentListItemBinding

    private lateinit var key:String

    private val commentDataList = mutableListOf<CommentModel>()

    private lateinit var commentAdapter : CommentLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        binding.boardSettingIcon.setOnClickListener {
            showDialog()
        }

        // 두번째 방법
        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)


        binding.commentBtn.setOnClickListener {
            insertComment(key)
        }


            /*   commentBinding.deleteBtn.setOnClickListener {

            Toast.makeText(this, "삭제완료", Toast.LENGTH_LONG).show()
        }*/


        commentAdapter = CommentLVAdapter(commentDataList)
        binding.commentLV.adapter = commentAdapter









        getCommentData(key)




    }
    public fun deleteCommand(key : String){

       /* val postListener = object : ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {
                TODO("Not yet implemented")

                for(dataModel in datasnapshot.children){
                    val comment = datasnapshot.getValue(CommentModel::class.java)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }*/
       // val myUid = FBAuth.getUid()
        //val writerUid = dataModel.uid

        //commentDataList.clear()
        //val item = dataModel.getValue(CommentModel::class.java)
       // commentDataList.add(item!!)
        commentRef.child(key).removeValue()
        Log.d(TAG, "삭제안됌")
        getCommentData(key)

    }

    private fun getCommentData(key : String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                commentDataList.clear()

                for (dataModel in dataSnapshot.children) {

                    val commentContents = dataSnapshot.getValue(CommentModel::class.java)



                   /* if(myUid.equals(writerUid)){
                        commentBinding.deleteBtn.isVisible = true
                    } else {
                        Log.d(TAG, "내가 쓴 글 아님")
                    }*/


                    val item = dataModel.getValue(CommentModel::class.java)
                    commentDataList.add(item!!)


                }

                commentAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.commentRef.child(key).addValueEventListener(postListener)


    }

    fun insertComment(key : String){
        // comment
        //   - BoardKey
        //        - CommentKey
        //            - CommentData
        //            - CommentData
        //            - CommentData
        FBRef.commentRef
            .child(key)
            .push()
            .setValue(
                CommentModel(
                    binding.commentArea.text.toString(),
                    FBAuth.getTime(),FBAuth.getUid()
                )
            )

        Toast.makeText(this, "댓글 입력 완료", Toast.LENGTH_SHORT).show()
        binding.commentArea.setText("")

    }

    private fun showDialog(){

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {
            Toast.makeText(this, "수정 버튼을 눌렀습니다", Toast.LENGTH_LONG).show()

            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key",key)
            startActivity(intent)
        }

        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {

            FBRef.boardRef.child(key).removeValue()
            Toast.makeText(this, "삭제완료", Toast.LENGTH_LONG).show()
            finish()

        }



    }

    private fun getImageData(key : String){

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if(task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

            } else {

                binding.getImageArea.isVisible = false
            }
        })


    }


    private fun getBoardData(key : String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
dataSnapshot.child("comment")
                try {

                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)
                    Log.d(TAG, dataModel!!.title)

                    binding.titleArea.text = dataModel!!.title
                    binding.textArea.text = dataModel!!.content
                    binding.timeArea.text = dataModel!!.time

                    val myUid = FBAuth.getUid()
                    val writerUid = dataModel.uid

                    if(myUid.equals(writerUid)){
                        Log.d(TAG, "내가 쓴 글")
                        binding.boardSettingIcon.isVisible = true
                    } else {
                        Log.d(TAG, "내가 쓴 글 아님")
                    }

                } catch (e : Exception){

                    Log.d(TAG, "삭제완료")

                }



            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)



    }

}