package org.example.domain.local;

import java.sql.Time;
import java.time.LocalTime;

public class Local {
    private int idLocal;
    private final String denumireLocal;
    private final int cui;

    private final String oras;
    private final Time oraInceputLocal;
    private final Time oraSfarsitProgram;
    private final String tipLocal;


    public Local(int idLocal, String denumireLocal, int cui, String oras, Time oraInceputLocal, Time oraSfarsitProgram, String tipLocal) {
        this.idLocal = idLocal;
        this.denumireLocal = denumireLocal;
        this.cui = cui;
        this.oras = oras;
        this.oraInceputLocal = oraInceputLocal;
        this.oraSfarsitProgram = oraSfarsitProgram;
        this.tipLocal = tipLocal;
    }
    public Local(String denumireLocal, int cui, String oras, Time oraInceputLocal, Time oraSfarsitProgram, String tipLocal) {
        this.denumireLocal = denumireLocal;
        this.cui = cui;
        this.oras = oras;
        this.oraInceputLocal = oraInceputLocal;
        this.oraSfarsitProgram = oraSfarsitProgram;
        this.tipLocal = tipLocal;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public String getDenumireLocal() {
        return denumireLocal;
    }

    public int getCui() {
        return cui;
    }

    public String getOras() {
        return oras;
    }

    public Time getOraInceputLocal() {
        return oraInceputLocal;
    }

    public Time getOraSfarsitProgram() {
        return oraSfarsitProgram;
    }

    public String getTipLocal() {
        return tipLocal;
    }

    @Override
    public String toString() {
        return "Local{" +
                "idLocal=" + idLocal +
                ", denumireLocal='" + denumireLocal + '\'' +
                ", cui=" + cui +
                ", oras='" + oras + '\'' +
                ", oraInceputLocal=" + oraInceputLocal +
                ", oraSfarsitProgram=" + oraSfarsitProgram +
                ", tipLocal='" + tipLocal + '\'' +
                '}';
    }
}
