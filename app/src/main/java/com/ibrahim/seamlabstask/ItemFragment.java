package com.ibrahim.seamlabstask;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ibrahim.seamlabstask.adapter.DataAdapter;
import com.ibrahim.seamlabstask.data.model.Article;
import com.ibrahim.seamlabstask.utils.Constants;
import com.ibrahim.seamlabstask.view.articleList.ArticlListFragmentViewModel;
import com.ibrahim.seamlabstask.view.articleList.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener , LifecycleOwner {
    private static final String TAG = "ItemFragment";

    private List<Article> articleStructure = new ArrayList<>();
    private DataAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;

    ArticlListFragmentViewModel mViewModel;
    private View view;

    int displayMode;

    public ItemFragment(int displayMode) {
        this.displayMode = displayMode ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item_list, container, false);

        initRecyclerView();
        mViewModel = ViewModelProviders.of(getActivity()).get(ArticlListFragmentViewModel.class);

        if (displayMode == Constants.SHOW_ARTICLE){
            mViewModel.articleLiveData.observe((LifecycleOwner) getActivity(), new Observer<List<Article>>() {
                @Override
                public void onChanged(List<Article> articles) {
                    Log.d(TAG, "onChanged: called "+articles.size());
                adapter.setArticles(articles);
                }
            });

            mViewModel.setRefreshing.observe(this , new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean isRefreshing) {
                    swipeRefreshLayout.setRefreshing(isRefreshing);
                }
            });
        }else {
            mViewModel.savedArticlesLiveData.observe( getActivity(), new Observer<List<Article>>() {
                @Override
                public void onChanged(List<Article> articles) {
                    Log.d(TAG, "onChanged: called saved"+articles.size());
                    adapter.setArticles(articles);
                }
            });
        }






        return view;
    }

    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.card_recycler_view);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DataAdapter(getActivity(), articleStructure);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);


    }

    @Override
    public void onRefresh() {
        mViewModel.getArticle();
    }
}
