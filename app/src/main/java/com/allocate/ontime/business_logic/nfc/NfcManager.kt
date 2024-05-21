package com.allocate.ontime.business_logic.nfc

import android.app.PendingIntent
import android.content.Context
import android.nfc.NfcAdapter
import android.util.Log
import android.widget.Toast
import android.content.Intent
import android.content.IntentFilter
import android.nfc.*
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Build
import androidx.activity.ComponentActivity
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NfcManager @Inject constructor(private val context: Context) {

    var nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(context)
    var messageToWrite: String? = null

    init {
        if (nfcAdapter == null) {
            Log.e("NFC", "NFC is not available on this device.")
            Toast.makeText(context, "NFC is not available on this device.", Toast.LENGTH_SHORT).show()
        }
    }

    fun enableForegroundDispatch() {
        nfcAdapter?.let { adapter ->
            if (adapter.isEnabled) {
                val nfcIntentFilter = arrayOf(
                    IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED),
                    IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED),
                    IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
                )

                val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(
                        context,
                        0,
                        Intent(context, context.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                        PendingIntent.FLAG_MUTABLE
                    )
                } else {
                    PendingIntent.getActivity(
                        context,
                        0,
                        Intent(context, context.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }

                adapter.enableForegroundDispatch(context as ComponentActivity, pendingIntent, nfcIntentFilter, null)
            }
        }
    }

    fun disableForegroundDispatch() {
        nfcAdapter?.disableForegroundDispatch(context as ComponentActivity)
    }

    fun handleNfcIntent(intent: Intent?) {
        intent?.let {
            val tag: Tag? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
            } else {
                it.getParcelableExtra(NfcAdapter.EXTRA_TAG)

            }
            tag?.let { tag -> handleNfcWriteIntent(tag, messageToWrite) }
            handleNfcReadIntent(it)
        }
    }

    private fun handleNfcReadIntent(intent: Intent?) {
        try {
            intent?.let {
                if (NfcAdapter.ACTION_TAG_DISCOVERED == it.action || NfcAdapter.ACTION_TECH_DISCOVERED == it.action || NfcAdapter.ACTION_NDEF_DISCOVERED == it.action) {
                    val rawMessages = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        it.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES,Tag::class.java)
                    } else {
                        it.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                    }
                    val nfcValue = String((rawMessages?.get(0) as NdefMessage).records[0].payload)
                    Log.d("nfc", nfcValue)
                }
            }
        } catch (e: NullPointerException) {
            Log.e("Error", e.toString())
        }
    }

    private fun handleNfcWriteIntent(tag: Tag, message: String?) {
        if (message.isNullOrEmpty()) {
            Log.e("NFC", "Message to write is null or empty")
            return
        }

        val ndefMessage = NdefMessage(
            NdefRecord.createMime("text/plain", message.toByteArray(StandardCharsets.UTF_8))
        )

        val ndef = Ndef.get(tag)
        if (ndef != null) {
            try {
                ndef.connect()
                if (ndef.isWritable) {
                    ndef.writeNdefMessage(ndefMessage)
                    Log.i("NFC", "Message written to NFC tag successfully.")
                    Toast.makeText(context, "Message written to NFC tag successfully.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("NFC", "NFC tag is not writable.")
                }
                ndef.close()
            } catch (e: TagLostException) {
                Log.e("NFC", "Tag lost exception: ${e.message}")
            } catch (e: Exception) {
                Log.e("NFC", "Exception: ${e.message}")
            }
        } else {
            val ndefFormatable = NdefFormatable.get(tag)
            if (ndefFormatable != null) {
                try {
                    ndefFormatable.connect()
                    ndefFormatable.format(ndefMessage)
                    Log.i("NFC", "Formatted and wrote message to NFC tag successfully.")
                    Toast.makeText(context, "Formatted and wrote message to NFC tag successfully.", Toast.LENGTH_SHORT).show()
                    ndefFormatable.close()
                } catch (e: TagLostException) {
                    Log.e("NFC", "Tag lost exception: ${e.message}")
                } catch (e: Exception) {
                    Log.e("NFC", "Exception: ${e.message}")
                }
            } else {
                Log.e("NFC", "NFC tag is not NDEF compatible.")
            }
        }
    }
}
