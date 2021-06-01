package tallify.greengoldindustries.tallify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.CellStyle
import tallify.greengoldindustries.tallify.databinding.ActivityMainBinding
import tallify.greengoldindustries.tallify.databinding.ActivitySecondBinding
import tallify.greengoldindustries.tallify.databinding.MeasurementFormsBinding
import java.time.LocalDateTime

class SecondActivity : AppCompatActivity() {
    private lateinit var binding_second: ActivitySecondBinding
    private lateinit var binding_template: MeasurementFormsBinding
    private lateinit var binding: ActivityMainBinding

    var counter_name= "String"
    var supplier_name= "String"
    var invoice = "String"
    var ref = 0
    var boiler = "String"
    var species = "String"
    var status = "String"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding_second = ActivitySecondBinding.inflate(layoutInflater)
        binding_template = MeasurementFormsBinding.inflate(layoutInflater)

        val bundle = intent.getBundleExtra("main_activity_data")
        counter_name = bundle?.getString("name").toString()
        supplier_name= bundle?.getString("supplier").toString()
        invoice = bundle?.getString("invoice").toString()
        ref = bundle?.getInt("ref").toString().toInt()
        boiler = bundle?.getString("boiler").toString()
        species = bundle?.getString("species").toString()
        status = bundle?.getString("status").toString()

        val view_second = binding_second.root
        binding_second.textHeader.text = "Tallied by "+ counter_name
        setContentView(view_second)
        println(counter_name)
        }

    fun handleClickButton (view: View) {
        with (view as Button) {
            var this_btn = view.resources.getResourceEntryName(id)
            val id_string = this_btn
            val delim = "_"
            val arr = id_string.split(delim).toTypedArray()
            println(binding_second.inputW0.text)
            val id_num = arr[2]
            println(counter_name)
        }
    }

    fun handleSubtractButton (view: View) {
        with (view as Button) {
            var this_btn = view.resources.getResourceEntryName(id)
            val id_string = this_btn
            val delim = "_"
            val arr = id_string.split(delim).toTypedArray()
            println(binding_second.inputW0.text)
            val id_num = arr[2]
            println(counter_name)
        }
    }
}
