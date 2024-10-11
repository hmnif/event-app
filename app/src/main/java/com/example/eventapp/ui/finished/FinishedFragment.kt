package com.example.eventapp.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventapp.EventAdapter
import com.example.eventapp.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    private val finishedViewModel: FinishedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(context)
        binding.rvFinished.layoutManager = layoutManager



        finishedViewModel.listFinished.observe(viewLifecycleOwner) { finishedEvents ->
            val adapter = EventAdapter(finishedEvents, object : EventAdapter.OnEventClickListener {
                override fun onEventClick(eventId: Int) {
                    val action = FinishedFragmentDirections.actionNavigationFinishedToDetailActivity(eventId)
                    requireView().findNavController().navigate(action)
                }
            })
            binding.rvFinished.adapter = adapter

        }
        finishedViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarFinishedEvent.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        finishedViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage(errorMessage)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}