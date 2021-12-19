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
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class personal_update : Fragment() {

    private var _binding: FragmentUserUpdateBinding? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var username: EditText
    private lateinit var oldUsername: String
    private lateinit var firstname: EditText
    private lateinit var lastname: EditText
    private lateinit var user: FirebaseUser

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserUpdateBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        username = view.findViewById(R.id.personalUsername)
        firstname = view.findViewById(R.id.personalFirstname)
        lastname = view.findViewById(R.id.personalLastName)
        user = Firebase.auth.currentUser!!
        val db = Firebase.firestore
        db.collection("Account").get().addOnSuccessListener { result ->
            for (document in result) {
                if (document.getString("uid")!!.toString() == user.uid) {
                    oldUsername = document.getString("username").toString()
                    firstname.setText(document.getString("first").toString())
                    lastname.setText(document.getString("last").toString())
                    username.setText(oldUsername)
                    break
                }
            }
        }



        binding.backButton.setOnClickListener{
            findNavController().navigate(R.id.action_PersonalFragment_to_First2Fragment)
        }


        binding.update.setOnClickListener {
            updatePosts(db, oldUsername, username.text.toString())
            db.collection("Account").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (document.getString("uid")!!.toString() == user.uid) {
                        document.reference.update("username", username.text.toString(),
                                                    "first", firstname.text.toString(),
                                                "last",lastname.text.toString())
                    }
                }
            }
            findNavController().navigate(R.id.action_PersonalFragment_to_First2Fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun updatePosts(db: FirebaseFirestore, old: String, new: String) {
        db.collection("Post").get().addOnSuccessListener { result ->
            for(document in result) {
                if (document.getString("username")!!.toString() == old) {
                    document.reference.update("username", new)
                }
            }
        }
    }
}