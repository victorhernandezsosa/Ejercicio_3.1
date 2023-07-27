package com.example.ejercicio31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private List<Contactos> listacontac = new ArrayList<>();

    ArrayAdapter<Contactos> adaptercontac;

    EditText txtnombre, txtapellido,txtcuenta, txtfecha;

    Spinner genero;

    Button btnagregar,btnedit,btndelete;

    ListView listView;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    Contactos contactoseleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtnombre = findViewById(R.id.txtnombre);
        txtapellido = findViewById(R.id.txtapellido);
        txtcuenta = findViewById(R.id.txtcuenta);
        txtfecha = findViewById(R.id.txtfecha);
        btnagregar = findViewById(R.id.btnagregar);
        btndelete = findViewById(R.id.btndelete);
        btnedit = findViewById(R.id.btnedit);
        genero = findViewById(R.id.genero);
        listView = findViewById(R.id.listview);

        firebase();
        mostrar();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contactoseleccionado=(Contactos) parent.getItemAtPosition(position);
                txtcuenta.setText(contactoseleccionado.getForo());
                txtnombre.setText(contactoseleccionado.getNombre());
                txtapellido.setText(contactoseleccionado.getApellido());
                txtfecha.setText(contactoseleccionado.getFecha());
                int index = obtenergenero(contactoseleccionado.getGenero());

                genero.setSelection(index);
            }
        });

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregardatos();
            }
        });

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar();

            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar();
            }
        });
    }

    private int obtenergenero(String generosel) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Gen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genero.setAdapter(adapter);

        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            if (adapter.getItem(i).equals(generosel)) {
                return i;
            }
        }

        return 0;
    }


    private void borrar() {
        if (contactoseleccionado != null) {
            Contactos contac = new Contactos();
            contac.setID(contactoseleccionado.getID());
            databaseReference.child("Contactos").child(contac.getID()).removeValue();
            Toast.makeText(this, "Se ha eliminado el dato correctamente", Toast.LENGTH_LONG).show();

            listacontac.remove(contactoseleccionado);
            adaptercontac.notifyDataSetChanged();

            Clear();
        } else {
            Toast.makeText(this, "No ha seleccionado ningun contacto", Toast.LENGTH_LONG).show();

        }
    }

    private void editar() {

        Contactos contac = new Contactos();
        contac.setID(contactoseleccionado.getID());
        contac.setForo(txtcuenta.getText().toString().trim());
        contac.setNombre(txtnombre.getText().toString().trim());
        contac.setApellido(txtapellido.getText().toString().trim());
        contac.setFecha(txtfecha.getText().toString().trim());
        contac.setGenero(genero.getSelectedItem().toString());
        databaseReference.child("Contactos").child(contac.getID()).setValue(contac);
        Toast.makeText(this,"Los datos se han actualizado correctamente", Toast.LENGTH_LONG).show();


    }

    private void agregardatos() {

        String cuenta = txtcuenta.getText().toString();
        String nombre = txtnombre.getText().toString();
        String apellido = txtapellido.getText().toString();
        String fecha = txtfecha.getText().toString();
        String gen = genero.getSelectedItem().toString();

        if (cuenta.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || fecha.isEmpty()){
            validar();
        }else {
            Contactos contac = new Contactos();
            contac.setID(UUID.randomUUID().toString());
            contac.setForo(cuenta);
            contac.setNombre(nombre);
            contac.setApellido(apellido);
            contac.setFecha(fecha);
            contac.setGenero(gen);

            databaseReference.child("Contactos").child(contac.getID()).setValue(contac);
            Toast.makeText(this,"Los datos se agregaron correctamente", Toast.LENGTH_LONG).show();
            Clear();

        }
    }

    private void Clear() {
        txtcuenta.setText("");
        txtnombre.setText("");
        txtapellido.setText("");
        txtfecha.setText("");
    }

    private void validar() {

        String cuenta = txtcuenta.getText().toString();
        String nombre = txtnombre.getText().toString();
        String apellido = txtapellido.getText().toString();
        String fecha = txtfecha.getText().toString();

        if (cuenta.isEmpty()){
            txtcuenta.setError("Campo Vacio");
        }else if (nombre.isEmpty()){
            txtnombre.setError("Campo Vacio");
        } else if (apellido.isEmpty()) {
            txtapellido.setError("Campo Vacio");
        } else if (fecha.isEmpty()) {
            txtfecha.setError("Campo Vacio");
        }
    }

    private void mostrar() {
        databaseReference.child("Contactos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listacontac.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Contactos contac = dataSnapshot.getValue(Contactos.class);
                    listacontac.add(contac);
                    adaptercontac = new ArrayAdapter<Contactos>(MainActivity.this, android.R.layout.simple_list_item_1,listacontac);
                    listView.setAdapter(adaptercontac);
                }

                if (adaptercontac == null) {
                    adaptercontac = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listacontac);
                    listView.setAdapter(adaptercontac);
                } else {
                    adaptercontac.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void firebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference= firebaseDatabase.getReference();
    }
}