package heima_multi_thread_exercise;

/**
 * @author ostangt
 * <p>
 * 多线程练习1(卖电影票)（学生自己练习)
 * 一共有1000张电影票,可以在两个窗口领取,假设每次领取的时间为3000毫秒，
 * 要求:请用多线程模拟卖票过程并打印剩余电影票的数量
 * Create by 2023/4/17 10:39
 */
public class test1 {


    public static void main(String[] args) {
        Sell sell = new Sell();
        new Thread(sell, "窗口1").start();
        new Thread(sell, "窗口2").start();
    }
}

class Sell implements Runnable {
    @Override
    public void run() {

        while (true) {
            synchronized (Ticket.lock) {
                if (Ticket.cnt == 0) break;
                Ticket.cnt--;
                System.out.println("当前线程" + Thread.currentThread().getName() + "剩余电影票的数量：" + Ticket.cnt);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

class Ticket {
    public static int cnt = 1000;
    public static Object lock = new Object();
}


