package com.company;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class TeachingAssistant implements Runnable{
    private Semaphore TALock;
    private Semaphore studentLock;
    private int TASLeeping;
    private int TAWalkingUp=0;

    public TeachingAssistant(Semaphore TALock, Semaphore studentLock, int nofTAS) {
        this.TALock = TALock;
        this.studentLock = studentLock;
        this.TASLeeping = nofTAS;
    }

    @Override
    public void run(){
        while (true){
            try{
                TALock.acquire();

                System.out.printf("%s waking up \n", Thread.currentThread().getName());
                TAWalkingUp++;
                TASLeeping--;
                System.out.printf("num of TAWalkingUp is %s and num of TASleeping is %s  \n", TAWalkingUp,TASLeeping);
                HelpStudents();

                studentLock.release();
                System.out.printf("%s sleeping \n", Thread.currentThread().getName());
                TASLeeping++;
                if(TAWalkingUp !=0)
                    TAWalkingUp--;
                System.out.printf("num of TAWalkingUp is %s and num of TASleeping is %s  \n", TAWalkingUp,TASLeeping);
//                Thread.sleep(5000);

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
