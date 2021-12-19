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
import com.example.groupproject.profile_page.UserSupplier.user
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class security_check : Fragment() {

    private var _binding: FragmentSecurityCheckBinding? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var credential: AuthCredential
    private lateinit var user: FirebaseUser

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecurityCheckBinding.inflate(inflater, container, false)
         user = Firebase.auth.currentUser!!
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        email = view.findViewById(R.id.confirm_email)
        password = view.findViewById(R.id.confirm_password)
        val uid = user.uid
        println(uid)



        binding.buttonSecond.setOnClickListener {
            println(email.text.toString())
            findNavController().navigate(R.id.action_Second2Fragment_to_First2Fragment)
        }

        binding.submit.setOnClickListener {

            credential = EmailAuthProvider.getCredential(email.text.toString(), password.text.toString())
            user.reauthenticate(credential).addOnCompleteListener {
                findNavController().navigate(R.id.action_Second2Fragment_to_Third2Fragment)
            }.addOnFailureListener {
                Toast.makeText(activity, "Invalid Credentials!", Toast.LENGTH_SHORT).show()
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}