package me.ronygomes.anoread.formatter.impl;

import me.ronygomes.anoread.formatter.ReadPromptFormatter;
import me.ronygomes.anoread.model.ReadMeta;

public class BasicReadPromptFormatter implements ReadPromptFormatter {

    public static final String DEFAULT_PROMPT_SUFFIX = ": ";

    private boolean hintVisible;
    private boolean fullLinePrompt;
    private String promptSuffix;

    public BasicReadPromptFormatter(boolean hintVisible, boolean fullLinePrompt) {
        this(hintVisible, fullLinePrompt, DEFAULT_PROMPT_SUFFIX);
    }

    public BasicReadPromptFormatter(boolean hintVisible, boolean fullLinePrompt, String promptSuffix) {
        this.hintVisible = hintVisible;
        this.fullLinePrompt = fullLinePrompt;
        this.promptSuffix = promptSuffix;
    }

    public boolean isHintVisible() {
        return hintVisible;
    }

    public void setHintVisible(boolean hintVisible) {
        this.hintVisible = hintVisible;
    }

    public boolean isFullLinePrompt() {
        return fullLinePrompt;
    }

    public void setFullLinePrompt(boolean fullLinePrompt) {
        this.fullLinePrompt = fullLinePrompt;
    }

    public String getPromptSuffix() {
        return promptSuffix;
    }

    public void setPromptSuffix(String promptSuffix) {
        this.promptSuffix = promptSuffix;
    }

    @Override
    public String format(ReadMeta meta) {
        StringBuilder prompt = new StringBuilder();

        prompt.append(meta.getPrompt());
        if (hintVisible) {
            prompt.append(String.format(" (%s)", meta.getHint()));
        }

        prompt.append(promptSuffix);
        prompt.append(fullLinePrompt ? System.lineSeparator() : "");

        return prompt.toString();
    }
}
