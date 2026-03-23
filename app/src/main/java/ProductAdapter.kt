import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = lista[position]

    holder.binding.tvNombre.text = item.nombre
    holder.binding.tvPrecio.text = item.precio

    Picasso.get().load(item.imagen).into(holder.binding.imgProducto)

    holder.binding.btnAgregar.setOnClickListener {

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseDatabase.getInstance().reference

        val carritoItem = hashMapOf(
            "nombre" to item.nombre,
            "precio" to item.precio,
            "imagen" to item.imagen
        )

        db.child("carrito").child(uid).push().setValue(carritoItem)
    }
}