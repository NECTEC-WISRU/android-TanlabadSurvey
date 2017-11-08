/*
 * Copyright (c) 2016 NECTEC
 *   National Electronics and Computer Technology Center, Thailand
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tanrabad.survey.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.tanrabad.survey.R;
import org.tanrabad.survey.domain.geographic.EllipsoidDistance;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.repository.BrokerPlaceSubTypeRepository;
import th.or.nectec.thai.widget.address.AddressPicker;

public class NearbyPlaceAdapter extends RecyclerView.Adapter<NearbyPlaceAdapter.ViewHolder>
        implements ListViewAdapter<Place> {

    private Context context;

    private List<Place> places = new ArrayList<>();
    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemLongClickListener onItemLongClickListener;
    private Location location;
    private EllipsoidDistance ellipsoidDistance = new EllipsoidDistance();
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public NearbyPlaceAdapter(Context context) {
        this.context = context;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override public void updateData(List<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    @Override public void clearData() {
        this.places = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override public Place getItem(int position) {
        return places.get(position);
    }

    @Override public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_place_nearby, parent, false);
        return new ViewHolder(v, this);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        Place place = places.get(position);
        holder.placeTextView.setText(place.getName());
        holder.placeSubtypeTextView.setText(
                BrokerPlaceSubTypeRepository.getInstance().findById(place.getSubType()).getName());
        holder.placeAddressTextView.setAddressCode(place.getSubdistrictCode());
        holder.placeIcon.setImageResource(PlaceIconMapping.getPlaceIcon(place));
        holder.placeHaveLocationIcon.setVisibility(place.getLocation() == null ? View.GONE : View.VISIBLE);
        if (location != null && place.getLocation() != null) {
            holder.placeDistanceTextView.setVisibility(View.VISIBLE);
            holder.placeDistanceTextView.setText(String.format("%s กม.",
                    decimalFormat.format(ellipsoidDistance.calculate(location, place.getLocation()))));
        } else {
            holder.placeDistanceTextView.setVisibility(View.GONE);
        }
    }

    @Override public long getItemId(int i) {
        return i;
    }

    @Override public int getItemCount() {
        return places.size();
    }

    private void onItemHolderClick(ViewHolder itemHolder) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(null, itemHolder.itemView, itemHolder.getAdapterPosition(),
                    itemHolder.getItemId());
        }
    }

    private void onItemHolderLongClick(ViewHolder itemHolder) {
        if (onItemLongClickListener != null) {
            onItemLongClickListener.onItemLongClick(null, itemHolder.itemView, itemHolder.getAdapterPosition(),
                    itemHolder.getItemId());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView placeTextView;
        private TextView placeSubtypeTextView;
        private TextView placeDistanceTextView;
        private AddressPicker placeAddressTextView;
        private ImageView placeIcon;
        private ImageView placeHaveLocationIcon;
        private NearbyPlaceAdapter adapter;

        public ViewHolder(View itemView, NearbyPlaceAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            placeTextView = (TextView) itemView.findViewById(R.id.place_name);
            placeSubtypeTextView = (TextView) itemView.findViewById(R.id.place_subtype);
            placeDistanceTextView = (TextView) itemView.findViewById(R.id.place_distance);
            placeAddressTextView = (AddressPicker) itemView.findViewById(R.id.place_address);
            placeIcon = (ImageView) itemView.findViewById(R.id.place_icon);
            placeHaveLocationIcon = (ImageView) itemView.findViewById(R.id.place_have_location_icon);
        }

        @Override public void onClick(View view) {
            adapter.onItemHolderClick(this);
        }

        @Override public boolean onLongClick(View view) {
            adapter.onItemHolderLongClick(this);
            return true;
        }
    }
}
