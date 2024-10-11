package com.example.eventapp.ui.calendar

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
import com.example.eventapp.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalendarViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        binding.rvCalendar.layoutManager = LinearLayoutManager(context)


        viewModel.listCalendar.observe(viewLifecycleOwner) {
            val adapter = EventAdapter(
                it, object : EventAdapter.OnEventClickListener {
                    override fun onEventClick(eventId: Int) {
                        val action = CalendarFragmentDirections.actionNavigationCalendarToDetailActivity(eventId)
                        findNavController().navigate(action)
                    }

                }
            )
            binding.rvCalendar.adapter = adapter
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarCalendar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
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