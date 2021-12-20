package com.example.groupproject

import android.content.Intent
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
import com.example.groupproject.databinding.FragmentDeleteAccountBinding
import com.example.groupproject.profile_page.UserSupplier.user
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class delete_account : Fragment() {

    private var _binding: FragmentDeleteAccountBinding? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var user: FirebaseUser

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDeleteAccountBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        user = Firebase.auth.currentUser!!
        val db = Firebase.firestore



        binding.backButton.setOnClickListener{
            findNavController().navigate(R.id.action_DeleteFragment_to_First2Fragment)
        }


        binding.deleteButton.setOnClickListener {
            val uid = user.uid
            db.collection("Account").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (document.getString("uid")!!.toString() == uid) {
                        deletePosts(db, document.getString("username")!!.toString())
                        document.reference.delete()
                        user.delete()
                        break
                    }
                }
            }
            startActivity(Intent(activity, LogInActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun deletePosts(db: FirebaseFirestore, username: String ){
        db.collection("Post").get().addOnSuccessListener { result ->
            val batch = db.batch()
            for(document in result){
                if(document.getString("username")!!.toString() == username) {
                    batch.delete(document.reference)
                }
            }
            batch.commit()

        }
}

}