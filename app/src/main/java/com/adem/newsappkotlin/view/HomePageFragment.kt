package com.adem.newsappkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Database
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.adem.newsappkotlin.R
import com.adem.newsappkotlin.adapter.NewsAdapter
import com.adem.newsappkotlin.databinding.FragmentHomePageBinding
import com.adem.newsappkotlin.db.NewsDAO
import com.adem.newsappkotlin.model.Article
import com.adem.newsappkotlin.model.NewsResponse
import com.adem.newsappkotlin.viewmodel.HomeViewModel


class HomePageFragment : Fragment() {
    private lateinit var binding : FragmentHomePageBinding


    private lateinit var viewModel : HomeViewModel
    private val newsAdapter = NewsAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomePageBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.refreshData()

        binding.recyclerViewHome.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewHome.adapter = newsAdapter

        swipeRefresh()

        observeLiveData()
    }


    private fun observeLiveData(){

        viewModel.newsDataFromSql.observe(viewLifecycleOwner, Observer { news ->

           news?.let {
               binding.homeProgressBar.visibility = View.GONE
               binding.homeErrorText.visibility = View.GONE
               binding.recyclerViewHome.visibility = View.VISIBLE
               newsAdapter.updateNewsList(news)
           }

        })

        viewModel.newsData.observe(viewLifecycleOwner, Observer { news ->

            news?.let {

                binding.recyclerViewHome.visibility = View.VISIBLE

                newsAdapter.updateNewsList(news.articles!!)
                binding.homeErrorText.visibility = View.GONE
                binding.homeProgressBar.visibility = View.GONE
            }
        })

        viewModel.newsError.observe(viewLifecycleOwner, Observer { error ->

            error?.let {
                if (it){
                    binding.homeProgressBar.visibility = View.GONE
                    binding.recyclerViewHome.visibility = View.GONE
                    binding.homeErrorText.visibility = View.VISIBLE
                }else{
                    binding.homeErrorText.visibility = View.GONE
                }

            }
        })

        viewModel.newsLoading.observe(viewLifecycleOwner, Observer { loading ->

            loading?.let {
                if (it){
                    binding.homeProgressBar.visibility = View.VISIBLE
                    binding.recyclerViewHome.visibility = View.GONE
                    binding.homeErrorText.visibility = View.GONE
                }else{
                    binding.homeProgressBar.visibility=View.GONE
                }
            }

        })



    }

    fun swipeRefresh(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.recyclerViewHome.visibility = View.GONE
            binding.homeErrorText.visibility = View.GONE
            binding.homeProgressBar.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing = false
        }


    }


}