package com.example.dummyproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.dummyproject.databinding.FragmentQuestionBinding
import com.example.dummyproject.model.Question
import com.example.dummyproject.model.TriviaSession
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuestionFragment : Fragment() {

    private val QUESTION_REQUEST_COUNT = 10

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentQuestionBinding
    private lateinit var session: TriviaSession

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        session = sharedViewModel.triviaSession.value!!
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNewQuestions(true)
        binding.buttonAnswer0.setOnClickListener {
            isButtonCorrect(binding.buttonAnswer0)
        }
        binding.buttonAnswer1.setOnClickListener {
            isButtonCorrect(binding.buttonAnswer1)
        }
        binding.buttonAnswer2.setOnClickListener {
            isButtonCorrect(binding.buttonAnswer2)
        }
        binding.buttonAnswer3.setOnClickListener {
            isButtonCorrect(binding.buttonAnswer3)
        }
        binding.buttonNextQuestion.setOnClickListener {
                session.questionsAnswered++
                var question = getNextQuestion()
                if (question != null){
                    prepareQuestion(question)
                }
        }
    }

    private fun getNewQuestions(prepare: Boolean){
        var amount = QUESTION_REQUEST_COUNT
        if ((session.questionsAvailable - session.questionsAnswered) < amount){
            amount = session.questionsAvailable - session.questionsAnswered
        }
        sharedViewModel.getQuestions(amount,session.categoryId,session.difficulty,session.sessionToken)
        sharedViewModel.questions.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            if(response.isSuccessful && prepare){
                var question = response.body()?.results
                prepareQuestion(question?.get(0)!!)

            }
        })
    }
    //TODO
    private fun getNextQuestion(): Question? {
        if (session.questionsAnswered % QUESTION_REQUEST_COUNT == 0){
            getNewQuestions(true)
            return null
        }
        val questions = sharedViewModel.questions.value?.body()?.results
        return questions?.get(session.questionsAnswered%QUESTION_REQUEST_COUNT)
    }

    private fun prepareQuestion(question: Question) {
        //Title
        binding.textTitleQuestion.text = "${session.categoryName}: (${session.questionsAnswered}/${session.questionsAvailable}"

        //Question
        binding.textQuestion.text = question.question

        //shuffle all possible answers
        val answers: MutableList<String> = question.incorrect_answers.toMutableList()
        answers.add(question.correct_answer)
        answers.shuffle()

        //fix text on buttons to match answers, and set visibility
        binding.buttonAnswer0.text = answers[0]
        binding.buttonAnswer1.text = answers[1]

        if (question.type == "multiple"){
            binding.buttonAnswer2.visibility = View.VISIBLE
            binding.buttonAnswer3.visibility = View.VISIBLE
            binding.buttonAnswer2.text = answers[2]
            binding.buttonAnswer3.text = answers[3]
        } else {
            binding.buttonAnswer2.visibility = View.INVISIBLE
            binding.buttonAnswer3.visibility = View.INVISIBLE
        }

        binding.buttonNextQuestion.visibility = View.INVISIBLE
        binding.textIsCorrect.visibility = View.INVISIBLE

        //store correct answer so that it can be looked at on button click
        session.correctAsnwer = question.correct_answer
    }

    private fun isButtonCorrect(button: Button){
        if(button.text == session.correctAsnwer){
            binding.textIsCorrect.text = "YOU ARE CORRECT!"
        } else {
            binding.textIsCorrect.text = "That is incorrect!"
        }
        binding.textIsCorrect.visibility = View.VISIBLE
        binding.buttonNextQuestion.visibility = View.VISIBLE
    }
}