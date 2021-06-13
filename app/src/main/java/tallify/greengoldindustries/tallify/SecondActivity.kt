package tallify.greengoldindustries.tallify

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.icu.util.Output
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import tallify.greengoldindustries.tallify.databinding.ActivityMainBinding
import tallify.greengoldindustries.tallify.databinding.ActivitySecondBinding
import tallify.greengoldindustries.tallify.databinding.MeasurementFormsBinding
import java.io.*
import kotlin.random.Random

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

    var btn_count = 0

    var this_filename = "String"
    var this_path= "/storage/emulated/0/Download/"

    var width_count = 10000
    var thickness_count = 20000
    var length_count = 30000
    var orderName_count = 400000
    var count_count = 50000

    var a_count = 11000
    var b_count = 21000
    var c_count = 31000
    var q_count = 41000
    var r_count = 51000
    var u_count = 61000
    var subtract_count = 71000

    var total_ton = 0.000000
    var total_pieces = 0

    var this_dir = "None"

    var ref_override = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding_second = ActivitySecondBinding.inflate(layoutInflater)
        binding_template = MeasurementFormsBinding.inflate(layoutInflater)

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
        setContentView(view_second)
        var randomNum = Random.nextInt(1, 1000000).toString()
        println(counter_name)

        this_filename = counter_name + "_" + ref.toString() + "_" + randomNum + ".txt"

        try{
            val fileOutputStream: FileOutputStream = openFileOutput(this_filename, Context.MODE_PRIVATE)
            val outPutWriter = OutputStreamWriter(fileOutputStream)
            val heading_arr= arrayOf("Date","Reference","Name", "Supplier","Order", "Species","Status","Grade", "Unit",
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
                println(getExternalFilesDir(null))
                var source_file= File("/data/data/tallify.greengoldindustries.tallify/files/" + this_filename)
                var target_file= File("/storage/emulated/0/Download/" + this_filename)
                println(target_file.canWrite())
                try {
                    source_file.copyTo(target_file)
                } catch (e: java.lang.Exception) {
                    println(e)
                }
                val m_intent = Intent(this@SecondActivity, MainActivity::class.java)
                startActivity(m_intent)
            }
        })
    }

    fun handleAddTally(view: View) {
        with(view as Button) {
            btn_count += 1

            val this_btn = view.resources.getResourceEntryName(id)
            var container_overall = LinearLayout(this@SecondActivity)
            container_overall.setOrientation(LinearLayout.VERTICAL)

            var container_inputs= LinearLayout(this@SecondActivity)
            container_inputs.setOrientation(LinearLayout.HORIZONTAL)
            var text_width= TextView(this@SecondActivity)
            text_width.setText("W:")
            var input_width= EditText(this@SecondActivity)
            text_width.setWidth(30)
            input_width.setWidth(90)
            input_width.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL)
            input_width.setRawInputType(Configuration.KEYBOARD_12KEY)
            input_width.id = width_count+btn_count

            var text_thickness= TextView(this@SecondActivity)
            text_thickness.setText("T:")
            var input_thickness= EditText(this@SecondActivity)
            text_thickness.setWidth(30)
            input_thickness.setWidth(90)
            input_thickness.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL)
            input_thickness.setRawInputType(Configuration.KEYBOARD_12KEY)
            if (status == "Log") {
                input_thickness.setText("0")
            } else {
                input_thickness.setText("")
            }

            var text_length= TextView(this@SecondActivity)
            text_length.setText("L:")
            var input_length= EditText(this@SecondActivity)
            text_length.setWidth(30)
            input_length.setWidth(90)
            input_length.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL)
            input_length.setRawInputType(Configuration.KEYBOARD_12KEY)

            var text_unit= TextView(this@SecondActivity)
            text_unit.setText("Unit:")
            var input_unit= EditText(this@SecondActivity)
            if (measurement_unit == "Imperial") {
                input_unit.setText("ft")
            } else {
                input_unit.setText("mm")
            }
            text_unit.setWidth(50)
            input_unit.setWidth(70)

            var text_count= TextView(this@SecondActivity)
            text_count.setText("0")
            text_count.setWidth(50)

            container_inputs.addView(text_width)
            container_inputs.addView(input_width)
            container_inputs.addView(text_thickness)
            container_inputs.addView(input_thickness)
            container_inputs.addView(text_length)
            container_inputs.addView(input_length)
            container_inputs.addView(text_unit)
            container_inputs.addView(input_unit)
            container_inputs.addView(text_count)

            container_overall.addView(container_inputs)

            var container_orderName= LinearLayout(this@SecondActivity)
            container_orderName.setOrientation(LinearLayout.HORIZONTAL)
            var text_orderName= TextView(this@SecondActivity)
            text_orderName.setText("Order Name:")
            var input_orderName= EditText(this@SecondActivity)
            text_orderName.setWidth(100)
            input_orderName.setWidth(150)
            input_orderName.setText("NA")
            var input_refChange= EditText(this@SecondActivity)
            input_refChange.setWidth(150)
            input_refChange.setText("NA")
            input_orderName.setId(orderName_count+btn_count)

            container_orderName.addView(text_orderName)
            container_orderName.addView(input_orderName)
            container_orderName.addView(input_refChange)
            container_overall.addView(container_orderName)

            var container_buttons= LinearLayout(this@SecondActivity)
            container_buttons.setOrientation(LinearLayout.HORIZONTAL)
            var btn_a = Button(this@SecondActivity)
            btn_a.id = a_count + btn_count

            var btn_b = Button(this@SecondActivity)
            var btn_c = Button(this@SecondActivity)
            var btn_q = Button(this@SecondActivity)
            var btn_r = Button(this@SecondActivity)
            var btn_u = Button(this@SecondActivity)
            var btn_subtract = Button(this@SecondActivity)

            btn_a.setText("A")
            btn_b.setText("B")
            btn_c.setText("C")
            btn_q.setText("Q")
            btn_r.setText("R")
            btn_u.setText("U")
            btn_subtract.setText("-")

            btn_a.id = a_count + btn_count
            var btn_a_int = a_count + btn_count
            btn_a.setTag(btn_a_int.toString())
            println(btn_a.id)
            btn_b.id = b_count + btn_count
            btn_c.id = c_count + btn_count
            btn_q.id = q_count + btn_count
            btn_r.id = r_count + btn_count
            btn_u.id = u_count + btn_count
            btn_subtract.id = subtract_count + btn_count

            container_buttons.addView(btn_a)
            container_buttons.addView(btn_b)
            container_buttons.addView(btn_c)
            container_buttons.addView(btn_q)
            container_overall.addView(container_buttons)

            var container_subtract= LinearLayout(this@SecondActivity)
            container_subtract.setOrientation(LinearLayout.HORIZONTAL)
            container_subtract.addView(btn_r)
            container_subtract.addView(btn_u)
            container_subtract.addView(btn_subtract)
            container_overall.addView(container_subtract)

            binding_second.containerWhole.addView(container_overall)

            btn_a.setOnClickListener(View.OnClickListener { view ->
                println("Clicked btn A")
                total_pieces += 1
                binding_second.totalNumPieces.setText(total_pieces.toString())
                var delim = "."
                var width_feet = 0
                var width_inches = 0
                var thickness_feet = 0
                var thickness_inches = 0
                var length_feet = 0
                var length_inches = 0
                var ton = 0.000000

                var width_arr = input_width.text.toString().split(delim)
                var thickness_arr = input_thickness.text.toString().split(delim)
                var length_arr = input_length.text.toString().split(delim)

                width_feet = width_arr[0].toInt()
                thickness_feet = thickness_arr[0].toInt()
                println("Clicked btn A")
                try {
                    width_inches = width_arr[1].toInt()
                    print(width_inches)
                } catch(e: Exception) {}
                try {
                    thickness_inches = thickness_arr[1].toInt()
                } catch(e: Exception) {}
                try {
                    length_inches = length_arr[1].toInt()
                    print(length_inches)
                } catch(e: Exception) {}

                if (status == "Log" && input_unit.text.toString() == "ft") {
                    var girdth_width = width_feet * 12 + width_inches
                    var log_length = input_length.text.toString().toDouble()
                    ton = (girdth_width * girdth_width * log_length / 115200).toDouble()
                    total_ton += ton
                } else if (status == "Conversion" && input_unit.text.toString() == "ft") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 7200).toDouble()
                    total_ton += ton
                } else if (status == "S4S" && input_unit.text.toString() == "ft") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 7200).toDouble()
                    total_ton += ton
                } else if (status == "Log" && input_unit.text.toString() == "mm") {
                    ton = 0.000000
                } else if (status == "Conversion" && input_unit.text.toString() == "mm") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 1415800000).toDouble()
                    total_ton += ton
                } else if (status == "S4S" && input_unit.text.toString() == "mm") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 1415800000).toDouble()
                    total_ton += ton
                }


                var num_of_wood = text_count.text.toString().toInt() + 1
                binding_second.totalNumTon.setText("%.5f".format(total_ton).toString())

                text_count.setText(num_of_wood.toString())
                ref += 1
                if (input_refChange.text.toString() == "NA") {
                    ref_override = ref
                } else {
                    ref_override = input_refChange.text.toString().toInt()
                    ref -= 1
                }

                print(text_count.text)
                try{
                    val fileOutputStream: FileOutputStream = openFileOutput(this_filename, Context.MODE_APPEND)
                    val outPutWriter = OutputStreamWriter(fileOutputStream)
                    val append_arr = arrayOf(tally_date,
                        ref_override.toString(),
                        counter_name,
                        supplier_name,
                        input_orderName.text,
                        species,
                        status,
                        "A",
                        input_unit.text,
                        input_width.text,
                        input_thickness.text,
                        input_length.text,
                        "%.5f".format(ton).toString(),
                        boiler,
                        "1"
                    )
                    outPutWriter.append("\n")
                    for (child in append_arr) {
                        outPutWriter.append(child)
                        outPutWriter.append(",")
                    }
                    outPutWriter.close()
                    print("\n Closing outputwrite")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                btn_a.setTextColor(Color.parseColor("#FF0000"))
                btn_b.setTextColor(Color.parseColor("#000000"))
                btn_c.setTextColor(Color.parseColor("#000000"))
                btn_q.setTextColor(Color.parseColor("#000000"))
                btn_r.setTextColor(Color.parseColor("#000000"))
                btn_u.setTextColor(Color.parseColor("#000000"))
                btn_subtract.setTextColor(Color.parseColor("#000000"))
                input_refChange.setText("NA")
                //input_orderName.setText("NA")
            })

            btn_b.setOnClickListener(View.OnClickListener { view ->
                total_pieces += 1
                binding_second.totalNumPieces.setText(total_pieces.toString())
                var delim = "."
                var width_feet = 0
                var width_inches = 0
                var thickness_feet = 0
                var thickness_inches = 0
                var length_feet = 0
                var length_inches = 0
                var ton = 0.000000

                var width_arr = input_width.text.toString().split(delim)
                var thickness_arr = input_thickness.text.toString().split(delim)
                var length_arr = input_length.text.toString().split(delim)

                width_feet = width_arr[0].toInt()
                thickness_feet = thickness_arr[0].toInt()
                length_feet = length_arr[0].toInt()
                println("Clicked btn A")
                try {
                    width_inches = width_arr[1].toInt()
                } catch(e: Exception) {}
                try {
                    thickness_inches = thickness_arr[1].toInt()
                } catch(e: Exception) {}
                try {
                    length_inches = length_arr[1].toInt()
                } catch(e: Exception) {}

                println(length_feet)
                println(length_inches)

                if (status == "Log" && input_unit.text.toString() == "ft") {
                    var girdth_width = width_feet * 12 + width_inches
                    var log_length = input_length.text.toString().toDouble()
                    ton = (girdth_width * girdth_width * log_length / 115200).toDouble()
                    total_ton += ton
                } else if (status == "Conversion" && input_unit.text.toString() == "ft") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 7200).toDouble()
                    total_ton += ton
                } else if (status == "S4S" && input_unit.text.toString() == "ft") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 7200).toDouble()
                    total_ton += ton
                } else if (status == "Log" && input_unit.text.toString() == "mm") {
                    ton = 0.000000
                } else if (status == "Conversion" && input_unit.text.toString() == "mm") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 1415800000).toDouble()
                    total_ton += ton
                } else if (status == "S4S" && input_unit.text.toString() == "mm") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 1415800000).toDouble()
                    total_ton += ton
                }

                binding_second.totalNumTon.setText("%.5f".format(total_ton).toString())
                var num_of_wood = text_count.text.toString().toInt() + 1
                text_count.setText(num_of_wood.toString())
                ref += 1
                if (input_refChange.text.toString() == "NA") {
                    ref_override = ref
                } else {
                    ref_override = input_refChange.text.toString().toInt()
                    ref -= 1
                }
                print(text_count.text)
                try{
                    val fileOutputStream: FileOutputStream = openFileOutput(this_filename, Context.MODE_APPEND)
                    val outPutWriter = OutputStreamWriter(fileOutputStream)
                    val append_arr = arrayOf(tally_date,
                        ref_override.toString(),
                        counter_name,
                        supplier_name,
                        input_orderName.text,
                        species,
                        status,
                        "B",
                        input_unit.text,
                        input_width.text,
                        input_thickness.text,
                        input_length.text,
                        "%.5f".format(ton).toString(),
                        boiler,
                        "1"
                    )
                    outPutWriter.append("\n")
                    for (child in append_arr) {
                        outPutWriter.append(child)
                        outPutWriter.append(",")
                    }
                    outPutWriter.close()
                    print("\n Closing outputwrite")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                //input_orderName.setText("NA")
                btn_b.setTextColor(Color.parseColor("#FF0000"))
                btn_a.setTextColor(Color.parseColor("#000000"))
                btn_c.setTextColor(Color.parseColor("#000000"))
                btn_q.setTextColor(Color.parseColor("#000000"))
                btn_r.setTextColor(Color.parseColor("#000000"))
                btn_u.setTextColor(Color.parseColor("#000000"))
                btn_subtract.setTextColor(Color.parseColor("#000000"))
                input_refChange.setText("NA")
            })

            btn_c.setOnClickListener(View.OnClickListener { view ->
                total_pieces += 1
                binding_second.totalNumPieces.setText(total_pieces.toString())
                var delim = "."
                var width_feet = 0
                var width_inches = 0
                var thickness_feet = 0
                var thickness_inches = 0
                var length_feet = 0
                var length_inches = 0
                var ton = 0.000000

                var width_arr = input_width.text.toString().split(delim)
                var thickness_arr = input_thickness.text.toString().split(delim)
                var length_arr = input_length.text.toString().split(delim)

                width_feet = width_arr[0].toInt()
                thickness_feet = thickness_arr[0].toInt()
                length_feet = length_arr[0].toInt()
                println("Clicked btn A")
                try {
                    width_inches = width_arr[1].toInt()
                } catch(e: Exception) {}
                try {
                    thickness_inches = thickness_arr[1].toInt()
                } catch(e: Exception) {}
                try {
                    length_inches = length_arr[1].toInt()
                } catch(e: Exception) {}

                println(length_feet)
                println(length_inches)

                if (status == "Log" && input_unit.text.toString() == "ft") {
                    var girdth_width = width_feet * 12 + width_inches
                    var log_length = input_length.text.toString().toDouble()
                    ton = (girdth_width * girdth_width * log_length / 115200).toDouble()
                    total_ton += ton
                } else if (status == "Conversion" && input_unit.text.toString() == "ft") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 7200).toDouble()
                    total_ton += ton
                } else if (status == "S4S" && input_unit.text.toString() == "ft") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 7200).toDouble()
                    total_ton += ton
                } else if (status == "Log" && input_unit.text.toString() == "mm") {
                    ton = 0.000000
                } else if (status == "Conversion" && input_unit.text.toString() == "mm") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 1415800000).toDouble()
                    total_ton += ton
                } else if (status == "S4S" && input_unit.text.toString() == "mm") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 1415800000).toDouble()
                    total_ton += ton
                }

                binding_second.totalNumTon.setText("%.5f".format(total_ton).toString())
                var num_of_wood = text_count.text.toString().toInt() + 1
                text_count.setText(num_of_wood.toString())
                ref += 1
                if (input_refChange.text.toString() == "NA") {
                    ref_override = ref
                } else {
                    ref_override = input_refChange.text.toString().toInt()
                    ref -= 1
                }
                print(text_count.text)
                try{
                    val fileOutputStream: FileOutputStream = openFileOutput(this_filename, Context.MODE_APPEND)
                    val outPutWriter = OutputStreamWriter(fileOutputStream)
                    val append_arr = arrayOf(tally_date,
                        ref_override.toString(),
                        counter_name,
                        supplier_name,
                        input_orderName.text,
                        species,
                        status,
                        "C",
                        input_unit.text,
                        input_width.text,
                        input_thickness.text,
                        input_length.text,
                        "%.5f".format(ton).toString(),
                        boiler,
                        "1"
                    )
                    outPutWriter.append("\n")
                    for (child in append_arr) {
                        outPutWriter.append(child)
                        outPutWriter.append(",")
                    }
                    outPutWriter.close()
                    print("\n Closing outputwrite")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                btn_c.setTextColor(Color.parseColor("#FF0000"))
                btn_b.setTextColor(Color.parseColor("#000000"))
                btn_a.setTextColor(Color.parseColor("#000000"))
                btn_q.setTextColor(Color.parseColor("#000000"))
                btn_r.setTextColor(Color.parseColor("#000000"))
                btn_u.setTextColor(Color.parseColor("#000000"))
                btn_subtract.setTextColor(Color.parseColor("#000000"))
                input_refChange.setText("NA")
                //input_orderName.setText("NA")
            })

            btn_q.setOnClickListener(View.OnClickListener { view ->
                total_pieces += 1
                binding_second.totalNumPieces.setText(total_pieces.toString())
                var delim = "."
                var width_feet = 0
                var width_inches = 0
                var thickness_feet = 0
                var thickness_inches = 0
                var length_feet = 0
                var length_inches = 0
                var ton = 0.000000

                var width_arr = input_width.text.toString().split(delim)
                var thickness_arr = input_thickness.text.toString().split(delim)
                var length_arr = input_length.text.toString().split(delim)

                width_feet = width_arr[0].toInt()
                thickness_feet = thickness_arr[0].toInt()
                length_feet = length_arr[0].toInt()
                println("Clicked btn A")
                try {
                    width_inches = width_arr[1].toInt()
                } catch(e: Exception) {}
                try {
                    thickness_inches = thickness_arr[1].toInt()
                } catch(e: Exception) {}
                try {
                    length_inches = length_arr[1].toInt()
                } catch(e: Exception) {}

                println(length_feet)
                println(length_inches)

                if (status == "Log" && input_unit.text.toString() == "ft") {
                    var girdth_width = width_feet * 12 + width_inches
                    var log_length = input_length.text.toString().toDouble()
                    ton = (girdth_width * girdth_width * log_length / 115200).toDouble()
                    total_ton += ton
                } else if (status == "Conversion" && input_unit.text.toString() == "ft") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 7200).toDouble()
                    total_ton += ton
                } else if (status == "S4S" && input_unit.text.toString() == "ft") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 7200).toDouble()
                    total_ton += ton
                } else if (status == "Log" && input_unit.text.toString() == "mm") {
                    ton = 0.000000
                } else if (status == "Conversion" && input_unit.text.toString() == "mm") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 1415800000).toDouble()
                    total_ton += ton
                } else if (status == "S4S" && input_unit.text.toString() == "mm") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 1415800000).toDouble()
                    total_ton += ton
                }

                binding_second.totalNumTon.setText("%.5f".format(total_ton).toString())
                var num_of_wood = text_count.text.toString().toInt() + 1
                text_count.setText(num_of_wood.toString())
                ref += 1
                if (input_refChange.text.toString() == "NA") {
                    ref_override = ref
                } else {
                    ref_override = input_refChange.text.toString().toInt()
                    ref -= 1
                }
                print(text_count.text)
                try{
                    val fileOutputStream: FileOutputStream = openFileOutput(this_filename, Context.MODE_APPEND)
                    val outPutWriter = OutputStreamWriter(fileOutputStream)
                    val append_arr = arrayOf(tally_date,
                        ref_override.toString(),
                        counter_name,
                        supplier_name,
                        input_orderName.text,
                        species,
                        status,
                        "Q",
                        input_unit.text,
                        input_width.text,
                        input_thickness.text,
                        input_length.text,
                        "%.5f".format(ton).toString(),
                        boiler,
                        "1"
                    )
                    outPutWriter.append("\n")
                    for (child in append_arr) {
                        outPutWriter.append(child)
                        outPutWriter.append(",")
                    }
                    outPutWriter.close()
                    print("\n Closing outputwrite")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                //input_orderName.setText("NA")
                btn_q.setTextColor(Color.parseColor("#FF0000"))
                btn_b.setTextColor(Color.parseColor("#000000"))
                btn_c.setTextColor(Color.parseColor("#000000"))
                btn_a.setTextColor(Color.parseColor("#000000"))
                btn_r.setTextColor(Color.parseColor("#000000"))
                btn_u.setTextColor(Color.parseColor("#000000"))
                btn_subtract.setTextColor(Color.parseColor("#000000"))
                input_refChange.setText("NA")
            })

            btn_r.setOnClickListener(View.OnClickListener { view ->
                total_pieces += 1
                binding_second.totalNumPieces.setText(total_pieces.toString())
                var delim = "."
                var width_feet = 0
                var width_inches = 0
                var thickness_feet = 0
                var thickness_inches = 0
                var length_feet = 0
                var length_inches = 0
                var ton = 0.000000

                var width_arr = input_width.text.toString().split(delim)
                var thickness_arr = input_thickness.text.toString().split(delim)
                var length_arr = input_length.text.toString().split(delim)

                width_feet = width_arr[0].toInt()
                thickness_feet = thickness_arr[0].toInt()
                length_feet = length_arr[0].toInt()
                try {
                    width_inches = width_arr[1].toInt()
                } catch(e: Exception) {}
                try {
                    thickness_inches = thickness_arr[1].toInt()
                } catch(e: Exception) {}
                try {
                    length_inches = length_arr[1].toInt()
                } catch(e: Exception) {}

                println(length_feet)
                println(length_inches)

                if (status == "Log" && input_unit.text.toString() == "ft") {
                    var girdth_width = width_feet * 12 + width_inches
                    var log_length = input_length.text.toString().toDouble()
                    ton = (girdth_width * girdth_width * log_length / 115200).toDouble()
                    total_ton += ton
                } else if (status == "Conversion" && input_unit.text.toString() == "ft") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 7200).toDouble()
                    total_ton += ton
                } else if (status == "S4S" && input_unit.text.toString() == "ft") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 7200).toDouble()
                    total_ton += ton
                } else if (status == "Log" && input_unit.text.toString() == "mm") {
                    ton = 0.000000
                } else if (status == "Conversion" && input_unit.text.toString() == "mm") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 1415800000).toDouble()
                    total_ton += ton
                } else if (status == "S4S" && input_unit.text.toString() == "mm") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 1415800000).toDouble()
                    total_ton += ton
                }

                binding_second.totalNumTon.setText("%.5f".format(total_ton).toString())
                var num_of_wood = text_count.text.toString().toInt() + 1
                text_count.setText(num_of_wood.toString())
                ref += 1
                if (input_refChange.text.toString() == "NA") {
                    ref_override = ref
                } else {
                    ref_override = input_refChange.text.toString().toInt()
                    ref -= 1
                }
                print(text_count.text)
                try{
                    val fileOutputStream: FileOutputStream = openFileOutput(this_filename, Context.MODE_APPEND)
                    val outPutWriter = OutputStreamWriter(fileOutputStream)
                    val append_arr = arrayOf(tally_date,
                        ref_override.toString(),
                        counter_name,
                        supplier_name,
                        input_orderName.text,
                        species,
                        status,
                        "R",
                        input_unit.text,
                        input_width.text,
                        input_thickness.text,
                        input_length.text,
                        "%.5f".format(ton).toString(),
                        boiler,
                        "1"
                    )
                    outPutWriter.append("\n")
                    for (child in append_arr) {
                        outPutWriter.append(child)
                        outPutWriter.append(",")
                    }
                    outPutWriter.close()
                    print("\n Closing outputwrite")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                //input_orderName.setText("NA")
                btn_r.setTextColor(Color.parseColor("#FF0000"))
                btn_b.setTextColor(Color.parseColor("#000000"))
                btn_c.setTextColor(Color.parseColor("#000000"))
                btn_q.setTextColor(Color.parseColor("#000000"))
                btn_a.setTextColor(Color.parseColor("#000000"))
                btn_u.setTextColor(Color.parseColor("#000000"))
                btn_subtract.setTextColor(Color.parseColor("#000000"))
                input_refChange.setText("NA")
            })

            btn_u.setOnClickListener(View.OnClickListener { view ->
                total_pieces += 1
                binding_second.totalNumPieces.setText(total_pieces.toString())
                var delim = "."
                var width_feet = 0
                var width_inches = 0
                var thickness_feet = 0
                var thickness_inches = 0
                var length_feet = 0
                var length_inches = 0
                var ton = 0.000000

                var width_arr = input_width.text.toString().split(delim)
                var thickness_arr = input_thickness.text.toString().split(delim)
                var length_arr = input_length.text.toString().split(delim)

                width_feet = width_arr[0].toInt()
                thickness_feet = thickness_arr[0].toInt()
                length_feet = length_arr[0].toInt()
                println("Clicked btn A")
                try {
                    width_inches = width_arr[1].toInt()
                } catch(e: Exception) {}
                try {
                    thickness_inches = thickness_arr[1].toInt()
                } catch(e: Exception) {}
                try {
                    length_inches = length_arr[1].toInt()
                } catch(e: Exception) {}

                println(length_feet)
                println(length_inches)

                if (status == "Log" && input_unit.text.toString() == "ft") {
                    var girdth_width = width_feet * 12 + width_inches
                    var log_length = input_length.text.toString().toDouble()
                    ton = (girdth_width * girdth_width * log_length / 115200).toDouble()
                    total_ton += ton
                } else if (status == "Conversion" && input_unit.text.toString() == "ft") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 7200).toDouble()
                    total_ton += ton
                } else if (status == "S4S" && input_unit.text.toString() == "ft") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 7200).toDouble()
                    total_ton += ton
                } else if (status == "Log" && input_unit.text.toString() == "mm") {
                    ton = 0.000000
                } else if (status == "Conversion" && input_unit.text.toString() == "mm") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 1415800000).toDouble()
                    total_ton += ton
                } else if (status == "S4S" && input_unit.text.toString() == "mm") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (this_width * this_thickness * this_length / 1415800000).toDouble()
                    total_ton += ton
                }

                binding_second.totalNumTon.setText("%.5f".format(total_ton).toString())
                var num_of_wood = text_count.text.toString().toInt() + 1
                text_count.setText(num_of_wood.toString())
                ref += 1
                if (input_refChange.text.toString() == "NA") {
                    ref_override = ref
                } else {
                    ref_override = input_refChange.text.toString().toInt()
                    ref -= 1
                }
                print(text_count.text)
                try{
                    val fileOutputStream: FileOutputStream = openFileOutput(this_filename, Context.MODE_APPEND)
                    val outPutWriter = OutputStreamWriter(fileOutputStream)
                    val append_arr = arrayOf(tally_date,
                        ref_override.toString(),
                        counter_name,
                        supplier_name,
                        input_orderName.text,
                        species,
                        status,
                        "U",
                        input_unit.text,
                        input_width.text,
                        input_thickness.text,
                        input_length.text,
                        "%.5f".format(ton).toString(),
                        boiler,
                        "1"
                    )
                    outPutWriter.append("\n")
                    for (child in append_arr) {
                        outPutWriter.append(child)
                        outPutWriter.append(",")
                    }
                    outPutWriter.close()
                    print("\n Closing outputwrite")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                //input_orderName.setText("NA")
                btn_u.setTextColor(Color.parseColor("#FF0000"))
                btn_b.setTextColor(Color.parseColor("#000000"))
                btn_c.setTextColor(Color.parseColor("#000000"))
                btn_q.setTextColor(Color.parseColor("#000000"))
                btn_r.setTextColor(Color.parseColor("#000000"))
                btn_a.setTextColor(Color.parseColor("#000000"))
                btn_subtract.setTextColor(Color.parseColor("#000000"))
                input_refChange.setText("NA")
            })


            btn_subtract.setOnClickListener(View.OnClickListener { view ->
                total_pieces -= 1
                binding_second.totalNumPieces.setText(total_pieces.toString())
                var delim = "."
                var width_feet = 0
                var width_inches = 0
                var thickness_feet = 0
                var thickness_inches = 0
                var length_feet = 0
                var length_inches = 0
                var ton = 0.000000

                var width_arr = input_width.text.toString().split(delim)
                var thickness_arr = input_thickness.text.toString().split(delim)
                var length_arr = input_length.text.toString().split(delim)

                width_feet = width_arr[0].toInt()
                thickness_feet = thickness_arr[0].toInt()
                length_feet = length_arr[0].toInt()
                println("Clicked btn A")
                try {
                    width_inches = width_arr[1].toInt()
                } catch(e: Exception) {}
                try {
                    thickness_inches = thickness_arr[1].toInt()
                } catch(e: Exception) {}
                try {
                    length_inches = length_arr[1].toInt()
                } catch(e: Exception) {}

                println(length_feet)
                println(length_inches)

                if (status == "Log" && input_unit.text.toString() == "ft") {
                    var girdth_width = width_feet * 12 + width_inches
                    var log_length = input_length.text.toString().toDouble()
                    ton = (-1 * girdth_width * girdth_width * log_length / 115200).toDouble()
                    total_ton += ton
                } else if (status == "Conversion" && input_unit.text.toString() == "ft") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (-1 * this_width * this_thickness * this_length / 7200).toDouble()
                    total_ton += ton
                } else if (status == "S4S" && input_unit.text.toString() == "ft") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (-1 * this_width * this_thickness * this_length / 7200).toDouble()
                    total_ton += ton
                } else if (status == "Log" && input_unit.text.toString() == "mm") {
                    ton = 0.000000
                } else if (status == "Conversion" && input_unit.text.toString() == "mm") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (-1 * this_width * this_thickness * this_length / 1415800000).toDouble()
                    total_ton += ton
                } else if (status == "S4S" && input_unit.text.toString() == "mm") {
                    var this_width = input_width.text.toString().toDouble()
                    var this_thickness = input_thickness.text.toString().toDouble()
                    var this_length = input_length.text.toString().toDouble()
                    ton = (-1 * this_width * this_thickness * this_length / 1415800000).toDouble()
                    total_ton += ton
                }

                binding_second.totalNumTon.setText("%.5f".format(total_ton).toString())
                var num_of_wood = text_count.text.toString().toInt() - 1
                text_count.setText(num_of_wood.toString())
                ref -= 1
                if (input_refChange.text.toString() == "NA") {
                    ref_override = ref
                } else {
                    ref_override = input_refChange.text.toString().toInt()
                    ref += 1
                }
                print(text_count.text)
                try{
                    val fileOutputStream: FileOutputStream = openFileOutput(this_filename, Context.MODE_APPEND)
                    val outPutWriter = OutputStreamWriter(fileOutputStream)
                    val append_arr = arrayOf(tally_date,
                        ref_override.toString(),
                        counter_name,
                        supplier_name,
                        input_orderName.text,
                        species,
                        status,
                        "NA",
                        input_unit.text,
                        input_width.text,
                        input_thickness.text,
                        input_length.text,
                        "%.5f".format(ton).toString(),
                        boiler,
                        "-1"
                    )
                    outPutWriter.append("\n")
                    for (child in append_arr) {
                        outPutWriter.append(child)
                        outPutWriter.append(",")
                    }
                    outPutWriter.close()
                    print("\n Closing outputwrite")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                //input_orderName.setText("NA")
                btn_subtract.setTextColor(Color.parseColor("#FF0000"))
                btn_b.setTextColor(Color.parseColor("#000000"))
                btn_c.setTextColor(Color.parseColor("#000000"))
                btn_q.setTextColor(Color.parseColor("#000000"))
                btn_r.setTextColor(Color.parseColor("#000000"))
                btn_u.setTextColor(Color.parseColor("#000000"))
                btn_a.setTextColor(Color.parseColor("#000000"))

                input_refChange.setText("NA")
            })
        }
    }


    fun handleSubtractButton(view: View) {
        with(view as Button) {
            println("handle clicks")
            println(view.tag)
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
                val append_arr = arrayOf(
                    tally_date,
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
