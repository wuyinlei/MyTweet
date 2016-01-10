package com.example.annation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.annation.R;
import com.example.annation.status.PicUrlsEntity;
import com.example.annation.status.StatusEntity;
import com.example.annation.utils.CircleTransform;
import com.example.annation.utils.PreferenceUtils;
import com.example.annation.utils.RichTextUtils;
import com.example.annation.utils.TimeFormatUtils;
import com.example.annation.widget.DrawCenterTextView;

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

    private Context mContext;

    public HomeAdapter(List<StatusEntity> entities, Context context) {
        mEntities = entities;
        mContext = context;
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
        //设置用户名字
        holder.tvUserName.setText(entity.user.screen_name);
        //设置发表时间
        holder.tvTime.setText(TimeFormatUtils.parseToYYMMDD(entity.created_at));
        //设置发表内容
        //
        holder.tvContent.setText(RichTextUtils.getRichText(mContext,entity.text));
        holder.tvContent.setMovementMethod(LinkMovementMethod.getInstance());
        //holder.tvContent.setText(entity.text);
        //设置内容来源
        holder.tvSource.setText(Html.fromHtml(entity.source).toString());
        //加载用户图片
        StatusEntity reStatues = entity.retweeted_status;
        Glide.with(mContext).load(entity.user.profile_image_url)/*.placeholder(R.mipmap.ic_launcher)*/
                .transform(new CircleTransform(mContext))
                .into(holder.ivHeader);
        List<PicUrlsEntity> pics = entity.pic_urls;

        /**
         * 设置评论、转发、点赞
         */
        holder.tvComment.setText(String.valueOf(entity.comments_count));
        holder.tvLike.setText(String.valueOf(entity.attitudes_count));
        holder.tvRetweet.setText(String.valueOf(entity.reposts_count));

        if (null != pics && pics.size() > 0) {
            //判断是否有头像，如果有头像就显示出来
            PicUrlsEntity pic = pics.get(0);
            pic.original_pic = pic.thumbnail_pic.replace("thumbnail", "large");
            pic.bmiddle_pic = pic.thumbnail_pic.replace("thumbnail", "bmiddle");
            holder.ivContent.setVisibility(View.VISIBLE);//头像
            Glide.with(mContext).load(pic.original_pic)
                    .into(holder.ivContent);
        } else {
            holder.ivContent.setVisibility(View.GONE);
        }
        if (null != reStatues) {
            String reContent = "@" +reStatues.user.screen_name + ":" + reStatues.text;

            //判断是否有内容图像，如果有，就显示
            holder.tvReContent.setVisibility(View.VISIBLE);
           // holder.tvReContent.setText(reStatues.text);
            holder.tvReContent.setText(RichTextUtils.getRichText(mContext,reContent));
            holder.tvReContent.setMovementMethod(LinkMovementMethod.getInstance());
            List<PicUrlsEntity> rePics = reStatues.pic_urls;
            if (null != rePics && rePics.size() > 0) {
                PicUrlsEntity pic = rePics.get(0);
                pic.original_pic = pic.thumbnail_pic.replace("thumbnail", "large");
                pic.bmiddle_pic = pic.thumbnail_pic.replace("thumbnail", "bmiddle");
                holder.ivReContent.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(pic.original_pic)
                        .into(holder.ivReContent);
            } else {
                holder.ivContent.setVisibility(View.GONE);
            }
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

        private ImageView ivHeader, ivContent, ivReContent;
        private TextView tvUserName;
        private TextView tvTime;
        private TextView tvSource;
        private TextView tvContent;
        private LinearLayout llRe;
        private TextView tvReContent;
        private DrawCenterTextView tvRetweet,tvLike,tvComment;


        /**
         * 初始化布局控件
         * @param itemView
         */
        public HomeViewHolder(View itemView) {
            super(itemView);
            ivHeader = (ImageView) itemView.findViewById(R.id.ivHeader);
            ivReContent = (ImageView) itemView.findViewById(R.id.ivReContent);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvSource = (TextView) itemView.findViewById(R.id.tvSource);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            llRe = (LinearLayout) itemView.findViewById(R.id.llRe);
            tvReContent = (TextView) itemView.findViewById(R.id.tvReContent);
            ivContent = (ImageView) itemView.findViewById(R.id.ivContent);
            tvRetweet = (DrawCenterTextView) itemView.findViewById(R.id.tvRetweet);
            tvLike = (DrawCenterTextView) itemView.findViewById(R.id.tvLike);
            tvComment = (DrawCenterTextView) itemView.findViewById(R.id.tvComment);
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
