package com.example.recipeapp_fragment

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp_room.data.Recipe
import com.example.recipeapp_room.data.RecipeDatabase


class FragmentNewPage : Fragment() {
    lateinit var recycle: RecyclerView
    lateinit var list:List<Recipe>
    lateinit var myViewModel :MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_new_page, container, false)
view.findViewById<Button>(R.id.button).setOnClickListener {
    Navigation.findNavController(view).navigate(R.id.action_fragmentNewPage_to_fragmentHome)

}
        recycle=view.findViewById(R.id.rv)
        list= listOf()
        myViewModel= ViewModelProvider(this).get(MyViewModel::class.java)

        updatedrecycle()
    return view
    }

    fun updatedrecycle(){
        myViewModel.getnotes().observe(requireActivity(),{
                notes1->
            recycle.adapter = RVAdapter (this, notes1)
            recycle.layoutManager = LinearLayoutManager(requireContext())
        })

    }

    fun deleteitem(id:Int) {
        val ob= RecipeDatabase.getinstant(requireContext())
        myViewModel.deleteNotes(id)

        Toast.makeText(requireContext(),"Successfully deleted", Toast.LENGTH_SHORT).show()

    }

    fun UpdateRec(recipe:Recipe) {
        var c=recipe
        val d = AlertDialog.Builder(requireContext())
        lateinit var input: EditText
        lateinit var tb1: EditText
        lateinit var tb2: EditText
        lateinit var tb3: EditText
        lateinit var vv: View

        d.setCancelable(false)
        d.setPositiveButton("update") { _, _ ->
            c.title = input.text.toString()
            c.author = tb1.text.toString()
            c.ingredents = tb2.text.toString()
            c.instruction = tb3.text.toString()
            myViewModel.updatesNotes(c.id,c.title,c.author,c.ingredents,c.instruction)
        }
            .setNegativeButton("Cancel") { d, _ -> d.cancel() }


        val alert = d.create()
        alert.setTitle("Edit celebrity")
        vv=layoutInflater.inflate(R.layout.alert,null)
        alert.setView(vv)
        input= vv.findViewById(R.id.edn)
        tb1=vv.findViewById(R.id.edatb1)
        tb2=vv.findViewById(R.id.edatb2)
        tb3=vv.findViewById(R.id.edatb3)
        input.setText(c.title)
        tb1.setText(c.author)
        tb2.setText(c.ingredents)
        tb3.setText(c.instruction)

        alert.show()

    }


    fun confirm(id:Int ){
        var at= AlertDialog.Builder(requireContext())
        at.setTitle("delete Celebrity")
        at.setPositiveButton("Delete", DialogInterface.OnClickListener { dialogInterface, i ->
            deleteitem(id)
        })
        at.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        at.show()
    }

}
