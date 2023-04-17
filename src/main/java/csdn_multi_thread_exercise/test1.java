package csdn_multi_thread_exercise;

import sun.util.locale.provider.FallbackLocaleProviderAdapter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ostangt
 * 写两个线程，一个线程打印1~ 52，另一个线程打印A~Z，打印顺序是12A34B…5152Z
 * Create by 2023/4/17 20:07
 */
public class test1 {
    public static void main(String[] args) {
        new Thread(new printNum()).start();
        new Thread(new printLetter()).start();
    }
}

class Common {
    public static boolean flag = false;
    public static final Object o = new Object();
    public static String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
}

class printNum implements Runnable {
    @Override
    public void run() {
        synchronized (Common.o) {
            for (int i = 1; i <= 52; ) {
                while (Common.flag) {
                    try {
                        Common.o.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.print(i++);
                System.out.print(i++);
                Common.flag = !Common.flag;
                Common.o.notifyAll();
            }
        }
    }
}

class printLetter implements Runnable {
    @Override
    public void run() {
        synchronized (Common.o) {
            for (int i = 0; i < 26; i++) {
                while (!Common.flag) {
                    try {
                        Common.o.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.print(Common.s.charAt(i));
                Common.flag = !Common.flag;
                Common.o.notifyAll();
            }
        }
    }
}


