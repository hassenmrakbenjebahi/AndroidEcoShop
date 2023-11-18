package tn.esprit.ecoshope.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.ActivityHistoryBinding
import tn.esprit.ecoshope.model.History
import tn.esprit.ecoshope.ui.adapter.HistoryRecyclerView
import tn.esprit.ecoshope.ui.adapter.OnListItemHistoryClick


class HistoryActivity : AppCompatActivity(),  OnListItemHistoryClick{

    lateinit var binding: ActivityHistoryBinding

    lateinit var historyAdapter: HistoryRecyclerView

     private var historyList: ArrayList<History> = ArrayList()

     private var originalHistoryList: ArrayList<History> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Copier la liste initiale dans historyList
        historyList = ArrayList(originalHistoryList)

        // Configurer le RecyclerView
        val layoutManager = LinearLayoutManager(this)
        binding.recViewHistory.setLayoutManager(layoutManager)

        // Initialiser et configurer l'adaptateur
        historyAdapter = HistoryRecyclerView()
        binding.recViewHistory.adapter = historyAdapter // Associer l'adaptateur au RecyclerView
        historyAdapter.setList(historyList)   // Configurer l'adaptateur pour RecyclerView avec la liste d'achats

        // ***************************** SPINNER ************************************************************
        // Configurer l'adaptateur pour les options du Spinner
        val adapter = ArrayAdapter.createFromResource(this, R.array.sort_options, android.R.layout.simple_spinner_item )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.triHistory.adapter = adapter

        // rendre "EditText" invisible
        binding.edtProductName.visibility = View.GONE

        // Écouter les sélections du Spinner
        binding.triHistory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val selectedOption = parentView?.getItemAtPosition(position).toString()

                // Mettre à jour l'affichage en fonction de l'option sélectionnée
                when (selectedOption) {
                    "All" -> {
                        // Afficher tous les produits
                        // Réinitialiser la liste à la liste originale
                        historyList = ArrayList(originalHistoryList)
                    }
                    "Favorite" -> {
                        // afficher uniquement les produits favoris
                        historyList = ArrayList(originalHistoryList.filter { it.isFavorite })
                    }
                    "Product name" -> {
                        binding.edtProductName.visibility = View.VISIBLE

                        binding.edtProductName.addTextChangedListener(object : TextWatcher {
                            override fun afterTextChanged(s: Editable?) {
                                val query = s.toString().lowercase()
                                historyList = ArrayList(originalHistoryList.filter { it.name.lowercase().contains(query) })
                                historyAdapter.setList(historyList)
                            }
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                        })
                    }

                }
                // Actualiser l'adaptateur avec la nouvelle liste triée
                historyAdapter.setList(historyList)
            }
                override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        historyAdapter.onListItemHistoryClick  = this
    }

    /* "onSaveInstanceState" et "onRestoreInstanceState" garantissent que originalHistoryList
     est sauvegardé et restauré lors de changements d'orientation
     ou d'autres modifications d'état de l'activité.*/
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("originalHistoryList", originalHistoryList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        originalHistoryList = savedInstanceState.getParcelableArrayList("originalHistoryList") ?: ArrayList()
    }

    override fun onItemHistoryClick(history: History) {
        // clique sur un produit pour aller au detaille de produit
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra("PRODUCT_DETAILS", history)
        startActivity(intent)
    }

}