package br.com.heiderlopes.recyclerviewactionmode.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;
import br.com.heiderlopes.recyclerviewactionmode.R;
import br.com.heiderlopes.recyclerviewactionmode.holder.DemoViewHolder;
import br.com.heiderlopes.recyclerviewactionmode.model.ItemModel;

/**
 * Created by heider on 10/03/17.
 */

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<DemoViewHolder> {

    private List<ItemModel> arrayList;
    private Context context;
    private SparseBooleanArray mSelectedItemsIds;

    public RecyclerViewAdapter(Context context,
                                List<ItemModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        mSelectedItemsIds = new SparseBooleanArray();

    }


    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(DemoViewHolder holder,
                                 int position) {

        //Setting text over text view
        holder.title.setText(arrayList.get(position).getTitle());
        holder.sub_title.setText(arrayList.get(position).getSubTitle());

        holder.itemView
                .setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4
                        : Color.TRANSPARENT);


    }

    @Override
    public DemoViewHolder onCreateViewHolder(
            ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item_row, viewGroup, false);
        return new DemoViewHolder(mainGroup);

    }

    //Toggle selection methods
    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }


    //Remove selected selections
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }


    //Put or delete selected position into SparseBooleanArray
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    //Get total selected count
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    //Return all selected ids
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }


}