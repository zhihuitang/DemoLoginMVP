package com.example.zhihuitang.demologinmvp;

import android.content.Context;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhihuitang.demologinmvp.Presenter.ILoginPresenter;
import com.example.zhihuitang.demologinmvp.Presenter.LoginPresenterCompl;
import com.example.zhihuitang.demologinmvp.view.ILoginView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements ILoginView, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.et_login_username)
    EditText editUser;
    @BindView(R.id.et_login_password)
    EditText editPass;
    @BindView(R.id.btn_login_login)
    Button btnLogin;
    @BindView(R.id.btn_login_clear)
    Button btnClear;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    ILoginPresenter loginPresenter;
    @BindView(R.id.progress_login)
    ProgressBar progressBar;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    final String TAG = "tang-demo";

    final int LOCATION_REFRESH_TIME = 5;
    final float LOCATION_REFRESH_DISTANCE = 100;
    private LocationManager mLocationManager;
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnClear.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        loginPresenter = new LoginPresenterCompl(this);
        loginPresenter.setProgressBarVisiblity(View.INVISIBLE);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_clear:
                loginPresenter.clear();
                break;
            case R.id.btn_login_login:
                loginPresenter.setProgressBarVisiblity(View.VISIBLE);
                btnLogin.setEnabled(false);
                btnClear.setEnabled(false);
                loginPresenter.doLogin(editUser.getText().toString(), editPass.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onClearText() {
        editPass.setText("");
        editUser.setText("");
    }


    @Override
    public void onLoginResult(Boolean result, int code) {
        loginPresenter.setProgressBarVisiblity(View.INVISIBLE);
        btnLogin.setEnabled(true);
        btnClear.setEnabled(true);
        if (result) {
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Login Fail, code = " + code, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void onLocation(double latitude, double longtitude) {
        tvLocation.setText(String.format("Current location: (%f, %f)", latitude, longtitude));
    }

    @Override
    public LocationManager getLocationManager() {
        return (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "getLocation onConnected");
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }else {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                Log.d(TAG, "set Location");
                loginPresenter.setLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            } else {
                Log.d(TAG, "Ooooops, mLastLocation is null");
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "getLocation onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "getLocation onConnectionFailed");
    }
}
