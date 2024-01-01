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
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Locale


class HistoryRecyclerView :RecyclerView.Adapter<HistoryRecyclerView.HistoryViewHolder>() {

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

                // Static
               iv_prod.setImageResource(history.imageId)
                tv_nameProd.text = history.name
                tv_dateProd.text = history.date


                // Dynamic
              /*  Picasso.get().load(history.productId.image).into(iv_prod)
                tv_nameProd.text = history.productId.name
                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                tv_dateProd.text = dateFormat.format(history.date) */


                // Condition de bouton favori
                bv_favoris.setImageResource(
                    history.isFavorite      // STATIC
                  // history.productId.isFavorite // dynamic
                        .takeIf { it }
                        ?.let { R.drawable.baseline_favorite_24 }
                        ?: R.drawable.baseline_favorite_border_24
                )

                bv_favoris.setOnClickListener {
                    // Inverser l'état isFavorite lorsque le bouton est cliqué
                    history.isFavorite = !history.isFavorite   // STATIC
                  // history.productId.isFavorite = !history.productId.isFavorite  // dynamic

                    // Mettre à jour l'image du bouton
                    bv_favoris.setImageResource(
                        history.isFavorite              // STATIC

                      //  history.productId.isFavorite // dynamic
                            .takeIf { it }
                            ?.let { R.drawable.baseline_favorite_24 }
                            ?: R.drawable.baseline_favorite_border_24
                    )
                }

                // Product click
                itemView.setOnClickListener {
                    onListItemHistoryClick?.onItemHistoryClick(history)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false)
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

    // methode pour la suppression d'un ITEM
    fun removeAt(position: Int) {
        historyList.removeAt(position)
        notifyItemRemoved(position)
    }

     fun onItemMove(fromPosition: Int, toPosition: Int) {
        // Mettez à jour la liste lorsque les éléments sont déplacés
        Collections.swap(historyList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    interface ItemTouchHelperAdapter {
        fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    }

}