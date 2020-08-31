package com.example.shoppinglistapp.ui.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.FragmentViewListsBinding
import com.example.shoppinglistapp.database.models.ItemList
import com.example.shoppinglistapp.database.ItemsDatabase
import com.example.shoppinglistapp.ui.list.adapters.ViewListsRecyclerAdapter

class ViewListsFragment : Fragment() {

    private lateinit var viewModel: ViewListsViewModel
    private lateinit var viewModelFactory: ViewListsViewModelFactory
    private lateinit var binding: FragmentViewListsBinding
    private lateinit var parentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentActivity = activity as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_lists, container, false)
        val application = requireNotNull(this.activity).application
        val listDataSource = ItemsDatabase.getInstance(application).itemListDatabaseDao
        viewModelFactory = ViewListsViewModelFactory(listDataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ViewListsViewModel::class.java)

        val viewManager = LinearLayoutManager(activity)
        val recyclerAdapter =
            ViewListsRecyclerAdapter(viewModel.listsLiveData.value ?: mutableListOf<ItemList>())

        binding.viewListsRecyclerView.apply {
            layoutManager = viewManager
            adapter = recyclerAdapter
        }

        binding.addListButton.setOnClickListener {
            viewModel.onCreateList(binding.textView.text.toString())
            binding.textView.setText("")
            Log.i("ViewListsFragment", "Create list button clicked!")
        }

        viewModel.listsLiveData.observe(viewLifecycleOwner, Observer { newList -> recyclerAdapter.updateData(newList) })
        parentActivity.showHomeButton(false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.app_title)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ViewListsFragment()
    }
}