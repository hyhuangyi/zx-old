package cn.common.util.algorithm;

import cn.common.exception.ZXException;
import cn.common.util.math.NumberUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SplitRedPacket {

    // 最小额度
    private static final double MINMONEY = 88;
    // 最大额度
    private static final double MAXMONEY = 888;


    /**
     * @Description: 随机拆分
     * @Author: liumeng
     * @Date: 2019/2/27
     * @Param: [money, count]
     * @Return: java.util.List<java.lang.Integer>
     **/
    public static List<Double> splitRedPackets(double money, int count) {

        if(money < count*MINMONEY || money > count* MAXMONEY){
            System.out.println("不可拆分");
            throw new ZXException("数据不可拆分");
        }
        // 先预留出 count 份 minS ， 其余的做随机
        double moreMoney= NumberUtil.sub(money, NumberUtil.IintegerMul(count,MINMONEY));
        List<Double> list = new ArrayList<Double>();
        for(int i=0; i<count; i++){
            double one = random(moreMoney,count-i, MINMONEY, MAXMONEY);
            list.add(NumberUtil.add1(one,MINMONEY));
            moreMoney = moreMoney-one;
        }
        Collections.shuffle(list);
        return list;
    }
    /**
     * @Description: 随机数额（加上minS 为实际金额）
     * @Author: liumeng
     * @Date: 2019/2/27
     * @Param: [money, count]
     * @Return: java.util.List<java.lang.Integer>
     **/
    private static double random(double money,  int count, double minS, double maxS) {
        // 数量为1，直接返回金额
        if (count == 1) {
            return money;
        }
        // 每次限定随机数值
        // 首先判断实际最小值
        double realMinS = money-(maxS-minS)*(count-1);
        double realRange ;
        // 如果存在实际最小值，则在实际最小值realMinS 和 maxS-minS 之间 random 数值
        if(realMinS > 0){
            realRange = maxS-minS-realMinS + 1;
        }
        //  如果不存在实际最小值（也就是说数值可以是minS）
        else{
            if(money > maxS-minS){
                realMinS = 0;
                realRange = maxS-minS + 1;
            }else{
                realMinS = 0;
                realRange = money + 1;
            }
        }
        return  NumberUtil.add1(new Random().nextDouble()*realRange , realMinS);
    }


    public static void main(String[] args) {
        int money = 5200;
        int count = 10;

        List list = splitRedPackets(money, count);
        System.out.println(list);

    }
}
