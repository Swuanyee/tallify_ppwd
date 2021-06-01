package tallify.greengoldindustries.tallify

import android.content.Context
import android.content.Intent
import android.icu.util.Output
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.CellStyle
import tallify.greengoldindustries.tallify.databinding.ActivityMainBinding
import tallify.greengoldindustries.tallify.databinding.ActivitySecondBinding
import tallify.greengoldindustries.tallify.databinding.MeasurementFormsBinding
import java.io.*
import java.time.LocalDateTime

class SecondActivity : AppCompatActivity() {
    private lateinit var binding_second: ActivitySecondBinding
    private lateinit var binding_template: MeasurementFormsBinding
    private lateinit var binding: ActivityMainBinding

    var counter_name= "String"
    var tally_date = "String"
    var supplier_name= "String"
    var invoice = "String"
    var ref = 0
    var boiler = "String"
    var species = "String"
    var status = "String"
    var measurement_unit = "String"

    var this_filename = "String"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding_second = ActivitySecondBinding.inflate(layoutInflater)
        binding_template = MeasurementFormsBinding.inflate(layoutInflater)
        /**
        count_0 = binding_second.textCount0.text.toString().toInt()
        count_2 = binding_second.textCount2.text.toString().toInt()
        count_3 = binding_second.textCount3.text.toString().toInt()
        count_4 = binding_second.textCount4.text.toString().toInt()
        count_5 = binding_second.textCount5.text.toString().toInt()
        count_6 = binding_second.textCount6.text.toString().toInt()
        count_7 = binding_second.textCount7.text.toString().toInt()
        count_8 = binding_second.textCount8.text.toString().toInt()
        count_9 = binding_second.textCount9.text.toString().toInt()
        count_10 = binding_second.textCount10.text.toString().toInt()
        count_11 = binding_second.textCount11.text.toString().toInt()
        count_12 = binding_second.textCount12.text.toString().toInt()
        count_13 = binding_second.textCount13.text.toString().toInt()
        count_14 = binding_second.textCount14.text.toString().toInt()

        width_0 = binding_second.textW0.text.toString().toInt()
        width_2 = binding_second.textW2.text.toString().toInt()
        width_3 = binding_second.textW3.text.toString().toInt()
        width_4 = binding_second.textW4.text.toString().toInt()
        width_5 = binding_second.textW5.text.toString().toInt()
        width_6 = binding_second.textW6.text.toString().toInt()
        width_7 = binding_second.textW7.text.toString().toInt()
        width_8 = binding_second.textW8.text.toString().toInt()
        width_9 = binding_second.textW9.text.toString().toInt()
        width_10 = binding_second.textW10.text.toString().toInt()
        width_11 = binding_second.textW11.text.toString().toInt()
        width_12 = binding_second.textW12.text.toString().toInt()
        width_13 = binding_second.textW13.text.toString().toInt()
        width_14 = binding_second.textW14.text.toString().toInt()

        thickness_0 = binding_second.textT0.text.toString().toInt()
        thickness_2 = binding_second.textT2.text.toString().toInt()
        thickness_3 = binding_second.textT3.text.toString().toInt()
        thickness_4 = binding_second.textT4.text.toString().toInt()
        thickness_5 = binding_second.textT5.text.toString().toInt()
        thickness_6 = binding_second.textT6.text.toString().toInt()
        thickness_7 = binding_second.textT7.text.toString().toInt()
        thickness_8 = binding_second.textT8.text.toString().toInt()
        thickness_9 = binding_second.textT9.text.toString().toInt()
        thickness_10 = binding_second.textT10.text.toString().toInt()
        thickness_11 = binding_second.textT11.text.toString().toInt()
        thickness_12 = binding_second.textT12.text.toString().toInt()
        thickness_13 = binding_second.textT13.text.toString().toInt()
        thickness_14 = binding_second.textT14.text.toString().toInt()

        length_0 = binding_second.textL0.text.toString().toInt()
        length_2 = binding_second.textL2.text.toString().toInt()
        length_3 = binding_second.textL3.text.toString().toInt()
        length_4 = binding_second.textL4.text.toString().toInt()
        length_5 = binding_second.textL5.text.toString().toInt()
        length_6 = binding_second.textL6.text.toString().toInt()
        length_7 = binding_second.textL7.text.toString().toInt()
        length_8 = binding_second.textL8.text.toString().toInt()
        length_9 = binding_second.textL9.text.toString().toInt()
        length_10 = binding_second.textL10.text.toString().toInt()
        length_11 = binding_second.textL11.text.toString().toInt()
        length_12 = binding_second.textL12.text.toString().toInt()
        length_13 = binding_second.textL13.text.toString().toInt()
        length_14 = binding_second.textL14.text.toString().toInt()
        **/
        val bundle = intent.getBundleExtra("main_activity_data")
        counter_name = bundle?.getString("name").toString()
        tally_date = bundle?.getString("date").toString()
        supplier_name= bundle?.getString("supplier").toString()
        invoice = bundle?.getString("invoice").toString()
        ref = bundle?.getInt("ref").toString().toInt()-1
        boiler = bundle?.getString("boiler").toString()
        species = bundle?.getString("species").toString()
        status = bundle?.getString("status").toString()
        measurement_unit = bundle?.getString("unit").toString()

        val view_second = binding_second.root
        binding_second.textHeader.text = "Tallied by "+ counter_name
        setContentView(view_second)
        println(counter_name)

        this_filename = tally_date + " " + counter_name + " " + ref.toString() + ".txt"
        try{
            val fileOutputStream: FileOutputStream = openFileOutput(this_filename, Context.MODE_PRIVATE)
            val outPutWriter = OutputStreamWriter(fileOutputStream)
            val heading_arr= arrayOf("Date","Reference","Name", "Supplier", "Species","Status","Grade", "Unit",
            "W", "T", "L", "CBT/CBM", "Boiler", "Quantity")
            for (child in heading_arr) {
                outPutWriter.write(child)
                outPutWriter.write(",")
            }
            outPutWriter.close()
            print("Successfully created .txt file")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding_second.btnDone.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val m_intent = Intent(this@SecondActivity, MainActivity::class.java)
                startActivity(m_intent)
            }
        })
    }

    fun handleClickButton(view: View) {
        with(view as Button) {
            println("handle clicks")
            val this_btn = view.resources.getResourceEntryName(id)
            val id_string = this_btn
            val delim = "_"
            val arr = id_string.split(delim).toTypedArray()

            val this_width= "input_w_" + arr[2]
            val this_thickness= "input_t_" + arr[2]
            val this_length= "input_l_" + arr[2]
            val this_count = "text_count_" + arr[2]

            val width_id = resources.getIdentifier(this_width, "id", context.packageName)
            val thickness_id = resources.getIdentifier(this_thickness, "id", context.packageName)
            val length_id = resources.getIdentifier(this_length, "id", context.packageName)
            val count_id = resources.getIdentifier(this_count, "id", context.packageName)

            var width_value = this@SecondActivity.findViewById<EditText>(width_id)
            var thickness_value = this@SecondActivity.findViewById<EditText>(thickness_id)
            var length_value = this@SecondActivity.findViewById<EditText>(length_id)
            var count_value = this@SecondActivity.findViewById<TextView>(count_id)
            var add_count = count_value.text.toString().toInt()
            add_count += 1
            count_value.setText(add_count.toString())
            ref += 1
            try{
                val fileOutputStream: FileOutputStream = openFileOutput(this_filename, Context.MODE_APPEND)
                val outPutWriter = OutputStreamWriter(fileOutputStream)
                val append_arr = arrayOf(tally_date,
                    ref.toString(),
                    counter_name,
                    supplier_name,
                    species,
                    status,
                    view.text,
                    measurement_unit,
                    width_value.text,
                    thickness_value.text,
                    length_value.text,
                    "Tonnage",
                    boiler,
                    "1"
                )
                outPutWriter.append("\n")
                for (child in append_arr) {
                    outPutWriter.append(child)
                    outPutWriter.append(",")
                }
                outPutWriter.close()
                print("Successfully created .txt file")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            println("W" + width_value.text + " " + "T" +  thickness_value.text+ " " + "L" + length_value.text)
        }
    }


    fun handleSubtractButton(view: View) {
        with(view as Button) {
            println("handle clicks")
            val this_btn = view.resources.getResourceEntryName(id)
            val id_string = this_btn
            val delim = "_"
            val arr = id_string.split(delim).toTypedArray()

            val this_width= "input_w_" + arr[2]
            val this_thickness= "input_t_" + arr[2]
            val this_length= "input_l_" + arr[2]
            val this_count = "text_count_" + arr[2]

            val width_id = resources.getIdentifier(this_width, "id", context.packageName)
            val thickness_id = resources.getIdentifier(this_thickness, "id", context.packageName)
            val length_id = resources.getIdentifier(this_length, "id", context.packageName)
            val count_id = resources.getIdentifier(this_count, "id", context.packageName)

            var width_value = this@SecondActivity.findViewById<EditText>(width_id)
            var thickness_value = this@SecondActivity.findViewById<EditText>(thickness_id)
            var length_value = this@SecondActivity.findViewById<EditText>(length_id)
            var count_value = this@SecondActivity.findViewById<TextView>(count_id)
            var add_count = count_value.text.toString().toInt()
            add_count -= 1
            count_value.setText(add_count.toString())
            ref -= 1
            try{
                val fileOutputStream: FileOutputStream = openFileOutput(this_filename, Context.MODE_APPEND)
                val outPutWriter = OutputStreamWriter(fileOutputStream)
                val append_arr = arrayOf(tally_date,
                    "NA",
                    counter_name,
                    supplier_name,
                    species,
                    status,
                    view.text,
                    measurement_unit,
                    width_value.text,
                    thickness_value.text,
                    length_value.text,
                    "Tonnage",
                    boiler,
                    "-1"
                )
                outPutWriter.append("\n")
                for (child in append_arr) {
                    outPutWriter.append(child)
                    outPutWriter.append(",")
                }
                outPutWriter.close()
                print("Successfully created .txt file")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            println("W" + width_value.text + " " + "T" +  thickness_value.text+ " " + "L" + length_value.text)
        }
    }

}
