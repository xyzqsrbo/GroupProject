package com.example.groupproject

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import java.sql.SQLException

abstract class ProfileActivity : AppCompatActivity() {

    var textBiography: TextView = findViewById<TextView>(R.id.txtBiography)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)
        Task().execute()


    }
    inner class Task : AsyncTask<Void, Void, Void>(){
        var records: String = ""
        var error: String = ""

        override fun doInBackground(vararg params: Void?): Void?{
            try{
                Class.forName("com.mysql.jdbc.Driver")
                var connection: Connection = DriverManager.getConnection(
                    "mappins-cluster-1.cluster-ro-c9cjjp7oxvnb.us-east-2.rds.amazonaws.com",
                    "root",
                    "Password1!")
                var statement: Statement = connection.createStatement()
                var resultSet: ResultSet = statement.executeQuery("Select * FROM user_account")

                while(resultSet.next()){
                    records += resultSet.getString(1) + " " + resultSet.getString(2) + "" + "\n"
                }
            }catch(e: SQLException){
                error = e.toString()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            textBiography.setText(records)
            if (error!=""){
                textBiography.setText(error)
            }
            super.onPostExecute(result)
        }
    }
}