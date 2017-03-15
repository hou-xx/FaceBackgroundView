# FaceBackgroundView（ 使用 **贝塞尔曲线** 实现 ）




## 实现解释

代码绘制瓜子形 透明 背景框，用户人脸识别页面

把整个界面分为三部分 ： 背景、虚线边框、透明区域（用于展示摄像头绘制内容）

- 计算两条 ** 贝塞尔 ** 曲线构成一个封闭图形

- 闭合图形画虚线边框

- 闭合图形内部画透明或者图片

- 闭合图形以外部分画背景测




## 效果展示

![扫描中][1] ![扫描成功][2] ![扫描失败][3]

## 动画效果（跑马灯）

![跑马灯][4]





  [1]: https://raw.githubusercontent.com/tianqing2117/FaceBackgroundView/master/screenshots/scanning.jpg
  [2]: https://raw.githubusercontent.com/tianqing2117/FaceBackgroundView/master/screenshots/success.jpg
  [3]:https://raw.githubusercontent.com/tianqing2117/FaceBackgroundView/master/screenshots/fail.jpg
  [4]:https://raw.githubusercontent.com/tianqing2117/FaceBackgroundView/master/screenshots/animation.gif