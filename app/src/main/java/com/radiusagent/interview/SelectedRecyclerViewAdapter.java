package com.radiusagent.interview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.radiusagent.interview.databinding.SelectedItemBinding;
import com.radiusagent.interview.model.OptionsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Charles Raj I on 30/06/23.
 *
 * @author Charles Raj I
 */
public class SelectedRecyclerViewAdapter extends RecyclerView.Adapter<SelectedRecyclerViewAdapter.SelectedViewHolder>{

    private Activity activity;
    private List<OptionsItem> optionsItems;

    public void setOptionsItems(List<OptionsItem> optionsItems) {
        this.optionsItems = optionsItems;
        notifyDataSetChanged();
    }

    public void addItem(OptionsItem optionsItem){
        if (optionsItems.contains(optionsItem)){

        }else {
            optionsItems.add(optionsItem);
        }
        notifyDataSetChanged();
    }

    public SelectedRecyclerViewAdapter(Activity activity, List<OptionsItem> optionsItems) {
        this.activity = activity;
        this.optionsItems = optionsItems;
    }

    @NonNull
    @Override
    public SelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SelectedItemBinding selectedItemBinding = DataBindingUtil.inflate(LayoutInflater.from(activity),R.layout.selected_item,parent,false);
        return new SelectedViewHolder(selectedItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedViewHolder holder, int position) {
        OptionsItem optionsItem = optionsItems.get(position);
        holder.onBind(optionsItem);
    }

    @Override
    public int getItemCount() {
        return optionsItems.size();
    }

    public static class SelectedViewHolder extends RecyclerView.ViewHolder{
        private SelectedItemBinding itemView;
        public SelectedViewHolder(@NonNull SelectedItemBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }

        private void onBind(OptionsItem optionsItem){
            itemView.setOptionModel(optionsItem);
            Picasso. get().load(getAptIcon(optionsItem.getIcon())).into(itemView.optionIcon);
        }

        private int getAptIcon(String icon){
            switch (icon){
                case "apartment": return R.drawable.apartment;
                case "boat": return R.drawable.boat;
                case "condo": return R.drawable.condo;
                case "garage": return R.drawable.garage;
                case "garden": return R.drawable.garden;
                case "land": return R.drawable.land;
                case "no-room": return R.drawable.no_room;
                case "rooms": return R.drawable.rooms;
                case "swimming": return R.drawable.swimming;
                default: return R.drawable.swimming;
            }
        }
    }
}
