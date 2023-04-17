package heima_multi_thread_exercise;

import java.util.*;

/**
 * @author ostangt
 * 需求：
 * 有一个抽奖池,该抽奖池中存放了奖励的金额,该抽奖池中的奖项为 {10,5,20,50,100,200,500,800,2,80,300,700};
 * 创建两个抽奖箱(线程)设置线程名称分别为“抽奖箱1”，“抽奖箱2”
 * 随机从抽奖池中获取奖项元素并打印在控制台上,格式如下:
 *    每次抽出一个奖项就打印一个(随机)
 *     抽奖箱1 又产生了一个 10 元大奖
 * Create by 2023/4/17 12:43
 */
public class test5 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        //初始化list的方法  Arrays.asList(); Collections.addAll();
        Collections.addAll(list,10,5,20,50,100,200,500,800,2,80,300,700);
        Box box = new Box(list);

        new Thread(box, "抽奖箱1").start();
        new Thread(box, "抽奖箱2").start();
    }
}

class Box implements  Runnable {
    // private int[] reward = new int[]{10,5,20,50,100,200,500,800,2,80,300,700};
    List<Integer> list;
    // private boolean[] isUsed = new boolean[12];
    // private int remain = 12;
    Box(List list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (Box.class) {
                if (list.size() == 0) break;
                int idx = new Random().nextInt(list.size());
                System.out.println(Thread.currentThread().getName() + "又产生了一个" + list.get(idx) + "元大奖");
                list.remove(idx);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

