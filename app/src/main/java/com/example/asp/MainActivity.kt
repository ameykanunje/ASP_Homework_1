package com.example.asp

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        val showButton = findViewById<Button>(R.id.sortButton)
        val enterText = findViewById<EditText>(R.id.enterEditText)
        val sortedTextView = findViewById<TextView>(R.id.displayTextView)
        val clearTextViewButton = findViewById<Button>(R.id.clearTextButton)

        clearTextViewButton.setOnClickListener{
            sortedTextView.text = " "
        }
        showButton.setOnClickListener {
            val inputText = enterText.text.toString()
            val unsortedArray = inputText.split(",").mapNotNull { it.trim().toIntOrNull() }.toTypedArray()
            if(unsortedArray.size > 8){
                val toast = Toast.makeText(this, "Entered number length should be less than 9", Toast.LENGTH_LONG)
                toast.show()
            }else if(unsortedArray.size <= 1 ){
                val toast = Toast.makeText(this, "Entered number length should be more than 2",Toast.LENGTH_LONG)
                toast.show()
            }else {
                val sortedArray = unsortedArray.copyOf() //created a copy to store the intermediate steps
                val steps = mutableListOf<String>() //To store intermediate steps

                for (i in 1 until sortedArray.size) {
                    val key = sortedArray[i]
                    var j = i - 1
                    while (j >= 0 && sortedArray[j] > key) {
                        sortedArray[j + 1] = sortedArray[j]
                        j--
                    }
                    sortedArray[j + 1] = key
                    steps.add("Step $i:  ${sortedArray.joinToString(",")}")

                }
                sortedTextView.text = "Sorting Steps: \n" + steps.joinToString("\n")
                sortedTextView.append("\n \nThe Sorted Array: " + sortedArray.joinToString(","))

            }

        }

        enterText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                // Hide the keyboard
                enterText.requestFocus()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(enterText.windowToken, 0)
                return@setOnEditorActionListener true
            }
            false
        }

        }

    }