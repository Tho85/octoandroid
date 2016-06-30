package com.nairbspace.octoandroid.ui.terminal;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nairbspace.octoandroid.R;
import com.nairbspace.octoandroid.app.SetupApplication;
import com.nairbspace.octoandroid.ui.templates.BasePagerFragmentListener;
import com.nairbspace.octoandroid.ui.templates.Presenter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConsoleFragment extends BasePagerFragmentListener<ConsoleScreen, ConsoleFragment.Listener>
        implements ConsoleScreen {

    private static final String LOG_LIST_KEY = "log_list_key";
    private static final String AUTO_SCROLL_KEY = "auto_scroll_key";
    private static final String LOCK_KEY = "lock_key";

    @BindString(R.string.lock) String LOCK;
    @BindString(R.string.unlock) String UNLOCK;
    @BindString(R.string.disable_autoscroll) String DISABLE_AUTOSCROLL;
    @BindString(R.string.enable_autoscroll) String ENABLE_AUTOSCROLL;

    @Inject ConsolePresenter mPresenter;
    private Listener mListener;

    @BindView(R.id.console_recyclerview) RecyclerView mRecyclerView;
    @BindDrawable(R.drawable.ic_close_white_24dp) Drawable mCloseDrawable;
    @BindDrawable(R.drawable.ic_expand_more_white_24dp) Drawable mExpandDrawable;
    @BindDrawable(R.drawable.ic_lock_open_white_24dp) Drawable mUnlockDrawable;
    @BindDrawable(R.drawable.ic_lock_outline_white_24dp) Drawable mLockDrawable;
    private View mMainView;
    private ArrayList<String> mLogList;
    private ConsoleRvAdapter mAdapter;
    private boolean mIsAutoScrollEnabled = true;

    public static ConsoleFragment newInstance() {
        return new ConsoleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetupApplication.get(getContext()).getAppComponent().inject(this);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_console, container, false);
        setUnbinder(ButterKnife.bind(this, mMainView));
        if (savedInstanceState == null) mLogList = new ArrayList<>();
        else mLogList = restoreInstance(savedInstanceState);
        mAdapter = new ConsoleRvAdapter(mLogList); // Passes field instance
        mRecyclerView.setAdapter(mAdapter);
        return mMainView;
    }

    private ArrayList<String> restoreInstance(Bundle savedInstanceState) {
        mIsAutoScrollEnabled = savedInstanceState.getBoolean(AUTO_SCROLL_KEY);
        boolean lock = savedInstanceState.getBoolean(LOCK_KEY);
        ViewCompat.setNestedScrollingEnabled(mMainView, lock);
        return savedInstanceState.getStringArrayList(LOG_LIST_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // This list is updated from within adapter since it was passed in constructor
        outState.putStringArrayList(LOG_LIST_KEY, mLogList);
        outState.putBoolean(LOCK_KEY, ViewCompat.isNestedScrollingEnabled(mMainView));
        outState.putBoolean(AUTO_SCROLL_KEY, mIsAutoScrollEnabled);
    }

    @Override
    public void updateUi(String log) {
        mAdapter.addLogItem(log);
        if (mIsAutoScrollEnabled) {
            mRecyclerView.setVerticalScrollBarEnabled(false);
            mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
            mRecyclerView.setVerticalScrollBarEnabled(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_console, menu);
        MenuItem lock = menu.findItem(R.id.console_lock_menu_item);
        if (lock != null) updateLockIcon(lock);
        MenuItem scroll = menu.findItem(R.id.console_stop_auto_scroll_menu_item);
        if (scroll != null) updateScrollIcon(scroll);
    }

    private void updateLockIcon(@NonNull MenuItem menuItem) {
        boolean isEnabled = ViewCompat.isNestedScrollingEnabled(mMainView);
        menuItem.setTitle(isEnabled ? UNLOCK : LOCK);
        menuItem.setIcon(isEnabled ? mUnlockDrawable : mLockDrawable);
    }

    private void toggleLock() {
        boolean isEnabled = ViewCompat.isNestedScrollingEnabled(mMainView);
        ViewCompat.setNestedScrollingEnabled(mMainView, !isEnabled);
    }

    private void updateScrollIcon(@NonNull MenuItem menuItem) {
        menuItem.setTitle(mIsAutoScrollEnabled ? DISABLE_AUTOSCROLL : ENABLE_AUTOSCROLL);
        menuItem.setIcon(mIsAutoScrollEnabled ? mCloseDrawable : mExpandDrawable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.console_lock_menu_item:
                toggleLock();
                updateLockIcon(item);
                return true;
            case R.id.console_stop_auto_scroll_menu_item:
                mIsAutoScrollEnabled = !mIsAutoScrollEnabled;
                updateScrollIcon(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    @Override
    protected Presenter setPresenter() {
        return mPresenter;
    }

    @NonNull
    @Override
    protected ConsoleScreen setScreen() {
        return this;
    }

    @NonNull
    @Override
    protected Listener setListener() {
        mListener = (Listener) getContext();
        return mListener;
    }

    public interface Listener {

    }
}
