package com.example.eventapp.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventapp.EventAdapter
import com.example.eventapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.rvHomeUpcomingEvent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHomeFinishedEvent.layoutManager = LinearLayoutManager(context)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBarHome.visibility = if (it) View.VISIBLE else View.GONE
            binding.scrollViewHome.visibility = if (it) View.GONE else View.VISIBLE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage(it)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        viewModel.upComingEvent.observe(viewLifecycleOwner) {
            val adapter = EventAdapter(it, object : EventAdapter.OnEventClickListener {
                override fun onEventClick(eventId: Int) {
                    val action = HomeFragmentDirections.actionNavigationHomeToDetailActivity(eventId)
                    findNavController().navigate(action)
                }

            }, 300)

            binding.rvHomeUpcomingEvent.adapter = adapter
        }

        viewModel.finishedEvent.observe(viewLifecycleOwner) {
            val adapter = EventAdapter(it, object : EventAdapter.OnEventClickListener {
                override fun onEventClick(eventId: Int) {
                    val action = HomeFragmentDirections.actionNavigationHomeToDetailActivity(eventId)
                    findNavController().navigate(action)
                }
            })
            binding.rvHomeFinishedEvent.adapter = adapter
        }

        return binding.root
    }
}