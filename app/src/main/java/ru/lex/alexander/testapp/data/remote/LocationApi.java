package ru.lex.alexander.testapp.data.remote;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import ru.lex.alexander.testapp.injection.component.DaggerApplicationComponent;

public class LocationApi implements OnCompleteListener<Location>{
    private FusedLocationProviderClient fusedLocationProviderClient;

    Context context;

    LocationApiListener locationApiListener;

    public LocationApi(Context context){
        this.context = context;
    }

    public void getLocation(LocationApiListener locationApiListener) throws SecurityException{
        this.locationApiListener = locationApiListener;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<Location> task) {
        double lat = task.getResult().getLatitude();
        double lon = task.getResult().getLongitude();

        locationApiListener.onLocationResult(lat, lon);
    }

    public interface LocationApiListener{
        void onLocationResult(double lat, double lon);
    }
}
