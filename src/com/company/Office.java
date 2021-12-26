package com.company;

import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Office {
    private Semaphore TAChair;
    private Semaphore TALock;
    private Semaphore studentLock;
    private AtomicInteger studentCount;
    private int noOfWaitingChar;
    private Lock lock;
    public int LateStudents=0;
    public int queueCount =0;
    public int arr [];

    public Office(Semaphore TALock, Semaphore studentLock, int noOfWaitingChar, int noOfTAS) {
        this.TALock = TALock;
        this.studentLock = studentLock;
        this.noOfWaitingChar = noOfWaitingChar;
        this.studentCount = new AtomicInteger(0);
        this.TAChair = new Semaphore(noOfTAS);
        this.lock = new ReentrantLock();
    }

    public void acceptStudent(){
        lock.lock();
        while(isOfficeFull()){
                        try {
                            System.out.printf("%s can't sit at %s \n",Thread.currentThread().getName(), new Date());
                            LateStudents = LateStudents +1;

                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
        }
        lock.unlock();
        studentCount.incrementAndGet();
        queueCount = studentCount.intValue();
        System.out.printf("%s students in Queue \n",queueCount);



        try {


            System.out.printf("%s students will come later \n",LateStudents );
            TAChair.acquire();
            studentCount.decrementAndGet();
            queueCount = studentCount.intValue();
            System.out.printf("%s students in Queue \n",queueCount);

            System.out.printf("%s having Meeting with TA at %s \n", Thread.currentThread().getName(), new Date());

            TALock.release();


            studentLock.acquire();
            System.out.printf("%s completed Meeting at %s \n", Thread.currentThread().getName(), new Date());

        } catch (InterruptedException e){
            e.printStackTrace();
            return;
        } finally {
            TAChair.release();
//            if(!isOfficeFull() && LateStudents!=0)
//                LateStudents = LateStudents -1;
//            else if (LateStudents==0)
//                LateStudents=0;
        }
    }

    private boolean isOfficeFull(){
        return studentCount.get() == noOfWaitingChar;
    }
}
