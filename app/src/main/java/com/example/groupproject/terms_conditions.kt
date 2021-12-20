package com.example.groupproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.groupproject.databinding.FragmentSecurityCheckBinding
import com.example.groupproject.databinding.FragmentCredentialUpdateBinding
import com.example.groupproject.databinding.FragmentTermsBinding
import com.example.groupproject.databinding.FragmentUserUpdateBinding
import com.example.groupproject.profile_page.UserSupplier.user
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.profile_page.*

/**
 * This subfragment loads in the users current profile details and allows the user to modify their details
 * and set them in the database.
 */
class terms_conditions : Fragment() {

    private var _binding: FragmentTermsBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTermsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}