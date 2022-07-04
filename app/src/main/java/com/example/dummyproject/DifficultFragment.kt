package com.example.dummyproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.dummyproject.databinding.FragmentDifficultBinding
import com.example.dummyproject.model.TriviaSession

class DifficultFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentDifficultBinding
    private lateinit var session: TriviaSession

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        session = sharedViewModel.triviaSession.value!!
        binding = FragmentDifficultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.getCategoryInfo(session.categoryId)
        sharedViewModel.categoryInfo.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                //shows how many questions are available for each given difficulty
                binding.textEasy.text =
                    "Easy: " + response.body()!!.category_question_count.total_easy_question_count.toString()
                binding.textMedium.text =
                    "Medium: " + response.body()!!.category_question_count.total_medium_question_count.toString()
                binding.textHard.text =
                    "Hard: " + response.body()!!.category_question_count.total_hard_question_count.toString()
            }
        })

        binding.buttonStart.setOnClickListener {
            when (binding.radioDifficulty.checkedRadioButtonId) {
                R.id.radio_easy -> {
                    session.difficulty = "easy"
                    session.questionsAvailable =
                        sharedViewModel.categoryInfo.value?.body()?.category_question_count?.total_easy_question_count!!
                }
                R.id.radio_medium -> {
                    session.difficulty = "medium"
                    session.questionsAvailable =
                        sharedViewModel.categoryInfo.value?.body()?.category_question_count?.total_medium_question_count!!
                }
                else -> {
                    session.difficulty = "hard"
                    session.questionsAvailable =
                        sharedViewModel.categoryInfo.value?.body()?.category_question_count?.total_hard_question_count!!
                }
            }

                sharedViewModel.getTriviaToken()
                sharedViewModel.triviaToken.observe(viewLifecycleOwner, Observer { response ->
                    if (response.isSuccessful){
                        session.sessionToken = response.body()?.token!!
                        sharedViewModel.triviaSession.value = session
                        Navigation.findNavController(binding.root).navigate(R.id.nav_to_questionFragment)
                    }
                })
        }


    }

}