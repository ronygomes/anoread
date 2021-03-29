package me.ronygomes.anoread.formatter.impl;

import me.ronygomes.anoread.exception.AnoReadException;
import me.ronygomes.anoread.exception.ConversionException;
import me.ronygomes.anoread.exception.ExtractionException;
import me.ronygomes.anoread.exception.ValidationError;
import me.ronygomes.anoread.formatter.ErrorPromptFormatter;
import me.ronygomes.anoread.model.ReadMeta;

public class BasicErrorPromptFormatter implements ErrorPromptFormatter {

    private boolean showErrorType;

    public BasicErrorPromptFormatter() {
    }

    public BasicErrorPromptFormatter(boolean showErrorType) {
        this.showErrorType = showErrorType;
    }

    @Override
    public String format(ReadMeta meta, String input, AnoReadException e) {

        String typeMessage = "";
        if (showErrorType) {
            if (e instanceof ExtractionException) {
                typeMessage = "[EXTRACTION]";
            } else if (e instanceof ConversionException) {
                typeMessage = "[CONVERSION]";
            } else if (e instanceof ValidationError) {
                typeMessage = "[VALIDATION]";
            }

            typeMessage += " ";
        }

        return String.format("%s%s[%s] - %s", typeMessage, meta.getName(), input, e.getDisplayMessage());
    }
}
