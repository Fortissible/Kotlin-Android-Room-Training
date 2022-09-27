package com.example.apitraining_reqresapiprofile

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.apitraining_reqresapiprofile.databinding.ActivityUploadUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadUserActivity : AppCompatActivity() {
    private lateinit var uploadUserBinding: ActivityUploadUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uploadUserBinding = ActivityUploadUserBinding.inflate(layoutInflater)
        setContentView(uploadUserBinding.root)

        uploadUserBinding.uploadDataUserBtn.setOnClickListener {
            uploadNewUser(
                uploadUserBinding.uploadNameUserInput.text.toString(),
                uploadUserBinding.uploadEmailUserInput.toString())
        }
    }

    private fun uploadNewUser(name: String, email: String){
        uploadUserBinding.uploadInProgressTv.visibility = View.VISIBLE
        uploadUserBinding.uploadLoading.visibility = View.VISIBLE
        val client = ApiConfig.getApiService().uploadUser(name,email)
        client.enqueue(object : Callback<UploadUserResponse> {
            override fun onResponse(
                call: Call<UploadUserResponse>,
                response: Response<UploadUserResponse>
            ) {
                if (response.isSuccessful){
                    uploadUserBinding.uploadInProgressTv.visibility = View.GONE
                    uploadUserBinding.uploadLoading.visibility = View.GONE
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val text = StringBuilder("Your ID is ")
                            .append(responseBody.id)
                            .append("\nCreated at : ")
                            .append(responseBody.createdAt)
                        Toast.makeText(this@UploadUserActivity,text,Toast.LENGTH_SHORT).show()
                    } else {
                        uploadUserBinding.uploadInProgressTv.visibility = View.GONE
                        uploadUserBinding.uploadLoading.visibility = View.GONE
                        Toast.makeText(this@UploadUserActivity,"Failed to connect to server",Toast.LENGTH_SHORT).show()
                    }
                } else {
                    uploadUserBinding.uploadInProgressTv.visibility = View.GONE
                    uploadUserBinding.uploadLoading.visibility = View.GONE
                    Log.e(ContentValues.TAG, "onFailure : ${response.message()}")
                    Toast.makeText(this@UploadUserActivity,response.message(),Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<UploadUserResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                Toast.makeText(this@UploadUserActivity,t.message,Toast.LENGTH_SHORT).show()
            }

        })
    }
}