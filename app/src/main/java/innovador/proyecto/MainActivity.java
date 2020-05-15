package innovador.proyecto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
     Button button,button2,button3,button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
        )
                != PackageManager.PERMISSION_GRANTED
        ) {

            // No se ha dado el permiso, mostrar una ventana
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_CONTACTS
            )
            ) {
                Toast.makeText(getApplicationContext(),"Habilite el permiso de sms",Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // Pedir permiso para leer contactos.
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{(Manifest.permission.READ_CONTACTS)},
                        1
                );
            };
        };

     //Botones del menú
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button= findViewById(R.id.button);
        button2= findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MainActivity.this,EditionsMensa.class);
                startActivity(intent);
            }

        }
        );
        //Los Intent nos sirven para llamar componentes en este caso como el cambio de vista
        //el setOnClickListener es el que nos permite realizar click al boton y ejecutar el Intent
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,agenda.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Manual.class);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,Desarrollador.class);
                startActivity(intent);
            }
        });




    }
}
