package innovador.proyecto;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import javax.microedition.khronos.egl.EGLDisplay;

public class EditionsMensa extends AppCompatActivity  {




    Button Enviar;
    EditText txtsms;
     Button btnsave;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    //Instanciamos Enviar
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_mensaje);
        Enviar =  findViewById(R.id.btnenviar);
        //Agregamos permisos para que la app pueda acceder a tu celular para enviar sms
        //tambien encontramos estos permisos en el manifest
        if (ActivityCompat.checkSelfPermission(
                EditionsMensa.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                EditionsMensa.this, Manifest
                        .permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditionsMensa.this, new String[]
                    {Manifest.permission.SEND_SMS,}, 1000);
        } else {
        }
        ;


        //estos son los parametros predeterminados para el envio del mensaje
        Enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                enviarMensaje("3324911788", txtsms);


            }
        });
    }
  // En este metodo devolvemos un valor para el envio del mensaje tales como si se envia o no y muestra un tipo advertencia o notificación
  //Se le llama Id del error Pelusa
        @SuppressLint("WrongViewCast")
    private void enviarMensaje(String numero, EditText txtsms) {
        try {
            SmsManager sms = SmsManager.getDefault();
            txtsms=  findViewById(R.id.txtsms);
            //getText().toString() nos sirve para guardar los datos de nuestro txtsms
            Log.d("Valor ET", txtsms.getText().toString());
            btnsave= findViewById(R.id.btnsave);

            {
                sms.sendTextMessage( numero , null, txtsms.getText().toString() , null, null);
            }
                 //Valores a tomar cuando se realiza el envio del sms
            Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

   //Este metodo guarda los valores que agregamos en el text view
    public void vervalor(View view) {

        EditText txtsms = (EditText) findViewById(R.id.txtsms);
        Log.d("Mensaje ",  txtsms.getText().toString());

    }

}


