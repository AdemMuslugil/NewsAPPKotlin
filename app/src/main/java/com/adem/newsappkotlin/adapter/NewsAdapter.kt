package com.adem.newsappkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.adem.newsappkotlin.Util.downloadFromUrl
import com.adem.newsappkotlin.Util.placeHolderProgressBar
import com.adem.newsappkotlin.databinding.HomeRecyclerRowBinding
import com.adem.newsappkotlin.model.Article
import com.adem.newsappkotlin.model.NewsResponse
import com.adem.newsappkotlin.view.HomePageFragmentDirections


class NewsAdapter(val newsList : ArrayList<Article>) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
    class NewsHolder(val binding: HomeRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding = HomeRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.binding.homeTitle.text= newsList[position].title
        holder.binding.homeSource.text= newsList[position].source!!.name

        getImage(holder.binding,holder.binding.root.context,position)


        holder.binding.root.setOnClickListener {
            val action = HomePageFragmentDirections.actionHomePageFragmentToDetailFragment(newsList[position].id)
            Navigation.findNavController(it).navigate(action)
            println(newsList[position].id)
        }


    }

    override fun getItemCount(): Int {
        return newsList.size
    }


    fun updateNewsList(newList: List<Article>){

        newsList.clear()
        newsList.addAll(newList)
        notifyDataSetChanged()
    }

    fun getImage(binding: HomeRecyclerRowBinding,context: Context,position: Int){

        if (newsList[position].urlToImage != null){

            binding.homeImageView.visibility = View.VISIBLE
            binding.homeImageView.downloadFromUrl(newsList[position].urlToImage!!, placeHolderProgressBar(context))

        }else{
            binding.homeImageView.visibility = View.GONE
        }


    }

}
