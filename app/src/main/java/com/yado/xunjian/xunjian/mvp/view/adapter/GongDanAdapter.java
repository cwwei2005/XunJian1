package com.yado.xunjian.xunjian.mvp.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yado.xunjian.xunjian.R;
import com.yado.xunjian.xunjian.mvp.model.bean.GongDanInfo;

import java.util.List;

/**recyclerview adapter
 * 加载更多UI参考了：blog.csdn.net/dl10210950/article/details/53635170
 * Created by Administrator on 2017/11/20.
 */

public class GongDanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<GongDanInfo> list = null;
    OnItemClickLisenter onItemClickLisenter = null;

    private final static int TYPE_ITEM = 0;//普通item
    private final static int TYPE_FOOTER = 1;//脚布局
    private final static int LOADING_MORE = 1;
    private final static int NO_MORE = 2;
    private final static int HIDE_FOOTVIEW = 3;
    int footer_state = 1;

    public GongDanAdapter(Context context, List<GongDanInfo> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter){
        this.onItemClickLisenter = onItemClickLisenter;
    }

    /**
     * 改变脚布局的状态的方法,在activity根据请求数据的状态来改变这个状态
     *
     * @param state
     */
    public void changeState(int state) {
        this.footer_state = state;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //用于获得子项布局的控件，都保存在ViewHolder中
//        GongDanViewHolder viewHolder = new GongDanViewHolder(View.inflate(context, R.layout.item_recyclerview_gongdan, null));
//        return viewHolder;
        if (viewType ==TYPE_ITEM){
            GongDanViewHolder viewHolder = new GongDanViewHolder(View.inflate(context, R.layout.item_recyclerview_gongdan, null));
            return viewHolder;
        }else {
            FootViewHolder footViewHolder = new FootViewHolder(View.inflate(context, R.layout.footview_recyclerview, null));
            return footViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        //从ViewHolder获得控件，position获得数据
//        holder.tv.setText("hello"+":"+list.get(position).getText());
//        holder.tv.setOnClickListener(new View.OnClickListener() {//将item的点击事件转到onItemClickLisenter.onItemClick
//            @Override
//            public void onClick(View view) {
//                onItemClickLisenter.onItemClick(view, holder.getLayoutPosition());
//            }
//        });

        if (holder instanceof GongDanViewHolder){
            ((GongDanViewHolder) holder).tv.setText(list.get(position).getText());
            ((GongDanViewHolder) holder).tv.setOnClickListener(new View.OnClickListener() {//将item的点击事件转到onItemClickLisenter.onItemClick
                @Override
                public void onClick(View view) {
                    onItemClickLisenter.onItemClick(view, holder.getLayoutPosition());
                }
            });
        }else if (holder instanceof FootViewHolder){
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            if (position == 0){
                footViewHolder.mProgressBar.setVisibility(View.GONE);
                footViewHolder.tv_line1.setVisibility(View.GONE);
                footViewHolder.tv_line2.setVisibility(View.GONE);
                footViewHolder.tv_state.setText("");
            }
            switch (footer_state) {//根据状态来让脚布局发生改变
//                case PULL_LOAD_MORE://上拉加载
//                    footViewHolder.mProgressBar.setVisibility(View.GONE);
//                    footViewHolder.tv_state.setText("上拉加载更多");
//                    break;
                case LOADING_MORE:
                    footViewHolder.mProgressBar.setVisibility(View.VISIBLE);
                    footViewHolder.tv_line1.setVisibility(View.GONE);
                    footViewHolder.tv_line2.setVisibility(View.GONE);
                    footViewHolder.tv_state.setText("正在加载...");
                    break;
                case NO_MORE:
                    footViewHolder.mProgressBar.setVisibility(View.GONE);
                    footViewHolder.tv_line1.setVisibility(View.VISIBLE);
                    footViewHolder.tv_line2.setVisibility(View.VISIBLE);
                    footViewHolder.tv_state.setText("没有更多了");
//                    footViewHolder.tv_state.setTextColor(Color.parseColor("#ff00ff"));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
//        return list.size();
        return list != null ? list.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position+1 == getItemCount()){//底部时itemview为footview
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }
}
