package com.tequila.tallybook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Window;
import android.widget.TabHost;

import com.tequila.tallybook.base.BaseFragment;
import com.tequila.tallybook.fragment.AccountFragment;
import com.tequila.tallybook.fragment.HomeFragment;
import com.tequila.tallybook.fragment.LifeFragment;
import com.tequila.tallybook.fragment.MoreFragment;
import com.tequila.tallybook.utils.BackHandledInterface;
import com.tequila.tallybook.utils.SysApplication;
import com.tequila.tallybook.view.TabIndicatorView;

public class MainActivity extends FragmentActivity
        implements TabHost.OnTabChangeListener, BackHandledInterface{

    private FragmentTabHost tabHost;
    private TabHost.TabSpec spec;
    private TabIndicatorView homeIndicator, lifeIndicator, accountIndicator, moreIndicator;

    private BaseFragment mBackHandedFragment;
    private boolean hadIntercept;

    private final static String TAB_HOME = "home";
    private final static String TAB_LIFE = "life";
    private final static String TAB_ACCOOUNT = "account";
    private final static String TAB_MORE = "more";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        SysApplication.getInstance().addActivity(this);

        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void initView() {

        tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.fl_content);

        spec = tabHost.newTabSpec(TAB_HOME);
        homeIndicator = new TabIndicatorView(this);
        homeIndicator.setTitle("首页");
        homeIndicator.setTabIcon(R.drawable.home_normal,
                R.drawable.home_press);
        spec.setIndicator(homeIndicator);
        tabHost.addTab(spec, HomeFragment.class, null);

        spec = tabHost.newTabSpec(TAB_LIFE);
        lifeIndicator = new TabIndicatorView(this);
        lifeIndicator.setTitle("ͨ生活");
        lifeIndicator.setTabIcon(R.drawable.lifeon_normal,
                R.drawable.lifeon_press);
        spec.setIndicator(lifeIndicator);
        tabHost.addTab(spec, LifeFragment.class, null);

        spec = tabHost.newTabSpec(TAB_ACCOOUNT);
        accountIndicator = new TabIndicatorView(this);
        accountIndicator.setTitle("账户");
        accountIndicator.setTabIcon(R.drawable.zhanghu_normal,
                R.drawable.zhanghu_press);
        spec.setIndicator(accountIndicator);
        tabHost.addTab(spec, AccountFragment.class, null);

        spec = tabHost.newTabSpec(TAB_MORE);
        moreIndicator = new TabIndicatorView(this);
        moreIndicator.setTitle("更多");
        moreIndicator.setTabIcon(R.drawable.gengduo_normal,
                R.drawable.gengduo_press);
        spec.setIndicator(moreIndicator);
        tabHost.addTab(spec, MoreFragment.class, null);

        tabHost.getTabWidget().setDividerDrawable(android.R.color.white);
        tabHost.setCurrentTabByTag(TAB_HOME);
        homeIndicator.setSelect(true);

        // 点击tab事件
        tabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onTabChanged(String tabId) {

        homeIndicator.setSelect(false);
        lifeIndicator.setSelect(false);
        accountIndicator.setSelect(false);
        moreIndicator.setSelect(false);

        if (TAB_HOME.equals(tabId)) {
            homeIndicator.setSelect(true);
        } else if (TAB_LIFE.equals(tabId)) {
            lifeIndicator.setSelect(true);
        } else if (TAB_ACCOOUNT.equals(tabId)) {
            accountIndicator.setSelect(true);
        } else if (TAB_MORE.equals(tabId)) {
            moreIndicator.setSelect(true);
        }
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }

    @Override
    public void onBackPressed() {
        if(mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()){
            if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                super.onBackPressed();
            }else{
                getSupportFragmentManager().popBackStack();
            }
        }
    }

}
