package tn.esprit.ecoshope.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.model.History
import tn.esprit.ecoshope.ui.adapter.HistoryRecyclerView


class HistoryActivity : AppCompatActivity() {

    lateinit var rv_showData: RecyclerView
    lateinit var tri_data: Spinner
    lateinit var historyAdapter: HistoryRecyclerView

     private var historyList: ArrayList<History> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Initialisation des vues
        tri_data = findViewById(R.id.tri_history);
        rv_showData = findViewById(R.id.recViewHistory);

        // Configurer l'adaptateur pour RecyclerView avec la liste d'achats
        historyAdapter.setList(historyList)

        // Configurer le RecyclerView
        val layoutManager = LinearLayoutManager(this)
        rv_showData.setLayoutManager(layoutManager)

        // Initialiser et configurer l'adaptateur
        historyAdapter = HistoryRecyclerView()
        rv_showData.adapter = historyAdapter // Associer l'adaptateur au RecyclerView

        // Testing with data simulation(Static)
        /* val Data = createData()
        historyAdapter.setList(Data) */

        // ***************************** SPINNER ************************************************************
        // Configurer l'adaptateur pour les options du Spinner
        val adapter = ArrayAdapter.createFromResource(this, R.array.sort_options, android.R.layout.simple_spinner_item )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tri_data.adapter = adapter

        // Écouter les sélections du Spinner
        tri_data.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val selectedOption = parentView?.getItemAtPosition(position).toString()

                // Mettre à jour l'affichage en fonction de l'option sélectionnée
                when (selectedOption) {
                    "Product name" -> {
                        // Trier l'historique par montant dépensé
                        historyList.sortBy { it.name }
                    }
                    /*"Date" -> {
                        // Trier l'historique par date
                        //historyList.sortBy { it.date }
                    }
                    "Impact environnemental" -> {
                        historyList.sortBy { it.environmentalImpact }
                    }*/
                }
                // Actualiser l'adaptateur avec la nouvelle liste triée
                historyAdapter.setList(historyList)
            }

                override fun onNothingSelected(parentView: AdapterView<*>?) {

                }

        }


        /*private fun createData(): ArrayList<History> {
            return arrayListOf(
                History(R.drawable.product, "Product 1", "Description 1", false),
                History(R.drawable.product, "Product 2", "Description 2", true),
            )
        } */
    }
}