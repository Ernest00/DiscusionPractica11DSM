package com.example.sqliteapp.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.sqliteapp.db.HelperDB
class Usuarios(context: Context?) {
    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA USUARIOS
        val TABLE_NAME_USUARIOS = "usuarios"
        //nombre de los campos de la tabla contactos
        val COL_ID = "idusuarios"
        val COL_NOMBRE = "nombre"
        val COL_PASS = "pass"
        //sentencia SQL para crear la tabla.
        val CREATE_TABLE_USUARIOS = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USUARIOS + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_NOMBRE + " varchar(20) NOT NULL,"
                        + COL_PASS + " varchar(20) NOT NULL);"
                )
    }
    // ContentValues
    fun generarContentValues(
        nombre: String?,
        pass: String?
    ): ContentValues? {
        val valores = ContentValues()
        valores.put(Usuarios.COL_NOMBRE, nombre)
        valores.put(Usuarios.COL_PASS, pass)
        return valores
    }

    //Agregar un nuevo registro
    fun addNewUsuario(nombre: String?, pass: String?) {
        db!!.insert(
            TABLE_NAME_USUARIOS,
            null,
            generarContentValues(nombre, pass)
        )
    }
    // Eliminar un registro
    fun deleteUsuario(id: Int) {
        db!!.delete(TABLE_NAME_USUARIOS, "$COL_ID=?", arrayOf(id.toString()))
    }
    //Modificar un registro
    fun updateUsuario(
        id: Int,
        idcategoria: Int?,
        nombre: String?,
        pass: String?,
        cantidad: Int?
    ) {
        db!!.update(
            TABLE_NAME_USUARIOS, generarContentValues(nombre,
                pass),
            "$COL_ID=?", arrayOf(id.toString())

        )
    }
    // Mostrar un registro particular
    fun searchUsuario(nick: String): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE, COL_PASS)
        return db!!.query(
            TABLE_NAME_USUARIOS, columns,
            "$COL_NOMBRE=?", arrayOf(nick), null, null, null
        )
    }
    // Mostrar todos los registros
    fun searchUsuariosAll(): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE, COL_PASS)
        return db!!.query(
            TABLE_NAME_USUARIOS, columns,
            null, null, null, null, "${Usuarios.COL_NOMBRE} ASC"
        )
    }
}