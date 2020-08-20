package com.example.shoppinglistapp.ui.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.database.models.Item
import com.example.shoppinglistapp.database.models.ItemsDatabase
import com.example.shoppinglistapp.databinding.FragmentItemListBinding
import com.example.shoppinglistapp.ui.list.adapters.ItemListRecyclerAdapter
import com.example.shoppinglistapp.ui.list.adapters.ItemListRecyclerClickListener
import kotlinx.android.synthetic.main.fragment_view_lists.*

class ItemListFragment : Fragment() {

    private lateinit var viewModel: ItemListViewModel
    private lateinit var viewModelFactory: ItemListViewModelFactory
    private lateinit var binding: FragmentItemListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = ItemListFragmentArgs.fromBundle(requireArguments())

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_list, container, false)
        val application = requireNotNull(this.activity).application
        val itemDataSource = ItemsDatabase.getInstance(application).itemDatabaseDAO
        val listDataSource = ItemsDatabase.getInstance(application).itemListDatabaseDao
        viewModelFactory = ItemListViewModelFactory(args.itemListID, listDataSource, itemDataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ItemListViewModel::class.java)

        val viewManager = LinearLayoutManager(activity)
        val recyclerAdapter =
            ItemListRecyclerAdapter(viewModel.itemListLiveData.value ?: mutableListOf<Item>(), object : ItemListRecyclerClickListener {
                override fun onViewClicked(view: View, position: Int) {
                    view.findViewById<CheckBox>(R.id.checkBox).toggle()
                }

                override fun onCheckBoxClicked(checkBox: CheckBox, item: Item) {
                    viewModel.onCompleteItem(item)
                }
            } )

        binding.itemListRecyclerView.apply {
            layoutManager = viewManager
            adapter = recyclerAdapter
        }

        binding.addItemButton.setOnClickListener {
            viewModel.onAddItem(binding.itemListEditText.text.toString())
            binding.itemListEditText.setText("")
            Log.i("ItemListFragment", "Add item button clicked!")
        }

        viewModel.itemListLiveData.observe(viewLifecycleOwner, Observer { newItemList -> recyclerAdapter.updateData(newItemList.sortedBy { it.isCompleted }) })

        binding.lifecycleOwner = this

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ItemListFragment()
    }
}