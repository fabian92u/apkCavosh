package ui.menu

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import es.oaemdl.apkcavoshcafe202610.databinding.FragmentCarritoBinding
import model.CarritoItem
import adapter.CarritoAdapter

class CarritoFragment : Fragment() {

    private lateinit var binding: FragmentCarritoBinding
    private val lista = mutableListOf<CarritoItem>()
    private lateinit var db: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCarritoBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference.child("carrito").child(uid)

        cargarCarrito()

        return binding.root
    }

    private fun cargarCarrito() {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lista.clear()

                for (data in snapshot.children) {
                    val item = data.getValue(CarritoItem::class.java)
                    if (item != null) {
                        lista.add(item)
                    }
                }

                binding.recyclerView.adapter = CarritoAdapter(lista)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}