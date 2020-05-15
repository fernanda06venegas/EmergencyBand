package innovador.proyecto;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class DispositivosBT extends AppCompatActivity {

    private static final String TAG = "DispositivosBT";

    ListView IdLista;

    public static String EXTRA_DEVICE_ADDRESS="device address";

    private BluetoothAdapter mbtAdapter;
    private ArrayAdapter<String>mPairedDevicesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos_bt);
    }

    @Override
    public void onResume(){
        super.onResume();

        VerificarEstadoBt();

        mPairedDevicesArrayAdapter =new ArrayAdapter<String>(this, R.layout.nombre_dispositivos);

        IdLista = (ListView)findViewById(R.id.IdLista);
        IdLista.setAdapter(mPairedDevicesArrayAdapter);
        IdLista.setOnItemClickListener(mDeviceClickListener);

        mbtAdapter= BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = mbtAdapter.getBondedDevices();


        if (pairedDevices.size()> 0)
        {

            for (BluetoothDevice device:  pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "/n" + device.getAddress());
            }

        }
    }


    private AdapterView.OnItemClickListener av;
    private AdapterView.OnItemClickListener mDeviceClickListener = av,v,arg2,arg3;{
        //String info =((TextView) v).getText().toString();
        //String address = info.substring(info.length()-17);

        Intent i = new Intent(DispositivosBT.this, UserInterfaz.class);
        //i.putExtra(EXTRA_DEVICE_ADDRESS, address);
        startActivity(i);

    }
  private  void  VerificarEstadoBt(){
        mbtAdapter= BluetoothAdapter.getDefaultAdapter();
        if (mbtAdapter==null);{
          Toast.makeText(getBaseContext(),"El dispositivo no soporta",Toast.LENGTH_SHORT);
      }
            if (mbtAdapter.isEnabled()){
                Log.d(TAG,"...Activado");
            } else {
                Intent enableBTIntent= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBTIntent, 1);
            }

  }
}
