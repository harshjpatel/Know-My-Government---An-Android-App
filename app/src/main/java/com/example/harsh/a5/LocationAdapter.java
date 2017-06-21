package com.example.harsh.a5;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by harsh on 3/10/17.
 */
public class LocationAdapter extends RecyclerView.Adapter<MyViewHolder>
{
    private static final String TAG = "LocationAdapter";
    private List<Location> locationList;
    MainActivity mainActivity;

    public LocationAdapter(List<Location> locationList, MainActivity ma)
    {
        this.locationList = locationList;
        mainActivity = ma;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        view.setOnClickListener(mainActivity);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Location location = locationList.get(position);
        holder.positionType.setText(location.getPositiontype());
        //Log.d(TAG, location.getName().toString());
        {
            if(location.getPartytype().equals("Unknown") || location.getPartytype().equals(""))
            {
                holder.name.setText(location.getPositionname());
            }
            else
                holder.name.setText(location.getPositionname()+"("+location.getPartytype()+")");
        }
        if(position==getItemCount()) {//check if this is the last child, if yes then hide the divider
            holder.divider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount()
    {
        return locationList.size();
    }
}
