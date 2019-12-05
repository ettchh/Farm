package com.example.Android_thingspeak;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;

import java.util.ArrayList;

public class InfoFragment extends Fragment {

   private OnFragmentInteractionListener mListener;

   // Network info
   private boolean networkState;
   private String networkName;
   private String networkIP;

   // Device info
   private String deviceSerial;
   private String deviceUnsupportedSensors;
   private ArrayList<String> listOfUnsupportedSensorsInModel;

   // Model info
   private String modelName;

    public InfoFragment() {}

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

       // Initialize properties when view is created
       setNetworkInfo();
       setDeviceInfo();
       setModelInfo();

       // Display the appropriate text sections
       displayNetworkInfo((TextView)rootView.findViewById(R.id.InfoTab_Network));
       displayDeviceInfo((TextView)rootView.findViewById(R.id.InfoTab_Device));
       displayModelInfo((TextView)rootView.findViewById(R.id.InfoTab_Model));

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       try {
           mListener = (OnFragmentInteractionListener) activity;
       } catch (ClassCastException e) {
           throw new ClassCastException(activity.toString()
           + " must implement OnFragmentInteractionListener");
       }
   }

   @Override
   public void onDetach() {
       super.onDetach();
       mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
       mListener.onFragmentStart("Info");
    }


    private void setNetworkInfo() {
       WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
       networkState = wifiManager.isWifiEnabled();

       if(networkState == true) {
           networkName     = wifiManager.getConnectionInfo().getSSID();
           networkName     = networkName.replace("\"","");
           int ipAddress   = wifiManager.getConnectionInfo().getIpAddress();
           networkIP       = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
       }
   }

   private void setDeviceInfo() {
       deviceSerial = Build.SERIAL;
            }

   private void setModelInfo() {
       modelName = "Android_thingspeak";
        }

   private void displayNetworkInfo(TextView tv) {
       // Network section displays: Title, Network name, ip address
       String networkTitle = "Network";
       tv.setText(Html.fromHtml("<b>"+networkTitle+"</b><br/>"));

       if (networkState != true) {// wifi disabled
           tv.append(Html.fromHtml("Wifi is not enabled.<br/>"));
           return;
       }
       tv.append(Html.fromHtml("<i>Name:</i> "+networkName+"<br/>"));
       tv.append(Html.fromHtml("<i>IP Address:</i> "+networkIP+"<br/>"));
   }

   private void displayDeviceInfo(TextView tv) {
       // Device section displays: Title, serial num, list of unsupported sensors (if used)
       String deviceTitle = "Device";
       tv.setText(Html.fromHtml("<b>"+deviceTitle+"</b><br/>"));

       tv.append(Html.fromHtml("<i>Serial:</i> "+deviceSerial+"<br/>"));
   }

   private void displayModelInfo(TextView tv) {
       // Model section displays: Title, model name, camera resolutions (if used)
       String modelTitle = "Model";
       tv.setText(Html.fromHtml("<b>"+modelTitle+"</b><br/>"));
       tv.append(Html.fromHtml("<i>Name:</i> "+modelName+"<br/>"));
   }
}