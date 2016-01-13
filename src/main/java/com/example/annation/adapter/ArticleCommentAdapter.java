package com.example.annation.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.annation.activity.PhotoViewActivity;
import com.example.annation.activity.RepostActivity;
import com.example.annation.status.CommentEntity;
import com.example.annation.status.PicUrlsEntity;
import com.example.annation.status.StatusEntity;
import com.example.annation.uri.ParameterKeySet;
import com.example.annation.utils.CircleTransform;
import com.example.annation.utils.RichTextUtils;
import com.example.annation.utils.TimeFormatUtils;
import com.example.annation.widget.DrawCenterTextView;

import java.util.List;

/**
 * Created by 若兰 on 2016/1/13.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class ArticleCommentAdapter extends RecyclerView.Adapter {

    /**
     * view的头部
     */
    private final static int VIEW_TYPE_HEADER = 0;

    /**
     * view的子item
     */
    private final static int VIEW_TYPE_ITEM = 1;

    /**
     * 存放评论的数据
     */
    private List<CommentEntity> mList;
    private Context mContext;
    private StatusEntity mStatusEntity;

    public ArticleCommentAdapter( Context mContext, StatusEntity mStatusEntity,List<CommentEntity> list) {
        this.mContext = mContext;
        this.mStatusEntity = mStatusEntity;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;

        View view;

        /**
         * 布局泵，得到布局泵，加载布局
         */
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        /**
         * 判断是否是头部，也就是原文
         */
        if (viewType == VIEW_TYPE_HEADER){
            view = layoutInflater.inflate(R.layout.item_mytweet_content,parent,false);
            viewHolder = new HomeViewHolder(view);
        } else {
            //否则的话，就是评论
            view = layoutInflater.inflate(R.layout.item_weibo_comment, parent, false);
            viewHolder = new CommonViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeViewHolder) {
            final StatusEntity entity = mStatusEntity;
            HomeViewHolder homeViewHolder = (HomeViewHolder) holder;
            //设置用户名字
            homeViewHolder.tvUserName.setText(entity.user.screen_name);
            //设置发表时间
            homeViewHolder.tvTime.setText(TimeFormatUtils.parseToYYMMDD(entity.created_at));
            //设置发表内容
            //
            homeViewHolder.tvContent.setText(RichTextUtils.getRichText(mContext, entity.text));
            homeViewHolder.tvContent.setMovementMethod(LinkMovementMethod.getInstance());
            //holder.tvContent.setText(entity.text);
            //设置内容来源
            homeViewHolder.tvSource.setText(Html.fromHtml(entity.source).toString());
            //加载用户图片
            StatusEntity reStatues = entity.retweeted_status;
            Glide.with(mContext).load(entity.user.profile_image_url)/*.placeholder(R.mipmap.ic_launcher)*/
                    .transform(new CircleTransform(mContext))
                    .into(homeViewHolder.ivHeader);
            List<PicUrlsEntity> pics = entity.pic_urls;

            /**
             * 设置评论、转发、点赞
             */
            homeViewHolder.tvComment.setText(String.valueOf(entity.comments_count));
            homeViewHolder.tvLike.setText(String.valueOf(entity.attitudes_count));
            homeViewHolder.tvRetweet.setText(String.valueOf(entity.reposts_count));

            /**
             * 转发的点击事件
             */
            homeViewHolder.tvRetweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RepostActivity.class);
                    intent.putExtra(ParameterKeySet.ID, entity.id);
                    intent.putExtra(ParameterKeySet.STATUS, entity.text);
                    intent.setAction("REPOST");
                    mContext.startActivity(intent);
                }
            });


            /**
             * 评论的点击事件
             */
            homeViewHolder.tvComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RepostActivity.class);
                    intent.putExtra(ParameterKeySet.ID, entity.id);
                    intent.setAction("COMMENT");
                    mContext.startActivity(intent);
                }
            });

            if (null != pics && pics.size() > 0) {
                //判断是否有头像，如果有头像就显示出来
                final PicUrlsEntity pic = pics.get(0);
                pic.original_pic = pic.thumbnail_pic.replace("thumbnail", "large");
                pic.bmiddle_pic = pic.thumbnail_pic.replace("thumbnail", "bmiddle");
                homeViewHolder.ivContent.setVisibility(View.VISIBLE);//头像
                Glide.with(mContext).load(pic.bmiddle_pic)
                        .into(homeViewHolder.ivContent);
                homeViewHolder.ivContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, PhotoViewActivity.class);
                        intent.putExtra(PicUrlsEntity.class.getSimpleName(), pic);
                        mContext.startActivity(intent);
                    }
                });
            } else {
                homeViewHolder.ivContent.setVisibility(View.GONE);
            }
            if (null != reStatues) {
                String reContent = "@" + reStatues.user.screen_name + ":" + reStatues.text;

                //判断是否有内容图像，如果有，就显示
                homeViewHolder.tvReContent.setVisibility(View.VISIBLE);
                // holder.tvReContent.setText(reStatues.text);
                homeViewHolder.tvReContent.setText(RichTextUtils.getRichText(mContext, reContent));
                homeViewHolder.tvReContent.setMovementMethod(LinkMovementMethod.getInstance());
                List<PicUrlsEntity> rePics = reStatues.pic_urls;
                if (null != rePics && rePics.size() > 0) {
                    final PicUrlsEntity pic = rePics.get(0);
                    pic.original_pic = pic.thumbnail_pic.replace("thumbnail", "large");
                    pic.bmiddle_pic = pic.thumbnail_pic.replace("thumbnail", "bmiddle");
                    homeViewHolder.ivReContent.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(pic.bmiddle_pic)
                            .into(homeViewHolder.ivReContent);
                    homeViewHolder.ivReContent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, PhotoViewActivity.class);
                            intent.putExtra(PicUrlsEntity.class.getSimpleName(), pic);
                            mContext.startActivity(intent);
                        }
                    });

                } else {
                    homeViewHolder.ivContent.setVisibility(View.GONE);
                }
            } else {
                homeViewHolder.tvReContent.setVisibility(View.GONE);
            }
        }

        /**
         * 判断当前的holder的类型是否是CommonViewHolder类型，如果是的话，就加载评论人的信息和评论的内容
         */
        if(holder instanceof CommonViewHolder){
            CommonViewHolder commonViewHolder = (CommonViewHolder) holder;
            CommentEntity  commentEntity = mList.get(position-1);
            Glide.with(mContext).load(commentEntity.user.profile_image_url).into(commonViewHolder.ivHeader);
            commonViewHolder.tvComment.setText(commentEntity.text);
            commonViewHolder.tvUserName.setText(commentEntity.user.screen_name);
            commonViewHolder.tvTime.setText(TimeFormatUtils.parseToYYMMDD(commentEntity.created_at));
        }
    }


    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    class ArticleCommentViewHolder extends RecyclerView.ViewHolder {

        public ArticleCommentViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position)? VIEW_TYPE_HEADER:VIEW_TYPE_ITEM;
    }

    private boolean isHeader(int position) {
        return position == 0;
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

        }


    }

    /**
     * 这个是评论的ViewHolder，在这里加载了评论的布局
     */
    class CommonViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHeader;
        private TextView tvUserName;
        private TextView tvTime;
        private TextView tvComment;

        public CommonViewHolder(View itemView) {
            super(itemView);
            initialize(itemView);
        }

        private void initialize(View view) {

            ivHeader = (ImageView) view.findViewById(R.id.ivHeader);
            tvUserName = (TextView) view.findViewById(R.id.tvUserName);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            tvComment = (TextView) view.findViewById(R.id.tvComment);
        }
    }
}
