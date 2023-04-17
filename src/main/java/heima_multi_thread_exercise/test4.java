package heima_multi_thread_exercise;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author ostangt
 * 需求：
 * 抢红包也用到了多线程。
 * 假设：100块，分成了3个包，现在有5个人去抢。
 * 其中，红包是共享数据。
 * 5个人是5条线程。
 * 打印结果如下：
 * XXX抢到了XXX元
 * XXX抢到了XXX元
 * Create by 2023/4/17 11:34
 */
public class test4 {
    public static void main(String[] args) {
        RedPacket redPacket = new RedPacket();
        for (int i = 0; i < 5; i++) {
            new Thread(redPacket, "线程" + i).start();
        }
    }
}

class RedPacket implements Runnable {
    static BigDecimal price = BigDecimal.valueOf(100.00);
    private static double[] threePacket = new double[3];
    private static boolean[] isUsed = new boolean[3];
    private static int cnt = 0;
    private static BigDecimal minPrice = BigDecimal.valueOf(0.01);

    // 随机分配三个红包的钱数
    static {
        // DecimalFormat df = new DecimalFormat("0.00");
        Random r = new Random();
        double bound = price.subtract(BigDecimal.valueOf(2).multiply(minPrice)).doubleValue();
        double a = r.nextDouble() * bound;
        a = BigDecimal.valueOf(a).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        // System.out.println("here");
        if (a <= minPrice.doubleValue()) a = minPrice.doubleValue();

        bound = bound - a;
        double b = r.nextDouble() * bound;
        b = BigDecimal.valueOf(b).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (b <= minPrice.doubleValue()) b = minPrice.doubleValue();

        double c = price.subtract(BigDecimal.valueOf(a)).subtract(BigDecimal.valueOf(b)).doubleValue();
        threePacket[0] = a;
        threePacket[1] = b;
        threePacket[2] = c;
        System.out.println(a + " " + b + " " + c + " " + price);
    }

    @Override
    public void run() {
        synchronized (isUsed) {
            if (cnt == 3) {
                System.out.println(Thread.currentThread().getName() + "没抢到");
                return;
            }
            isUsed[cnt] = true;
            System.out.println(Thread.currentThread().getName() + "抢到了" + threePacket[cnt] + "元");
            cnt++;
            // int idx = new Random().nextInt(4);
            // if (!isUsed[idx]) {
            //     isUsed[idx] = true;
            //     System.out.println(Thread.currentThread().getName() + "抢到了" + threePacket[idx] + "元");
            //     cnt --;
            // }
        }
    }
}
