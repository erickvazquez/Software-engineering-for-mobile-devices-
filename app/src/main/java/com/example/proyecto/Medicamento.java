package com.example.proyecto;

import android.graphics.Bitmap;

public class Medicamento {
    private String nombre, tipo, padecimiento, doctor, dias, periodo, horario, dosis;
    private int fotoEnvase, fotoMedicamento, idMedicamento, idUsuario;
    private Bitmap imgMedicamento;

    public Medicamento() {
    }

    public Medicamento(String nombre, String tipo, String padecimiento, String doctor, String dias, String periodo, String horario, int idUsuario, int idMedicamento, Bitmap imgMedicamento, String dosis) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.padecimiento = padecimiento;
        this.dias = dias;
        this.horario = horario;
        this.periodo = periodo;
        this.doctor = doctor;
        this.fotoMedicamento = fotoMedicamento;
        this.idUsuario = idUsuario;
        this.idMedicamento = idMedicamento;
        this.imgMedicamento = imgMedicamento;
        this.dosis = dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getDosis() {
        return dosis;
    }

    public Bitmap getImgMedicamento() {
        return imgMedicamento;
    }

    public void setImgMedicamento(Bitmap imgMedicamento) {
        this.imgMedicamento = imgMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setFotoEnvase(int fotoEnvase) {
        this.fotoEnvase = fotoEnvase;
    }

    public void setFotoMedicamento(int fotoMedicamento) {
        this.fotoMedicamento = fotoMedicamento;
    }

    public String getTipo() {
        return tipo;
    }

    public String getPadecimiento() {
        return padecimiento;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getDias() {
        return dias;
    }

    public String getPeriodo() {
        return periodo;
    }

    public String getHorario() {
        return horario;
    }

    public int getFotoEnvase() {
        return fotoEnvase;
    }

    public int getFotoMedicamento() {
        return fotoMedicamento;
    }
}
