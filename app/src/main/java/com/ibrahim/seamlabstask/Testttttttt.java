package com.ibrahim.seamlabstask;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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

public class Testttttttt extends AppCompatActivity implements OnRecyclerItemClicked {
    private static final String TAG = "Testttttttt";

    BottomNavigationView mNavigationView;

    ArticlListViewModel mViewModel;
    private MotionLayout motionLayout;
    private ItemFragment fragment2 , fragment1;
    private WebView webView;
    private ImageView imageView;

    boolean visible;

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

//        initToolBar();
        initRecyclerView();
        initBottomNavigationView();
        webView = findViewById(R.id.recyclerview_front);
        motionLayout = findViewById(R.id.motionLayout);
        imageView = findViewById(R.id.top_image);
        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == 0 && motionLayout.getCurrentState() == R.layout.motion_333_findal){
                    Log.d(TAG, "onScrollChange: 000000000");
                    webView.setNestedScrollingEnabled(false);
                    setMotionSwipViewHeight(2000);

                }else {
                    setMotionSwipViewHeight(200);
                }


            }
        });
        
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: webView");
                return false;
            }
        });

        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                if (i == R.layout.motion_333_start) {
                    Log.d(TAG, "onTransitionCompleted: MOTION_LAUOUT_COLAPSED  "+i);
                    fragment1.recyclerView.setNestedScrollingEnabled(false);
                    fragment2.recyclerView.setNestedScrollingEnabled(false);
                }else {
                    fragment1.recyclerView.setNestedScrollingEnabled(true);
                    fragment2.recyclerView.setNestedScrollingEnabled(true);
                }

                if (i == R.layout.motion_333_findal) setMotionSwipViewHeight(200);

            }


            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }

        });


        ImageView imageView = findViewById(R.id.close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked");
                setFloatingArticleSize(1);
            }
        });

    }

    private void setMotionSwipViewHeight(int height) {
        ConstraintSet set1 = motionLayout.getConstraintSet(R.layout.motion_333_findal);
        set1.constrainHeight(R.id.view , height);
        motionLayout.requestLayout();
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
    }

    @Override
    public void onArticleClicked(final Article article) {
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

//        Glide.with(this).load(article.getUrlToImage()).into(imageView);



        webView.loadUrl(url);


        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

            setFloatingArticleSize(250);
            transitionToState(R.layout.motion_333_eend);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(Testttttttt.this).load(article.getUrlToImage()).into(imageView);

            }
        } , 50);

        motionLayout.requestLayout();

        visible = !visible ;

    }

    private void setFloatingArticleSize(int i) {
        getCurrentConsraintSet().constrainHeight(R.id.top_image_container, i);
        getCurrentConsraintSet().constrainHeight(R.id.top_image,i);
        getCurrentConsraintSet().constrainHeight(R.id.view, i);
        getCurrentConsraintSet().constrainHeight(R.id.tttt, i);
        getCurrentConsraintSet().constrainHeight(R.id.close, i);

        motionLayout.requestLayout();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        Log.d(TAG, "onBackPressed: "+motionLayout.getCurrentState());
        if (motionLayout.getCurrentState() == R.layout.motion_333_start) {

            super.onBackPressed();
        }else {
            transitionToState(R.layout.motion_333_start);
        }
    }

    private void transitionToState(int stateLayoutId) {
        motionLayout.transitionToState(stateLayoutId);
    }
    private ConstraintSet getCurrentConsraintSet() {
        return motionLayout.getConstraintSet(motionLayout.getCurrentState());
    }

}
