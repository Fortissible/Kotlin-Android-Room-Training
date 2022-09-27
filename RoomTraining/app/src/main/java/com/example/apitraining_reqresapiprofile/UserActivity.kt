package com.example.apitraining_reqresapiprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.apitraining_reqresapiprofile.databinding.ActivityUserBinding
import kotlinx.coroutines.launch

class UserActivity : AppCompatActivity() {
    private lateinit var activityUserBinding: ActivityUserBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityUserBinding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(activityUserBinding.root)

        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]

        val dataType = intent.getIntExtra(ListUsersActivity.DETAIL_TYPE,-1)
        if (dataType == 1){
            val dataUser = intent.getParcelableExtra<DataItem>(ListUsersActivity.RV_ITEM_ID)

            activityUserBinding.apply {
                userDetailFirstnameTv.text = dataUser?.firstName
                userDetailLastnameTv.text = dataUser?.lastName
                userDetailEmailTv.text = dataUser?.email
                Glide.with(activityUserBinding.root)
                    .load(dataUser?.avatar)
                    .into(userPhotoDetailIv)
            }

            activityUserBinding.userDetailSaveUserBtn.visibility = View.VISIBLE
            activityUserBinding.userDetailDeleteUserBtn.visibility = View.GONE
            activityUserBinding.userDetailUpdateUserBtn.visibility = View.GONE

            activityUserBinding.userDetailSaveUserBtn.setOnClickListener {
                val userData = UserEntity(
                    lastName = dataUser!!.lastName,
                    firstName = dataUser.firstName,
                    email = dataUser.email,
                    id = dataUser.id
                )
                lifecycleScope.launch {
                    viewModel.insert(userData)
                }
            }
        } else if (dataType == 0) {
            val dataUserOffline = intent.getParcelableExtra<UserEntity>(OfflineListActivity.RV_OFFLINE_ITEM_ID)

            activityUserBinding.apply {
                userDetailFirstnameTv.text = dataUserOffline?.firstName
                userDetailLastnameTv.text = dataUserOffline?.lastName
                userDetailEmailTv.text = dataUserOffline?.email
            }

            activityUserBinding.userDetailSaveUserBtn.visibility = View.GONE
            activityUserBinding.userDetailDeleteUserBtn.visibility = View.VISIBLE
            activityUserBinding.userDetailUpdateUserBtn.visibility = View.VISIBLE

            activityUserBinding.userDetailDeleteUserBtn.setOnClickListener {
                val userData = UserEntity(
                    lastName = dataUserOffline!!.lastName,
                    firstName = dataUserOffline.firstName,
                    email = dataUserOffline.email,
                    id = dataUserOffline.id
                )
                lifecycleScope.launch {
                    viewModel.delete(userData)
                }
                val intent = Intent(this,OfflineListActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}