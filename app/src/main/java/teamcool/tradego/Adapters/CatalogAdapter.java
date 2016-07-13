package teamcool.tradego.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import teamcool.tradego.Models.Item;
import teamcool.tradego.R;

/**
 * Created by selinabing on 7/6/16.
 */
public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //butterknife binding goes here:
        //@BindView(R.id.someId) Type someId;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            //set item's onClickListener to (this);
        }
    }

    private List<Item> items;

    public CatalogAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View catalogView = inflater.inflate(R.layout.item_each, parent, false);
        ViewHolder viewHolder = new ViewHolder(catalogView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = items.get(position);
        //populate each item by setting its text and media
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<Item> newItems) {
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void insert(int index, Item item) {
        items.add(index,item);
        notifyItemInserted(index);
    }

}