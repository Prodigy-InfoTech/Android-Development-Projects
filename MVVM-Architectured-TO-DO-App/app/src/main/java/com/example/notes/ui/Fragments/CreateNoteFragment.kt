package com.example.notes.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.notes.Entity.NotesEntity
import com.example.notes.R
import com.example.notes.ViewModel.NotesViewModel
import com.example.notes.databinding.FragmentCreateNoteBinding
import java.util.*

class CreateNoteFragment : Fragment() {

    lateinit var binding:FragmentCreateNoteBinding
     var priority:String="1"
    val viewModel:NotesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCreateNoteBinding.inflate(inflater,container,false)

        binding.reddot.setOnClickListener {
            binding.reddot.setImageResource(R.drawable.ic_tick)
            binding.greendot.setImageResource(0)
            binding.bluedot.setImageResource(0)
            priority="1"
        }

        binding.greendot.setOnClickListener {
            binding.greendot.setImageResource(R.drawable.ic_tick)
            binding.reddot.setImageResource(0)
            binding.bluedot.setImageResource(0)
            priority="2"
        }

        binding.bluedot.setOnClickListener {
            binding.bluedot.setImageResource(R.drawable.ic_tick)
            binding.greendot.setImageResource(0)
            binding.reddot.setImageResource(0)
            priority="3"
        }

        binding.floatingbbutton.setOnClickListener {
            createNotes(it)
        }



        return binding.root

    }

    private fun createNotes(view: View) {
        var title = binding.titleedittext.text.toString()
        var subtitle = binding.subtitleedittext.text.toString()
        var description = binding.descriptionedittext.text.toString()

        val d = Date()
        val s: CharSequence = DateFormat.format("MMMM d, yyyy ", d.getTime())
        Log.i("@@@",s.toString())
        var notes=NotesEntity(null, title = title, subtitle = subtitle, notes = description, date = s as String, priority = priority)

        viewModel.addNotes(notes)
        Log.i("@@@@@","Text Added Sucessfully")

        Navigation.findNavController(view!!).navigate(R.id.action_createNoteFragment_to_notesHomeFragment)
    }
}