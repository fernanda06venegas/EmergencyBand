package innovador.proyecto;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

public class EditionsMensa extends AppCompatActivity  {



    //Boton que programaeromos
    Button Enviar;



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
                enviarMensaje("3324870305", "Buscame, Estoy en peligro");
            }
        });
    }
  // En este metodo devolvemos un valor para el envio del mensaje tales como si se envia o no y muestra un tipo advertencia o notificación
    private void enviarMensaje(String numero, String mensaje) {
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(numero, null, mensaje, null, null);
            Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }




}


