package com.example.apitraining_reqresapiprofile

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonWriter
import android.util.Log
import com.example.apitraining_reqresapiprofile.databinding.ActivityMainBinding
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.btnGetListusers.setOnClickListener {
            val intentToListUsersActivity = Intent(this@MainActivity,ListUsersActivity::class.java)
            startActivity(intentToListUsersActivity)
        }

        activityMainBinding.btnGetUser.setOnClickListener {
            val intentToUserActivity = Intent(this@MainActivity,SearchUserActivity::class.java)
            startActivity(intentToUserActivity)
        }

        activityMainBinding.btnUploadUser.setOnClickListener {
            val intentToUploadUserActivity = Intent(this@MainActivity,UploadUserActivity::class.java)
            startActivity(intentToUploadUserActivity)
        }

        activityMainBinding.btnOfflineList.setOnClickListener{
            val intentToOfflineUsersList = Intent(this@MainActivity,OfflineListActivity::class.java)
            startActivity(intentToOfflineUsersList)
        }
    }
}