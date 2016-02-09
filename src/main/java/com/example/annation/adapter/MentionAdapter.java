package com.example.annation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.annation.R;
import com.example.annation.status.StatusEntity;
import com.example.annation.utils.RichTextUtils;
import com.example.annation.utils.TimeFormatUtils;

import java.util.List;

/**
 * Created by 若兰 on 2016/2/9.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class MentionAdapter extends RecyclerView.Adapter<MentionAdapter.MentionedHolder> {
    private List<StatusEntity> mDataSet;
    private Context mContext;

    public MentionAdapter(List<StatusEntity> dataSet, Context context) {
        mDataSet = dataSet;
        mContext = context;
    }

    public MentionedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentioned, null);
        return new MentionedHolder(view);
    }

    public void onBindViewHolder(MentionedHolder holder, int position) {
        StatusEntity entity = mDataSet.get(position);
        Glide.with(mContext).load(entity.user.profile_image_url).into(holder.ivHeader);
        holder.tvContent.setText(RichTextUtils.getRichText(mContext, entity.text));
        holder.tvTime.setText(TimeFormatUtils.parseToYYMMDD(entity.created_at));
        holder.tvSource.setText(Html.fromHtml(entity.source).toString());
        holder.tvUserName.setText(entity.user.screen_name);
        StatusEntity reEntity = entity.retweeted_status;
        //判断是否有转发，如果没有就不要显示了
        if(null!=reEntity){
            holder.llStatus.setVisibility(View.VISIBLE);

            //在这里要判断一下，如果deleted==1，那么代表微博已经删除了，这个时候在设置头像，就会报错的
            if(reEntity.deleted!=1){
                holder.tvMentionedName.setVisibility(View.VISIBLE);
                holder.ivMentioned.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(reEntity.user.profile_image_url).into(holder.ivMentioned);
                holder.tvMentionedName.setText(RichTextUtils.getRichText(mContext,"@" + reEntity.user.screen_name));
                holder.tvReContent.setText(reEntity.text);
            }
            else {
                holder.tvMentionedName.setVisibility(View.GONE);
                holder.ivMentioned.setVisibility(View.GONE);
                holder.tvReContent.setText("原微博已经删除！");
            }

        }
        else {
            holder.llStatus.setVisibility(View.GONE);
        }
    }

    public int getItemCount() {
        return mDataSet.size();
    }

    class MentionedHolder extends RecyclerView.ViewHolder{
        private ImageView ivHeader;
        private TextView tvUserName;
        private TextView tvTime;
        private TextView tvSource;
        private TextView tvContent;


        private TextView tvMentionedName;
        private TextView tvReContent;
        private LinearLayout llStatus;
        private ImageView ivMentioned;
        public MentionedHolder(View itemView) {
            super(itemView);
            initialize(itemView);
        }

        private void initialize(View view) {
            ivHeader = (ImageView) view.findViewById(R.id.ivHeader);
            tvUserName = (TextView) view.findViewById(R.id.tvUserName);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            tvSource = (TextView) view.findViewById(R.id.tvSource);
            tvContent = (TextView) view.findViewById(R.id.tvContent);

            tvMentionedName = (TextView) view.findViewById(R.id.tvMentionedName);
            tvReContent = (TextView) view.findViewById(R.id.tvReContent);
            llStatus = (LinearLayout) view.findViewById(R.id.llStatus);
            ivMentioned = (ImageView) view.findViewById(R.id.ivMentioned);
        }
    }
}
