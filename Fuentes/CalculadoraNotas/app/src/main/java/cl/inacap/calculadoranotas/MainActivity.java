package cl.inacap.calculadoranotas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.calculadoranotas.dto.Nota;

public class MainActivity extends AppCompatActivity {

    private int porcentajeActual = 0;
    private EditText notaTxt;
    private EditText porcentajeTxt;
    private TextView promedioTxt;
    private LinearLayout promedioLl;
    private Button agregarBtn;
    private Button limpiarBtn;
    private ListView notasLv;
    private List<Nota> notas = new  ArrayList<>();
    private ArrayAdapter<Nota> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.promedioTxt = findViewById(R.id.promedioTxt);
        this.promedioLl = findViewById(R.id.promedioLl);
        this.notaTxt = findViewById(R.id.notaTxt);
        this.porcentajeTxt = findViewById(R.id.Porcentajetxt);
        this.agregarBtn = findViewById(R.id.agregarBtn);
        this.limpiarBtn = findViewById(R.id.limpiarBtn);
        this.notasLv = findViewById(R.id.notasLv);
        this.adapter =  new ArrayAdapter<>(
                this
                , android.R.layout.simple_list_item_1
                , notas);
        this.notasLv.setAdapter(adapter);


        this.limpiarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //limpiar los edittext
                notaTxt.setText("");
                porcentajeTxt.setText("");
                //oculatr el linear layout de resultado
                promedioLl.setVisibility(View.INVISIBLE);
                //vlver el porcentaje a 0
                porcentajeActual = 0;
                //limpiar la lista
                notas.clear();
                adapter.notifyDataSetChanged();
            }
        });
        this.agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> errores = new ArrayList<>();
                String notaStr = notaTxt.getText().toString().trim();
                String porcStr = porcentajeTxt.getText().toString().trim();
                int porcentaje = 0;
                double nota = 0;
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
                    if(porcentaje + porcentajeActual > 100){
                        //se excede el valor permitido
                        Toast.makeText(MainActivity.this,
                                "El porcentaje acumulado no puede ser mayor que 100",
                                Toast.LENGTH_SHORT).show();
                    }else {

                        Nota n = new Nota();
                        n.setValor(nota);
                        n.setPorcentaje(porcentaje);
                        porcentajeActual += porcentaje;
                        notas.add(n);
                        adapter.notifyDataSetChanged();
                        mostrarPromedio();
                    }
                }else{
                    mostrarErrores(errores);
                }
            }
        });
    }
    private void mostrarPromedio(){
        double promedio = 0;
        for(Nota n:notas){
            promedio+= (n.getValor()*n.getPorcentaje()/100);
        }

        this.promedioTxt.setText(String.format("%.1f",promedio));
        if(promedio < 4.0){
            this.promedioTxt.setTextColor(ContextCompat.
                    getColor(this, R.color.colorError));
        }else{
            this.promedioTxt.setTextColor(ContextCompat.
                    getColor(this,R.color.colorExitoso));
        }
        this.promedioLl.setVisibility(View.VISIBLE);
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