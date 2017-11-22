package com.yado.xunjian.xunjian.mvp.view.adapter;

import android.view.View;

/**recyclerview 的点击事件
 * Created by Administrator on 2017/11/20.
 */

public interface OnItemClickLisenter {
    public void onItemClick(View v, int position);
    public void onItemLongClick(View v, int position);
}
