package innovador.proyecto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class agenda extends AppCompatActivity {
    EditText mNombre,mNumero;
    static final int PICK_CONTACT_REQUEST = 1;
    // Crear archivo de preferencias
    SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // instancia ...
        mNombre=findViewById(R.id.nombreId);
        mNumero=findViewById(R.id.numeroId);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            //Metodo de la selección del contacto

            public void onClick(View view) {
                selectercontacts();

            }
        });


    }


    //Obtenemos los contactos usando URI y pick
    private void  selectercontacts() {

        Intent selector=new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        selector.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(selector,PICK_CONTACT_REQUEST);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_CONTACT_REQUEST){
            if (resultCode==RESULT_OK){
                Uri uri= data.getData();
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                // cursor retorna valores verdaderos
                if (cursor.moveToFirst()){
                    int columnaNombre=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int columnaNumero=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);


                    String nombre=cursor.getString(columnaNombre);
                    String numero=cursor.getString(columnaNumero);
                    mNombre.setText(nombre);
                    mNumero.setText(numero);

                    // Crear editor de preferencias
                    SharedPreferences.Editor editor = sharedPref.edit();

                    // editor.putIn(id_del_valor, valor_a_almacenar)
                    editor.putInt("contacto_nombre", Integer.parseInt(nombre));
                    editor.putInt("contacto_numero", Integer.parseInt(numero));

                    // Almacenar los datos
                    editor.commit();

                    // Obtener strings guardados
                    // sharedPref.getString(id_del_valor, valor_en_caso_de_que_este_vacio)
                    sharedPref.getString("contacto_nombre", "");
                    sharedPref.getString("contacto_numero", "");
                }

            }
        }
    }

}