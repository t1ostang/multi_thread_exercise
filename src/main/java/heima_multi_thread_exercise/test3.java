package heima_multi_thread_exercise;

/**
 * @author ostangt
 *
 * 需求：
 * 同时开启两个线程，共同获取1-100之间的所有数字。
 * 将输出所有的奇数。
 * Create by 2023/4/17 11:27
 */
public class test3 {
    public static void main(String[] args) {
        PrintOddNum printOddNum = new PrintOddNum();
        new Thread(printOddNum, "线程1").start();
        new Thread(printOddNum, "线程2").start();
    }
}

class PrintOddNum implements Runnable {
    private static int idx = 1;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (idx > 100) break;
                if (idx % 2 == 1) System.out.println(Thread.currentThread().getName() +"打印的奇数是" + idx);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                idx ++;
            }
        }
    }
}
