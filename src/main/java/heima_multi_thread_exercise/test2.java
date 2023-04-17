package heima_multi_thread_exercise;

/**
 * @author ostangt
 *
 * 需求：
 * 有100份礼品,两人同时发送，当剩下的礼品小于10份的时候则不再送出。
 * 利用多线程模拟该过程并将线程的名字和礼物的剩余数量打印出来.
 * Create by 2023/4/17 11:22
 */
public class test2 {
    public static void main(String[] args) {
        Gift gift = new Gift();
        new Thread(gift, "甲").start();
        new Thread(gift, "乙").start();
    }

}

class Gift implements Runnable{
    private static volatile int cnt = 100;
    private static Object lock = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                if (cnt < 10) break;
                cnt --;
                System.out.println(Thread.currentThread().getName() + "还剩余的礼物数量为" + cnt);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
