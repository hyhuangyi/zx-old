package cn.common.util.algorithm;

import java.util.Arrays;

/**
 * 冒泡排序 2019-3-21
 */
public class BubbleSortUtil {
    public static int[] bubbleSort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {//轮数
            boolean flag = true;//增加标志位
            for (int k = 0; k < len - 1 - i; k++) {//每轮次数
                if (arr[k] > arr[k + 1]) {
                    int temp = arr[k];
                    arr[k] = arr[k + 1];
                    arr[k + 1] = temp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
        return arr;
    }

    public static void main(String[]args){
        int arr[]={3,5,9,1,3,9,25,3};
        System.out.println(Arrays.toString(bubbleSort(arr)));
    }
}
