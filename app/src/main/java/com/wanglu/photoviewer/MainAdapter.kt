package com.wanglu.photoviewer

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wanglu.photoviewerlibrary.OnLongClickListener
import com.wanglu.photoviewerlibrary.PhotoViewer

class MainAdapter(private val activity: MainActivity) : BaseQuickAdapter<Int, BaseViewHolder>(R.layout.activity_main){
    override fun convert(helper: BaseViewHolder?, item: Int?) {
        val rv = helper!!.getView<RecyclerView>(R.id.gv)

        val picData = arrayListOf(
                "https://qiniucdn.fairyever.com/15149579640159.jpg",
                "https://qiniucdn.fairyever.com/15149577854174.png",
                "https://qiniucdn.fairyever.com/15248077829234.jpg"
        )

        val adapter = RvAdapter(R.layout.item_img, picData)
        rv.adapter = adapter
        rv.layoutManager = GridLayoutManager(activity, 3)
        adapter.setOnItemClickListener { _, _, position ->
            PhotoViewer
                    .setData(picData)
                    .setImgContainer(rv)
                    .setOnPhotoViewerCreatedListener{

                        Toast.makeText(mContext, "created", Toast.LENGTH_LONG).show()
                    }
                    .setOnLongClickListener(object : OnLongClickListener {
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
                    .start( activity)
        }
    }
}