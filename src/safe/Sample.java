package safe;

import java.util.concurrent.locks.Lock;

public class Sample {
    public static void main(String args[]) {
        final Lock lock1 = new safe.EclipseLock();
        final Lock lock2 = new safe.EclipseLock();
        
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
          }, "alice");
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
          }, "bob");
        t1.start();
        t2.start();
    }
}
