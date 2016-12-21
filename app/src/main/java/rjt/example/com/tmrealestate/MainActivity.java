package rjt.example.com.tmrealestate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import rjt.example.com.tmrealestate.adapters.Property;
import rjt.example.com.tmrealestate.fragments.DisplayPropertyFragment;
import rjt.example.com.tmrealestate.fragments.SinglePropertyFragment;

public class MainActivity extends AppCompatActivity implements DisplayPropertyFragment.OnPropertyItemClick, SinglePropertyFragment.OnSinglePropertyFragmentInteracted {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayPropertyFragment dpf = new DisplayPropertyFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_default, dpf);
        ft.commit();
    }

    @Override
    public void proceedToProperty(Property property) {
        SinglePropertyFragment spf = new SinglePropertyFragment(property);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_default, spf);
        ft.commit();
    }

    @Override
    public void callRelevantPerson(String mobile_no) {
        Log.d(TAG, "function called");
        try {
            Intent call_internal = new Intent(Intent.ACTION_CALL, Uri.parse("tel:00" + mobile_no));
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CALL_PHONE},0);
            }
            startActivity(call_internal);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Your call has failed...", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void textToRelevantPerson(String prov_mobile) {

    }

}
