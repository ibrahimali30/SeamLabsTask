package com.ibrahim.seamlabstask.view.articleActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ibrahim.seamlabstask.R;
import com.ibrahim.seamlabstask.data.model.Article;
import com.ibrahim.seamlabstask.utils.Constants;
import com.ibrahim.seamlabstask.view.articleList.ArticlListViewModel;

import java.io.File;
import java.util.List;

public class ArticleActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";
    //views
    private WebView webView;
    private ProgressBar progressBar;
    private String articlUrl;
    private String articleTitle;
    private TextView mTitle;

    //variables
    ArticleViewModel mArticleViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        createToolbar();

        articlUrl = getIntent().getStringExtra(Constants.INTENT_URL);
        articleTitle = getIntent().getStringExtra(Constants.INTENT_TITLE);

        mArticleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);

        ArticlListViewModel mArticleViewModel2 = ViewModelProviders.of(this).get(ArticlListViewModel.class);

        mArticleViewModel2.articleLiveData.observe(this , new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                Log.d(TAG, "onChanged: called "+articles.size());
            }
        });

        mArticleViewModel2.savedArticlesLiveData.observe(this , new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                Log.d(TAG, "onChanged: called saved"+articles.size());
            }
        });

//        mArticleViewModel.updateArticleAsSaved(articlUrl);


        webView = findViewById(R.id.webView_article);
        progressBar = findViewById(R.id.progressBar);



        String folderName =getFilesDir().getAbsolutePath() + "/cachedFiles";
        String filename = folderName + "/"+articleTitle + ".mht";
        File file = new File(filename);
        String pathUrl = "file://" + filename ;
//        webView.loadUrl("file://" + filename);

        Log.d(TAG, "onCreate: "+filename);


        webView.loadUrl(articlUrl);


        initWebView();


    }


    private void initWebView() {

        webView.getSettings().setAppCachePath( getCacheDir().getAbsolutePath() );
        webView.getSettings().setAllowFileAccess( true );
        webView.getSettings().setAppCacheEnabled( true );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK ); // load online by default

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                        }
                    } , 500);

                }
                super.onProgressChanged(view, newProgress);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mArticleViewModel.updateArticleAsSaved(articlUrl);

                Log.d(TAG, "onPageFinished: called *****"+articlUrl+"  -  "+articleTitle+ArticleActivity.this.articlUrl);
                String folderName = getFilesDir().getAbsolutePath() + "/cachedFiles";
                Log.d(TAG, "onPageFinished: "+folderName);

                File folder = new File(folderName);
                Log.d(TAG, "onPageFinished: "+folder.getAbsolutePath());
                if (!folder.exists()) {
                    Log.d(TAG, "onPageFinished: created");
                    Log.d(TAG, "onPageFinished: "+folder.getAbsolutePath());
                    folder.mkdir();
                }
                webView.saveWebArchive(folderName + "/" + articleTitle + ".mht", false, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.d(TAG, "onReceiveValue: sss"+s);
                    }
                });

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });


    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_web_view);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitle = findViewById(R.id.toolbar_title_web_view);
        mTitle.setText(articlUrl);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*
             * Override the Up/Home Button
             * */
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
