package rjt.example.com.tmrealestate.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ramotion.foldingcell.FoldingCell;

import java.io.IOException;
import java.util.List;

import rjt.example.com.tmrealestate.R;
import rjt.example.com.tmrealestate.adapters.Property;
import rjt.example.com.tmrealestate.volley.VolleyAppController;

/**
 * Created by shutenmei on 2016/12/8.
 */
@SuppressLint("ValidFragment")
public class SinglePropertyFragment extends Fragment {
    private static final String TAG=SinglePropertyFragment.class.getSimpleName();
    Property mProperty;
    Context mContext;
    MapView mMapView;
    GoogleMap googleMap;
    GoogleApiClient mGoogleApiClient;
    ImageLoader mImageLoader;
    private double longitude;
    private double latitude;
    Geocoder mGeocoder;
    OnSinglePropertyFragmentInteracted mCallBackSingleProp;

    public SinglePropertyFragment(Property property){
        mProperty=property;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
        mGeocoder=new Geocoder(context);
        try{
            List<Address> destBuffer=mGeocoder.getFromLocationName("3128 Euclid Ave, Berwyn, IL 60402",1);
            latitude=destBuffer.get(0).getLatitude();
            longitude=destBuffer.get(0).getLongitude();
        }catch (IOException e){
            e.printStackTrace();
        }
        Log.d(TAG, latitude+longitude+"");
        mImageLoader= VolleyAppController.getmInstance().getmImageLoader();
        mCallBackSingleProp=(OnSinglePropertyFragmentInteracted)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view_single_property=inflater.inflate(R.layout.fragment_single_property,container,false);
        init(view_single_property);
        mMapView=(MapView)view_single_property.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        if(mMapView!=null){
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.house_blue_102))
                    .position(new LatLng(latitude,longitude))
                    .draggable(true)
                    .title("Current Location"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));

                }
            });
        }
        return view_single_property;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMapView!=null){
            try {
                mMapView.onDestroy();
            }catch (NullPointerException e){
                Log.d(TAG, "Error while attempting MapView.onDestroy(), ignoring exception", e);
            }
        }
    }

    @Override
    public void onLowMemory() {
        if(mMapView!=null){
            mMapView.onLowMemory();
        }
        super.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mMapView!=null){
            mMapView.onSaveInstanceState(outState);
        }
    }

    public void init(View view){
        TextView text_p_price,text_p_address, text_p_bed, text_p_bath, text_p_car, text_prov_name1, text_prov_mobile1, text_prov_name2, text_prov_mobile2;
        NetworkImageView image_prov_prof1, image_prov_prof2;

        image_prov_prof1=(NetworkImageView)view.findViewById(R.id.image_relevant_profile1);
        image_prov_prof2=(NetworkImageView)view.findViewById(R.id.image_relevant_profile2);

        image_prov_prof1.setImageUrl(mProperty.getProv_image(),mImageLoader);
        image_prov_prof2.setImageUrl(mProperty.getProv_image(),mImageLoader);
        text_p_price=(TextView)view.findViewById(R.id.text_fsp_property_price);
        text_p_address=(TextView)view.findViewById(R.id.text_fsp_property_address);
        text_p_bath=(TextView)view.findViewById(R.id.text_fsp_property_baths);
        text_p_bed=(TextView)view.findViewById(R.id.text_fsp_property_beds);
        text_p_car=(TextView)view.findViewById(R.id.text_fsp_property_cars);
        text_prov_name1 = (TextView) view.findViewById(R.id.text_relevant_p_name1);
        text_prov_name2 = (TextView) view.findViewById(R.id.text_relevant_p_name2);
        text_prov_mobile1 = (TextView) view.findViewById(R.id.text_relevant_p_mobile1);
        text_prov_mobile2 = (TextView) view.findViewById(R.id.text_relevant_p_mobile2);

        if(!mProperty.getP_on_rent().equals("1")){
            if(mProperty.getP_morgage().equals("0"))
                text_p_price.setText("$" + mProperty.getP_price());
            else
                text_p_price.setText("$" + mProperty.getP_price() + "    -    " + "Morgage: $" + mProperty.getP_morgage() + "/mon");
        }
        else{
            text_p_price.setText("$" + mProperty.getP_price() + "/mon");
        }

        text_p_address.setText(mProperty.getP_address());
        text_p_bed.setText("Beds" + ":" + mProperty.getP_num_bedroom());
        text_p_bath.setText("Baths" + ":" + mProperty.getP_num_bath());
        text_p_car.setText("Car Allown"+ ":" + mProperty.getP_num_car_allow());
        text_prov_name1.setText(mProperty.getProv_name());
        text_prov_name2.setText(mProperty.getProv_name());
        text_prov_mobile1.setText(mProperty.getProv_mobile());
        text_prov_mobile2.setText(mProperty.getProv_mobile());

        final FoldingCell fc=(FoldingCell)view.findViewById(R.id.folding_cell);
        fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc.toggle(false);
            }
        });

        final Button foldBtn=(Button)view.findViewById(R.id.button_contact_now);
        foldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBackSingleProp.callRelevantPerson(mProperty.getProv_mobile());
            }
        });
        final Button toastBtn=(Button)view.findViewById(R.id.button_send_message);
        toastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
                LayoutInflater inflater=getActivity().getLayoutInflater();
                alertDialog.setView(inflater.inflate(R.layout.dialog_compose_sms,null))
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();

            }
        });
    }
    public interface OnSinglePropertyFragmentInteracted{
        void callRelevantPerson(String mobile_no);
        void textToRelevantPerson(String prov_mobile);
    }


}
