package com.example.groupproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.groupproject.databinding.FragmentSettingsMainBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mysql.jdbc.log.Log

/**
 * This subclass is the main page for settings. Its meant for simple navigation for the 4
 * different actions it can perform. Each onclick listener navigates through nav_graph2 layout
 * and performs the action in the xml.
 */
class settings_main : Fragment() {

    private var _binding: FragmentSettingsMainBinding? = null
    private lateinit var user: FirebaseUser

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = Firebase.auth.currentUser!!

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_First2Fragment_to_Second2Fragment)
        }

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_First2Fragment_to_PersonalFragment)
        }

        binding.buttonThird.setOnClickListener {
            findNavController().navigate(R.id.action_First2Fragment_to_DeleteFragment)
        }

        // This listener is for logout, so it just unauthorizes them and sends them to the login page
        binding.buttonFourth.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(activity, LogInActivity::class.java))
        }



        binding.buttonFifth.setOnClickListener{
            findNavController().navigate(R.id.action_First2Fragment_to_TermsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}