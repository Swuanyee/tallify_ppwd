package tallify.greengoldindustries.tallify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import tallify.greengoldindustries.tallify.databinding.ActivityMainBinding
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val Log = Logger.getLogger(MainActivity::class.java.name)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val boiler_type: Spinner = findViewById(R.id.dropdown_boiler)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.boiler_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            boiler_type.adapter = adapter
        }

        val species: Spinner = findViewById(R.id.dropdown_species)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.species_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            species.adapter = adapter
        }

        val log_status: Spinner = findViewById(R.id.dropdown_status)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.log_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            log_status.adapter = adapter
        }

        val measurement_unit: Spinner = findViewById(R.id.dropdown_measurement)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.unit,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            measurement_unit.adapter = adapter
        }
        Log.info("onCreate")
        binding.btnTally.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                try {
                    val m_intent = Intent(this@MainActivity, SecondActivity::class.java)
                    val m_bundle = Bundle()
                    m_bundle.putString("name", binding.inputName.text.toString())
                    m_bundle.putString("date", binding.inputDate.text.toString())
                    m_bundle.putString("supplier", binding.inputSupplier.text.toString())
                    m_bundle.putString("invoice", binding.inputInvoiceNum.text.toString())
                    m_bundle.putInt("ref", binding.inputRef.text.toString().toInt())
                    m_bundle.putString("boiler", binding.dropdownBoiler.selectedItem.toString())
                    m_bundle.putString("species", binding.dropdownSpecies.selectedItem.toString())
                    m_bundle.putString("status", binding.dropdownStatus.selectedItem.toString())
                    m_bundle.putString("unit", binding.dropdownMeasurement.selectedItem.toString())
                    m_intent.putExtra("main_activity_data", m_bundle)
                    startActivity(m_intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@MainActivity,
                        "Warning! You have provided an invalid input! Please resubmit" +
                                " after correcting the necessary input.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }
}