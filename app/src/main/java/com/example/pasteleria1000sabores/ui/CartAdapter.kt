package com.example.pasteleria1000sabores.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.pasteleria1000sabores.R
import com.example.pasteleria1000sabores.model.Cart
import com.example.pasteleria1000sabores.model.CartItem

/**
 * Adaptador para mostrar los ítems del carrito permitiendo modificar la
 * cantidad mediante botones más y menos. Al modificar la cantidad se
 * notificará al callback para actualizar el total en la pantalla.
 */
class CartAdapter(
    private val context: Context,
    private val items: MutableList<CartItem>,
    private val onQuantityChanged: () -> Unit
) : BaseAdapter() {
    override fun getCount(): Int = items.size
    override fun getItem(position: Int): Any = items[position]
    override fun getItemId(position: Int): Long = items[position].product.id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
            holder = ViewHolder(
                name = view.findViewById(R.id.textName),
                price = view.findViewById(R.id.textPrice),
                quantity = view.findViewById(R.id.textQuantity),
                buttonMinus = view.findViewById(R.id.buttonMinus),
                buttonPlus = view.findViewById(R.id.buttonPlus)
            )
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }
        val item = items[position]
        holder.name.text = item.product.name
        holder.price.text = "$${String.format("%.0f", item.product.price * item.quantity)}"
        holder.quantity.text = item.quantity.toString()
        // Configuramos listeners para botones + y -
        holder.buttonMinus.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
            } else {
                // Si la cantidad es 1 y se resta, eliminamos el ítem
                items.removeAt(position)
            }
            notifyDataSetChanged()
            onQuantityChanged()
        }
        holder.buttonPlus.setOnClickListener {
            item.quantity++
            notifyDataSetChanged()
            onQuantityChanged()
        }
        return view
    }

    private data class ViewHolder(
        val name: TextView,
        val price: TextView,
        val quantity: TextView,
        val buttonMinus: Button,
        val buttonPlus: Button
    )
}