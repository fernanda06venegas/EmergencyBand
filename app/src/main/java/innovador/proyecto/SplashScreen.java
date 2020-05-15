package innovador.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {                             //Este metodo indica que despues del tiempo del splash screen muestra
                                                            // la siguiente vista de forma automatica este caso el menú
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
            }
            //tiempo de espera de la pantallo
        } , 1000);
    }
}
