package com.radiusagent.interview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.radiusagent.interview.databinding.ActivityMainBinding;
import com.radiusagent.interview.model.ExclusionsItemItem;
import com.radiusagent.interview.model.FacilitiesResponse;
import com.radiusagent.interview.model.OptionsItem;
import com.radiusagent.interview.service.DisposableManager;
import com.radiusagent.interview.service.RetrofitClient;
import com.radiusagent.interview.service.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SelectItemInterface{

    private RetrofitInterface service;
    private List<OptionsItem> optionsItems = new ArrayList<>();
    private ActivityMainBinding activityMainBinding;
    private SelectedRecyclerViewAdapter selectedRecyclerViewAdapter;
    private FacilitiesResponse facilitiesResponseItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        service = RetrofitClient.getClient();

        try {
            DisposableManager.add(service.getFacilities()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(facilitiesResponse -> {
                        facilitiesResponseItem = facilitiesResponse;
                        FacilityRecyclerView facilityAdapter = new FacilityRecyclerView(this,facilitiesResponse, (SelectItemInterface)this);
                        activityMainBinding.facilityRecyclerview.setLayoutManager(new LinearLayoutManager(this));
                        activityMainBinding.facilityRecyclerview.setAdapter(facilityAdapter);

                        if (facilitiesResponse != null){
                            Log.d("TAG", "onCreate: " + facilitiesResponse.toString());
                        }
                    }));
        }catch (Exception e){
            Log.d("TAG", "onCreate: " + e.getMessage());
        }

        activityMainBinding.resetAll.setOnClickListener(view -> {
            optionsItems.clear();
            selectedRecyclerViewAdapter.setOptionsItems(optionsItems);
        });

    }

    @Override
    public void onSelect(OptionsItem optionsItem) {
        if (selectedRecyclerViewAdapter == null) {
            optionsItems.add(optionsItem);
            selectedRecyclerViewAdapter = new SelectedRecyclerViewAdapter(this, optionsItems);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
            activityMainBinding.selectedRecyclerView.setLayoutManager(linearLayoutManager);
            activityMainBinding.selectedRecyclerView.setAdapter(selectedRecyclerViewAdapter);
        }else {

            facilitiesResponseItem.getFacilities().forEach(facilitiesItem -> {
                if (facilitiesItem.getFacilityId().equals(optionsItem.getFacilitiesId())){
                    facilitiesItem.getOptions().forEach(optionsItem1 -> {
                        if (optionsItems.contains(optionsItem1) && checkEval(optionsItem)){
                            optionsItems.remove(optionsItem1);
                            optionsItems.add(optionsItem);
                            selectedRecyclerViewAdapter.setOptionsItems(optionsItems);
                        }
                    });
                }
            });
            if (checkEval(optionsItem)) {
                selectedRecyclerViewAdapter.addItem(optionsItem);
            }
        }
    }


    private boolean checkEval(OptionsItem optionsItem){
        List<ExclusionsItemItem> exclusionsItemList = new ArrayList<>();
        AtomicBoolean result = new AtomicBoolean(true);
        activityMainBinding.errorText.setVisibility(View.GONE);
        facilitiesResponseItem.getExclusions().forEach(exclusionsItemItems -> {
            exclusionsItemItems.forEach(exclusionsItemItem -> {
                if (exclusionsItemItem.getFacilityId().equals(optionsItem.getFacilitiesId())
                && exclusionsItemItem.getOptionsId().equals(optionsItem.getId())){
                    exclusionsItemList.addAll(exclusionsItemItems);
                    return;
                }
            });
        });

        exclusionsItemList.forEach(exclusionsItemItem -> {
            if (!optionsItem.getId().equals(exclusionsItemItem.getOptionsId())){
                optionsItems.forEach(optionsItem1 -> {
                    if (optionsItem1.getId().equals(exclusionsItemItem.getOptionsId())) {
                        result.set(false);
                        activityMainBinding.errorText.setVisibility(View.VISIBLE);
                        activityMainBinding.errorText.setText("You can not add " + optionsItem.getName() + " with " + optionsItem1.getName());
                    }
                });
            }
        });
        return result.get();
    }
}