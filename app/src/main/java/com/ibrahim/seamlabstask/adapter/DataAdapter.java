package com.ibrahim.seamlabstask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ibrahim.seamlabstask.R;
import com.ibrahim.seamlabstask.data.model.Article;

import java.util.List;

/*
** This Class is Used to fetch the data from the POJO Article and bind them to the views.
**/
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private static final String TAG = "DataAdapter";

    private List<Article> articles;
    private Context mContext;
    private int lastPosition = -1;

    OnRecyclerItemClicked mOnRecyclerItemClicked;

    public DataAdapter(Context mContext, List<Article> articles) {
        this.mContext = mContext;
        this.articles = articles;
        mOnRecyclerItemClicked = (OnRecyclerItemClicked) mContext;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }


    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        Article currentArticle = articles.get(position);

        holder.tv_card_main_title.setText(currentArticle.getTitle());

        Glide.with(mContext)
                .load(currentArticle.getUrlToImage())
                .thumbnail(0.1f)
                .centerCrop()
                .into(holder.img_card_main);

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.item_animation_fall_down);
            holder.cardView.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_card_main_title;
        private ImageView img_card_main;
        private CardView cardView;

        public ViewHolder(View view) {
            super(view);
            tv_card_main_title = view.findViewById(R.id.tv_card_main_title);
            img_card_main = view.findViewById(R.id.img_card_main);
            cardView = view.findViewById(R.id.card_row);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mOnRecyclerItemClicked.onArticleClicked(articles.get(getAdapterPosition()));

        }
    }
}
