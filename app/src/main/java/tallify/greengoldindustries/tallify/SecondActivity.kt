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
import android.widget.*
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

    var delim = "."
    var width_feet = 0
    var width_inches = 0
    var lumber_width = 1.000000
    var thickness = 0.000000
    var length = 0.000000
    var ton = 0.000000
    var width_arr = listOf<String>()
    var log_width = "String"

    var grade = "Lumber Grade"
    var orderName = "String"
    var unit = "String"

    var refChange = "String"

    var text_width_log = "String"
    var text_thickness_log = "String"
    var text_length_log = "String"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding_second = ActivitySecondBinding.inflate(layoutInflater)

        val bundle = intent.getBundleExtra("main_activity_data")
        counter_name = bundle?.getString("name").toString()
        tally_date = bundle?.getString("date").toString()
        supplier_name= bundle?.getString("supplier").toString()
        invoice = bundle?.getString("invoice").toString()
        ref = bundle?.getInt("ref").toString().toInt()
        boiler = bundle?.getString("boiler").toString()
        species = bundle?.getString("species").toString()
        status = bundle?.getString("status").toString()
        measurement_unit = bundle?.getString("unit").toString()

        val view_second = binding_second.root
        setContentView(view_second)
        var randomNum = Random.nextInt(1, 1000000).toString()
        println(counter_name)

        this_filename = counter_name + "_" + ref.toString() + "_" + randomNum + ".txt"

        if (status != "Bundle") {
            ref -= 1
        }

        if (status == "Conversion" || status == "S4S" || status == "Bundle") {
            binding_second.textTotalTon.setText("Ref")
        }

        try{
            val fileOutputStream: FileOutputStream = openFileOutput(this_filename, Context.MODE_PRIVATE)
            val outPutWriter = OutputStreamWriter(fileOutputStream)
            val heading_arr= arrayOf("Date","Reference","Name", "Location", "Supplier", "Order", "Species","Status","Grade", "Unit",
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
            input_refChange.setText("0")
            input_refChange.setRawInputType(Configuration.KEYBOARD_12KEY)
            input_refChange.setInputType(InputType.TYPE_CLASS_NUMBER)
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
                try {
                    lumber_width = input_width.text.toString().toDouble()
                    thickness = input_thickness.text.toString().toDouble()
                    length = input_length.text.toString().toDouble()
                    grade = "A"
                    orderName = input_orderName.text.toString()
                    unit = input_unit.text.toString()
                    log_width = input_width.text.toString()
                    refChange = input_refChange.text.toString()
                    text_width_log = input_width.text.toString()
                    text_thickness_log = input_thickness.text.toString()
                    text_length_log = input_length.text.toString()


                    if (status == "Log" && input_unit.text.toString() == "ft") {
                        calculateLog(log_width, length)
                    } else if (status == "Conversion" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "S4S" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "Bundle" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "Log" && input_unit.text.toString() == "mm") {
                        ton = 0.000000
                    } else if (status == "Conversion" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    } else if (status == "S4S" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    } else if (status == "Bundle" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    }


                    var num_of_wood = text_count.text.toString().toInt() + 1
                    text_count.setText(num_of_wood.toString())
                    total_pieces += 1
                    binding_second.totalNumPieces.setText(total_pieces.toString())
                    refAdded()

                    recordMeasurement(grade)

                    btn_a.setTextColor(Color.parseColor("#FF0000"))
                    btn_b.setTextColor(Color.parseColor("#000000"))
                    btn_c.setTextColor(Color.parseColor("#000000"))
                    btn_q.setTextColor(Color.parseColor("#000000"))
                    btn_r.setTextColor(Color.parseColor("#000000"))
                    btn_u.setTextColor(Color.parseColor("#000000"))
                    btn_subtract.setTextColor(Color.parseColor("#000000"))
                    if (status != "Bundle") {
                        input_refChange.setText("0")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@SecondActivity,
                        "Warning! You have provided an invalid input! Please resubmit the last" +
                                " measurement.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

            btn_b.setOnClickListener(View.OnClickListener { view ->
                try {
                    lumber_width = input_width.text.toString().toDouble()
                    thickness = input_thickness.text.toString().toDouble()
                    length = input_length.text.toString().toDouble()
                    grade = "B"
                    orderName = input_orderName.text.toString()
                    unit = input_unit.text.toString()
                    log_width = input_width.text.toString()
                    refChange = input_refChange.text.toString()
                    text_width_log = input_width.text.toString()
                    text_thickness_log = input_thickness.text.toString()
                    text_length_log = input_length.text.toString()

                    if (status == "Log" && input_unit.text.toString() == "ft") {
                        calculateLog(log_width, length)
                    } else if (status == "Conversion" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "S4S" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "Bundle" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "Log" && input_unit.text.toString() == "mm") {
                        ton = 0.000000
                    } else if (status == "Conversion" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    } else if (status == "S4S" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    } else if (status == "Bundle" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    }


                    var num_of_wood = text_count.text.toString().toInt() + 1
                    text_count.setText(num_of_wood.toString())
                    total_pieces += 1
                    binding_second.totalNumPieces.setText(total_pieces.toString())
                    refAdded()

                    recordMeasurement(grade)
                    //input_orderName.setText("NA")
                    btn_b.setTextColor(Color.parseColor("#FF0000"))
                    btn_a.setTextColor(Color.parseColor("#000000"))
                    btn_c.setTextColor(Color.parseColor("#000000"))
                    btn_q.setTextColor(Color.parseColor("#000000"))
                    btn_r.setTextColor(Color.parseColor("#000000"))
                    btn_u.setTextColor(Color.parseColor("#000000"))
                    btn_subtract.setTextColor(Color.parseColor("#000000"))
                    if (status != "Bundle") {
                        input_refChange.setText("0")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@SecondActivity,
                        "Warning! You have provided an invalid input! Please resubmit the last" +
                                " measurement.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

            btn_c.setOnClickListener(View.OnClickListener { view ->
                try {
                    lumber_width = input_width.text.toString().toDouble()
                    thickness = input_thickness.text.toString().toDouble()
                    length = input_length.text.toString().toDouble()
                    grade = "C"
                    orderName = input_orderName.text.toString()
                    unit = input_unit.text.toString()
                    log_width = input_width.text.toString()
                    refChange = input_refChange.text.toString()
                    text_width_log = input_width.text.toString()
                    text_thickness_log = input_thickness.text.toString()
                    text_length_log = input_length.text.toString()

                    if (status == "Log" && input_unit.text.toString() == "ft") {
                        calculateLog(log_width, length)
                    } else if (status == "Conversion" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "S4S" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "Bundle" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "Log" && input_unit.text.toString() == "mm") {
                        ton = 0.000000
                    } else if (status == "Conversion" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    } else if (status == "S4S" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    } else if (status == "Bundle" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    }


                    var num_of_wood = text_count.text.toString().toInt() + 1
                    text_count.setText(num_of_wood.toString())
                    total_pieces += 1
                    binding_second.totalNumPieces.setText(total_pieces.toString())
                    refAdded()

                    recordMeasurement(grade)
                    btn_c.setTextColor(Color.parseColor("#FF0000"))
                    btn_b.setTextColor(Color.parseColor("#000000"))
                    btn_a.setTextColor(Color.parseColor("#000000"))
                    btn_q.setTextColor(Color.parseColor("#000000"))
                    btn_r.setTextColor(Color.parseColor("#000000"))
                    btn_u.setTextColor(Color.parseColor("#000000"))
                    btn_subtract.setTextColor(Color.parseColor("#000000"))
                    if (status != "Bundle") {
                        input_refChange.setText("0")
                    }
                    //input_orderName.setText("NA")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@SecondActivity,
                        "Warning! You have provided an invalid input! Please resubmit the last" +
                                " measurement.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

            btn_q.setOnClickListener(View.OnClickListener { view ->
                try {
                    lumber_width = input_width.text.toString().toDouble()
                    thickness = input_thickness.text.toString().toDouble()
                    length = input_length.text.toString().toDouble()
                    grade = "QS"
                    orderName = input_orderName.text.toString()
                    unit = input_unit.text.toString()
                    log_width = input_width.text.toString()
                    refChange = input_refChange.text.toString()
                    text_width_log = input_width.text.toString()
                    text_thickness_log = input_thickness.text.toString()
                    text_length_log = input_length.text.toString()

                    if (status == "Log" && input_unit.text.toString() == "ft") {
                        calculateLog(log_width, length)
                    } else if (status == "Conversion" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "S4S" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "Bundle" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "Log" && input_unit.text.toString() == "mm") {
                        ton = 0.000000
                    } else if (status == "Conversion" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    } else if (status == "S4S" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    } else if (status == "Bundle" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    }

                    var num_of_wood = text_count.text.toString().toInt() + 1
                    text_count.setText(num_of_wood.toString())
                    total_pieces += 1
                    binding_second.totalNumPieces.setText(total_pieces.toString())
                    refAdded()

                    recordMeasurement(grade)
                    //input_orderName.setText("NA")
                    btn_q.setTextColor(Color.parseColor("#FF0000"))
                    btn_b.setTextColor(Color.parseColor("#000000"))
                    btn_c.setTextColor(Color.parseColor("#000000"))
                    btn_a.setTextColor(Color.parseColor("#000000"))
                    btn_r.setTextColor(Color.parseColor("#000000"))
                    btn_u.setTextColor(Color.parseColor("#000000"))
                    btn_subtract.setTextColor(Color.parseColor("#000000"))
                    if (status != "Bundle") {
                        input_refChange.setText("0")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@SecondActivity,
                        "Warning! You have provided an invalid input! Please resubmit the last" +
                                " measurement.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

            btn_r.setOnClickListener(View.OnClickListener { view ->
                try {
                    lumber_width = input_width.text.toString().toDouble()
                    thickness = input_thickness.text.toString().toDouble()
                    length = input_length.text.toString().toDouble()
                    grade = "R"
                    orderName = input_orderName.text.toString()
                    unit = input_unit.text.toString()
                    log_width = input_width.text.toString()
                    refChange = input_refChange.text.toString()
                    text_width_log = input_width.text.toString()
                    text_thickness_log = input_thickness.text.toString()
                    text_length_log = input_length.text.toString()

                    if (status == "Log" && input_unit.text.toString() == "ft") {
                        calculateLog(log_width, length)
                    } else if (status == "Conversion" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "S4S" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "Bundle" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "Log" && input_unit.text.toString() == "mm") {
                        ton = 0.000000
                    } else if (status == "Conversion" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    } else if (status == "S4S" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    } else if (status == "Bundle" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    }

                    var num_of_wood = text_count.text.toString().toInt() + 1
                    text_count.setText(num_of_wood.toString())
                    total_pieces += 1
                    binding_second.totalNumPieces.setText(total_pieces.toString())
                    refAdded()

                    recordMeasurement(grade)
                    //input_orderName.setText("NA")
                    btn_r.setTextColor(Color.parseColor("#FF0000"))
                    btn_b.setTextColor(Color.parseColor("#000000"))
                    btn_c.setTextColor(Color.parseColor("#000000"))
                    btn_q.setTextColor(Color.parseColor("#000000"))
                    btn_a.setTextColor(Color.parseColor("#000000"))
                    btn_u.setTextColor(Color.parseColor("#000000"))
                    btn_subtract.setTextColor(Color.parseColor("#000000"))
                    if (status != "Bundle") {
                        input_refChange.setText("0")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@SecondActivity,
                        "Warning! You have provided an invalid input! Please resubmit the last" +
                                " measurement.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

            btn_u.setOnClickListener(View.OnClickListener { view ->
                try {
                    lumber_width = input_width.text.toString().toDouble()
                    thickness = input_thickness.text.toString().toDouble()
                    length = input_length.text.toString().toDouble()
                    grade = "U"
                    orderName = input_orderName.text.toString()
                    unit = input_unit.text.toString()
                    log_width = input_width.text.toString()
                    refChange = input_refChange.text.toString()
                    text_width_log = input_width.text.toString()
                    text_thickness_log = input_thickness.text.toString()
                    text_length_log = input_length.text.toString()

                    if (status == "Log" && input_unit.text.toString() == "ft") {
                        calculateLog(log_width, length)
                    } else if (status == "Conversion" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "S4S" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "Bundle" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "Log" && input_unit.text.toString() == "mm") {
                        ton = 0.000000
                    } else if (status == "Conversion" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    } else if (status == "S4S" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    } else if (status == "Bundle" && input_unit.text.toString() == "mm") {
                        calculateMm(lumber_width, thickness, length)
                    }

                    var num_of_wood = text_count.text.toString().toInt() + 1
                    text_count.setText(num_of_wood.toString())
                    total_pieces += 1
                    binding_second.totalNumPieces.setText(total_pieces.toString())
                    refAdded()


                    recordMeasurement(grade)
                    //input_orderName.setText("NA")
                    btn_u.setTextColor(Color.parseColor("#FF0000"))
                    btn_b.setTextColor(Color.parseColor("#000000"))
                    btn_c.setTextColor(Color.parseColor("#000000"))
                    btn_q.setTextColor(Color.parseColor("#000000"))
                    btn_r.setTextColor(Color.parseColor("#000000"))
                    btn_a.setTextColor(Color.parseColor("#000000"))
                    btn_subtract.setTextColor(Color.parseColor("#000000"))
                    if (status != "Bundle") {
                        input_refChange.setText("0")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@SecondActivity,
                        "Warning! You have provided an invalid input! Please resubmit the last" +
                                " measurement.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

            btn_subtract.setOnClickListener(View.OnClickListener { view ->
                try {
                    lumber_width = input_width.text.toString().toDouble()
                    thickness = input_thickness.text.toString().toDouble()
                    length = input_length.text.toString().toDouble()
                    grade = "NA"
                    orderName = input_orderName.text.toString()
                    unit = input_unit.text.toString()
                    log_width = input_width.text.toString()
                    refChange = input_refChange.text.toString()
                    text_width_log = input_width.text.toString()
                    text_thickness_log = input_thickness.text.toString()
                    text_length_log = input_length.text.toString()

                    if (status == "Log" && input_unit.text.toString() == "ft") {
                        calculateSubtractLog(log_width, length)
                    } else if (status == "Conversion" && input_unit.text.toString() == "ft") {
                        calculateFeet(lumber_width, thickness, length)
                    } else if (status == "S4S" && input_unit.text.toString() == "ft") {
                        calculateSubtractFeet(lumber_width, thickness, length)
                    } else if (status == "Bundle" && input_unit.text.toString() == "ft") {
                        calculateSubtractFeet(lumber_width, thickness, length)
                    } else if (status == "Log" && input_unit.text.toString() == "mm") {
                        ton = 0.000000
                    } else if (status == "Conversion" && input_unit.text.toString() == "mm") {
                        calculateSubtractMm(lumber_width, thickness, length)
                    } else if (status == "S4S" && input_unit.text.toString() == "mm") {
                        calculateSubtractMm(lumber_width, thickness, length)
                    } else if (status == "Bundle" && input_unit.text.toString() == "mm") {
                        calculateSubtractMm(lumber_width, thickness, length)
                    }

                    var num_of_wood = text_count.text.toString().toInt() - 1
                    text_count.setText(num_of_wood.toString())
                    total_pieces -= 1
                    binding_second.totalNumPieces.setText(total_pieces.toString())

                    refSubtractedOverride()
                    recordSubtractMeasurement(grade)

                    btn_subtract.setTextColor(Color.parseColor("#FF0000"))
                    btn_b.setTextColor(Color.parseColor("#000000"))
                    btn_c.setTextColor(Color.parseColor("#000000"))
                    btn_q.setTextColor(Color.parseColor("#000000"))
                    btn_r.setTextColor(Color.parseColor("#000000"))
                    btn_u.setTextColor(Color.parseColor("#000000"))
                    btn_a.setTextColor(Color.parseColor("#000000"))

                    refSubtracted()

                    if (status != "Bundle") {
                        input_refChange.setText("0")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@SecondActivity,
                        "Warning! You have provided an invalid input! Please resubmit the last" +
                                " measurement.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }

    fun calculateLog(log_width: String, length: Double) {
        if (log_width.contains(".")) {
            try {
                width_arr = log_width.split(delim)
                width_feet = width_arr[0].toInt()
            } catch(e: Exception) {}
            try {
                width_inches = width_arr[1].toInt()
                print(width_inches)
            } catch(e: Exception) {}
        } else {
            width_feet = log_width.toInt()
        }

        var girdth_width = width_feet * 12 + width_inches
        var log_length = length
        ton = (girdth_width * girdth_width * log_length / 115200).toDouble()
        total_ton += ton
    }

    fun calculateFeet(feet_width: Double, feet_thickness: Double, feet_length: Double) {
        ton = (feet_width * feet_thickness * feet_length / 7200).toDouble()
        total_ton += ton
    }

    fun calculateMm(mm_width: Double, mm_thickness: Double, mm_length: Double) {
        ton = (mm_width * mm_thickness * mm_length / 1415800000).toDouble()
        total_ton += ton
    }

    fun recordMeasurement(lumber_grade: String) {
        if (orderName == "NA") {
            orderName = "Ground Stock"
        }
        try{
            val fileOutputStream: FileOutputStream = openFileOutput(this_filename, Context.MODE_APPEND)
            val outPutWriter = OutputStreamWriter(fileOutputStream)
            val append_arr = arrayOf(
                tally_date,
                ref_override.toString(),
                counter_name,
                invoice,
                supplier_name,
                orderName,
                species,
                status,
                lumber_grade,
                unit,
                text_width_log,
                text_thickness_log,
                text_length_log,
                "%.4f".format(ton).toString(),
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
            Toast.makeText(this@SecondActivity,
                "Warning! Your last measurement" +
                        " was not recorded. Please re-enter button.",
                Toast.LENGTH_LONG).show()
        }
    }

    fun refAdded() {
        if (status != "Bundle") {
            ref += 1

            if (refChange == "0") {
                ref_override = ref
            } else {
                ref_override = refChange.toInt()
                ref -= 1
            }
        } else {
            if (refChange == "0") {
                ref_override = ref
            } else {
                ref_override = refChange.toInt()
            }
       }
        if(status == "Conversion" || status == "S4S" || status == "Bundle") {
            binding_second.totalNumTon.setText(ref_override.toString())
        } else {
            binding_second.totalNumTon.setText("%.4f".format(total_ton).toString())
        }
    }

    fun calculateSubtractLog(log_width: String, length: Double) {
        if (log_width.contains(".")) {
            try {
                width_arr = log_width.split(delim)
                width_feet = width_arr[0].toInt()
            } catch(e: Exception) {}
            try {
                width_inches = width_arr[1].toInt()
                print(width_inches)
            } catch(e: Exception) {}
        } else {
            width_feet = log_width.toInt()
        }

        var girdth_width = width_feet * 12 + width_inches
        var log_length = length
        ton = (-1*girdth_width * girdth_width * log_length / 115200).toDouble()
        total_ton += ton
    }

    fun calculateSubtractFeet(feet_width: Double, feet_thickness: Double, feet_length: Double) {
        ton = (-1*feet_width * feet_thickness * feet_length / 7200).toDouble()
        total_ton += ton
    }

    fun calculateSubtractMm(mm_width: Double, mm_thickness: Double, mm_length: Double) {
        ton = (-1*mm_width * mm_thickness * mm_length / 1415800000).toDouble()
        total_ton += ton
    }

    fun recordSubtractMeasurement(lumber_grade: String) {
        try{
            val fileOutputStream: FileOutputStream = openFileOutput(this_filename, Context.MODE_APPEND)
            val outPutWriter = OutputStreamWriter(fileOutputStream)
            val append_arr = arrayOf(
                tally_date,
                ref_override.toString(),
                counter_name,
                invoice,
                supplier_name,
                orderName,
                species,
                status,
                lumber_grade,
                unit,
                "%.2f".format(lumber_width).toString(),
                "%.2f".format(thickness).toString(),
                "%.2f".format(length).toString(),
                "%.4f".format(ton).toString(),
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
            Toast.makeText(this@SecondActivity,
                "Warning! Your last measurement" +
                        " was not recorded. Please re-enter button.",
                Toast.LENGTH_LONG).show()
        }
    }

    fun refSubtractedOverride() {
        if (refChange == "0") {
            ref_override = ref
        } else {
            ref_override = refChange.toInt()
        }
    }

    fun refSubtracted() {
        if (status != "Bundle") {
            ref -= 1
        }
        if(status == "Conversion" || status == "S4S" || status == "Bundle") {
            binding_second.totalNumTon.setText(ref_override.toString())
        } else {
            binding_second.totalNumTon.setText("%.4f".format(total_ton).toString())
        }
    }
}
