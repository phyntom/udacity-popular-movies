package com.phyntom.android.popular_movies;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by aimable on 08/10/2017.
 */

// Based on : https://gist.github.com/nesquena/d09dc68ff07e845cc622
// 2017-10-08

public abstract class LoadMoreRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private static String TAG = LoadMoreRecyclerOnScrollListener.class.getSimpleName();

    // items to have below the current scroll position before load more pages
    private int visibleThreshold = 3;

    // total number of items after the last load
    private int previousTotalItemCount = 0;

    // true in case we are waiting the last set of data to be loaded
    private boolean loading = true;

    // the current page
    private int currentPage = 1;

    // initialize the starting page at the beginning
    private int startingPage = 0;

    // set the last visible items position to the beginning
    private int lastVisibleItemPosition = 0;

    private int totalItemCount;

    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * in case the RecyclerView is using GridLayoutManager
     */

    public LoadMoreRecyclerOnScrollListener(GridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    /**
     * in case the RecyclerView is using LinearLayoutManager
     */
    public LoadMoreRecyclerOnScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        totalItemCount = mLayoutManager.getItemCount();
        lastVisibleItemPosition = 0;
        // get the last visible item position base of which type of layoutManager
        if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }
        //this means if the list size is fewer compared to the previous one which is 0

        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPage;
            previousTotalItemCount = totalItemCount;
            loading = totalItemCount == 0;
        }

        // if it is still loading but the dataSet has changed we consider that it has finished
        // we update the current page number and update total item count
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }
        // if it is not loading, we will check if we have not reached the threshold so that we can
        // load more data.
        //
        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage++;
            onLoadMorePage(currentPage);
            loading = true;
        }
    }

    public abstract void onLoadMorePage(int page);

}
