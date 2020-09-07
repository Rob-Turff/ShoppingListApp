package com.example.shoppinglistapp.ui.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.database.ItemsDatabase
import com.example.shoppinglistapp.database.models.Item
import com.example.shoppinglistapp.databinding.FragmentSelectItemsBinding
import com.example.shoppinglistapp.ui.list.adapters.SelectItemsRecyclerAdapter
import com.example.shoppinglistapp.ui.list.adapters.SelectItemsRecyclerClickListener
import com.example.shoppinglistapp.ui.list.dialogboxes.EditItemDialogFragment

class SelectItemsFragment : Fragment(), EditItemDialogFragment.EditItemDialogListener {

    private lateinit var viewModel: SelectItemsViewModel
    private lateinit var viewModelFactory: SelectItemsViewModelFactory
    private lateinit var binding: FragmentSelectItemsBinding
    private lateinit var parentActivity: MainActivity
    private lateinit var recyclerAdapter: SelectItemsRecyclerAdapter

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
        viewModelFactory = SelectItemsViewModelFactory(args.itemId, args.itemList.asList(), listDataSource, itemDataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelectItemsViewModel::class.java)

        setHasOptionsMenu(true)
        parentActivity.showHomeButton(true)
        parentActivity.changeHomeIcon(true)

        recyclerAdapter = SelectItemsRecyclerAdapter(object : SelectItemsRecyclerClickListener {
            override fun onViewClicked(view: View, item : Item) {
                view.findViewById<CheckBox>(R.id.checkBox).toggle()
                viewModel.onSelectItem(item)
                setButtonVisibility()
            }

            override fun onCheckBoxClicked(item : Item) {
                viewModel.onSelectItem(item)
                setButtonVisibility()
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

        binding.renameSelectedButton.setOnClickListener {
            val dialogFragment = EditItemDialogFragment()

            val args = Bundle()
            val item = viewModel.getSelectedItems().first()!!
            args.putParcelable("item", item)

            dialogFragment.arguments = args
            dialogFragment.setTargetFragment(this, 0)
            dialogFragment.show(parentFragmentManager, "edit_item_dialog")
        }

        binding.lifecycleOwner = this
        recyclerAdapter.submitList(viewModel.itemList.sortedBy { it.isCompleted })

        // Inflate the layout for this fragment
        return binding.root
    }

    fun setButtonVisibility() {
        val list = viewModel.getSelectedItems()
        binding.renameSelectedButton.isEnabled = (list.size == 1)
        binding.deleteSelectedButton.isEnabled = (list.isNotEmpty())
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

    override fun onEditDialogPositiveClick(dialog: DialogFragment, text: String, item : Item) {
        viewModel.onUpdateItemName(text, item)
        recyclerAdapter.submitList(viewModel.itemList.sortedBy { it.isCompleted })
    }
}