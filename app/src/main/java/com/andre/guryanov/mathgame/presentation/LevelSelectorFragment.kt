package com.andre.guryanov.mathgame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.andre.guryanov.mathgame.databinding.FragmentLevelSelectorBinding
import com.andre.guryanov.mathgame.domain.entity.Level

class LevelSelectorFragment : Fragment() {

    private var _binding: FragmentLevelSelectorBinding? = null
    private val binding: FragmentLevelSelectorBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentLevelSelectorBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelSelectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initListeners() = with(binding) {
        btnTestFLevelSelector.setOnClickListener {
            launchLevel(Level.TEST)
        }
        btnEasyFLevelSelector.setOnClickListener {
            launchLevel(Level.EASY)
        }
        btnMediumFLevelSelector.setOnClickListener {
            launchLevel(Level.MEDIUM)
        }
        btnHardFLevelSelector.setOnClickListener {
            launchLevel(Level.HARD)
        }
    }

    private fun launchLevel(level: Level) {
        findNavController().navigate(
            LevelSelectorFragmentDirections.actionLevelSelectorFragmentToGameFragment(level)
        )
    }
}