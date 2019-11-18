package cn.common.util.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RedPacket {

    /**
     *
     * @param allMoney 总金额
     * @param count  总条数
     * @param min   最小额
     * @param max   最大额
     * @return
     */

    public static List<Integer>  randomAssign(int allMoney,int count,int min,int max){
        List<Integer> list=new ArrayList<>();
        if(allMoney<min*count||allMoney>max*count){
            throw new RuntimeException("不可拆分");
        }
        int money=allMoney-count*min;//剩余可支配金额
        for(int i=0;i<count;i++){
            int one=random(money,count-i,min,max);//随机金额
            list.add(min+one);
            money=money-one;//剩余金额
        }
        Collections.shuffle(list);//随机打乱
        return list;
    }

    /**
     *
     * @param money  剩余分配的金额
     * @param count  剩余份数
     * @return
     */
    public static Integer random(int money,int count,int min,int max){
        if(count==1){//最后一份
            return money;
        }
        //可能存在的最小值
        int m=money-(max-min)*(count-1);//假如其他都是最大值  若m>0  则随机值不能小于m,及最小值是m,最大值不能大于max-min
        int n=0;//随机值
        if(m>0){// m至max-min之间
            n=max-min-m+1;
        }else {
            if(money>max-min){//0至 max-min之间
                m=0;
                n=max-min+1;
            }else{ //0至money之间
                m=0;
                n=money+1;
            }
        }
        return new Random().nextInt(n)+m;
    }

    public static void main(String[]args){
        List list=randomAssign(100,5,10,20);
        System.out.println(list);
    }

}
