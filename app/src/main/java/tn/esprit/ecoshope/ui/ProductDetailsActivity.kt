package tn.esprit.ecoshope.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.model.History
import tn.esprit.ecoshope.model.Product

class ProductDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_product_details)

        // Views
        val header = findViewById<TextView>(R.id.tvHeaderText)
        header.text="Product Details"

        val ivProductDetails: ImageView = findViewById(R.id.iv_product_details)
        val tvNameDetails: TextView = findViewById(R.id.tv_name_details)
        val tvDescriptionDetails: TextView = findViewById(R.id.tv_description_details)
        val tvCarbonFootprint: TextView = findViewById(R.id.tv_carbonFootprint)
        val tvWaterConsumption: TextView = findViewById(R.id.tv_waterConsumption)
        val tvRecyclability: TextView = findViewById(R.id.tv_recyclability)

        // STATIC :  get productId from intent
        val productDetails = intent.getParcelableExtra<History>("productDetails")
        if (productDetails != null) {
            ivProductDetails.setImageResource(productDetails.imageId)
            tvNameDetails.text = productDetails.name
            tvDescriptionDetails.text = "Description: ${productDetails.description}"
            tvCarbonFootprint.text = "Carbon Footprint: ${productDetails.carbonFootprint}"
            tvWaterConsumption.text = "Water Consumption: ${productDetails.waterConsumption}"
            tvRecyclability.text = "Recyclability: ${productDetails.recyclability}"
        }



        // Dynamic : get product from intent
      /*  val product = intent.getParcelableExtra<Product>("product")
        if (product != null) {
             Picasso.get().load(product.image).into(ivProductDetails)
             tvNameDetails.text = product.name
             tvDescriptionDetails.text = "Description: ${product.description}"
             tvCarbonFootprint.text = "Carbon Footprint: ${product.carbonFootprint}"
             tvWaterConsumption.text = "Water Consumption: ${product.waterConsumption}"
             tvRecyclability.text = "Recyclability: ${product.recyclability}"
        }  */

    }

}