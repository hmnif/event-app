package com.example.eventapp.ui.search

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventapp.EventAdapter
import com.example.eventapp.R
import com.example.eventapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.rvSearch.layoutManager = LinearLayoutManager(context)


        viewModel.listEvent.observe(viewLifecycleOwner) {
            val adapter = EventAdapter(it, object : EventAdapter.OnEventClickListener {
                override fun onEventClick(eventId: Int) {
                    val action = SearchFragmentDirections.actionSearchFragmentToDetailActivity(eventId)
                    findNavController().navigate(action)
                }
            })
            binding.rvSearch.adapter = adapter
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarSearch.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage(errorMessage)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.app_bar_menu, menu)
                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem?.actionView as SearchView
                searchItem.expandActionView()

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewModel.findEventByQuery(query.toString())
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (!newText.isNullOrEmpty()) {
                            viewModel.findEventByQuery(newText)
                        }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_search -> {
                        // Add logic for menu item here
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}