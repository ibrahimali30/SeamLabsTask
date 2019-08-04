package com.ibrahim.seamlabstask;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ibrahim.seamlabstask.ItemFragment;
import com.ibrahim.seamlabstask.Main2Activity;
import com.ibrahim.seamlabstask.Main3Activity;
import com.ibrahim.seamlabstask.R;
import com.ibrahim.seamlabstask.Testttttttt;
import com.ibrahim.seamlabstask.adapter.OnRecyclerItemClicked;
import com.ibrahim.seamlabstask.data.model.Article;
import com.ibrahim.seamlabstask.utils.Constants;
import com.ibrahim.seamlabstask.view.SearchActivity;
import com.ibrahim.seamlabstask.view.articleActivity.ArticleActivity;
import com.ibrahim.seamlabstask.view.articleList.ArticlListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OnRecyclerItemClicked, LifecycleOwner {
    private static final String TAG = "MainActivity";

    //view
//    private List<Article> articleStructure = new ArrayList<>();
//    private DataAdapter adapter;
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private RecyclerView recyclerView;

    BottomNavigationView mNavigationView;

    ArticlListViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initToolBar();
        initRecyclerView();
        initBottomNavigationView();

startActivity(new Intent(this , Testttttttt.class));



        mViewModel = ViewModelProviders.of(this).get(ArticlListViewModel.class);

        mViewModel.articleLiveData.observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {

            }
        });

        mViewModel.setRefreshing.observe((LifecycleOwner) this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRefreshing) {
//                swipeRefreshLayout.setRefreshing(isRefreshing);
            }
        });

        mViewModel.savedArticlesLiveData.observe((LifecycleOwner) this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                Log.d(TAG, "onChanged: called "+articles.size());
            }
        });

    }

    private void initBottomNavigationView() {
        mNavigationView = findViewById(R.id.bottom_nav);

        final ItemFragment fragment1 = new ItemFragment(Constants.SHOW_ARTICLE);
        final ItemFragment fragment2 = new ItemFragment(Constants.SHOW_SAVED_ARTICLE);

        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment2).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();

        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Log.d(TAG, "onNavigationItemSelected: sssssssssssssssss");
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right)
                                .show(fragment1).commit();
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right)
                                .hide(fragment2).commit();
                        break;

                    case R.id.navigation_offline :
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left)
                                .show(fragment2).commit();
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left)
                                .hide(fragment1).commit();
                        break;
                }
                return true;
            }
        });

        mNavigationView.setSelectedItemId(R.id.navigation_home);


    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main_activity);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }


    private void initRecyclerView() {
//        recyclerView = findViewById(R.id.card_recycler_view);
//        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
//        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        adapter = new DataAdapter(MainActivity.this, articleStructure);
//        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        mViewModel.getArticle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                openSearchActivity();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSearchActivity() {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
//        this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public void onArticleClicked(Article article) {

        Intent intent = new Intent(this, ArticleActivity.class);

        String url ;
        if (article.getIsSaved()==0){
            url = article.getUrl();
        }else {
            url = "file://" + getFilesDir().getAbsolutePath() + "/cachedFiles"  + "/"+article.getTitle() + ".mht";
        }

        intent.putExtra(Constants.INTENT_URL, url);
        intent.putExtra(Constants.INTENT_TITLE, article.getTitle());
        Log.d(TAG, "onArticleClicked: ");
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
