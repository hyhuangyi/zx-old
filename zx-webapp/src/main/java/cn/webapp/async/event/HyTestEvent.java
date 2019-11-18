package cn.webapp.async.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by huangYi on 2018/8/17
 **/
public class HyTestEvent extends ApplicationEvent {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public HyTestEvent(Object source) {
        super(source);
    }

    public HyTestEvent(Object source, String name, Integer age) {
        super(source);
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "HyTestEvent{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
