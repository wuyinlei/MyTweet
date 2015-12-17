package com.example.annation.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.annation.R;
import com.example.annation.status.StatusEntity;
import com.example.annation.utils.TimeFormatUtils;

import java.util.List;

/**
 * Created by ruolan on 2015/12/16.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    //上面的这一句话，必须在继承于RecycleView.Adapter，这个必须还要是泛型，也就是传入我们下面创建的ViewHolder
    private List<StatusEntity> mEntities;
    private OnItemClickListener mListener;


    public HomeAdapter(List<StatusEntity> entities) {
        mEntities = entities;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mytweet_content, parent, false);
        return new HomeViewHolder(view);
    }


    /**
     * 绑定ViewHolder，也就是给控件赋值
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        StatusEntity entity = mEntities.get(position);
        holder.tvUserName.setText(entity.user.screen_name);
        holder.tvTime.setText(TimeFormatUtils.parseToYYMMDD(entity.created_at));
        holder.tvContent.setText(entity.text);
        holder.tvSource.setText(Html.fromHtml(entity.source).toString());
        StatusEntity reStatues = entity.retweeted_status;
        if (null != reStatues) {
            holder.tvReContent.setVisibility(View.VISIBLE);
            holder.tvReContent.setText(reStatues.text);
        } else {
            holder.tvReContent.setVisibility(View.GONE);
        }
    }

    public void onItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mEntities.size();
    }

    /**
     * 使用RecycleView，创建的ViewHolder必须继承与RecycleView的ViewHolder
     */
    class HomeViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivHeader;
        private TextView tvUserName;
        private TextView tvTime;
        private TextView tvSource;
        private TextView tvContent;
        private LinearLayout llRe;
        private TextView tvReContent;

        public HomeViewHolder(View itemView) {
            super(itemView);
            ivHeader = (ImageView) itemView.findViewById(R.id.ivHeader);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvSource = (TextView) itemView.findViewById(R.id.tvSource);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            llRe = (LinearLayout) itemView.findViewById(R.id.llRe);
            tvReContent = (TextView) itemView.findViewById(R.id.tvReContent);

            //在这里我们调用我们自定义的接口，来实现我们想要的点击效果
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClickListener(v, getLayoutPosition());
                    }
                }
            });

        }


    }

    /**
     * 用RecycleView的时候官方没有提供ItemClickListener这个方法，也就是说
     * 我们要自己写，下面是定义的点击接口，正是这一点，我们可以自定义自己的
     * 想要的效果
     */
    public interface OnItemClickListener {
        public void onItemClickListener(View v, int position);
    }


}
