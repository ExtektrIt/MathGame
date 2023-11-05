package com.andre.guryanov.mathgame.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andre.guryanov.mathgame.data.GameRepositoryImpl
import com.andre.guryanov.mathgame.databinding.FragmentGameBinding
import com.andre.guryanov.mathgame.domain.entity.GameResult
import com.andre.guryanov.mathgame.domain.entity.GameSettings

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    private val repo = GameRepositoryImpl
    private val viewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory) [GameViewModel::class.java]
    }

    private lateinit var settings: GameSettings
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentGameBinding == null")

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            with(binding) {
                add(tvOption1FGame)
                add(tvOption2FGame)
                add(tvOption3FGame)
                add(tvOption4FGame)
                add(tvOption5FGame)
                add(tvOption6FGame)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() = with(binding) {
        viewModel.formattedTime.observe(viewLifecycleOwner) {
            tvTimerFGame.text = it
        }

        viewModel.question.observe(viewLifecycleOwner) {
            tvSumFGame.text = it.sum.toString()
            tvVisibleNumber.text = it.visibleNumber.toString()
            for (i in 0 until tvOptions.size) {
                tvOptions[i].text = it.options[i].toString()
            }
        }

        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            progressBarFGame.progress = it
        }

        viewModel.enoughCount.observe(viewLifecycleOwner) {
            tvAnswersProgressFGame.setTextColor(getColorByState(it))
        }

        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            tvAnswersProgressFGame.text = it
        }

        viewModel.enoughPercent.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            progressBarFGame.progressTintList = ColorStateList.valueOf(color)
        }

        viewModel.gameResult.observe(viewLifecycleOwner) {
            finishGame(it)
        }

        viewModel.minPercent.observe(viewLifecycleOwner) {
            progressBarFGame.secondaryProgress = it
        }
    }

    private fun initListeners() {
        for (option in tvOptions) {
            option.setOnClickListener {
                viewModel.selectAnswer(option.text.toString().toInt())
            }
        }
    }

    private fun getColorByState(state: Boolean) : Int {
        val colorRes = if (state) android.R.color.holo_green_light
        else android.R.color.holo_red_light
        return ContextCompat.getColor(requireContext(), colorRes)
    }

    private fun finishGame(result: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(result)
        )
    }
}