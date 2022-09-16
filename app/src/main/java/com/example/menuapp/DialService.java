package com.example.menuapp;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;

public class DialService extends Service {
    private static final String TAG = "MobileDialSrvc:abhinav";
    boolean debug = true;
    private static final String ACTION_USB_ATTACHED  = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    private static final String ACTION_USB_DETACHED  = "android.hardware.usb.action.USB_DEVICE_DETACHED";
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    UsbManager usbManager;
    UsbDevice usbDevice;
    UsbDevice mUsbDevice;

    TTS textToSpeech;
    Vibrator v;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (debug)
            Log.i(TAG, "Service onStartCommand " + startId);

        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        textToSpeech = new TTS();
        textToSpeech.initialize(this,v);

        if (debug)
            Log.i(TAG,"OnCreate");
        System.out.println("Before connect");
//        long[] pattern = {0, 15, 10,15};
//        v.vibrate(pattern, -1);

        connect();

        System.out.println("After connect");
    }

    private void connect() {
        this.usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();

        // just get the first enumerated USB device
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        if (deviceIterator.hasNext()) {
            this.usbDevice = deviceIterator.next();
        }

        System.out.println("Checking for usb now");

        if (usbDevice == null) {
            Log.w(TAG, "no USB device found");
            return;
        }


        // ask for permission
        final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
        final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(ACTION_USB_PERMISSION)) {
                    synchronized (this) {
                        UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            if(device != null){
                                // call method to set up device communication
                                Log.i(TAG, "permission granted.");

                                new ConnectionThread().start();
                                mUsbDevice = device;
                                //transfer(device);
                            }
                        }
                        else {
                            Log.d(TAG, "permission denied for device " + device);
                        }
                    }
                } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (device != null) {
                        // TODO:
                        // call your method that cleans up and closes communication with the device
                        // usbInterface.releaseInterface();
                        // usbDeviceConnection.close();
                    }
                }
            }
        };

        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);

        usbManager.requestPermission(usbDevice, mPermissionIntent);
    }

    private class ConnectionThread extends Thread {

        @Override
        public void run() {
            transfer(mUsbDevice);
        }
    }

    private void transfer(UsbDevice device) {

        int TIMEOUT = 0;
        boolean forceClaim = true;
        Log.i(TAG,"at 1111, device = "+device);

        // Taking HID interface into consideration
        UsbInterface intf = device.getInterface(3);

        Log.i(TAG,"at 222="+intf.getId());

        Log.i(TAG,"at 22222="+intf.getEndpointCount());
        UsbEndpoint endpoint = intf.getEndpoint(0);
        //UsbEndpoint endpoint2 = intf.getEndpoint(1);
        Log.i(TAG,"at 333="+endpoint.getType());
        Log.i(TAG,"at enpoint dir="+endpoint.getDirection());
        //Log.i(TAG,"at enpoint2 dir="+endpoint2.getDirection());
        UsbDeviceConnection connection = this.usbManager.openDevice(device);
        if (connection != null)
            Log.i(TAG,"at 444");
        boolean intfc;
        intfc = connection.claimInterface(intf, forceClaim);
        if(intfc)
            Log.i(TAG,"at 555");

        byte[] bytes = new byte[endpoint.getMaxPacketSize()];
        //byte[] bytes = new byte[1064];

        Log.i(TAG,"at 666 = "+bytes.length);

        int readCount;

        UsbRequest request = new UsbRequest(); // create an URB
        boolean initilzed = request.initialize(connection, endpoint);

        if (!initilzed) {
            Log.i(TAG, "Request initialization failed for reading");
        }
        while (true) {
            int bufferMaxLength = endpoint.getMaxPacketSize();
            ByteBuffer buffer = ByteBuffer.allocate(bufferMaxLength);


            //clockwise -> buffer[3] == 7
            //anticlockwise -> buffer[3] = 24
            //click buffer[3] = 27
            if (request.queue(buffer, bufferMaxLength) == true) {
                if (connection.requestWait().equals(request)){

                    int received = buffer.get(3);

                    if(received==7){
                        System.out.println("Clockwise direction");
                        Intent intent = new Intent();
                        intent.setAction("sbuCustomGesture");
                        intent.putExtra("sbuGestureAction", 3);
                        sendBroadcast(intent);
                    }
                    else if (received==24) {
                        System.out.println("Anticlockwise direction");
                        Intent intent = new Intent();
                        intent.setAction("sbuCustomGesture");
                        intent.putExtra("sbuGestureAction", 4);
                        sendBroadcast(intent);
                    }
                    if(received==27){
                        System.out.println("Click");
                        Intent intent = new Intent();
                        intent.setAction("sbuCustomGesture");
                        intent.putExtra("sbuGestureAction", 100);
                        sendBroadcast(intent);
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        if(textToSpeech !=null){
            textToSpeech.onPause();
        }
        super.onDestroy();
        Log.i(TAG,"onDestroy()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
