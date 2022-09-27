package com.example.apitraining_reqresapiprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.apitraining_reqresapiprofile.databinding.ActivityOfflineListBinding
import kotlinx.coroutines.launch

class OfflineListActivity : AppCompatActivity() {
    private lateinit var activityOfflineListBinding: ActivityOfflineListBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityOfflineListBinding = ActivityOfflineListBinding.inflate(layoutInflater)
        setContentView(activityOfflineListBinding.root)

        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this@OfflineListActivity,factory)[MainViewModel::class.java]

        activityOfflineListBinding.offlineListUsersRv.setHasFixedSize(true)

//        viewModel.getAllUserOffline().observe(this){
//            showRecyclerView(it)
//        }

        activityOfflineListBinding.offlineListUsersAddBtn.setOnClickListener {
            val intentToAddUserActivity = Intent(this,UpdateAddUserActivity::class.java)
            intentToAddUserActivity.putExtra(UserActivity.ACTIVITY_TYPE,1)
            startActivity(intentToAddUserActivity)
        }
    }

    private fun showRecyclerView(data : List<UserEntity>){
        val adapter = OfflineListAdapter(data)
        activityOfflineListBinding.offlineListUsersRv.layoutManager = GridLayoutManager(this,2)
        activityOfflineListBinding.offlineListUsersRv.adapter = adapter

        adapter.setOnItemClickCallback(object: OfflineListAdapter.OnItemClickCallback{
            override fun onItemClicked(userData: UserEntity) {
                val intentToDetail = Intent(this@OfflineListActivity,UserActivity::class.java)
                intentToDetail.putExtra(RV_OFFLINE_ITEM_ID,userData)
                intentToDetail.putExtra(ListUsersActivity.DETAIL_TYPE,0)
                startActivity(intentToDetail)
            }
            override fun onDeleteUser(userData: UserEntity) {
                lifecycleScope.launch {
                    viewModel.delete(userData)
                }
            }
        })
    }

    companion object {
        const val RV_OFFLINE_ITEM_ID = "OFFLINE_ITEM_ID"
    }
}