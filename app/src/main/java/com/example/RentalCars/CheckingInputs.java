package com.example.RentalCars;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class CheckingInputs {
    private String name;
    private TextInputLayout textInput;
    private EditText edText;

    public CheckingInputs(String name, TextInputLayout textInput) {
        this.name = name;
        this.textInput = textInput;
    }

    public CheckingInputs(String name, EditText edText) {
        this.name = name;
        this.edText = edText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TextInputLayout getTextInput() {
        return textInput;
    }

    public void setTextInput(TextInputLayout textInput) {
        this.textInput = textInput;
    }

    public EditText getEdText(){ return edText; }

    public void setEdText(EditText edText){ this.edText = edText; }
}
