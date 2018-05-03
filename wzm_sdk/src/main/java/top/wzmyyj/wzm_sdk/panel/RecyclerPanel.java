package top.wzmyyj.wzm_sdk.panel;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import top.wzmyyj.wzm_sdk.R;
import top.wzmyyj.wzm_sdk.inter.IVD;
import top.wzmyyj.wzm_sdk.tools.L;

/**
 * Created by wzm on 2018/4/23 0023.
 */

public abstract class RecyclerPanel<T> extends InitPanel
        implements  MultiItemTypeAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FrameLayout mFrameLayout;
    protected List<T> mData;
    protected List<IVD<T>> mIVD;
    protected HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    protected View mHeader;
    protected View mFooter;


    public RecyclerPanel(Context context) {
        super(context);
        this.title = "";
    }

    @Override
    public void initView() {
        view = mInflater.inflate(R.layout.panel_sr, null);
        mFrameLayout = view.findViewById(R.id.frameLayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(context.getResources()
                .getColor(R.color.colorBlue));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        mData = new ArrayList<>();
        mData = getData(mData);

        mIVD = new ArrayList<>();
        mIVD = getIVD(mIVD);

        setView(mRecyclerView, mSwipeRefreshLayout, mFrameLayout);
        mHeader = getHeader();
        mFooter = getFooter();
    }

    @NonNull
    protected abstract List<T> getData(List<T> data);

    @NonNull
    protected abstract List<IVD<T>> getIVD(List<IVD<T>> ivd);

    protected abstract void setView(RecyclerView rv, SwipeRefreshLayout srl, FrameLayout layout);

    protected abstract View getHeader();

    protected abstract View getFooter();


    @Override
    public void initData() {

        MultiItemTypeAdapter mAdapter = new MultiItemTypeAdapter(context, mData);

        for (ItemViewDelegate<T> ivd : mIVD) {
            mAdapter.addItemViewDelegate(ivd);
        }

        mAdapter.setOnItemClickListener(this);
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        if (mHeader != null)
            mHeaderAndFooterWrapper.addHeaderView(mHeader);
        if (mFooter != null)
            mHeaderAndFooterWrapper.addFootView(mFooter);
        mRecyclerView.setAdapter(mHeaderAndFooterWrapper);
    }




    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

    }

    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Override
    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                try {
                    update();
                    L.e("update data success");
                } catch (Exception e) {
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);

            }
        });

    }

    protected void update() {
        mData.clear();
        mData = getData(mData);
        notifyDataSetChanged();
    }

    protected void notifyDataSetChanged() {
        mHeaderAndFooterWrapper.notifyDataSetChanged();
        upHeaderAndFooter();
    }

    protected void upHeaderAndFooter() {

    }


}
