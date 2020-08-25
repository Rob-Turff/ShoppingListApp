package com.example.shoppinglistapp.ui.list

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.CheckBox
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.database.models.Item
import com.example.shoppinglistapp.database.models.ItemsDatabase
import com.example.shoppinglistapp.databinding.FragmentItemListBinding
import com.example.shoppinglistapp.ui.list.adapters.ItemListRecyclerAdapter
import com.example.shoppinglistapp.ui.list.adapters.ItemListRecyclerClickListener
import com.example.shoppinglistapp.ui.list.dialogboxes.DeleteListDialogFragment
import com.example.shoppinglistapp.ui.list.dialogboxes.RenameListDialogFragment

class ItemListFragment : Fragment(),
        DeleteListDialogFragment.DeleteListDialogListener,
        RenameListDialogFragment.RenameListDialogListener {

    private lateinit var viewModel: ItemListViewModel
    private lateinit var viewModelFactory: ItemListViewModelFactory
    private lateinit var binding: FragmentItemListBinding
    private lateinit var parentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = ItemListFragmentArgs.fromBundle(requireArguments())
        parentActivity = activity as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_list, container, false)
        val application = requireNotNull(this.activity).application
        val itemDataSource = ItemsDatabase.getInstance(application).itemDatabaseDAO
        val listDataSource = ItemsDatabase.getInstance(application).itemListDatabaseDao
        viewModelFactory = ItemListViewModelFactory(args.itemListID, listDataSource, itemDataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ItemListViewModel::class.java)

        setHasOptionsMenu(true)
        parentActivity.showHomeButton(true)

        val viewManager = LinearLayoutManager(parentActivity)
        val recyclerAdapter =
            ItemListRecyclerAdapter(viewModel.itemListLiveData.value ?: mutableListOf<Item>(), object : ItemListRecyclerClickListener {
                override fun onViewClicked(view: View, position: Int) {
                    view.findViewById<CheckBox>(R.id.checkBox).toggle()
                }

                override fun onCheckBoxClicked(checkBox: CheckBox, item: Item) {
                    viewModel.onCompleteItem(item)
                }

                override fun onTextChanged(item: Item, text : String) {
                    viewModel.onItemTextChanged(item, text)
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
        viewModel.listInfoLiveData.observe(viewLifecycleOwner, Observer { newListInfo -> parentActivity.title = newListInfo.listName })

        binding.lifecycleOwner = this

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun onDeleteListClicked() {
        val dialogFragment = DeleteListDialogFragment()
        dialogFragment.setTargetFragment(this, 0)
        dialogFragment.show(parentFragmentManager, "delete_list_dialog")
    }

    private fun onRenameListClicked() {
        val dialogFragment = RenameListDialogFragment()
        dialogFragment.setTargetFragment(this, 0)
        dialogFragment.show(parentFragmentManager, "rename_list_dialog")
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.item_list_action_bar, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_completed -> viewModel.onDeleteCompleted()
            R.id.menu_delete_list -> onDeleteListClicked()
            R.id.menu_rename_list -> onRenameListClicked()
            android.R.id.home -> findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ItemListFragment()
    }

    override fun onDeleteListDialogPositiveClick(dialog: DialogFragment) {
        viewModel.onDeleteList()
        findNavController().navigateUp()
    }

    override fun onRenameListDialogPositiveClick(dialog: DialogFragment, text: String) {
        viewModel.onRenameList(text)
    }
}