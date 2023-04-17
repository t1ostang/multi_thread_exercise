package producer_and_consumer_problem;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ostangt
 * 消费者与生产者模型 LinkedBlockingQueue实现
 * Create by 2023/4/17 16:25
 */
public class test1 {
    public static void main(String[] args) {
        GoodFactory goodFactory = new GoodFactory();

        new Thread(new GoodConsumer(goodFactory)).start();
        new Thread(new GoodProducer(goodFactory)).start();
    }
}


class GoodConsumer implements Runnable{
    GoodFactory goodFactory;
    public GoodConsumer(GoodFactory goodFactory) {
        this.goodFactory = goodFactory;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(6000);
                goodFactory.consumeGood();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class GoodProducer implements Runnable {
    GoodFactory goodFactory;

    public GoodProducer(GoodFactory goodFactory) {
        this.goodFactory = goodFactory;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(500);
                goodFactory.produceGood();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}




// 商品工厂 定义生产、消费方法
class GoodFactory {
    AtomicInteger atomicInteger = new AtomicInteger();
    LinkedBlockingQueue<Good> queue = new LinkedBlockingQueue<Good>(5);
    public void consumeGood() throws InterruptedException {
        Good take = queue.take();
        System.out.println("消费者" + Thread.currentThread().getName() + "消费了商品: 名称为" + take.getName() + " id为" + take.getGoodId());
    }

    public void produceGood() throws InterruptedException {
        Good good = new Good(atomicInteger.incrementAndGet(), "商品" + atomicInteger.get());
        queue.put(good);
        System.out.println("生产者" + Thread.currentThread().getName() + "生产了商品: 名称为" + good.getName() + " id为" + good.getGoodId());
    }
}

class Good {
    private int goodId;
    private String name;

    public Good(int incrementAndGet, String s) {
        this.goodId = incrementAndGet;
        this.name = s;
    }

    public int getGoodId() {
        return goodId;
    }

    @Override
    public String toString() {
        return "Good{" +
                "goodId=" + goodId +
                ", name='" + name + '\'' +
                '}';
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
