package com.tequila.tallybook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tequila.tallybook.R;
import com.tequila.tallybook.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tequila on 2017/5/4.
 */

public class MoreFragment extends BaseFragment {

    private View mRootView;
    private final String WebUrl = "http://www.tequilastudio.cn/index.html";

    @Bind(R.id.webview)
    WebView webview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.more_fragment, container, false);
        }

        ViewGroup mViewGroup = (ViewGroup)mRootView.getParent();
        if(mViewGroup!=null){
            mViewGroup.removeView(mRootView);
        }

        ButterKnife.bind(this, mRootView);

        initView();

        return mRootView;
    }

    private void initView() {
        webview.loadUrl(WebUrl);

        // 启用支持javaScript
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);

        // 优先使用缓存
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 不使用缓存
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        if(webview.canGoBack()) webview.goBack();//返回上一页面

        return true;
    }
}
