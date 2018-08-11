# PhotoViewer

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
	        implementation 'com.github.wanglu1209:PhotoViewer:0.17'
	}
	
```

## 使用



```Kotlin
PhotoViewer
          .setData(图片链接List<String>)
          .setCurrentPage(现在是哪页)
          .setPicSpace(图片之间的横距离, 图片之间的竖距离（如果只有一行则写0）)
          .setCountOfRow(一行几个图片)
          .setClickView(点击的View)
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



## 更新日志

### 0.17

修改了退出时缩放的代码，缩放更加精准

### 0.16

修复了一张图片时的问题

### 0.15

增加了指示器


### 0.14

简化了调用链
修复了退出动画


