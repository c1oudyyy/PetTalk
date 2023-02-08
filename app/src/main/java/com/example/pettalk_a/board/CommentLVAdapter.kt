package com.example.pettalk_a.board

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.pettalk_a.R
import com.example.pettalk_a.utils.FBAuth
import com.example.pettalk_a.utils.FBRef
import com.example.pettalk_a.utils.FBRef.Companion.commentRef

class CommentLVAdapter(val commentList : MutableList<CommentModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return commentList.size
    }

    override fun getItem(position: Int): Any {
        return commentList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView


              view = LayoutInflater.from(parent?.context).inflate(R.layout.comment_list_item, parent, false)


        val itemLinearLayoutView = view?.findViewById<LinearLayout>(R.id.itemView)
        val title = view?.findViewById<TextView>(R.id.titleArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)




        //val button = view.findViewById<Button>(R.id.deleteBtn)
        //button.isVisible = true
        //button.setOnClickListener {

        //}

        title!!.text = commentList[position].commentTitle
        time!!.text = commentList[position].commentCreatedTime

        return view!!
    }

}