package com.example.githubapi_search.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubapi_search.databinding.UserListBinding
import com.example.githubapi_search.model.UserGit
import com.example.githubapi_search.ui.detail.DetailActivity
import com.example.githubapi_search.util.Const.EXTRA_USER


class UserGitAdapter: RecyclerView.Adapter<UserGitAdapter.UserGitViewHolder>() {

    private val listUserGit = ArrayList<UserGit>()

    fun setAllData(data: List<UserGit>) {
        listUserGit.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserGitViewHolder {
        val view = UserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserGitViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserGitViewHolder, position: Int) {
        holder.bind(listUserGit[position])
    }

    override fun getItemCount(): Int = listUserGit.size

    inner class UserGitViewHolder(private val view: UserListBinding): RecyclerView.ViewHolder(view.root) {

        fun bind(user: UserGit) {
            view.apply {
                name.text = user.username

            }
            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(view.imgItemPhoto)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(EXTRA_USER, user.username)
                itemView.context.startActivity(intent)
            }
        }
    }

}