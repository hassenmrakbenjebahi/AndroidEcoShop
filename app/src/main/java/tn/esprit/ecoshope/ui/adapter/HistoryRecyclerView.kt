package tn.esprit.ecoshope.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.model.History

class HistoryRecyclerView :RecyclerView.Adapter<HistoryRecyclerView.HistoryViewHolder>(){

    // methode de l'interface "OnListItemHistoryClick"
    var onListItemHistoryClick:OnListItemHistoryClick? = null

    var historyList: ArrayList<History> = ArrayList()

    fun setList(list: ArrayList<History>){
        this.historyList = list
        notifyDataSetChanged()
    }
    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_prod: ImageView = itemView.findViewById(R.id.iv_product)
        var tv_nameProd: TextView = itemView.findViewById(R.id.tv_name_prod)
        var tv_dateProd: TextView = itemView.findViewById(R.id.tv_date_product)
        var bv_favoris: ImageButton = itemView.findViewById(R.id.bv_favoris)

        fun bind(history: History){
            with(itemView) {
                Picasso.get()
                    .load(history.imageId)
                    .placeholder(R.drawable.baseline_find_replace_24) // Image de remplacement
                    .error(R.drawable.baseline_error_24) // Image à afficher en cas d'erreur de chargement
                    .into(iv_prod)

                tv_nameProd.text = history.nameProduct
                tv_dateProd.text = history.date

                // Condition de bouton favori
                bv_favoris.setImageResource(
                    history.isFavorite
                        .takeIf { it }
                        ?.let { R.drawable.baseline_favorite_24 }
                        ?: R.drawable.baseline_favorite_border_24
                )

                // method click of product
                setupClickListeners(history)
            }
        }

        private fun setupClickListeners(history: History) {
            // Product click
            itemView.setOnClickListener {
                onListItemHistoryClick?.onItemHistoryClick(history)
            }
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