package com.example.sqliteapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sqliteapp.db.HelperDB
import com.example.sqliteapp.model.Productos
import com.example.sqliteapp.model.Usuarios

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var txtUsuario: EditText? = null
    private var txtPass: EditText? = null
    private var btnIniciar: Button? = null
    private var btnRegistrar: Button? = null
    private var cursor: Cursor? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    private var managerUsuarios: Usuarios? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUsuario = findViewById(R.id.txtUsuario)
        txtPass = findViewById(R.id.txtPass)
        btnIniciar = findViewById(R.id.btnIniciar)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnIniciar!!.setOnClickListener(this)
        btnRegistrar!!.setOnClickListener(this)

        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase

    }

    override fun onClick(view: View) {
        managerUsuarios = Usuarios(this)

        val usuario: String = txtUsuario!!.text.toString().trim()
        val pass: String = txtPass!!.text.toString().trim()
        if(db!=null){
            if (view === btnRegistrar) {
                if (vericarFormulario()) {
                    managerUsuarios!!.addNewUsuario(
                        usuario,
                        pass
                    )
                    Toast.makeText(
                        this, "Usuario registrado",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else if (view === btnIniciar) {
                if (vericarFormulario()) {

                    cursor= managerUsuarios!!.searchUsuario(usuario)

                    if(cursor !=null && cursor!!.count>0){
                        cursor!!.moveToFirst()
                        Toast.makeText(this, "Iniciando Sesion", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this, "No se encontro el usuario o la contraseña", Toast.LENGTH_LONG).show()
                    }

                }
            }
        }


    }

    private fun vericarFormulario(): Boolean {
        var usuario: String = txtUsuario!!.text.toString().trim()
        var password: String = txtPass!!.text.toString().trim()

        var response = true
        var usuario_v = true
        var pass_v = true

        var notificacion: String = "Se han generado algunos errores, favor verifiquelos"


            if (usuario.isEmpty()) {
                txtUsuario!!.error = "Ingrese el nombre del usuario"
                txtUsuario!!.requestFocus()
                usuario_v = false
            }
            if (password.isEmpty()) {
                txtPass!!.error = "Ingrese la password"
                txtPass!!.requestFocus()
                pass_v = false
            }

        response = !(usuario_v == false || pass_v == false )
        //Mostrar errores
        if (response == false) {
            Toast.makeText(
                this,
                notificacion,
                Toast.LENGTH_LONG
            ).show()
        }
        return response

    }
}