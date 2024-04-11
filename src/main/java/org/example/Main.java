package org.example;

import org.example.connectionUtil.DbManager;
import org.example.domain.local.Local;
import org.example.domain.local.LocalLoader;
import org.example.exceptions.AlreadyExistsException;
import org.example.exceptions.DBComunicationException;
import org.example.exceptions.MultipleOrNoEntityException;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DbManager bazaDeDateManager = new DbManager();
        LocalLoader localDao = new LocalLoader(bazaDeDateManager);
        List<Local> localuri = null;
        try {


            localuri = localDao.getAll();
            if (localuri != null) {
                System.out.println("------Get Localuri-------");
                for (Local local : localuri) {
                    System.out.println(local);
                }
                System.out.println("-----------Sfarsit get localuri-------");
            }

            System.out.println();
            System.out.println("-----Get specific local based on name-------");
            while(true){
                System.out.println("Give me the name of the local");
                Scanner scanner = new Scanner(System.in);
                String command = scanner.nextLine();
                if(command.equals("end")){
                    break;
                }
                try{
                    for(Local local : localDao.findByName(command)){
                        System.out.println(local);
                    }
                }catch (DBComunicationException e){
                    System.out.println("failed getting the data" + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
             }
            System.out.println("---------Get specific local based on name Ending");
            System.out.println();
            System.out.println("-----Creaza un local-------");
            Local local = new Local( "MistretulNastrusnic", 21243351, "Galati", Time.valueOf(LocalTime.parse("08:30")), Time.valueOf(LocalTime.parse("22:30")), "Restaurant");

            try {
                System.out.println( localDao.saveObject(local));

            } catch (MultipleOrNoEntityException e) {
                System.out.println(e.getMessage());
            }
            ;
            System.out.println("---------Sfarsit creare Local---");


        } catch (DBComunicationException e) {
            System.out.println("failed getting the data" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}