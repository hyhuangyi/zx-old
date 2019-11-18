package cn.common.practice.pattern;

import lombok.Data;

/**
 * 工厂模式
 */
public class FactoryPattern {
    public static void main(String[] args) {
        CarFactory bmwFactory=new BMWFactory();
        bmwFactory.getCar();
        CarFactory audiFactory=new AudiFactory();
        audiFactory.getCar();
    }
}
//实体类
@Data
class Car{
    private String name;
    public Car(String name) {
        this.name = name;
    }
}
//工厂接口，功能就是生产汽车
interface CarFactory{
    Car getCar();
}
//宝马工厂
class BMWFactory implements CarFactory{
    @Override
    public Car getCar() {
        System.out.println("通过宝马工厂获取宝马");
        return new Car("bmw");
    }
}
//奥迪工厂
class AudiFactory implements CarFactory{
    @Override
    public Car getCar() {
        System.out.println("通过奥迪工厂获取奥迪");
        return new Car("audi");
    }
}