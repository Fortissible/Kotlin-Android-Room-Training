package com.example.apitraining_reqresapiprofile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apitraining_reqresapiprofile.databinding.UserCardItemBinding

class OfflineListAdapter(private val usersEntity: List<UserEntity>) : RecyclerView.Adapter<OfflineListAdapter.ViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OfflineListAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(val binding: UserCardItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = UserCardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = usersEntity[position]
        holder.binding.tvUserName.text = StringBuilder(data.firstName)
            .append(" ")
            .append(data.lastName)
        holder.binding.root.setOnClickListener {
            onItemClickCallback
                .onItemClicked(usersEntity[holder.adapterPosition])
        }
        holder.binding.cardDeleteUserBtn.visibility = View.VISIBLE
        holder.binding.cardDeleteUserBtn.setOnClickListener {
            onItemClickCallback.onDeleteUser(usersEntity[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = usersEntity.size

    interface OnItemClickCallback {
        fun onItemClicked(userData: UserEntity)
        fun onDeleteUser(userData: UserEntity)
    }
}