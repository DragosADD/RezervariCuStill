package org.example.domain.client;

import org.example.connectionUtil.DbManager;
import org.example.domain.GenericDao;

public class Client {
    private int idClient;
    private String nume;
    private String prenume;
    private String email;
    private String telefon;
    private String userName;
    private String parola;

    public Client(int idClient, String nume, String prenume, String email, String telefon, String userName, String parola) {
        this.idClient = idClient;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.telefon = telefon;
        this.userName = userName;
        this.parola = parola;
    }

    public Client(String nume, String prenume, String email, String telefon, String userName, String parola) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.telefon = telefon;
        this.userName = userName;
        this.parola = parola;
    }

    // Getters
    public int getIdClient() {
        return idClient;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getUserName() {
        return userName;
    }

    public String getParola() {
        return parola;
    }


    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", email='" + email + '\'' +
                ", telefon='" + telefon + '\'' +
                ", userName='" + userName + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }
}
