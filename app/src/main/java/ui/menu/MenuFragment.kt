package ui.menu

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import es.oaemdl.apkcavoshcafe202610.databinding.FragmentMenuBinding
import adapter.ProductoAdapter
import model.Producto

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var db: DatabaseReference
    private val lista = mutableListOf<Producto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMenuBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        db = FirebaseDatabase.getInstance().reference.child("productos")

        cargarDatos()

        return binding.root
    }

    private fun cargarDatos() {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lista.clear()

                for (data in snapshot.children) {
                    val producto = data.getValue(Producto::class.java)
                    if (producto != null) {
                        lista.add(producto)
                    }
                }

                binding.recyclerView.adapter = ProductoAdapter(lista)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}