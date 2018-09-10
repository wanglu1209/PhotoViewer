# PhotoViewer

<p >
	<a><img src="https://img.shields.io/github/release/wanglu1209/PhotoViewer.svg"/></a>
  	<a><img src="https://img.shields.io/github/last-commit/wanglu1209/PhotoViewer.svg"/></a>
	<a><img src="https://img.shields.io/github/issues/wanglu1209/PhotoViewer.svg"/></a>
	<a><img src="https://img.shields.io/github/issues-closed/wanglu1209/PhotoViewer.svg"/></a>
	<a><img src="https://img.shields.io/github/issues-pr/wanglu1209/PhotoViewer.svg"/></a>
	<a><img src="https://img.shields.io/github/issues-pr-closed/wanglu1209/PhotoViewer.svg"/></a>
	<a><img src="https://img.shields.io/github/forks/wanglu1209/PhotoViewer.svg"/></a>
	<a><img src="https://img.shields.io/github/stars/wanglu1209/PhotoViewer.svg"/></a>
</p>

<div>


该图片查看器是模仿微信朋友圈查看图片编写


<img src="https://github.com/wanglu1209/PhotoViewer/blob/master/gif/gif1.gif?raw=true" style="zoom:70%" /> &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="https://github.com/wanglu1209/PhotoViewer/blob/master/gif/gif2.gif?raw=true" style="zoom:70%" />

<img src="https://github.com/wanglu1209/PhotoViewer/blob/master/gif/gif3.gif?raw=true" style="zoom:70%" />



```Gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
```

```Gradle
dependencies {
	        implementation 'com.github.wanglu1209:PhotoViewer:lastRelease'
	}
	
```

## 使用



```Kotlin
PhotoViewer
          .setData(图片链接List<String>)
          .setCurrentPage(现在是哪页)
          .setImgContainer(img的容器 rv/gv/lv)
          .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
              override fun show(iv: ImageView, url: String) {
               
                // 设置自己加载图片的框架来加载图片
                  Glide.with(iv.context).load(url).into(iv)
              }
          })
          .start(this)
```


代码中，`photoview`文件夹为**chrisbanes**大神的[PhotoView](https://github.com/chrisbanes/PhotoView)

把代码加入到其中做了一些修改来达到效果



## Feature

增加弹出动画
增加加载图片时候的loading
点击退出时的动画（目前只有滑动退出时有动画效果）


## 更新日志

### 0.20
重构了代码，简化了调用链，更改了弹出方式（以前为activity，现在改为在当前Activity中增加一个layout），所以退出更顺滑，也不会出现两个activity之间退出的问题

### 0.18

修改了滑动时修改透明度的数值，不会那么快变为透明

### 0.17

修改了退出时缩放的代码，缩放更加精准

### 0.16

修复了一张图片时的问题

### 0.15

增加了指示器


### 0.14

简化了调用链
修复了退出动画


