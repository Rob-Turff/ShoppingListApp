package com.example.shoppinglistapp.ui.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.database.ItemsDatabase
import com.example.shoppinglistapp.databinding.FragmentSelectItemsBinding
import com.example.shoppinglistapp.ui.list.adapters.SelectItemsRecyclerAdapter
import com.example.shoppinglistapp.ui.list.adapters.SelectItemsRecyclerClickListener

class SelectItemsFragment : Fragment() {

    private lateinit var viewModel: SelectItemsViewModel
    private lateinit var viewModelFactory: SelectItemsViewModelFactory
    private lateinit var binding: FragmentSelectItemsBinding
    private lateinit var parentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = SelectItemsFragmentArgs.fromBundle(requireArguments())
        parentActivity = activity as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_items, container, false)
        val application = requireNotNull(this.activity).application
        val itemDataSource = ItemsDatabase.getInstance(application).itemDatabaseDAO
        val listDataSource = ItemsDatabase.getInstance(application).itemListDatabaseDao
        viewModelFactory = SelectItemsViewModelFactory(args.itemListId, args.itemId, listDataSource, itemDataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelectItemsViewModel::class.java)

        setHasOptionsMenu(true)
        parentActivity.showHomeButton(true)
        parentActivity.changeHomeIcon(true)

        val recyclerAdapter = SelectItemsRecyclerAdapter(viewModel.pairList, object : SelectItemsRecyclerClickListener {
            override fun onViewClicked(view: View, position: Int) {
                view.findViewById<CheckBox>(R.id.checkBox).toggle()
            }

            override fun onCheckBoxClicked(checkBox: CheckBox, position : Int) {
                viewModel.pairList[position] = Pair(viewModel.pairList[position].first, !viewModel.pairList[position].second)
            }

        })
        val viewManager = LinearLayoutManager(parentActivity)
        binding.selectItemRecyclerView.apply {
            layoutManager = viewManager
            adapter = recyclerAdapter
        }

        binding.deleteSelectedButton.setOnClickListener {
            viewModel.onDeleteSelected()
            findNavController().navigateUp()
        }

        binding.lifecycleOwner = this
        viewModel.itemListLiveData.observe(viewLifecycleOwner, Observer { viewModel.updatedData(); recyclerAdapter.updateData(viewModel.pairList) })

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.item_list_action_bar, menu)
        menu.setGroupVisible(R.id.item_list_group, false)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SelectItemsFragment()
    }
}