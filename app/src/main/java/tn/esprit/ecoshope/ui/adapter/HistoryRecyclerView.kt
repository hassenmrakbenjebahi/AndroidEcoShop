package tn.esprit.ecoshope.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.model.History

class HistoryRecyclerView :RecyclerView.Adapter<HistoryRecyclerView.HistoryViewHolder>(){

    var historyList: ArrayList<History> = ArrayList()

    fun setList(list: ArrayList<History>){
        this.historyList = list
        notifyDataSetChanged()
    }
    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_prod: ImageView = itemView.findViewById(R.id.iv_product)
        var tv_nameProd: TextView = itemView.findViewById(R.id.tv_name_prod)
        var tv_descriptionProd: TextView = itemView.findViewById(R.id.tv_desc_product)
        var bv_favoris: ImageButton = itemView.findViewById(R.id.bv_favoris)

        fun bind(history: History){
            iv_prod.setImageResource(history.imageId)
            tv_nameProd.text = history.name
            tv_descriptionProd.text = history.description
            // condition of favorite button
            bv_favoris.setImageResource(
                if (history.isFavorite) R.drawable.baseline_favorite_24
                else R.drawable.baseline_favorite_border_24
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        // Configurez les vues à l'intérieur de chaque élément
        var history: History = historyList.get(position)
        holder.bind(history)
    }
}