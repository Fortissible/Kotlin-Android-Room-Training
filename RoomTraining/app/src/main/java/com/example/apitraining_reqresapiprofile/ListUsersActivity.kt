package com.example.apitraining_reqresapiprofile

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apitraining_reqresapiprofile.databinding.ActivityListUsersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUsersActivity : AppCompatActivity() {
    private lateinit var listUsersActivityBinding: ActivityListUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listUsersActivityBinding = ActivityListUsersBinding.inflate(layoutInflater)
        setContentView(listUsersActivityBinding.root)

        listUsersActivityBinding.listUsersRv.setHasFixedSize(true)

        searchList()
    }

    private fun searchList(){
        val client = ApiConfig.getApiService().getUsersList("1","12")
        listUsersActivityBinding.listUserLoading.visibility = View.VISIBLE
        client.enqueue(object : Callback<ListUserResponse> {
            override fun onResponse(
                call: Call<ListUserResponse>,
                response: Response<ListUserResponse>
            ) {
                listUsersActivityBinding.listUserLoading.visibility = View.GONE
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null) {
                        showRecyclerView(responseBody.data)
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListUserResponse>, t: Throwable) {
                listUsersActivityBinding.listUserLoading.visibility = View.GONE
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showRecyclerView(data : List<DataItem>){
        val rvAdapter = ListUsersAdapter(data)
        listUsersActivityBinding.listUsersRv.layoutManager = GridLayoutManager(this,2)
        listUsersActivityBinding.listUsersRv.adapter = rvAdapter

        rvAdapter.setOnItemClickCallback(object : ListUsersAdapter.OnItemClickCallback{
            override fun onItemClicked(userData: DataItem) {
                val intentToDetail = Intent(this@ListUsersActivity,UserActivity::class.java)
                intentToDetail.putExtra(RV_ITEM_ID,userData)
                intentToDetail.putExtra(DETAIL_TYPE,1)
                startActivity(intentToDetail)
            }
        })
    }

    companion object {
        const val RV_ITEM_ID = "RV_ITEM_ID"
        const val DETAIL_TYPE = "DETAIL_TYPE"
    }
}
