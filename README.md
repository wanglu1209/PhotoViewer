# PhotoViewer

该图片查看器是模仿微信朋友圈查看图片编写

![](https://fairyever.qiniudn.com/1.gif)

![](https://fairyever.qiniudn.com/2.gif)

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
	        implementation 'com.github.wanglu1209:PhotoViewer:0.13'
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



## Future

- 指示器
- 只有一个图片时的一些问题


