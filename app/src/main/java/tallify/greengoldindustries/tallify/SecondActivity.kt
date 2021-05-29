package tallify.greengoldindustries.tallify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tallify.greengoldindustries.tallify.databinding.ActivityMainBinding
import tallify.greengoldindustries.tallify.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding_second: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_second = ActivitySecondBinding.inflate(layoutInflater)
        val view_second = binding_second.root
        setContentView(view_second)
    }
}