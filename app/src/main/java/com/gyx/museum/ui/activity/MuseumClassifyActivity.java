package com.gyx.museum.ui.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyx.museum.R;
import com.gyx.museum.adapter.HomeActiveAdapter;
import com.gyx.museum.adapter.MuseumClassifyAdapter;
import com.gyx.museum.adapter.WaterFallAdapter;
import com.gyx.museum.base.BaseActivity;
import com.gyx.museum.base.BasePresenter;
import com.gyx.museum.utils.CommonUtil;
import com.gyx.museum.widget.StaggerRecyclerView;

import butterknife.BindView;

/**
 * 功能：馆藏分类
 * Created by Administrator on 2017/11/27.
 */

public class MuseumClassifyActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener{
    @BindView(R.id.rlv)
    StaggerRecyclerView recyclerView;
    @BindView(R.id.swiper)
    SwipeRefreshLayout swipeRefreshLayout;
    MuseumClassifyAdapter museumClassifyAdapter;
    private WaterFallAdapter mAdapter;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_museum_classify;
    }

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
       /* mAdapter = new WaterFallAdapter(mContext);
        recyclerView.setAdapter(mAdapter);
        mAdapter.getList().addAll(CommonUtil.getMenuActive());
        mAdapter.getRandomHeight(CommonUtil.getMenuActive());
        mAdapter.notifyDataSetChanged();*/
        museumClassifyAdapter = new MuseumClassifyAdapter(CommonUtil.getMenuActive(),mContext);
        museumClassifyAdapter.getRandomHeight(CommonUtil.getMenuActive());
        recyclerView.setAdapter(museumClassifyAdapter);
        museumClassifyAdapter.setOnLoadMoreListener(this);
        museumClassifyAdapter.openLoadMore(5,true);
        museumClassifyAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    public void onLoadMoreRequested() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                museumClassifyAdapter.getRandomHeight(CommonUtil.getMenuActive());
                museumClassifyAdapter.notifyDataChangedAfterLoadMore(CommonUtil.getMenuActive(), true);//更新数据
                //.notifyItemRangeInserted(m);
            }
        };
        handler.post(r);
    }
}
