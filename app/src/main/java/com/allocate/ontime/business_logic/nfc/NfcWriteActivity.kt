//package com.allocate.ontime.business_logic.nfc
//
//import android.app.PendingIntent
//import android.content.Intent
//import android.nfc.NdefMessage
//import android.nfc.NdefRecord
//import android.nfc.NfcAdapter
//import android.nfc.Tag
//import android.nfc.TagLostException
//import android.nfc.tech.Ndef
//import android.nfc.tech.NdefFormatable
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.ColorFilter
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import com.allocate.ontime.R
//import com.allocate.ontime.business_logic.nfc.ui.theme.OnTimeTheme
//import com.allocate.ontime.presentation_logic.navigation.OnTimeNavigation
//import com.allocate.ontime.presentation_logic.theme.dimens
//import java.nio.charset.StandardCharsets
//
//
//class NfcWriteActivity : ComponentActivity() {
//    private var mNfcAdpt: NfcAdapter? = null
//    private var mNfcPendingIntent: PendingIntent? = null
//    private var mMessageToWrite: String? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val employeeData = intent.getStringExtra("employeeData")
//        mMessageToWrite = employeeData
//        mNfcAdpt = NfcAdapter.getDefaultAdapter(this)
//        if (mNfcAdpt == null) {
//            Log.e("NFC", "NFC is not available on this device.")
//            Toast.makeText(this, "NFC is not available on this device.", Toast.LENGTH_SHORT).show()
//            finish()
//            return
//        }
//
//        mNfcPendingIntent = PendingIntent.getActivity(
//            this, 0,
//            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
//            PendingIntent.FLAG_MUTABLE
//        )
//
//        setContent {
//            OnTimeTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    ShowNfcIcon()
//                }
//            }
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mNfcAdpt?.enableForegroundDispatch(
//            this, mNfcPendingIntent, null, null
//        )
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mNfcAdpt?.disableForegroundDispatch(this)
//    }
//
//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
//        tag?.let { handleIntent(it, mMessageToWrite) }
//    }
//
//    private fun handleIntent(tag: Tag, message: String?) {
//        if (message.isNullOrEmpty()) {
//            Log.e("NFC", "Message to write is null or empty")
//            return
//        }
//
//        val ndefMessage = NdefMessage(
//            NdefRecord.createMime("text/plain", message.toByteArray(StandardCharsets.UTF_8))
//        )
//
//        val ndef = Ndef.get(tag)
//        if (ndef != null) {
//            try {
//                ndef.connect()
//                if (ndef.isWritable) {
//                    ndef.writeNdefMessage(ndefMessage)
//                    Log.i("NFC", "Message written to NFC tag successfully.")
//                    Toast.makeText(this, "Message written to NFC tag successfully.", Toast.LENGTH_SHORT).show()
//                } else {
//                    Log.e("NFC", "NFC tag is not writable.")
//                }
//                ndef.close()
//            } catch (e: TagLostException) {
//                Log.e("NFC", "Tag lost exception: ${e.message}")
//            } catch (e: Exception) {
//                Log.e("NFC", "Exception: ${e.message}")
//            }
//        } else {
//            val ndefFormatable = NdefFormatable.get(tag)
//            if (ndefFormatable != null) {
//                try {
//                    ndefFormatable.connect()
//                    ndefFormatable.format(ndefMessage)
//                    Log.i("NFC", "Formatted and wrote message to NFC tag successfully.")
//                    Toast.makeText(this, "Formatted and wrote message to NFC tag successfully.", Toast.LENGTH_SHORT).show()
//                    ndefFormatable.close()
//                } catch (e: TagLostException) {
//                    Log.e("NFC", "Tag lost exception: ${e.message}")
//                } catch (e: Exception) {
//                    Log.e("NFC", "Exception: ${e.message}")
//                }
//            } else {
//                Log.e("NFC", "NFC tag is not NDEF compatible.")
//            }
//        }
//    }
//}
//
//@Composable
//fun ShowNfcIcon() {
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//       Text(text = "Nfc Write")
//    }
//
//}
//
