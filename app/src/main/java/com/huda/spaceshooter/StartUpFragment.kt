package com.huda.spaceshooter

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.huda.spaceshooter.databinding.FragmentStartUpBinding

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class StartUpFragment : Fragment() {
    private var _binding: FragmentStartUpBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartUpBinding.inflate(inflater, container, false)
        events()
        return binding.root

    }

    private fun events() {
        binding.playButton.setOnClickListener {
            findNavController().navigate(R.id.play)
        }
    }













    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}