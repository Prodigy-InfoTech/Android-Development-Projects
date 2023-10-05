package com.example.notes.ui.Adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Entity.NotesEntity
import com.example.notes.R
import com.example.notes.ViewModel.NotesViewModel
import com.example.notes.databinding.ItemnoteBinding
import com.example.notes.ui.Fragments.EditNoteFragment
import com.example.notes.ui.Fragments.NotesHomeFragment

class NotesAdapter(
    requireContext: Context,
    val notelist: List<NotesEntity>,
    val viewmodel: NotesViewModel,
) :RecyclerView.Adapter<NotesAdapter.ViewHolder>() {



    class ViewHolder(val binding: ItemnoteBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemnoteBinding.inflate(
                LayoutInflater.from(parent.context)
                ,parent
                , false)
            )
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var title =notelist[position].title
        var subtitle=notelist[position].notes
        var date=notelist[position].date


        holder.binding.title.text= title
        holder.binding.subtitle.text= subtitle
        holder.binding.datetextview.text=date

        when(notelist[position].priority){

            "1"->{
                holder.binding.priority.setBackgroundResource(R.drawable.red)
            }
            "2"->{
                holder.binding.priority.setBackgroundResource(R.drawable.green)
            }
            "3"->{
                holder.binding.priority.setBackgroundResource(R.drawable.blue)
            }
        }

        var id = notelist[position].id
        holder.binding.root.setOnClickListener() {

//            viewmodel.deleteNotes(id!!)
            var bundle = Bundle()

            Log.i("Adaptertitle",title)
            bundle.putString("title",title)
            bundle.putString("subtitle",subtitle)
            bundle.putString("date",date)
            bundle.putString("priority",notelist[position].priority)
            bundle.putString("description",notelist[position].notes)
            bundle.putInt("id",notelist[position].id!!)
            Log.i("id",notelist[position].id.toString())

            var fragment= EditNoteFragment()
            fragment.arguments=bundle

            holder.binding.root.findNavController().navigate(R.id.action_notesHomeFragment_to_editNoteFragment,bundle)
//            fragmentManager?.beginTransaction()?.replace(R.id.notefragmentcontainer,fragment)?.commit()




        }

    }

    override fun getItemCount() = notelist.size


}