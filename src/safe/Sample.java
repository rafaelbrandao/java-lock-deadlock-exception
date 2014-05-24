package safe;

import java.util.concurrent.locks.Lock;

public class Sample {
    public static void main(String args[]) {
        final Lock lock1 = new safe.ReentrantLock();
        final Lock lock2 = new safe.ReentrantLock();
        
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                lock2.lock();
                try {
                    lock1.lock();
                    try {
                        // do some work                    }
                    } finally { lock1.unlock(); }
                } finally { lock2.unlock(); }
            }
          }        
        );
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                lock1.lock();
                try {
                    lock2.lock();
                    try {
                        // do some work                    }
                    } finally { lock2.unlock(); }
                } finally { lock1.unlock(); }
            }
          }        
        );
        t1.start();
        t2.start();
    }
}
