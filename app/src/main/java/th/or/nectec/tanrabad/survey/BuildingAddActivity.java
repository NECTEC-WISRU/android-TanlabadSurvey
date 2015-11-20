package th.or.nectec.tanrabad.survey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.UUID;

import th.or.nectec.tanrabad.domain.BuildingSavePresenter;
import th.or.nectec.tanrabad.domain.BuildingSaver;
import th.or.nectec.tanrabad.domain.PlaceController;
import th.or.nectec.tanrabad.domain.PlacePresenter;
import th.or.nectec.tanrabad.entity.Building;
import th.or.nectec.tanrabad.entity.Location;
import th.or.nectec.tanrabad.entity.Place;
import th.or.nectec.tanrabad.survey.maps.LiteMapFragment;
import th.or.nectec.tanrabad.survey.repository.InMemoryBuildingRepository;
import th.or.nectec.tanrabad.survey.repository.StubPlaceRepository;
import th.or.nectec.tanrabad.survey.utils.alert.Alert;
import th.or.nectec.tanrabad.survey.validator.SaveBuildingValidator;

public class BuildingAddActivity extends TanrabadActivity implements PlacePresenter, BuildingSavePresenter, View.OnClickListener {

    public static final String PLACE_UUID_ARG = "place_uuid_arg";
    public static final int MARK_LOCATION_REQUEST_CODE = 50000;
    private TextView placeName;
    private Toolbar toolbar;
    private TextView buildingNameTitle;
    private EditText buildingNameView;
    private FrameLayout addLocationBackground;
    private Button addMarkerButton;
    private LatLng buildingLocation;

    private PlaceController placeController = new PlaceController(new StubPlaceRepository(), this);
    private BuildingSaver buildingSaver = new BuildingSaver(InMemoryBuildingRepository.getInstance(), new SaveBuildingValidator(), this);

    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_add);
        assignViews();

        setSupportActionBar(toolbar);
        placeController.showPlace(UUID.fromString(getPlaceUUID()));
        setupPreviewMap();
    }

    private void setupPreviewMap() {
        SupportMapFragment supportMapFragment = LiteMapFragment.setupLiteMapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.map_container, supportMapFragment).commit();
    }

    private String getPlaceUUID() {
        return getIntent().getStringExtra(PLACE_UUID_ARG);
    }

    private void assignViews() {
        placeName = (TextView) findViewById(R.id.place_name);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        buildingNameTitle = (TextView) findViewById(R.id.building_name_title);
        buildingNameView = (EditText) findViewById(R.id.building_name);
        addLocationBackground = (FrameLayout) findViewById(R.id.add_location_background);
        addMarkerButton = (Button) findViewById(R.id.button);
        addMarkerButton.setOnClickListener(this);
    }

    @Override
    public void displayPlace(Place place) {
        this.place = place;
        placeName.setText(place.getName());
        if(place.getType()==Place.TYPE_VILLAGE_COMMUNITY){
            buildingNameTitle.setText(R.string.house_no);
        }else{
            buildingNameTitle.setText(R.string.building_name);
        }
    }

    @Override
    public void alertPlaceNotFound() {
        Alert.highLevel().show(R.string.place_not_found);
    }

    @Override
    public void onClick(View view) {
        openMapMarkerActivity();
    }

    private void openMapMarkerActivity() {
        Intent intent = new Intent(BuildingAddActivity.this, MapMarkerActivity.class);
        startActivityForResult(intent, MARK_LOCATION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MARK_LOCATION_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    setupPreviewMapWithPosition(data.<LatLng>getParcelableExtra(MapMarkerActivity.MAP_LOCATION));
                }
        }
    }

    private void setupPreviewMapWithPosition(LatLng latLng) {
        addLocationBackground.setVisibility(View.GONE);
        buildingLocation = latLng;
        SupportMapFragment supportMapFragment = LiteMapFragment.setupLiteMapFragmentWithPosition(latLng);
        getSupportFragmentManager().beginTransaction().replace(R.id.map_container, supportMapFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveBuildingData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveBuildingData() {
        String buildingName = buildingNameView.getText().toString();
        Building building = Building.withName(buildingName);
        building.setPlace(place);
        Location location = buildingLocation == null
                ? null : new Location(buildingLocation.latitude, buildingLocation.longitude);
        building.setLocation(location);
        buildingSaver.save(building);
    }

    @Override
    public void displaySaveSuccess() {
        Alert.lowLevel().show(R.string.save_success);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void displaySaveFail() {
        Alert.highLevel().show(R.string.save_fail);
    }
}
