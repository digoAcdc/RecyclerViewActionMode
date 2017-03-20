package br.com.heiderlopes.recyclerviewactionmode;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import br.com.heiderlopes.recyclerviewactionmode.adapter.RecyclerViewAdapter;
import br.com.heiderlopes.recyclerviewactionmode.listener.RecyclerClickListener;
import br.com.heiderlopes.recyclerviewactionmode.listener.RecyclerTouchListener;
import br.com.heiderlopes.recyclerviewactionmode.model.ItemModel;

public class MainActivity extends AppCompatActivity implements ActionMode.Callback, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private List<ItemModel> item_models;
    private RecyclerViewAdapter adapter;
    private ActionMode mActionMode;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateRecyclerView();
        implementRecyclerViewClickListeners();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void populateRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        item_models = new ArrayList<>();
        for (int i = 1; i <= 40; i++)
            item_models.add(new ItemModel("Title " + i, "Sub Title " + i));

        adapter = new RecyclerViewAdapter(this, item_models);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //Implement item click and long click over recycler view
    private void implementRecyclerViewClickListeners() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                //If ActionMode not null select item
                if (mActionMode != null)
                    onListItemSelect(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                //Select item on long click
                onListItemSelect(position);
            }
        }));
    }

    private void onListItemSelect(int position) {
        adapter.toggleSelection(position);//Toggle the selection
        boolean hasCheckedItems = adapter.getSelectedCount() > 0;
        //Check if any items are already selected or not
        if (hasCheckedItems && mActionMode == null)
            // there are some selected items, start the actionMode
            mActionMode = startSupportActionMode(this);
        else if (!hasCheckedItems && mActionMode != null)
            // there no selected items, finish the actionMode
            mActionMode.finish();

        if (mActionMode != null)
            //set action mode title on item selection
            mActionMode.setTitle(String.valueOf(adapter
                    .getSelectedCount()) + " selected");


    }

    //Set action mode null after use
    public void setNullToActionMode() {
        if (mActionMode != null)
            mActionMode = null;
    }

    public void deleteRows() {
        SparseBooleanArray selected = adapter
                .getSelectedIds();//Get selected ids

        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
                item_models.remove(selected.keyAt(i));
                adapter.notifyDataSetChanged();//notify adapter

            }
        }
        Toast.makeText(this, selected.size() + " item deleted.", Toast.LENGTH_SHORT).show();//Show Toast
        mActionMode.finish();//Finish action mode after use
    }

    private void copyRows() {
        SparseBooleanArray selected;
        selected = adapter.getSelectedIds();

        int selectedMessageSize = selected.size();

        for (int i = (selectedMessageSize - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                ItemModel model = item_models.get(selected.keyAt(i));
                String title = model.getTitle();
                String subTitle = model.getSubTitle();
                Log.e("Selected Items", "Title - " + title + "\n" + "Sub Title - " + subTitle);

            }
        }
        Toast.makeText(this, "You selected Copy menu.", Toast.LENGTH_SHORT).show();//Show toast
        mActionMode.finish();
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        /*if (Build.VERSION.SDK_INT < 11) {
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_delete), MenuItemCompat.SHOW_AS_ACTION_NEVER);
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_copy), MenuItemCompat.SHOW_AS_ACTION_NEVER);
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_forward), MenuItemCompat.SHOW_AS_ACTION_NEVER);
        } else {
            menu.findItem(R.id.action_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.findItem(R.id.action_copy).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.findItem(R.id.action_forward).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } */
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteRows();
                break;

            case R.id.action_copy:
                copyRows();
                break;

            case R.id.action_forward:
                Toast.makeText(this, "You selected Forward menu.", Toast.LENGTH_SHORT).show();//Show toast
                mode.finish();
                break;
        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        adapter.removeSelection();
        setNullToActionMode();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        refreshList();
    }

    private void refreshList() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable(){
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }}, 2000);
    }
}
