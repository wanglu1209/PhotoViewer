package com.wanglu.photoviewerlibrary

class PhotoViewerPagerAdapter(private var mData: MutableList<PhotoViewerFragment>, fragmentManager: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return mData[position]
    }

    override fun getCount(): Int {
        return mData.size
    }

}
