package tn.esprit.ecoshope.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.model.Product
import tn.esprit.ecoshope.util.ApiHistoriqueAchat

class ProductDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_product_details)

        // Views
        val layoutHeader : TextView = findViewById(R.id.layoutHeader)
        val ivProductDetails: ImageView = findViewById(R.id.iv_product_details)
        val tvNameDetails: TextView = findViewById(R.id.tv_name_details)
        val tvDescriptionDetails: TextView = findViewById(R.id.tv_description_details)
        val tvCarbonFootprint: TextView = findViewById(R.id.tv_carbonFootprint)
        val tvWaterConsumption: TextView = findViewById(R.id.tv_waterConsumption)
        val tvRecyclability: TextView = findViewById(R.id.tv_recyclability)

        // get productId from intent
        val productId = intent.getStringExtra("productId")

        // API
        val apiService = ApiHistoriqueAchat.create()
        lifecycleScope.launch(Dispatchers.Main) {
            try {
                val response = apiService.getOnce(productId ?: "")
                if (response.isSuccessful) {
                    val product = response.body()

                    layoutHeader.text = "Product Details"
                    //charger l'image depuis l'URL
                    Picasso.get()
                        .load(product?.image)
                        .placeholder(R.drawable.baseline_find_replace_24)
                        .error(R.drawable.baseline_error_24)
                        .into(ivProductDetails)
                    tvNameDetails.text = product?.name
                    tvDescriptionDetails.text = product?.description
                    tvCarbonFootprint.text = "Carbon Footprint: ${product?.carbonFootprint}"
                    tvWaterConsumption.text = "Water Consumption: ${product?.waterConsumption}"
                    tvRecyclability.text = "Recyclability: ${product?.recyclability}"

                } else {
                    showToast("Failed to fetch product details.")
                }
            } catch (e: Exception) {
                showToast("An error occurred. Please try again later.")
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}