package com.radiusagent.interview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.radiusagent.interview.databinding.OptionItemBinding;
import com.radiusagent.interview.model.OptionsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Charles Raj I on 30/06/23.
 *
 * @author Charles Raj I
 */
public class OptionRecyclerView extends RecyclerView.Adapter<OptionRecyclerView.OptionViewHolder>{

    private Activity activity;
    private List<OptionsItem> options;
    private SelectItemInterface selectItemInterface;
    private String facilityId;
    public OptionRecyclerView(Activity activity, List<OptionsItem> options, SelectItemInterface selectItemInterface, String facilityId) {
        this.activity = activity;
        this.options = options;
        this.selectItemInterface = selectItemInterface;
        this.facilityId = facilityId;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OptionItemBinding optionItemBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.option_item,parent,false);
        return new OptionViewHolder(optionItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        OptionsItem optionsItem = options.get(position);
        holder.onBind(optionsItem, selectItemInterface,facilityId);
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public static class OptionViewHolder extends RecyclerView.ViewHolder{
        private final OptionItemBinding itemView;
        public OptionViewHolder(@NonNull OptionItemBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }

        private void onBind(OptionsItem optionsItem, SelectItemInterface selectItemInterface, String facilityId){
            itemView.setOptionModel(optionsItem);
            Picasso. get().load(getAptIcon(optionsItem.getIcon())).into(itemView.optionIcon);
            itemView.base.setOnClickListener(view -> {
                optionsItem.setFacilitiesId(facilityId);
                selectItemInterface.onSelect(optionsItem);
            });
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
