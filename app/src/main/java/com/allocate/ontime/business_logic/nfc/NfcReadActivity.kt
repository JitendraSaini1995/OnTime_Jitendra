//package com.allocate.ontime.business_logic.nfc
//
//import android.app.PendingIntent
//import android.content.Intent
//import android.content.IntentFilter
//import android.nfc.NdefMessage
//import android.nfc.NfcAdapter
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import com.allocate.ontime.business_logic.nfc.ui.theme.OnTimeTheme
//
//class NfcReadActivity : ComponentActivity() {
//
//    private var nfcAdapter: NfcAdapter? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
//        setContent {
//            OnTimeTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting2("Read")
//                }
//            }
//        }
//    }
//
//    private fun enableNfcForegroundDispatch() {
//        nfcAdapter.let { adapter ->
//            if (adapter != null) {
//                if (adapter.isEnabled) {
//                    val nfcIntentFilter = arrayOf(
//                        IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED),
//                        IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED),
//                        IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
//                    )
//
//                    val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                        PendingIntent.getActivity(
//                            this,
//                            0,
//                            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
//                            PendingIntent.FLAG_MUTABLE
//                        )
//                    } else {
//                        PendingIntent.getActivity(
//                            this,
//                            0,
//                            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
//                            PendingIntent.FLAG_UPDATE_CURRENT
//                        )
//                    }
//
//                    adapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null)
//                }
//            }
//        }
//    }
//
//    private fun disableNfcForegroundDispatch() {
//        nfcAdapter?.disableForegroundDispatch(this)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        enableNfcForegroundDispatch()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        disableNfcForegroundDispatch()
//    }
//
//
//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        handleIntent(intent)
//    }
//
//    private fun handleIntent(intent: Intent?) {
//        try {
//            if (intent != null) if (NfcAdapter.ACTION_TAG_DISCOVERED == intent.action || NfcAdapter.ACTION_TECH_DISCOVERED == intent.action || NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
//                val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
//                val nfcValue = String((rawMessages?.get(0) as NdefMessage).records[0].payload)
//                Log.d("nfc",nfcValue)
//
//
//            }
//        } catch (e: NullPointerException) {
//            Log.e("Error",e.toString())
//        }
//    }
//}
//
//@Composable
//fun Greeting2(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
