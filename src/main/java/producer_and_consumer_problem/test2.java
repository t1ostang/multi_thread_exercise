package producer_and_consumer_problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ostangt
 * 生产者消费者模型  wait notify实现
 * https://blog.csdn.net/CSDN_WYL2016/article/details/109540018
 * https://blog.csdn.net/lijiuhuizanping/article/details/117401034 可参考
 * Create by 2023/4/17 19:47
 */
public class test2 {
    public static void main(String[] args) {
        GoodFactory1 goodFactory1 = new GoodFactory1();
        new Thread(new GoodConsumer1(goodFactory1)).start();
        new Thread(new GoodProducer1(goodFactory1)).start();
    }
}

class GoodFactory1 {
    List<Good1> list = new ArrayList<>(5);
    AtomicInteger atomicInteger = new AtomicInteger(1);

    public void consume() throws InterruptedException {
        synchronized (list) {
            if (list.size() == 0) {
                System.out.println("消费者阻塞");
                list.wait();
            }
            else {
                Good1 good1 = list.remove(0);
                System.out.println("消费者" + Thread.currentThread().getName() + "消费了商品: 名称为" + good1.getName()+ " 价值为" + good1.getValue());
                list.notifyAll();
            }
        }
    }

    public void produce() throws InterruptedException {
        synchronized (list) {
            if (list.size() == 5) {
                System.out.println("生产者阻塞");
                list.wait();
            }
            else {
                Good1 good1 = new Good1(new Random().nextInt(100), "商品" + atomicInteger.getAndIncrement());
                list.add(good1);
                System.out.println("生产者" + Thread.currentThread().getName() + "生产者了商品: 名称为" + good1.getName()+ " 价值为" + good1.getValue());
                list.notifyAll();
            }
        }
    }
}

class GoodConsumer1 implements Runnable {

    GoodFactory1 goodFactory1;

    public GoodConsumer1(GoodFactory1 goodFactory1) {
        this.goodFactory1 = goodFactory1;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(2000);
                goodFactory1.consume();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class GoodProducer1 implements Runnable {


    GoodFactory1 goodFactory1;

    public GoodProducer1(GoodFactory1 goodFactory1) {
        this.goodFactory1 = goodFactory1;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(100);
                goodFactory1.produce();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


class Good1 {
    String name;
    int value;

    public Good1(int value, String name) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Good1{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}

