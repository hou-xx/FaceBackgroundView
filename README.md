# FaceBackgroundView

## 实现解释
代码绘制瓜子形 透明 背景框，用户人脸识别页面
把整个界面分为三部分 ： 背景、虚线边框、透明区域（用于展示摄像头绘制内容）
- 计算两条贝塞尔曲线构成一个封闭图形
- 闭合图形画虚线边框
- 闭合图形内部画透明或者图片
- 闭合图形以外部分画背景测

## 效果展示
![](https://github.com/tianqing2117/FaceBackgroundView/tree/master/screenshots.scanning.jpg)

![](https://github.com/tianqing2117/FaceBackgroundView/tree/master/screenshots.success.jpg)

![](https://github.com/tianqing2117/FaceBackgroundView/tree/master/screenshots.fail.jpg)
![扫描中][1]


  [1]: https://github.com/tianqing2117/FaceBackgroundView/tree/master/screenshots.scanning.jpg