package com.yado.xunjian.xunjian.mvp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yado.xunjian.xunjian.R;

/**对应layout：R.layout.item_recyclerview_gongdan
 * Created by Administrator on 2017/11/20.
 */

public class GongDanViewHolder extends RecyclerView.ViewHolder{

    TextView tv;//保存控件

    public GongDanViewHolder(View itemView) {
        super(itemView);
        tv = itemView.findViewById(R.id.tv);
//        ButterKnife.bind(this, view);
    }
}
