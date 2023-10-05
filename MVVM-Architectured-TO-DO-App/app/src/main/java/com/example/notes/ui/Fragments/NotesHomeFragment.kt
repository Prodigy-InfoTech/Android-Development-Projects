package com.example.notes.ui.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.R
import com.example.notes.ViewModel.NotesViewModel
import com.example.notes.databinding.FragmentNotesHomeBinding
import com.example.notes.ui.Adapter.NotesAdapter

class NotesHomeFragment : Fragment() {

    lateinit var binding: FragmentNotesHomeBinding

    val viewmodel :NotesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotesHomeBinding.inflate(inflater, container,false)
        // Inflate the layout for this fragment




        viewmodel.getAllNotes().observe(viewLifecycleOwner,{notelist->
            val staggeredGridLayoutManager= StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

            binding.notesrecycler.layoutManager= staggeredGridLayoutManager
            binding.notesrecycler.adapter=NotesAdapter(requireContext(),notelist,viewmodel)

        })



        binding.addbutton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_notesHomeFragment_to_createNoteFragment)
            Log.i("Addbutton pressed","Addbutton pressed")
        }

        binding.allfilter.setOnClickListener {notelist->
            viewmodel.getAllNotes().observe(viewLifecycleOwner,{notelist->
                val staggeredGridLayoutManager= StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

                binding.notesrecycler.layoutManager= staggeredGridLayoutManager
                binding.notesrecycler.adapter=NotesAdapter(requireContext(),notelist,viewmodel)

            })
        }

        binding.high.setOnClickListener {notelist->

            viewmodel.getHighNotes().observe(viewLifecycleOwner,{notelist->

                val staggeredGridLayoutManager= StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

                binding.notesrecycler.layoutManager= staggeredGridLayoutManager
                binding.notesrecycler.adapter=NotesAdapter(requireContext(),notelist,viewmodel)
            })
        }

        binding.medium.setOnClickListener {notelist->

            viewmodel.getMediumNotes().observe(viewLifecycleOwner,{notelist->
                val staggeredGridLayoutManager= StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

                binding.notesrecycler.layoutManager= staggeredGridLayoutManager
                binding.notesrecycler.adapter=NotesAdapter(requireContext(),notelist,viewmodel)
            })
        }

        binding.low.setOnClickListener {notelist->

            viewmodel.getLowNotes().observe(viewLifecycleOwner,{notelist->
                val staggeredGridLayoutManager= StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

                binding.notesrecycler.layoutManager= staggeredGridLayoutManager
                binding.notesrecycler.adapter=NotesAdapter(requireContext(),notelist,viewmodel)
            })
        }



        return binding.root
    }



}