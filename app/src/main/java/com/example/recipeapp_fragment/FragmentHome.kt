package com.example.recipeapp_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation


class FragmentHome : Fragment() {
    lateinit var title: EditText
    lateinit var name: EditText
    lateinit var ingre: EditText
    lateinit var instr: EditText
    lateinit var save : Button

    lateinit var myViewModel :MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)

        title=view.findViewById(R.id.edtitle)
        name=view.findViewById(R.id.edname)
        ingre=view.findViewById(R.id.edinge)
        instr=view.findViewById(R.id.edins)
        save=view.findViewById(R.id.btnsave)
        myViewModel= ViewModelProvider(this).get(MyViewModel::class.java)

        view.findViewById<Button>(R.id.btnview).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fragmentHome_to_fragmentNewPage)


        }
        save.setOnClickListener {
            var s1=title.text.toString()
            var s2=name.text.toString()
            var s3=ingre.text.toString()
            var s4=instr.text.toString()
            if(s1.isNotEmpty()&&s2.isNotEmpty()&&s3.isNotEmpty()&&s4.isNotEmpty())
            {
                myViewModel.addNotes(0,s1,s2,s3,s4)
                title.text.clear()
                name.text.clear()
                ingre.text.clear()
                instr.text.clear()
                Toast.makeText(requireContext(), "data successfully added", Toast.LENGTH_SHORT)
                    .show()
            }
            else{
                Toast.makeText(requireContext(),"one or more field empty", Toast.LENGTH_SHORT).show()
            }


        }


        return view
    }


}