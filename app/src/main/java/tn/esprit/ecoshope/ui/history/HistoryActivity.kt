package tn.esprit.ecoshope.ui.history

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.ActivityHistoryBinding
import tn.esprit.ecoshope.model.history.History
import tn.esprit.ecoshope.ui.history.Adapter.HistoryRecyclerView
import tn.esprit.ecoshope.ui.history.Adapter.OnListItemHistoryClick
import tn.esprit.ecoshope.util.ClientObject
import tn.esprit.ecoshope.util.retrofitUser.Api
import tn.esprit.ecoshope.util.retrofithistory.ApiHistoriqueAchat

class HistoryActivity : AppCompatActivity(), OnListItemHistoryClick {
    lateinit var binding: ActivityHistoryBinding

    lateinit var historyAdapter: HistoryRecyclerView

    private var historyList: ArrayList<History> = ArrayList()

    private var originalHistoryList: ArrayList<History> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                                val query = s.toString().toLowerCase()
                                historyList = ArrayList(originalHistoryList.filter { it.nameProduct.toLowerCase().contains(query) })
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

        // getAllHistory pour afficher l'historique de l'user
        loadHistoryData()
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

    private fun loadHistoryData() {
        val apiService = ClientObject.buildService(ApiHistoriqueAchat::class.java)
        val sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", "")
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch(Dispatchers.Main) {
            apiService.getAllHistory("$userId")
                .enqueue(object :Callback<List<History>>{
                    override fun onResponse(
                        call: Call<List<History>>,
                        response: Response<List<History>>,
                    ) {
                        if (response.isSuccessful) {
                            originalHistoryList = ArrayList(response.body() ?: emptyList())
                            historyList = ArrayList(originalHistoryList)
                            historyAdapter.setList(historyList)
                            showToast("History loaded successfully.")
                        }else{
                            when (response.code()) {
                                401 -> {
                                    // Code 401 : Unauthorized
                                    // Redirigez l'utilisateur vers la page de connexion ou affichez un message d'erreur
                                    showToast("Unauthorized. Redirecting to login.")
                                }
                                404 -> {
                                    // Code 404 : Not Found
                                    showToast("History not found.")
                                }
                                else -> {
                                    // Affichez un message générique d'erreur
                                    showToast("An error occurred.")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<History>>, t: Throwable) {
                        Toast.makeText(this@HistoryActivity, "Error", Toast.LENGTH_SHORT)
                            .show()
                    }

                })

          /*  try {
                val userId = sharedPreferences.getString("USER_ID", "")
                 apiService.getAllHistory(userId.toString())

                if (response.isSuccessful) {
                    originalHistoryList = ArrayList(response.body() ?: emptyList())
                    historyList = ArrayList(originalHistoryList)
                    historyAdapter.setList(historyList)
                    showToast("History loaded successfully.")
                } else {
                    when (response.code()) {
                        401 -> {
                            // Code 401 : Unauthorized
                            // Redirigez l'utilisateur vers la page de connexion ou affichez un message d'erreur
                            showToast("Unauthorized. Redirecting to login.")
                        }
                        404 -> {
                            // Code 404 : Not Found
                            showToast("History not found.")
                        }
                        else -> {
                            // Affichez un message générique d'erreur
                            showToast("An error occurred.")
                        }
                    }
                }
            } catch (e: Exception) {
                showToast("An error occurred. Please try again later.")
            }finally {
                binding.progressBar.visibility = View.GONE
            }*/
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemHistoryClick(history: History) {
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra("productId", history.productId)
        startActivity(intent)
    }
}