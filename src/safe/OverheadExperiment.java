package safe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OverheadExperiment {

    private static void runExecutionBoundedTest(int nthreads, List<Counter> counters,
                                                boolean random, int nexecutions) throws InterruptedException {
        final List<CounterThread> threads = new ArrayList<CounterThread>();
        for (int i = 0; i < nthreads; i++) {
            CounterThread t;
            if (random)
                t = new CounterThread(counters, nexecutions);
            else
                t = new CounterThread(counters.get(i % counters.size()), nexecutions);

            t.start();
            threads.add(t);
        }

        for (int i = 0; i < nthreads; i++) {
            CounterThread t = threads.get(i);
            t.join();
        }
    }

    public static void main(String[] args) throws InterruptedException, NoSuchMethodException {
        int nthreads = Integer.parseInt(args[0]);
        int ncounters = Integer.parseInt(args[1]);
        String type = args[2];

        List<Counter> counters = new ArrayList<Counter>();
        for (int i = 0; i < ncounters; i++) {
            Lock lock;
            if (type.equals("s"))
                lock = new safe.ReentrantLock();
            else if (type.equals("e"))
                lock = new safe.EclipseLock();
            else
                lock = new ReentrantLock();
            counters.add(new Counter(lock));
        }

        runExecutionBoundedTest(nthreads, counters, false, 1000);
    }
}
