package com.example.pasteleria1000sabores.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.pasteleria1000sabores.R
import com.example.pasteleria1000sabores.model.Product

/**
 * Adaptador para mostrar la lista de productos en la pantalla de catálogo y
 * back office. Utiliza un layout simple definido en item_product.xml.
 */
class ProductAdapter(
    private val context: Context,
    private val products: List<Product>
) : BaseAdapter() {
    override fun getCount(): Int = products.size

    override fun getItem(position: Int): Any = products[position]

    override fun getItemId(position: Int): Long = products[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
            holder = ViewHolder(
                image = view.findViewById(R.id.imageProduct),
                name = view.findViewById(R.id.textName),
                description = view.findViewById(R.id.textDescription),
                price = view.findViewById(R.id.textPrice)
            )
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }
        val product = products[position]
        holder.name.text = product.name
        holder.description.text = product.description
        holder.price.text = "$${String.format("%.0f", product.price)}"
        holder.image.setImageResource(product.imageRes)
        return view
    }

    private data class ViewHolder(
        val image: ImageView,
        val name: TextView,
        val description: TextView,
        val price: TextView
    )
}