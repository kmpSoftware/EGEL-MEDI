package com.kmpegel.medi.ui.Preguntas;

public class Pregunta {
    private String enunciado;
    private String[] opciones;
    private int respuestaCorrecta;
    private String retroalimentacion;

    public Pregunta(String enunciado, String[] opciones, int respuestaCorrecta, String retroalimentacion) {
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
        this.retroalimentacion = retroalimentacion;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public int getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public String getRetroalimentacion() {
        return retroalimentacion;
    }
}