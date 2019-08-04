package com.ibrahim.seamlabstask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ibrahim.seamlabstask.adapter.OnRecyclerItemClicked;
import com.ibrahim.seamlabstask.data.model.Article;
import com.ibrahim.seamlabstask.utils.Constants;
import com.ibrahim.seamlabstask.view.SearchActivity;
import com.ibrahim.seamlabstask.view.articleList.ArticlListViewModel;

public class TestCollapseToolBar extends AppCompatActivity implements OnRecyclerItemClicked {
    private static final String TAG = "TestCollapseToolBar";

    BottomNavigationView mNavigationView;

    ArticlListViewModel mViewModel;
//    private MotionLayout motionLayout;
    private ItemFragment fragment2 , fragment1;
    private WebView webView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motion_333_start);

//        initToolBar();
        initRecyclerView();
        initBottomNavigationView();
        webView = findViewById(R.id.card_recycler_view);
        imageView = findViewById(R.id.top_image);

//        motionLayout = findViewById(R.id.motionLayout);
//
//        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
//            @Override
//            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {
//
//            }
//
//            @Override
//            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {
//
//            }
//
//            @Override
//            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
//                if (i == R.layout.motion_333_start) {
//                    Log.d(TAG, "onTransitionCompleted: MOTION_LAUOUT_COLAPSED  "+i);
//                    fragment1.recyclerView.setNestedScrollingEnabled(false);
//                    fragment2.recyclerView.setNestedScrollingEnabled(false);
//                }else {
//                    fragment1.recyclerView.setNestedScrollingEnabled(true);
//                    fragment2.recyclerView.setNestedScrollingEnabled(true);
//                }
////                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            }
//
//
//            @Override
//            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {
//
//            }
//
//        });

    }

    private void initBottomNavigationView() {
        mNavigationView = findViewById(R.id.bottom_nav);

        fragment1 = new ItemFragment(Constants.SHOW_ARTICLE);
        fragment2 = new ItemFragment(Constants.SHOW_SAVED_ARTICLE);

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

//    @Override
//    public void onRefresh() {
//        mViewModel.getArticle();
//    }

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
//        if (motionLayout.getStartState() != R.layout.motion_333_start)return;
//        Intent intent = new Intent(this, ArticleActivity.class);
        String url ;
        if (article.getIsSaved()==0){
            url = article.getUrl();
        }else {
            url = "file://" + getFilesDir().getAbsolutePath() + "/cachedFiles"  + "/"+article.getTitle() + ".mht";
        }

//        intent.putExtra(Constants.INTENT_URL, url);
//        intent.putExtra(Constants.INTENT_TITLE, article.getTitle());
//        startActivity(intent);

        Glide.with(this).load(article.getUrlToImage()).into(imageView);

        webView.loadUrl(url);


        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.setNestedScrollingEnabled(false);
        transitionToState(R.layout.motion_333_eend);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
//        Log.d(TAG, "onBackPressed: "+motionLayout.getCurrentState());
//        if (motionLayout.getCurrentState() == R.layout.motion_333_start) {

            super.onBackPressed();
//        }else {
//            transitionToState(R.layout.motion_333_start);
//        }
    }

    private void transitionToState(int stateLayoutId) {
//        motionLayout.transitionToState(stateLayoutId);
    }


}
