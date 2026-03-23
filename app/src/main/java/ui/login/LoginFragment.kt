package ui.login

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import es.oaemdl.apkcavoshcafe202610.R
import es.oaemdl.apkcavoshcafe202610.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val correo = binding.etCorreo.text.toString()
            val pass = binding.etPassword.text.toString()

            if (correo.isNotEmpty() && pass.isNotEmpty()) {
                login(correo, pass)
            } else {
                Toast.makeText(context, "Completa los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegistro.setOnClickListener {
            findNavController().navigate(R.id.registroFragment)
        }

        return binding.root
    }

    private fun login(correo: String, pass: String) {
        auth.signInWithEmailAndPassword(correo, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    findNavController().navigate(R.id.menuFragment)
                } else {
                    Toast.makeText(context, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                }
            }
    }
}