package innovador.proyecto;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class UserInterfaz extends AppCompatActivity {



    Button IdEncender , IdApagar,IdDesconectar;
    TextView IdBufferin;

    Handler bluetoothIn;
    final int handlerState = 0 ;
    private BluetoothAdapter  btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder DataStringIn = new StringBuilder();
    private ConnectedThread  MyConexionBt;

    private static final UUID BTMODULEUUID = UUID.fromString("002");
    private static String addres = null;
    private Message msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interfaz);

        IdEncender= (Button)findViewById(R.id.IdEncender);
        IdApagar=(Button)findViewById(R.id.IdApagar);
        IdDesconectar=(Button)findViewById(R.id.IdDesconectar);
        IdBufferin=(TextView)findViewById(R.id.IdBufferIn);

         bluetoothIn.handleMessage(msg); {
            if (msg.what== handlerState){
                String readMesage = (String) msg.obj;
                DataStringIn.append(readMesage);

                int endOfLineIndex = DataStringIn.indexOf("#");
                if (endOfLineIndex>0){
                    String dataInPrint= DataStringIn.substring(0, endOfLineIndex);
                    IdBufferin.setText("Dato"+ dataInPrint);
                    DataStringIn.delete(0,DataStringIn.length());
                }
            }
        }

         btAdapter = BluetoothAdapter.getDefaultAdapter();
        VerificarEstadoBT();

        IdEncender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyConexionBt.write("conectado");


            }
        });

        IdApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyConexionBt.write("desconectado");

            }
        });


        IdDesconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ACCION
            }
        });

        if (btSocket!=null){
            try { btSocket.close();}
            catch (IOException e){
                Toast.makeText(getBaseContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        finish();

        }
    }



    private  BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws  IOException{
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);

    }

    @Override
    public void  onResume(){
        super.onResume();

        Intent intent = getIntent();

        addres = intent.getStringExtra(DispositivosBT.EXTRA_DEVICE_ADDRESS);

        BluetoothDevice device = btAdapter.getRemoteDevice(addres);

        try {
            btSocket= createBluetoothSocket(device);
        } catch (IOException e){
            Toast.makeText(getBaseContext(),"Fallo",Toast.LENGTH_LONG).show();
        }

        try {
            btSocket.connect();
        } catch (IOException e){
            try {
                btSocket.close();
            } catch (IOException e2){

            }
        }

        MyConexionBt = new ConnectedThread(btSocket);
        MyConexionBt.start();

    }

    @Override
    public void onPause(){
        super.onPause();

        try {
            btSocket.close();
        } catch (IOException e2){ }
    }

    private void VerificarEstadoBT(){
        if (btAdapter==null){
            Toast.makeText(getBaseContext(),"El dispositiva no soporta Bluethooth",Toast.LENGTH_LONG);
        } else {
            if (btAdapter.isEnabled()){

            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,1);
            }
        }
    }

    private  class ConnectedThread extends  Thread{
        private InputStream mmInStream = null;
        private OutputStream mmOutStream= null;

        public  ConnectedThread(BluetoothSocket socket){
            InputStream tmpIn= null;
            OutputStream tmOut= null;
            try {
                tmpIn= socket.getInputStream();
                tmOut=socket.getOutputStream();
            } catch (IOException e){
                mmInStream = tmpIn;
                mmOutStream=tmOut;

            }
        }
        public void run(){
            byte[] buffer= new byte[256];
            int bytes;

            while (true){
                try {
                    bytes=mmInStream.read(buffer);
                    String readMessage= new String(buffer,0,bytes);

                    bluetoothIn.obtainMessage(handlerState,bytes,-1,readMessage).sendToTarget();
                } catch (IOException e){
                    break;
                }
            }
        }

        public void  write (String input ){
            try {
                mmOutStream.write(input.getBytes());
            } catch (IOException e){
                Toast.makeText(getBaseContext(),"La conexión fallo",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }


}
