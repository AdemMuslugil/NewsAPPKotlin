package com.adem.newsappkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adem.newsappkotlin.R
import com.adem.newsappkotlin.Util.downloadFromUrl
import com.adem.newsappkotlin.Util.placeHolderProgressBar
import com.adem.newsappkotlin.databinding.FragmentDetailBinding
import com.adem.newsappkotlin.viewmodel.DetailsViewModel

class DetailFragment : Fragment() {
    private lateinit var binding : FragmentDetailBinding

    private lateinit var viewModel: DetailsViewModel
    private var newsId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentDetailBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            newsId = DetailFragmentArgs.fromBundle(it).id
        }

        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        viewModel.getDataFromRoom(newsId)

        observeLiveData()

    }

    private fun observeLiveData(){

        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer { news->

            news?.let {
                binding.descriptionText.text = news.content
                println(news.content)
                binding.detailsImageView.downloadFromUrl(news.urlToImage!!, placeHolderProgressBar(binding.root.context))
            }


        })

    }


}