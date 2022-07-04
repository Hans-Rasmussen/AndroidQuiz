package com.example.dummyproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.dummyproject.databinding.FragmentSetupBinding
import com.example.dummyproject.model.Category
import com.example.dummyproject.model.TriviaSession

class SetupFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentSetupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetupBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //requests a list of available categories
        sharedViewModel.getCategories()
        sharedViewModel.categories.observe(viewLifecycleOwner, Observer { response ->
            if(response.isSuccessful){
                //adds list of categories to a listview
                val categories: List<Category> = response.body()?.trivia_categories!!
                val arrayAdapter: ArrayAdapter<Category> = ArrayAdapter<Category>(requireContext(),android.R.layout.simple_list_item_1, categories)
                binding.listviewCategory.adapter = arrayAdapter
                //on click listner to navigate futher into the app, with the selected category
                binding.listviewCategory.setOnItemClickListener { adapterView, view, i, l ->
                    var triviaSession = TriviaSession()

                    //stores session values to help save system state if the application is rotated or minimized
                    triviaSession.categoryId = categories[i].id
                    triviaSession.categoryName = categories[i].name
                    sharedViewModel.triviaSession.value = triviaSession

                    Navigation.findNavController(binding.root).navigate(R.id.nav_to_difficultyFragment)
                }
            }
        })
    }
}