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
                "https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-704146.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15248077829234.jpg"
        )

        val adapter = RvAdapter(R.layout.item_img, picData)
//        val adapter = GvAdapter(this)
        gv.adapter = adapter
//        adapter.setData(picData)
//        gv.setOnItemClickListener { _, view, position, _ ->
//            PhotoViewer
//                    .setData(picData)
//                    .setCurrentPage(position)
//                    .setImgContainer(gv)
//                    .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
//                        override fun show(iv: ImageView, url: String) {
//                            Glide.with(iv.context).load(url).into(iv)
//                        }
//                    })
//                    .start(this)
//        }

        gv.layoutManager = GridLayoutManager(this, 3)
        adapter.setOnItemClickListener { adapter, view, position ->
            PhotoViewer
                    .setData(picData)
                    .setImgContainer(gv)
                    .setCurrentPage(position)
                    .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
                        override fun show(iv: ImageView, url: String) {
                            Glide.with(iv.context).load(url).into(iv)
                        }
                    })
                    .start( this)
        }
//        Timer().schedule(timerTask {
//            runOnUiThread {
//                gv.layoutManager.scrollToPosition(8)
//                Timer().schedule(timerTask {
//                    runOnUiThread {
//                        Log.d("112233", "${gv.layoutManager.findViewByPosition(8) == null}")
//                    }
//                }, 300)
//                Log.d("112233", "${gv.layoutManager.findViewByPosition(8) == null}")
//            }
//        }, 4000)

//        Glide.with(this).load(picData[3]).into(iv)
//
//        iv.setOnClickListener {
//            PhotoViewer
//                    .setClickSingleImg(picData[3], iv)
//                    .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
//                        override fun show(iv: ImageView, url: String) {
//                            Glide.with(iv.context).load(url).into(iv)
//                        }
//                    })
//                    .start(this)
//        }

    }
}
