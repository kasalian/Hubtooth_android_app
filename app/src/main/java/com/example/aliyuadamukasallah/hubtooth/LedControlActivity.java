package com.example.aliyuadamukasallah.hubtooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.UUID;

public class LedControlActivity extends AppCompatActivity {

    ToggleButton fan, bulb, socket1, socket2;
    Button disc, exit;

    String address = null;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private ProgressDialog progress;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_led_control);

        Intent i = getIntent();
        address = i.getStringExtra(MainActivity.EXTRA_ADDRESS); //receive the address of the bluetooth device

        //view of the ledControl
        setContentView(R.layout.activity_led_control);

        fan = (ToggleButton) findViewById(R.id.fanToggle);
        bulb = (ToggleButton) findViewById(R.id.bulbToggle);
        socket1 = (ToggleButton) findViewById(R.id.s1Toggle);
        socket2 = (ToggleButton) findViewById(R.id.s2Toggle);

        disc = (Button) findViewById(R.id.disconnect);
        exit = (Button) findViewById(R.id.exit);

        new ConnectBT().execute(); //Call the class to connect


        fan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    turnOnFan();
                } else {
                    turnOffFan();
                }

            }
        });


        bulb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    turnOnBulb();
                } else {
                    turnOffBulb();
                }

            }
        });


        socket1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    turnOnSocket1();
                } else {
                    turnOffSocket1();
                }

            }
        });


        socket2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    turnOnSocket2();
                } else {
                    turnOffSocket2();
                }

            }
        });



        disc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Disconnect();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }
    // method that does disconnection
    private void Disconnect() {
        if (btSocket != null) //If the btSocket is busy
        {
            try {
                btSocket.close(); //close connection
            } catch (IOException e) {
                msg("Error");
            }
        }
        finish(); //return to the first layout

    }

    // method that turn On socket1
    private void turnOnSocket1() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("a".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    // method that turn Off socket1
    private void turnOffSocket1() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("b".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    //method that turn On Fan
    private void turnOnFan() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("c".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    //method that turn Off Fan
    private void turnOffFan() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("d".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    //method that turn On Bulb
    private void turnOnBulb() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("e".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    //method that turn Off Bulb
    private void turnOffBulb() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("f".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    //method that turn On Socket2
    private void turnOnSocket2() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("g".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    //method that turn Off Socket2
    private void turnOffSocket2() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("h".toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }


    // fast way to call Toast
    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(LedControlActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (!ConnectSuccess) {
                msg("Connection Faild. It is SPP Bluetooth? Try again");
                finish();
            } else {
                msg("CONNECTED");
                isBtConnected = false;
            }
            progress.dismiss();
        }


    }
}
