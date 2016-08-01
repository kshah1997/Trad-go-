package teamcool.tradego.Fragments;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamcool.tradego.Clients.ParseClient;
import teamcool.tradego.Models.Item;
import teamcool.tradego.R;

/**
 * Created by selinabing on 7/8/16.
 */
public class CategoriesTimelineFragment extends CatalogListFragment {

    boolean isViewCreated = false;
    boolean isSeen = false;
    boolean isLoaded = false;
    boolean isRefresh = false;
    List<Item> items;

    @BindView(R.id.swipeContainerCatalog) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.ivNoItems) ImageView ivNoItems;

    public CategoriesTimelineFragment() {

    }

    public static CategoriesTimelineFragment newInstance (String category) {
        CategoriesTimelineFragment frag = new CategoriesTimelineFragment();
        Bundle args = new Bundle();
        args.putString("category",category);
        frag.setArguments(args);
        return frag;
    }

    private class AsyncDataLoading extends AsyncTask<String,Void,List<Item>> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            if (!isRefresh) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
            }
            super.onPreExecute();
        }

        @Override
        protected List<Item> doInBackground(String... category) {
            ParseClient parseClient = new ParseClient();
            items = parseClient.queryItemsOnCategory(category[0]);
            return items;
        }

        @Override
        protected void onPostExecute(List<Item> items) {
            addAll(items);
            swipeContainer.setRefreshing(false);
            isRefresh = false;
            if (items.size() == 0) {
                Glide.with(getContext()).load(R.drawable.placeholder_transparent).into(ivNoItems);
            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            super.onPostExecute(items);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog_list, container, false);
        ButterKnife.bind(this,view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            isViewCreated = true;
            if (isSeen && !isLoaded)
                populate(getArguments().getString("category"));
            //if swipe container exists, must setOnRefreshListener here, not onCreateView or onCreate
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    isRefresh = true;
                    populate(getArguments().getString("category"));
                }
            });
            swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
        } else {
            addAll(items);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            isSeen = true;
        if (isViewCreated && !isLoaded)
            populate(getArguments().getString("category"));
    }

    public void populate(String category) {
        isLoaded = true;
        new AsyncDataLoading().execute(category);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvItems.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        } else {
            rvItems.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        }
    }

}
