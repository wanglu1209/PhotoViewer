package com.wanglu.photoviewer

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.wanglu.photoviewerlibrary.OnLongClickListener
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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        gv.layoutManager = GridLayoutManager(this, 3)
        adapter.setOnItemClickListener { _, _, position ->
            PhotoViewer
                    .setData(picData)
                    .setImgContainer(gv)
                    .setOnPhotoViewerCreatedListener{

                        Toast.makeText(this, "created", Toast.LENGTH_LONG).show()
                    }
                    .setOnLongClickListener(object : OnLongClickListener{
                        override fun onLongClick(view: View) {
                            Toast.makeText(view.context, "haha", Toast.LENGTH_LONG).show()
                        }
                    })
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
