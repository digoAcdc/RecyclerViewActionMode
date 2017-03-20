package br.com.heiderlopes.recyclerviewactionmode.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.heiderlopes.recyclerviewactionmode.R;

/**
 * Created by heider on 10/03/17.
 */

public class DemoViewHolder extends RecyclerView.ViewHolder {


    public TextView title, sub_title;


    public DemoViewHolder(View view) {
        super(view);


        this.title = (TextView) view.findViewById(R.id.title);
        this.sub_title = (TextView) view.findViewById(R.id.sub_title);

    }
}