package com.andre.guryanov.mathgame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andre.guryanov.mathgame.R
import com.andre.guryanov.mathgame.databinding.FragmentGameFinishedBinding
import com.andre.guryanov.mathgame.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentGameFinishedBinding == null")

//    private val result by lazy {
//        args.result
//    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        initViews()
        binding.result = args.result
        binding.btnTryAgainFGameFinished.setOnClickListener {
            tryAgain()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun tryAgain() {
        findNavController().popBackStack()
    }

//    private fun initViews() = with(binding) {
//        tvRequiredAnswersFGameFinished.text = requireActivity()
//            .getString(R.string.f_game_finished__needle_right_answers)
//            .format(result.gameSettings.minCountOfRightAnswers)
//
//        tvScoreFGameFinished.text = requireActivity()
//            .getString(R.string.f_game_finished__your_score)
//            .format(result.countOfRightAnswers)
//
//        tvPercentOfRightAnswersFGameFinished.text = requireActivity()
//            .getString(R.string.f_game_finished__percent_of_right_answers)
//            .format(calculatePercent(result))
//
//        tvRequiredPercentFGameFinished.text = requireActivity()
//            .getString(R.string.f_game_finished__needle_percent_of_right_answers)
//            .format(result.gameSettings.minPercentOfRightAnswers)
//
//        if (result.winner) imgResultFGameFinished.setImageResource(R.drawable.img_win)
//        else imgResultFGameFinished.setImageResource(R.drawable.img_lose)
//    }

//    private fun calculatePercent(result: GameResult) : Int {
//        if (result.countOfQuestions == 0) return 0
//        return result.countOfRightAnswers * 100 / result.countOfQuestions
//    }
}