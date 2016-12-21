package rjt.example.com.tmrealestate.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import rjt.example.com.tmrealestate.R;
import rjt.example.com.tmrealestate.adapters.DisplayPropertyAdapter;
import rjt.example.com.tmrealestate.adapters.Property;
import rjt.example.com.tmrealestate.volley.VolleyAppController;

/**
 * Created by shutenmei on 2016/12/7.
 */
public class DisplayPropertyFragment extends Fragment {
    private static final String TAG=DisplayPropertyFragment.class.getSimpleName();
    Context mContext;
    ArrayList<Property> mPropertyList;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    DisplayPropertyAdapter mAdapter;
    OnPropertyItemClick mCallbackProperty;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
        mPropertyList=new ArrayList<Property>();
        mCallbackProperty= (OnPropertyItemClick) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view_custom_property_fragment=inflater.inflate(R.layout.fragment_property,container,false);
        mRecyclerView=(RecyclerView)view_custom_property_fragment.findViewById(R.id.recycler_view_property);
        mLayoutManager=new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new DisplayPropertyAdapter(mContext,mPropertyList);
        getProperties();
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new DisplayPropertyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mCallbackProperty.proceedToProperty(mPropertyList.get(position));
            }
        });
        return view_custom_property_fragment;
    }

    void getProperties(){
        final String p_request_tag="p_request_tag_j1";
        String p_url="http://mertvurgun.x10host.com/realestate_responser.php";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, p_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonArray=response.getJSONArray("properties");
                    for(int i=0;i<jsonArray.length();i++){
                        Property property=new Property();
                        JSONObject itemProperty= (JSONObject)jsonArray.get(i);
                        property.setProperty_id(itemProperty.getString("property_id"));
                        property.setP_address(itemProperty.getString("property_address"));
                        property.setP_image(itemProperty.getString("property_image"));
                        property.setP_type(itemProperty.getString("property_type"));
                        property.setP_num_bedroom(itemProperty.getString("number_of_bedroom"));
                        property.setP_num_bath(itemProperty.getString("number_of_bath"));
                        property.setP_num_car_allow(itemProperty.getString("number_of_car_allowance"));
                        property.setP_on_rent(itemProperty.getString("on_sale"));
                        property.setP_on_rent(itemProperty.getString("on_rent"));
                        property.setP_price(itemProperty.getString("property_price"));
                        property.setP_morgage(itemProperty.getString("morgage"));
                        property.setProv_name(itemProperty.getString("provider_name"));
                        property.setProv_mobile(itemProperty.getString("provider_mobile"));
                        property.setProv_image(itemProperty.getString("provider_image"));

                        mPropertyList.add(property);
                    }
                    mRecyclerView.setAdapter(mAdapter);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error);
            }
        });

        VolleyAppController.getmInstance().addToRequestQueue(jsonObjectRequest,p_request_tag);
    }

    public interface OnPropertyItemClick{
        void proceedToProperty(Property property);
    }
}
