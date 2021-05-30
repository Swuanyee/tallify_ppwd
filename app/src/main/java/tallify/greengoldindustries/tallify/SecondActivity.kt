package tallify.greengoldindustries.tallify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import tallify.greengoldindustries.tallify.databinding.ActivitySecondBinding
import tallify.greengoldindustries.tallify.databinding.MeasurementFormsBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding_second: ActivitySecondBinding
    private lateinit var binding_template: MeasurementFormsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_second = ActivitySecondBinding.inflate(layoutInflater)
        binding_template = MeasurementFormsBinding.inflate(layoutInflater)
        val view_second = binding_second.root
        setContentView(view_second)
        var wxt_pressed = 0

        binding_second.btnDone.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                wxt_pressed += 1
                println(wxt_pressed)
                var contWhole = findViewById<LinearLayout>(R.id.container_1)
                var contWxt = binding_second.containerWhole
                println(contWhole)
            }
            })
    }
    }
