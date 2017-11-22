package com.yado.xunjian.xunjian.mvp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yado.xunjian.xunjian.R;

/**对应layout：footview_recyclerview
 * Created by Administrator on 2017/11/20.
 */

public class FootViewHolder extends RecyclerView.ViewHolder{

    TextView tv_line1;//保存控件
    TextView tv_state;
    TextView tv_line2;
    ProgressBar mProgressBar;

    public FootViewHolder(View itemView) {
        super(itemView);
        tv_line1 = itemView.findViewById(R.id.tv_line1);
        tv_state = itemView.findViewById(R.id.foot_view_item_tv);
        tv_line2 = itemView.findViewById(R.id.tv_line2);
        mProgressBar = itemView.findViewById(R.id.progressbar);
//        ButterKnife.bind(this, view);
    }
}
