package com.radiusagent.interview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radiusagent.interview.databinding.FacilitiesItemBinding;
import com.radiusagent.interview.model.FacilitiesItem;
import com.radiusagent.interview.model.FacilitiesResponse;

/**
 * Created by Charles Raj I on 30/06/23.
 *
 * @author Charles Raj I
 */
public class FacilityRecyclerView extends RecyclerView.Adapter<FacilityRecyclerView.FacilityViewHolder> {

    private Activity activity;
    private FacilitiesResponse facilitiesResponse;
    private SelectItemInterface selectItemInterface;
    public FacilityRecyclerView(Activity activity, FacilitiesResponse facilitiesResponse, SelectItemInterface selectItemInterface) {
        this.activity = activity;
        this.facilitiesResponse = facilitiesResponse;
        this.selectItemInterface =selectItemInterface;
    }

    @NonNull
    @Override
    public FacilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FacilitiesItemBinding facilitiesItemBinding = DataBindingUtil.inflate(LayoutInflater.from(activity),R.layout.facilities_item,parent,false);
        return new FacilityViewHolder(facilitiesItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityViewHolder holder, int position) {
        FacilitiesItem facilityItem = facilitiesResponse.getFacilities().get(position);
        holder.onBind(activity,facilityItem,selectItemInterface);
    }

    @Override
    public int getItemCount() {
        return facilitiesResponse.getFacilities().size();
    }

    public static class FacilityViewHolder extends RecyclerView.ViewHolder {

        FacilitiesItemBinding itemView;
        public FacilityViewHolder(@NonNull FacilitiesItemBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }

        public void onBind(Activity activity, FacilitiesItem facilityItem, SelectItemInterface selectItemInterface){
            itemView.setFacilitate(facilityItem);
            OptionRecyclerView optionRecyclerView = new OptionRecyclerView(activity,facilityItem.getOptions(),selectItemInterface,facilityItem.getFacilityId());
            itemView.optionRecyclerList.setLayoutManager(new LinearLayoutManager(activity));
            itemView.optionRecyclerList.setAdapter(optionRecyclerView);
        }

    }
}
