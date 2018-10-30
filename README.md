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

<div>
<img src="https://github.com/wanglu1209/PhotoViewer/blob/master/gif/new_gif3.gif?raw=true" width="200" height="350" /> &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="https://github.com/wanglu1209/PhotoViewer/blob/master/gif/gif1.gif?raw=true" width="200" height="350" />

</div>


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

点击多张图片（类似微信朋友圈查看图片）

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

只点击一张图片时（类似点击查看头像）

```Kotlin
PhotoViewer
          .setClickSingleImg(url, iv)   //因为本框架不参与加载图片，所以还是要写回调方法
          .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
              override fun show(iv: ImageView, url: String) {
                  Glide.with(iv.context).load(url).into(iv)
              }
          })
          .start(this)
```

代码中，`photoview`文件夹为**chrisbanes**大神的[PhotoView](https://github.com/chrisbanes/PhotoView)

把代码加入到其中做了一些修改来达到效果



## Feature

- ~~增加弹出动画~~（完成）
- 增加加载图片时候的loading
- ~~点击退出时的动画（目前只有滑动退出时有动画效果）~~（完成）


## 更新日志

### 0.36
增加view的判空处理，解决由小圆点引发的闪退问题


### 0.35
使用软引用解决了内存泄露的问题

### 0.34
增加了返回按钮监听

### 0.33
增加了简单的loading，loading颜色是根据主model的colorAccent来改变的，暂时不能自己设置颜色

### 0.32
增加了点击一张图片的方法

### 0.31
增加了弹出动画！

### 0.30
重构了view移动的代码，再次重新更改了退出动画，现在贼鸡儿流畅！！！

### 0.21
点按退出增加动画效果了，再次更新了退出的位置，效果更好


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


