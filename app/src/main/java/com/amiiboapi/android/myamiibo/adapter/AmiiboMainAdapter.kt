package com.amiiboapi.android.myamiibo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amiiboapi.android.myamiibo.R
import com.amiiboapi.android.myamiibo.model.AmiiboData
import com.squareup.picasso.Picasso

class AmiiboMainAdapter : RecyclerView.Adapter<AmiiboMainAdapter.ViewHolder>() {

    private var dataSet: List<AmiiboData>? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val amiiboSeries: TextView
        val image_view: ImageView
        init {
            // Define click listener for the ViewHolder's View.
            name = view.findViewById(R.id.name)
            amiiboSeries = view.findViewById(R.id.amiiboSeries)
            image_view = view.findViewById(R.id.image)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.amiibo_item_layout, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.text = dataSet?.get(position)?.name
        viewHolder.amiiboSeries.text = dataSet?.get(position)?.amiiboSeries
        Picasso.get().load(dataSet?.get(position)?.image).into(viewHolder.image_view);
    }

    override fun getItemCount(): Int {
        if(dataSet != null) return dataSet!!.size
        else return 0
    }

    fun setList(amiibo: List<AmiiboData>) {
        this.dataSet = amiibo
        notifyDataSetChanged()
    }
}
