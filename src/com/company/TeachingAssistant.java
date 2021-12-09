package com.company;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class TeachingAssistant implements Runnable{
    private Semaphore TALock;
    private Semaphore studentLock;

    public TeachingAssistant(Semaphore TALock, Semaphore studentLock) {
        this.TALock = TALock;
        this.studentLock = studentLock;
    }

    @Override
    public void run(){
        while (true){
            try{
                TALock.acquire();

                System.out.printf("%s waking up \n", Thread.currentThread().getName());

                HelpStudents();

                studentLock.release();

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void HelpStudents(){
        try{
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
