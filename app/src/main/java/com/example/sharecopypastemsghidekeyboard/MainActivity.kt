package com.example.sharecopypastemsghidekeyboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edMessage = findViewById<EditText>(R.id.edMessage)
        val shareBtn = findViewById<Button>(R.id.shareBtn)

        shareBtn.setOnClickListener {
            if (edMessage.text.toString().isNotEmpty()){
                shareMsg(edMessage.text.toString())
            }else{
                edMessage.error = "Required"
            }
        }

        val copyBtn = findViewById<Button>(R.id.copyBtn)
        copyBtn.setOnClickListener {
            if (edMessage.text.toString().isNotEmpty()){
                copyToClipBoard(edMessage.text.toString())
            }else{
                edMessage.error = "Required"
            }
        }

        val pasteBtn = findViewById<Button>(R.id.pasteBtn)
        pasteBtn.setOnClickListener {
            edMessage.setText(pasteFromClipBoard())
        }

        val hideKeyBoardBtn = findViewById<Button>(R.id.hideKeyBoardBtn)
        hideKeyBoardBtn.setOnClickListener {
            hideKeyBoard(it)
        }
    }

    private fun hideKeyBoard(it: View) {
        try {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken,0)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun pasteFromClipBoard(): String {
        val clipBoard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        return clipBoard.primaryClip!!.getItemAt(0).text.toString()
    }

    private fun copyToClipBoard(message: String) {
        val clipBoard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text",message)
        clipBoard.setPrimaryClip(clip)
        Toast.makeText(this,"Copied!!",Toast.LENGTH_LONG).show()
    }

    private fun shareMsg(message: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,message)
        startActivity(Intent.createChooser(intent,"Share Message"))
    }

}