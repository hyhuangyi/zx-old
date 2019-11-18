package cn.common.pojo;

/**枚举的单例模式*/
public class Singleton {
    private Singleton (){}//私有构造
    public static Singleton getInstance(){
        return SingletonEnum.INSTANCE.getInstance();
    }
    /*目前最为安全的实现单例的方法是通过内部静态enum的方法来实现，
     *因为JVM会保证enum不能被反射并且构造器方法只执行一次。*/
    private enum SingletonEnum{
        INSTANCE;

        private Singleton singleton;
        //JVM会保证此方法绝对只调用一次
        SingletonEnum(){
            singleton=new Singleton();
        }
        public Singleton getInstance(){
            return singleton;
        }
    }
}
