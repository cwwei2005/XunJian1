package com.yado.xunjian.xunjian.utils;

import android.support.v4.view.ViewPager;
import android.view.View;

/**官方viewpager页面切换动画实例：vp.setPageTransformer(true,new ZoomOutPageTransformer());
 * Created by Administrator on 2017/11/22.
 */

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1) 不可见状态
            // This page is way off-screen to the left.
            view.setAlpha(0); //透明度设置为0

        } else if (position <= 1) { // [-1,1] 可见状态，设置动画效果
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                            (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else { // (1,+Infinity] 不可见状态
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
