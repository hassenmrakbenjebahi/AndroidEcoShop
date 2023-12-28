package tn.esprit.ecoshope.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.ActivityHistoryBinding
import tn.esprit.ecoshope.model.History
import tn.esprit.ecoshope.model.Product
import tn.esprit.ecoshope.ui.adapter.HistoryRecyclerView
import tn.esprit.ecoshope.ui.adapter.OnListItemHistoryClick



class HistoryActivity : AppCompatActivity(),  OnListItemHistoryClick{

    lateinit var binding: ActivityHistoryBinding

    lateinit var historyAdapter: HistoryRecyclerView

     private var historyList: ArrayList<History> = ArrayList()

     private var originalHistoryList: ArrayList<History> = ArrayList()

    private var favorisList: ArrayList<History> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Title of the page
        val header = findViewById<TextView>(R.id.tvHeaderText)
        header.text="Purchases History"

        // Configurer le RecyclerView
        val layoutManager = LinearLayoutManager(this)
        binding.recViewHistory.setLayoutManager(layoutManager)

        historyList.add(History(R.drawable.product, getString(R.string.prod1), getString(R.string.date1), isFavorite = false,
            description = "Destructeur D'insectes · Diffuseur De Parfum · Recharge Diffuseur Parfum ... FLYTOX CHOC COMBAT.",
            carbonFootprint = "250 KG",
            waterConsumption = "0 L",
            recyclability = "Non recyclable"))

        historyList.add(History(R.drawable.eau, getString(R.string.prod4) , getString(R.string.date4), isFavorite = false,
            description = "Corps liquide à la température et à la pression ordinaires, incolore, inodore, insipide, " +
                    "dont les molécules sont composées d'un atome d'oxygène et de deux atomes d'hydrogène.",
            carbonFootprint = "0 KG",
            waterConsumption = "1 L",
            recyclability = "Recyclable"))

        historyList.add(History( R.drawable.danao, getString(R.string.prod3) , getString(R.string.date3), isFavorite =false,
            description = "Epluchez les carottes et les oranges. Passez-les à la centrifugeuse. Ajoutez le lait et le miel ou le sirop d'agave et mélangez bien. " +
                    "Votre Danao maison se déguste hyper frais pour commencer la journée en douceur et en fraicheur ",
            carbonFootprint = "0.2 KG",
            waterConsumption = "3 L",
            recyclability = "Non recyclable"))

        historyList.add(History(R.drawable.papier, getString(R.string.prod2) , getString(R.string.date2), isFavorite = false,
            description = "Apparu en 1924, le mouchoir en papier, non lavable, est plus hygiénique et participe de l'ère du « tout-jetable  ",
            carbonFootprint = "40 KG",
            waterConsumption = "0 L",
            recyclability = "Non recyclable"))

        historyList.add(History(R.drawable.makrouna, getString(R.string.prod5) , getString(R.string.date5), isFavorite =false,
            description = "Les spaghetti ou spaghettis sont un plat de pâtes longues, fines et cylindriques, typique de la cuisine italienne",
            carbonFootprint = "20 KG",
            waterConsumption = "0 L",
            recyclability = "Non recyclable"))

        // Initialiser et configurer l'adaptateur
        historyAdapter = HistoryRecyclerView()
        binding.recViewHistory.adapter = historyAdapter // Associer l'adaptateur au RecyclerView
        historyAdapter.setList(historyList)   // Configurer l'adaptateur pour RecyclerView avec la liste d'achats

        historyAdapter.onListItemHistoryClick  = this

        // to delete ITEM
        val swipeHandler = object : SwipeToDeleteCallback(historyAdapter) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Appeler la fonction removeAt lorsqu'un élément est swipé
                val position = viewHolder.adapterPosition
                historyAdapter.removeAt(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recViewHistory)

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
                        binding.edtProductName.visibility = View.GONE
                        historyAdapter.setList(historyList)
                    }
                    "Favorite" -> {
                        binding.edtProductName.visibility = View.GONE
                        favorisList = ArrayList(historyList.filter { it.isFavorite })
                        historyAdapter.setList(favorisList)
                    }
                    "Product name" -> {
                        binding.edtProductName.visibility = View.VISIBLE

                        binding.edtProductName.addTextChangedListener(object : TextWatcher {
                            override fun afterTextChanged(s: Editable?) {
                                val query = s.toString().toLowerCase()
                                originalHistoryList = ArrayList(historyList.filter { it.nameProduct.toLowerCase().contains(query) })
                                historyAdapter.setList(originalHistoryList)
                            }
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                        })
                    }

                }
            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

    }

    override fun onItemHistoryClick(history: History) {
        val intent = Intent(this, ProductDetailsActivity::class.java)
        val productDetails = History(
            imageId = history.imageId,
            nameProduct = history.nameProduct,
            date = history.date,
            isFavorite = history.isFavorite,
            description = history.description,
            carbonFootprint = history.carbonFootprint,
            waterConsumption = history.waterConsumption,
            recyclability = history.recyclability
        )
        intent.putExtra("productDetails", productDetails)
        startActivity(intent)
    }

}