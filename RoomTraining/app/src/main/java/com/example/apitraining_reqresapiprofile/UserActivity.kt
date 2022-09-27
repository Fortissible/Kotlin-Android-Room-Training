package com.example.apitraining_reqresapiprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
                Toast.makeText(this,"User berhasil tersimpan",Toast.LENGTH_SHORT).show()
                val intent = Intent(this,ListUsersActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
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
                lifecycleScope.launch {
                    viewModel.delete(dataUserOffline!!)
                }
                Toast.makeText(this,"User berhasil dihapus",Toast.LENGTH_SHORT).show()
                val intent = Intent(this,OfflineListActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }

            activityUserBinding.userDetailUpdateUserBtn.setOnClickListener {
                val intentToEditActivity = Intent(this,UpdateAddUserActivity::class.java)
                intentToEditActivity.putExtra(ACTIVITY_TYPE,0)
                intentToEditActivity.putExtra(UPDATE_FIRST_VALUE,dataUserOffline)
                intentToEditActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intentToEditActivity)
                finish()
            }
        }
    }

    companion object{
        const val ACTIVITY_TYPE = "ACTIVITY_TYPE"
        const val UPDATE_FIRST_VALUE = "UPDATE_FIRST_VALUE"
    }
}