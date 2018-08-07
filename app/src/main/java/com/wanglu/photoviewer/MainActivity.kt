package com.wanglu.photoviewer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.wanglu.photoviewerlibrary.PhotoViewer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val picData = arrayListOf(
                "http://fairyever.qiniudn.com/15149572836867.jpg",
                "http://fairyever.qiniudn.com/15149575161065.jpg",
                "http://fairyever.qiniudn.com/15149656480663.jpg",
                "http://wallpaper-pub.d2collection.com/class/cover/%E5%8A%A8%E6%BC%AB.png"
        )

        val adapter = RVAdapter(this)
        adapter.setData(picData)
        rv.layoutManager = GridLayoutManager(this, 3)
        rv.adapter = adapter
        adapter.setOnClickListener { position, location ->
            PhotoViewer
                    .setData(picData)
                    .setCurrentPage(position)
                    .setPicSize(110, 110)
                    .setPicSpace(10, 10)
                    .setCountOfRow(3)
                    .setClickViewLocation(location)
                    .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
                        override fun show(iv: ImageView, url: String) {
                            Glide.with(iv.context).load(url).into(iv)
                        }
                    })
                    .start(this)
        }

    }
}
