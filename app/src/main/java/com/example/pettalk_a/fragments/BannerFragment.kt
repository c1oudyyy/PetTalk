package com.example.pettalk_a.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pettalk_a.R
import com.example.pettalk_a.board.*
import com.example.pettalk_a.databinding.FragmentBannerBinding
import com.example.pettalk_a.databinding.FragmentTalkBinding


class BannerFragment : Fragment() {


    private lateinit var binding: FragmentBannerBinding

    private val items = ArrayList<BannerModel>()
    private val keyList = ArrayList<String>()

    private val TAG = BannerFragment::class.java.simpleName

    private lateinit var bannerAdapter : BannerLVAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //items.add(BannerModel("https://indigo3d.co.kr/wp-content/uploads/2021/04/01_cat_A-0000001946ms-2-1024x576.png","https://www.naver.com/"))
        //items.add(BannerModel("https://indigo3d.co.kr/wp-content/uploads/2021/04/191127_%EB%A9%B8%EC%A2%85%EB%8F%99%EB%AC%BC%EC%9B%90_Digital-0000054597ms-1024x576.png","https://www.daum.net/"))
       // items.add(BannerModel("https://indigo3d.co.kr/wp-content/uploads/2021/04/200225_%ED%99%8D%EC%9D%B4%EC%9E%A5%EA%B5%B0-%EB%AF%B8%EC%B7%A8%ED%95%99%ED%8E%B8-0000028953ms-1024x576.png","https://www.google.com/"))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_banner, container, false)

        binding.ad1.setOnClickListener{
            //임시 url입니다.
            binding.webview.loadUrl("https://www.naver.com/") //
        }
        binding.ad2.setOnClickListener{
            //임시 url입니다.
            binding.webview.loadUrl("https://www.daum.net/")
        }
        binding.ad3.setOnClickListener{
            //
            // 임시 url입니다.
            binding.webview.loadUrl("https://www.kakao.com/")
        }

        /*binding.bannerListView.setOnItemClickListener { parent, view, position, id ->

            var position = binding.bannerListView.checkedItemPosition

            var checked : BannerModel = bannerAdapter.getItem(position) as BannerModel

           binding.webview.loadUrl(checked.url.toString())


        }*/

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bannerFragment_to_homeFragment)
        }

        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bannerFragment_to_talkFragment)
        }

        return binding.root
    }



}