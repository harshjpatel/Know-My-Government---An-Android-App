package com.example.harsh.a5;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by harsh on 3/26/17.
 */

class MyViewHolder extends RecyclerView.ViewHolder
{
    public TextView positionType;
    public TextView name;
    public ImageView divider;
    public MyViewHolder(View view)
    {
        super(view);
        positionType = (TextView) view.findViewById(R.id.positionType);
        name = (TextView) view.findViewById(R.id.name);
        divider = (ImageView) view.findViewById(R.id.divider);
    }
}
