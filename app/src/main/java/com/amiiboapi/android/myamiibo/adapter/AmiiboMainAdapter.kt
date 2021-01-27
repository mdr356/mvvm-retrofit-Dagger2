package com.amiiboapi.android.myamiibo.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amiiboapi.android.myamiibo.R
import com.amiiboapi.android.myamiibo.model.AmiiboData
import com.squareup.picasso.Picasso

class AmiiboMainAdapter : RecyclerView.Adapter<AmiiboMainAdapter.ViewHolder>() {

    private var dataSet: List<AmiiboData>? = null
    var amiiboClickListener: AmiiboClickListener? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val amiiboSeries: TextView
        val image_view: ImageView
        val amiiboLinearLayout: LinearLayout
        init {
            // Define click listener for the ViewHolder's View.
            name = view.findViewById(R.id.name)
            amiiboSeries = view.findViewById(R.id.amiiboSeries)
            image_view = view.findViewById(R.id.image)
            amiiboLinearLayout = view.findViewById(R.id.amiibo_linear_layout)
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
        Picasso.get().load(dataSet?.get(position)?.image).into(viewHolder.image_view)

        if (dataSet?.get(position)?.purchase == 1) {
            viewHolder.amiiboLinearLayout.setBackgroundColor(Color.parseColor("#008000"))
            Log.d("marc",("position of purchase: "+position))
        }

        viewHolder.itemView.setOnClickListener {
            dataSet?.get(position)?.let { it1 -> amiiboClickListener?.onClickListener(it1) }
        }
    }

    override fun getItemCount(): Int {
        if(dataSet != null) return dataSet!!.size
        else return 0
    }

    fun setListener(amiiboClickListener: AmiiboClickListener) {
        this.amiiboClickListener = amiiboClickListener
    }
    fun setList(amiibo: List<AmiiboData>) {
        this.dataSet = amiibo
        notifyDataSetChanged()
    }
}

interface AmiiboClickListener {
    fun onClickListener(amiiboData: AmiiboData)
}
