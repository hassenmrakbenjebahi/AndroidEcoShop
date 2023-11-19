package tn.esprit.ecoshope.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.model.ProductDetails

class ProductDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_product_details)

        // Récupérer les données du produit depuis l'intent
        val productDetails: ProductDetails? = intent.getParcelableExtra("PRODUCT_DETAILS")

        // Vérifier si les données sont disponibles
        if (productDetails != null) {
            // Mettre à jour les vues avec les détails du produit
            val layoutHeader : TextView = findViewById(R.id.layoutHeader)
            val ivProductDetails: ImageView = findViewById(R.id.iv_product_details)
            val tvNameDetails: TextView = findViewById(R.id.tv_name_details)
            val tvDescriptionDetails: TextView = findViewById(R.id.tv_description_details)
            val tvImpactDetails: TextView = findViewById(R.id.tv_impact_details)

            layoutHeader.text = "Product Details"
            ivProductDetails.setImageResource(productDetails.imageId)
            tvNameDetails.text = productDetails.name
            tvDescriptionDetails.text = productDetails.description
            tvImpactDetails.text = productDetails.impact
        }
    }
}