package com.cml.androidlcmlsenioranimation.activity_package;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cml.androidlcmlsenioranimation.R;


/**
 *   图片分类
 *      1.png,jpeg,gif... (由像素点构成,GPU根据每个像素点去做计算,加载快)
 *      2.svg... (矢量图,由GPU计算的,可缩放性)
 *
 *
 *   SVG和Vector的差异
 *      SVG : 前端中使用,是一套语法规范
 *      Vector : Android中使用,只是实现了SVG语法的Path标签
 *
 *   Vector的常用语法
 *      M = moveto(M X,Y) :将画笔移动到指定的坐标位置
 *      L = lineto(L X,Y) : 画直线到指定的坐标位置
 *      Z = closepath() : 关闭路径
 *      H = horizontal lineto(H X) : 画水平线到指定的X坐标位置
 *      V = vertical lineto(V Y)　: 画垂直线到指定的Y坐标位置
 *      例(画一个矩形):
 *      <vector xmlns:android="http://schemas.android.com/apk/res/android"
 *              android:width = "200dp"
 *              android:height = "200dp"
 *              android:viewportHeight = "500"
 *              android:viewportWidth = "500">
 *
 *          <path
 *              android:name="square"
 *              android:fillColor="#000000"
 *              android:pathData="M100,100 L400,100 L400,400 L100,400 z"/>
 *
 *      </vector>
 *      说明: vector是将200dp映射到500,500这样的坐标系中
 *
 *
 *
 *      SVG编辑器:http://editor.method.ac/
 *
 *      SVG 转换成 VectorDrawable : http://inloop.github.io/svg2android/
 *
 *      图标库:http://www.iconfont.cn/search?q=eye
 *
 *      AndroidStudio中使用:
 *          res-->drawable-->(右键)new --> Vector Asset --> Material Icon(已有的icon选择)/Local file(本地导入icon)
 *
 *      兼容: 在Gradle Plugin 1.5 中 做了VectorDrawable向下兼容
 *          SDK >= 21 --> 使用Vector
 *          SDK < 21  -->将Vector转换为PNG
 *
 *          AppCompat 23.2  : 中 静态的Vector支持到Android2.1+
 *                               动态的Vector支持到Android3.0+
 *
 *
 *
 *      使用:app/build.gradle中增加
 *          android {
 *              defaultConfig {
 *                  vectorDrawables.useSupportLibrary = true
 *              }
 *          }
 *          compile 'com.android.support:appcompat-v7:23.4.0'
 *
 *      控件中使用:
 *          ImageView\ImageButton
 *              app:srcCompat="@drawable/vector_image"
 *
 *          Button (带状态的)
 *              1.使用selector来设置背景
 *              2.代码中增加
     *              static{
     *                  AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
     *              }
 *
 *      使用动态的VectorDrawable
 *          配置动画的粘合剂 --> animated-vector (将vector和animator粘合在一起)
 *          例如:res/animator/anim_left
 *
 *
 *      动态VectorDrawable的兼容问题
 *          向下兼容:
 *              Path Morphing (路径变换动画,例如:5角星变为5边形....) :在Android pre-L版本下无法使用
 *              Path Interpolation(路径插值器):在Android pre-L版本只能使用系统提供的差值器,不能自定义
 *          向上兼容:
 *              Path Morphing (路径变换动画): 在Android L 版本以上需要使用代码配置
 *          抽取string兼容问题
 *              不支持从Strings.xml中读取<PathData>
 *
 *       Vector vs Bitmap (png vs VectorDrawable)
 *          png : 好处--可以借助GPU对图像进行渲染,渲染效率高
 *          VectorDrawable : 好处--体积小,缩放不失真
 *                           劣势--只能借助CPU去解析运算,无法用GPU实现渲染加速
 *          主要从两方面考虑
     *          图像复杂度/图像更新频率
 *                  1.Bitmap的绘制效率并不一定会比Vector高,它们有一定的平衡点,当Vector比较简单时,其效率是一定比bitmap高的
 *                      所以,为了保证Vector的高效率,Vector需要更加简单,PathData更加标准,精简.当Vector图像非常复杂时,就要用Bitmap来代替了
 *                  2.Vector 试用于ICON/Button/ImageView的图标等小的ICON,或者是需要的动画效果,由于Bitmap在GPU中有缓存功能,
 *                      而Vector并没有,所以Vector图像不能做频繁的重绘
 *                  3.Vector 图像过于复杂时,不仅仅要注意绘制效率,初始化效率也是需要考虑的重要因素
 *                  4.SVG加载速度会快于PNG,但是渲染速度会慢于PNG,毕竟PNG有硬件加速,但平均下来,加载速度的提升弥补了绘制的速度缺陷
 *
 *
 */
public class VectorDrawableActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable);
    }

    public void anim(View view){
        ImageView imageView = (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        if(drawable instanceof Animatable){
            ((Animatable)drawable).start();
        }
    }

    /**
     * Path Morphing 动画演示
     *      在Android L Later 上的使用示例
     */
    public void anim_L_Later(View view){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ImageView imageView = (ImageView) view;
//            Drawable drawable = imageView.getDrawable();
//            if(drawable instanceof Animatable){
//                ((Animatable)drawable).start();
//            }
            AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) getDrawable(R.drawable.fivestart_anim);
            imageView.setImageDrawable(drawable);
            if(drawable != null){
                drawable.start();
            }
        }else {
            Toast.makeText(this, "Android 5.0 pre 系统无法使用", Toast.LENGTH_SHORT).show();
        }

    }
}
