package com.androdevdk.scanbarcodezxingapp

import android.view.View
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class PortraitCaptureActivity : CaptureActivity() {
    override fun initializeContent(): DecoratedBarcodeView {
        setContentView(R.layout.capture_portrait)
        return findViewById<View>(R.id.zxing_barcode_scanner) as DecoratedBarcodeView
    }
}
