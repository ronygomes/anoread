package me.ronygomes.anoread.model;

import java.io.Serializable;

public class ReadMeta implements Serializable {

    private String name;
    private String prompt;
    private String hint;

    public ReadMeta() {
    }

    public ReadMeta(String name, String prompt, String hint) {
        this.name = name;
        this.prompt = prompt;
        this.hint = hint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
