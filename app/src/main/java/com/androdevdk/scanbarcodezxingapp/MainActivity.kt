package com.androdevdk.scanbarcodezxingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.androdevdk.scanbarcodezxingapp.databinding.ActivityMainBinding
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val barcodeLauncher = registerForActivityResult(ScanContract()) {
        if (it.getContents() == null) {
            val originalIntent: Intent = it.getOriginalIntent()
            if (originalIntent == null) {
                Log.d("MainActivity", "Cancelled scan")
                Toast.makeText(this@MainActivity, "Cancelled", Toast.LENGTH_LONG).show()
            } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                Log.d("MainActivity", "Cancelled scan due to missing camera permission")
                Toast.makeText(
                    this@MainActivity,
                    "Cancelled due to missing camera permission",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Log.d("MainActivity", "Scanned")
            binding.textContent.setText(it.getContents())
            binding.textFormat.setText(it.getFormatName())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // adding listener to the button
        binding.scanBtn.setOnClickListener(listener)
        binding.scanPortraitBtn.setOnClickListener(listener)
        binding.customScanBtn.setOnClickListener(listener)
    }

    val listener = View.OnClickListener { view ->
        when (view.getId()) {
            R.id.scanBtn -> {
                scan()
            }
            R.id.scanPortraitBtn -> {
                scanPortraitScanner()
            }
            R.id.customScanBtn -> {
                customScan()
            }
        }
    }

    private fun customScan() {
        val options = ScanOptions().setOrientationLocked(false).setCaptureActivity(
            CustomScannerActivity::class.java
        )
        barcodeLauncher.launch(options)
    }

    private fun scan() {
        val options = ScanOptions()
        options.setPrompt("Scan a barcode or QR Code") //message
        options.setCameraId(0) //use specific camera of device
        options.setOrientationLocked(false);
        options.setBarcodeImageEnabled(true)
        barcodeLauncher.launch(options)
    }

    fun scanPortraitScanner() {
        val options = ScanOptions()
        options.setOrientationLocked(false)
        options.captureActivity = PortraitCaptureActivity::class.java
        barcodeLauncher.launch(options)
    }
}