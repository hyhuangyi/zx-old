package cn.common.util.tree;

import java.io.Serializable;
import java.util.List;

/*
 * 
 */

/**
 * 构造树节点的接口规范
 * 
 * @author juny
 * @date 2019年4月24日
 *
 */
public interface TreeNode extends Serializable{

    /**
     * 获取节点id
     */
    long getId();

    /**
     * 获取节点父id
     */
    long getParentId();

    /**
     * 设置children
     */
    void setChildrens(List childrenNodes);
    
    List getChildrens();

}
