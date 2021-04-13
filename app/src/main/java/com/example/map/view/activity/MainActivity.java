package com.example.map.view.activity;

import androidx.annotation.NonNull;

import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.map.R;
import com.example.map.model.Model_ReverseItem;
import com.example.map.model.Search;
import com.example.map.network.Api;
import com.example.map.view.adapter.AdapterSearch;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.map)
    MapView map;
    IMapController iMapController;
    GeoPoint geoPoint;
    Marker marker;
    Double latitude;
    View view;
    Double longitude;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text)
    TextView text;
    TextView house_number, road, hamlet, town,
            village, city, county, state_district, state, postcode, country, country_code;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.linear)
    LinearLayout linear;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.image)
    ImageView image;
    ImageView bottom_image;
    @SuppressLint("NonConstantResourceId")
    EditText search;
    RecyclerView recyclerview;
    AdapterSearch adapterSearch;
    Button close;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Context ctx = getApplicationContext().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        SelectMap();
        image.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.Animation_Design_BottomSheetDialog);
            view = LayoutInflater.from(MainActivity.this).inflate(R.layout.bottom_sheet, null);
            bottom_image = view.findViewById(R.id.bottom_image);
            search = view.findViewById(R.id.search);
            recyclerview = view.findViewById(R.id.recyclerview);
            bottom_image.setOnClickListener(view1 -> {
                String text = search.getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please write something", Toast.LENGTH_SHORT).show();
                } else {
                    Search(text, bottomSheetDialog);
                }
            });

            bottomSheetDialog.setContentView(view);
            bottomSheetDialog.show();
        });

    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    public void AlertDialog(Response<Model_ReverseItem> response) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        view = LayoutInflater.from(MainActivity.this).inflate(R.layout.alert_dialog, null, false);
        cast(view);
        assert response.body() != null;
        house_number.setText(response.body().address.getHouse_number() == null ? "house number = not available" : "house number = " + response.body().address.getHouse_number());
        road.setText(response.body().address.getRoad() == null ? "road = not available" : "road = " + response.body().address.getRoad());
        hamlet.setText(response.body().address.getHamlet() == null ? "hamlet = not available" : "hamlet = " + response.body().address.getHamlet());
        town.setText(response.body().address.getTown() == null ? "town = not available" : "town = " + response.body().address.getTown());
        village.setText(response.body().address.getVillage() == null ? "village = not available" : "village = " + response.body().address.getVillage());
        city.setText(response.body().address.getCity() == null ? "city = not available" : "city = " + response.body().address.getCity());
        county.setText(response.body().address.getCounty() == null ? "county = not available" : "county = " + response.body().address.getCounty());
        state_district.setText(response.body().address.getState_district() == null ? "state_district = not available" : "state_district = " + response.body().address.getState_district());
        state.setText(response.body().address.getState() == null ? "state = not available" : "state = " + response.body().address.getState());
        postcode.setText(response.body().address.getPostcode() == null ? "postcode = not available" : "postcode = " + response.body().address.getPostcode());
        country.setText(response.body().address.getCountry() == null ? "country = not available" : "country = " + response.body().address.getCountry());
        country_code.setText(response.body().address.getCountry_code() == null ? "country code = not available" : "country code = " + response.body().address.getCountry_code());
        alertDialog.setView(view);
        final AlertDialog show = alertDialog.show();
        close.setOnClickListener(view1 -> show.dismiss());

    }

    public void cast(View view) {

        house_number = view.findViewById(R.id.house_number);
        road = view.findViewById(R.id.road);
        hamlet = view.findViewById(R.id.hamlet);
        town = view.findViewById(R.id.town);
        village = view.findViewById(R.id.village);
        city = view.findViewById(R.id.city);
        county = view.findViewById(R.id.county);
        state_district = view.findViewById(R.id.state_district);
        state = view.findViewById(R.id.state);
        postcode = view.findViewById(R.id.postcode);
        country = view.findViewById(R.id.country);
        country_code = view.findViewById(R.id.country_code);
        search = view.findViewById(R.id.search);
        close = view.findViewById(R.id.close);

    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ClickableViewAccessibility"})
    public void SelectMap() {
        map.isTilesScaledToDpi();
        map.getTilesScaleFactor();
        map.setMultiTouchControls(true);
        map.invalidate();
        iMapController = map.getController();
        iMapController.setZoom(16.5);
        iMapController = map.getController();
        geoPoint = new GeoPoint(43.7502121, -79.4284489);
        iMapController.setCenter(geoPoint);
        map.setZoomRounding(false);
        marker = new Marker(map);
        marker.setIcon(getResources().getDrawable(R.drawable.ic_round_location_on_24));
        marker.setPosition(geoPoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(marker);
        marker.setInfoWindow(null);
        map.addMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                IGeoPoint IGeoPoint = map.getMapCenter();
                GeoPoint geoPoint = new GeoPoint(IGeoPoint.getLatitude(), IGeoPoint.getLongitude());
                MarkerChangePosition(geoPoint, marker);
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                return true;
            }
        });

        map.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    linear.setVisibility(View.GONE);
                    break;
                case MotionEvent.ACTION_UP:
                    linear.setVisibility(View.VISIBLE);
                    GeoPoint geoPoint = new GeoPoint(marker.getPosition().getLatitude(), marker.getPosition().getLongitude());
                    Get_Service(geoPoint);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    linear.setVisibility(View.VISIBLE);
                    break;
            }

            return false;
        });

    }

    public void Get_Service(GeoPoint geoPoint) {
        latitude = geoPoint.getLatitude();
        longitude = geoPoint.getLongitude();
        Api.Compation.invoke().reverse(latitude, longitude).enqueue(new Callback<Model_ReverseItem>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Model_ReverseItem> call, @NonNull Response<Model_ReverseItem> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    text.setText("Your select location : " + response.body().getDisplay_name());
                    marker.setOnMarkerClickListener((marker, mapView) -> {
                        AlertDialog(response);

                        return true;
                    });
                }

            }

            @Override
            public void onFailure(@NonNull Call<Model_ReverseItem> call, @NonNull Throwable t) {
            }
        });

    }

    public void Search(String q, BottomSheetDialog bottomSheetDialog) {
        Api.Compation.invoke().search(q).enqueue(new Callback<List<Search>>() {
            @Override
            public void onResponse(@NonNull Call<List<Search>> call, @NonNull Response<List<Search>> response) {
                if (response.isSuccessful()) {
                    adapterSearch = new AdapterSearch(response.body(), getApplicationContext(), (lat, lon) -> {
                        geoPoint = new GeoPoint(lat, lon);
                        iMapController.setCenter(geoPoint);
                        bottomSheetDialog.dismiss();

                    });
                    recyclerview.setAdapter(adapterSearch);

                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Search>> call, @NonNull Throwable t) {

            }
        });

    }

    public void MarkerChangePosition(GeoPoint geoPoint, Marker marker) {
        marker.setPosition(geoPoint);
    }
}