package ui.login

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import es.oaemdl.apkcavoshcafe202610.databinding.FragmentRegistroBinding
import model.Usuario

class RegistroFragment : Fragment() {

    private lateinit var binding: FragmentRegistroBinding
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegistroBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val correo = binding.etCorreo.text.toString()
            val pass = binding.etPassword.text.toString()

            if (nombre.isNotEmpty() && correo.isNotEmpty() && pass.isNotEmpty()) {
                registrar(nombre, correo, pass)
            } else {
                Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun registrar(nombre: String, correo: String, pass: String) {
        auth.createUserWithEmailAndPassword(correo, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    val uid = auth.currentUser!!.uid
                    val usuario = Usuario(uid, nombre, correo)

                    db.child("usuarios").child(uid).setValue(usuario)

                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()

                    findNavController().popBackStack()

                } else {
                    Toast.makeText(context, "Error al registrar", Toast.LENGTH_SHORT).show()
                }
            }
    }
}