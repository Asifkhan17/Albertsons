package com.demo.albertsons.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.demo.albertsons.data.model.AcromineData
import com.demo.albertsons.databinding.ActivitySearchAcromineBinding
import com.demo.albertsons.util.Resource
import com.demo.albertsons.util.SEARCH_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_search_acromine.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SearchAcromineActivity"

@AndroidEntryPoint
class SearchAcromineActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchAcromineBinding
    private val viewModel: SearchAcromineViewModel by viewModels()

    @Inject
    lateinit var adapterAcromine: AdapterAcromine

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchAcromineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        searchInput()
        observeSearchAcromine()
        setSupportActionBar(toolbar)
    }

    private fun observeSearchAcromine() {
        viewModel.searchAcromine.observe(this) {
            when (it) {
                is Resource.Success -> {
                    paginationProgressBar.visibility = View.INVISIBLE
                    handleSuccessResponse(it)
                }
                is Resource.Error -> {
                    paginationProgressBar.visibility = View.INVISIBLE
                    it.message?.let { message ->
                        Log.e(TAG, "Error: $message")
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    paginationProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun handleSuccessResponse(it: Resource) {
        val list: List<AcromineData> = it.data as List<AcromineData>
        if (list.isEmpty()) {
            tvMessage.visibility = View.VISIBLE
            rvSearch.visibility = View.INVISIBLE
        } else {
            rvSearch.visibility = View.VISIBLE
            tvMessage.visibility = View.GONE
            list.let { rootNode ->
                rootNode[0].lfs?.let { lf -> adapterAcromine.updateListItems(lf) }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun searchInput() {
        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let {
                    if (editable.toString().length > 1) {
                        viewModel.searchForAcromine(editable.toString())
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.apply {
            rvSearch.apply {
                adapter = adapterAcromine
            }
        }
    }

}