package cl.inacap.calculadoranotas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.calculadoranotas.dto.Nota;

public class MainActivity extends AppCompatActivity {

    private EditText notaTxt;
    private EditText porcentajeTxt;
    private Button agregarBtn;
    private Button limpiarBtn;
    private ListView notasLv;
    private List<Nota> notas = new  ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.notaTxt = findViewById(R.id.notaTxt);
        this.porcentajeTxt = findViewById(R.id.Porcentajetxt);
        this.agregarBtn = findViewById(R.id.agregarBtn);
        this.limpiarBtn = findViewById(R.id.limpiarBtn);
        this.notasLv = findViewById(R.id.notasLv);

        this.agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> errores = new ArrayList<>();
                String notaStr = notaTxt.getText().toString().trim();
                String porcStr = porcentajeTxt.getText().toString().trim();
                int porcentaje;
                double nota;
                try {
                    nota = Double.parseDouble(notaStr);
                    if(nota < 1.0 || nota > 7.0){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException Ex){
                    errores.add("La nota debe estar entre 1y 7");
                }
                try {
                    porcentaje  = Integer.parseInt(porcStr);
                    if(porcentaje<1 || porcentaje > 100){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException Ex){
                    errores.add("el porcentaje debe estar entre 1 y 100");
                }
                if(errores.isEmpty()){
                    //ingresar la nota
                    //
                }else{
                    mostrarErrores(errores);
                }
            }
        });
    }
    public void mostrarErrores( List<String> errores){
        //1. generar cadena de texto con los erropres
        String mensaje= "";
        for(String e: errores){
            mensaje+= "-" + e + "/n";
        }
        //2. mostra mensaje de alerta
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        //chaining
        alertBuilder
                .setTitle("errore de Validacion") //esto define el titulo
                .setMessage(mensaje)  //define el mensaje del dialogo
                .setPositiveButton("Aceptar",null) //agrega el bton aceptar
                .create()  //crea el alert
                .show();    //lo muestra

    }
}