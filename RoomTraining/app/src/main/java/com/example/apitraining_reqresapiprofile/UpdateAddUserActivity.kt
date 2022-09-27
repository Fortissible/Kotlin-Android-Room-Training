package com.example.apitraining_reqresapiprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.apitraining_reqresapiprofile.databinding.ActivityUpdateAddUserBinding
import kotlinx.coroutines.launch

class UpdateAddUserActivity : AppCompatActivity() {
    private lateinit var updateAddUserActivityBinding: ActivityUpdateAddUserBinding
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateAddUserActivityBinding = ActivityUpdateAddUserBinding.inflate(layoutInflater)
        setContentView(updateAddUserActivityBinding.root)

        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]

        val activityType = intent.getIntExtra(UserActivity.ACTIVITY_TYPE,-1)

        if (activityType == 0){
            val firstValue = intent.getParcelableExtra<UserEntity>(UserActivity.UPDATE_FIRST_VALUE)
            updateAddUserActivityBinding.updateEmailInputEdt.setText(firstValue?.email.toString())
            updateAddUserActivityBinding.updateFirstnameInputEdt.setText(firstValue?.firstName.toString())
            updateAddUserActivityBinding.updateLastnameInputEdt.setText(firstValue?.lastName.toString())

            updateAddUserActivityBinding.updateUserBtn.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.update(getDataFromInput(),firstValue!!.id!!)
                }
                Toast.makeText(this,"Informasi user berhasil diperbarui",Toast.LENGTH_SHORT).show()
                val intent = Intent(this,OfflineListActivity::class.java)
                startActivity(intent)
                finish()
            }

        } else if (activityType == 1){
            updateAddUserActivityBinding.updateUserTitleTv.text = "Add user"
            updateAddUserActivityBinding.updateUserBtn.visibility = View.GONE
            updateAddUserActivityBinding.addUserBtn.visibility = View.VISIBLE

            updateAddUserActivityBinding.addUserBtn.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.insert(getDataFromInput())
                }
                Toast.makeText(this,"User berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,OfflineListActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun getDataFromInput(): UserEntity {
        return UserEntity(
            lastName = updateAddUserActivityBinding.updateLastnameInputEdt.text.toString(),
            firstName = updateAddUserActivityBinding.updateFirstnameInputEdt.text.toString(),
            email = updateAddUserActivityBinding.updateEmailInputEdt.text.toString()
        )
    }
}