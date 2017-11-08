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

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import java.util.List;
import org.tanrabad.survey.R;
import org.tanrabad.survey.TanrabadApp;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.nearby.ImpLocationBoundary;
import org.tanrabad.survey.nearby.ImpMergeAndSortNearbyPlaces;
import org.tanrabad.survey.nearby.ImpNearbyPlacesWithLocation;
import org.tanrabad.survey.nearby.ImpNearbyPlacesWithoutLocation;
import org.tanrabad.survey.nearby.MergeAndSortNearbyPlaces;
import org.tanrabad.survey.nearby.NearbyPlacePresenter;
import org.tanrabad.survey.nearby.NearbyPlacesFinderController;
import org.tanrabad.survey.nearby.NearbyPlacesWithLocation;
import org.tanrabad.survey.nearby.NearbyPlacesWithoutLocation;
import org.tanrabad.survey.presenter.view.EmptyLayoutView;
import org.tanrabad.survey.repository.BrokerPlaceRepository;
import org.tanrabad.survey.utils.GpsUtils;
import org.tanrabad.survey.utils.PlayLocationService;
import org.tanrabad.survey.utils.prompt.AlertDialogPromptMessage;
import org.tanrabad.survey.utils.prompt.PromptMessage;

public class PlaceNearbyListFragment extends Fragment
        implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener,
    NearbyPlacePresenter {

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 888;
    private NearbyPlaceAdapter nearbyPlaceAdapter;

    private TextView placeCountView;
    private RecyclerView placeListView;
    private RecyclerViewHeader placeListHeader;
    private EmptyLayoutView emptyPlacesView;

    PlayLocationService playLocationService = PlayLocationService.getInstance();
    private NearbyPlacesFinderController nearbyPlacesFinderController;

    GoogleApiClient.ConnectionCallbacks locationServiceCallback = new GoogleApiClient.ConnectionCallbacks() {
        @Override public void onConnected(@Nullable Bundle bundle) {
            playLocationService.setupLocationUpdateService(new LocationListener() {
                @Override public void onLocationChanged(android.location.Location location) {
                    loadPlaceList(location);
                }
            });
        }

        @Override public void onConnectionSuspended(int i) {

        }
    };
    private Location currentLocation;

    @Override public void onStart() {
        super.onStart();
        playLocationService.connect();
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        playLocationService.connect();
    }

    @Override public void onDetach() {
        super.onDetach();
        playLocationService.removeConnectionCallbacks(locationServiceCallback);
        playLocationService.disconnect();
    }

    public static PlaceNearbyListFragment newInstance() {
        PlaceNearbyListFragment fragment = new PlaceNearbyListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void displayPlaceNotFound() {
        nearbyPlaceAdapter.clearData();
        placeCountView.setVisibility(View.GONE);
        emptyPlacesView.showEmptyLayout();
    }

    @Override public void displayNearbyPlaces(List<Place> places) {
        nearbyPlaceAdapter.setLocation(currentLocation);
        nearbyPlaceAdapter.updateData(places);
        placeCountView.setText(getString(R.string.format_place_count, places.size()));
        placeCountView.setVisibility(View.VISIBLE);
        emptyPlacesView.hide();
    }

    protected void loadPlaceList(android.location.Location location) {
        emptyPlacesView.showProgressBar();
        currentLocation = new Location(location.getLatitude(), location.getLongitude());
        nearbyPlacesFinderController.findNearbyPlaces(currentLocation);
    }

    @Override public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
        final Place placeData = nearbyPlaceAdapter.getItem(position);
        PromptMessage promptMessage = new AlertDialogPromptMessage(getActivity());
        promptMessage.setOnConfirm(getString(R.string.survey), new PromptMessage.OnConfirmListener() {
            @Override public void onConfirm() {
                TanrabadApp.action().startSurvey(placeData);
                SurveyBuildingHistoryActivity.open(getActivity(), placeData);
            }
        });
        promptMessage.setOnCancel(getString(R.string.cancel), null);
        promptMessage.show(getString(R.string.start_survey), nearbyPlaceAdapter.getItem(position).getName());
    }

    @Override public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
        return true;
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nearby_place_list, container, false);
        setupViews(view);
        setupEmptyList();
        setupPlaceList();
        setHasOptionsMenu(true);
        return view;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NearbyPlacesWithLocation nearbyPlacesWithLocation =
            new ImpNearbyPlacesWithLocation(BrokerPlaceRepository.getInstance().find(), new ImpLocationBoundary());
        NearbyPlacesWithoutLocation nearbyPlacesWithoutLocation =
            new ImpNearbyPlacesWithoutLocation(BrokerPlaceRepository.getInstance().find());
        MergeAndSortNearbyPlaces mergeAndSortNearbyPlaces = new ImpMergeAndSortNearbyPlaces();
        nearbyPlacesFinderController =
                new NearbyPlacesFinderController(nearbyPlacesWithLocation, nearbyPlacesWithoutLocation,
                        mergeAndSortNearbyPlaces, this);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            emptyPlacesView.setEmptyText(R.string.please_enable_location_permission_before_use);
            requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            startSearchNearbyPlaces();
        }
    }

    private void startSearchNearbyPlaces() {
        if (!GpsUtils.isGpsEnabled(getContext())) {
            emptyPlacesView.setEmptyText(R.string.please_enable_gps_before_use);
            displayPlaceNotFound();
            GpsUtils.showGpsSettingsDialog(getContext());
        }
        playLocationService.addConnectionCallbacks(locationServiceCallback);
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startSearchNearbyPlaces();
                } else {
                    displayPlaceNotFound();
                }
            }
        }
    }

    private void setupViews(View view) {
        this.placeListView = (RecyclerView) view.findViewById(R.id.place_list);
        this.placeCountView = (TextView) view.findViewById(R.id.place_count);
        placeListHeader = (RecyclerViewHeader) view.findViewById(R.id.card_header);
        emptyPlacesView = (EmptyLayoutView) view.findViewById(R.id.empty_layout);
    }

    private void setupEmptyList() {
        emptyPlacesView.setEmptyIcon(R.mipmap.ic_place);
        emptyPlacesView.setEmptyText(R.string.nearby_place_not_found);
        emptyPlacesView.setEmptyButtonVisibility(false);
    }

    private void setupPlaceList() {
        nearbyPlaceAdapter = new NearbyPlaceAdapter(getActivity());
        nearbyPlaceAdapter.setOnItemClickListener(this);
        nearbyPlaceAdapter.setOnItemLongClickListener(this);
        placeListView.setAdapter(nearbyPlaceAdapter);
        placeListView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        placeListView.setLayoutManager(linearLayoutManager);
        placeListHeader.attachTo(placeListView, true);
    }
}
