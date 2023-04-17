package heima_multi_thread_exercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author ostangt
 * 需求：
 * 	在上一题基础上继续完成如下需求：
 *  每次抽的过程中，不打印，抽完时一次性打印(随机)
 *  在此次抽奖过程中，抽奖箱1总共产生了6个奖项。
 *  分别为：10,20,100,500,2,300最高奖项为300元，总计额为932元
 *  在此次抽奖过程中，抽奖箱2总共产生了6个奖项。
 *  分别为：5,50,200,800,80,700最高奖项为800元，总计额为1835元
 * Create by 2023/4/17 15:13
 */
public class test6 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        //初始化list的方法  Arrays.asList(); Collections.addAll();
        Collections.addAll(list,10,5,20,50,100,200,500,800,2,80,300,700);
        Box1 box = new Box1(list);

        new Thread(box, "抽奖箱1").start();
        new Thread(box, "抽奖箱2").start();
    }
}

class Box1 implements  Runnable {
    List<Integer> list;
    Box1(List list) {
        this.list = list;
    }
    // 使用threadlocal 在每个线程中保持自己的变量
    ThreadLocal<List<Integer>> sum = ThreadLocal.withInitial(ArrayList::new);

    // threadlocal 初始化 第二种方式
    // ThreadLocal<ArrayList<Integer>> threadLocalList = new ThreadLocal<ArrayList<Integer>>() {
    //     @Override
    //     protected ArrayList<Integer> initialValue() {
    //         return new ArrayList<Integer>();
    //     }
    // };


    @Override
    public void run() {
        while (true) {
            synchronized (Box.class) {
                if (list.size() == 0) {
                    System.out.println(Thread.currentThread().getName());
                    List<Integer> integers = sum.get();
                    for (int i = 0; i < integers.size(); i ++) {
                        System.out.print(integers.get(i) + " ");
                    }
                    System.out.println();
                    break;
                }
                int idx = new Random().nextInt(list.size());
                List<Integer> integers = sum.get();
                integers.add(list.get(idx));
                // 不用set 也行 因为本来就有引用
                // sum.set(integers);
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

