package com.company;

import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args)  throws InterruptedException{
        int noOfStudents = 15;
        int nofTAS =2;
        Thread [] Studentthreads = new Thread[noOfStudents];
        Thread [] TASthreads = new Thread[nofTAS];

        Semaphore TALock = new Semaphore(0);
        Semaphore StudentLock = new Semaphore(0);

        Office office = new Office(TALock, StudentLock, 5, nofTAS);

        TeachingAssistant ta = new TeachingAssistant(TALock, StudentLock);

       // Thread TAthread = new Thread(ta);
        for(int i =0; i<nofTAS; i++ ){
            Thread TAthread = new Thread(ta);
            TAthread.setName("TA-" +i);
            TASthreads[i] = TAthread;
        }


//            TAthread.start();

        for(int i=0; i< nofTAS; i++) {
            TASthreads[i].start();
        }

            for (int i = 0; i < noOfStudents; i++) {
                Thread thread = new Thread(new Student(office));
                thread.setName("Student-" + i);
                Studentthreads[i] = thread;
            }

            for (int i = 0; i < noOfStudents; i++) {
                Studentthreads[i].start();
            }

            for (int i = 0; i < noOfStudents; i++) {
                Studentthreads[i].join();
            }
//            TAthread.interrupt();

        for(int i=0; i< nofTAS; i++) {
            TASthreads[i].join();
        }
        }
    }

