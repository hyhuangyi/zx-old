package cn.common.util.comm;

import cn.common.pojo.PageResultDO;
import java.util.ArrayList;
import java.util.List;

/**
 * java 对list 分页
 */
public class ListUtil {
    /**
     * @param list     进行分页的list
     * @param currPage 当前页
     * @param pageNum  每页显示条数
     * @return 分页后数据
     */
    public static <T> PageResultDO<T> pageList(List<T> list, Integer currPage, Integer pageNum) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        if (currPage == null) {
            currPage = 1;
        }
        if (pageNum == null) {
            pageNum = 10;
        }

        Integer start = (currPage - 1) * pageNum;
        Integer end=currPage*pageNum;
        long count = (long) list.size();

        List<T> pageList = list.subList(start, end);
        return new PageResultDO<T>(pageList,count);
    }

    public static void main(String[] args) {
        List<Integer> list=new ArrayList<>();
        for(int i=1;i<=100;i++){
            list.add(i);
        }
        PageResultDO vo= ListUtil.pageList(list,3,10);
        System.out.println(vo.getData());
    }

}
