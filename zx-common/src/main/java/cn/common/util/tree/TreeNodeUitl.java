package cn.common.util.tree;


import java.util.ArrayList;
import java.util.List;

/**
 * 默认递归工具类，用于遍历有父子关系的节点，例如菜单树，字典树等等
 * 
 * @author juny
 * @date 2019年4月24日
 *
 */
public class TreeNodeUitl{

    /**
     * 顶级节点的父节点id(默认0)
     */
    private static int ROOT_PARENT_ID = 0;
    
    public static <T extends TreeNode> List<T> buildTree(List<T> nodes) {
        //构建之前的节点处理工作
        List<T> readyToBuild = beforeBuild(nodes);
        //具体构建的过程
        List<T> builded = executeBuilding(readyToBuild);
        //构建之后的处理工作
        return afterBuild(builded);
    }

    /**
     * 查询子节点的集合
     *
     * @param totalNodes     所有节点的集合
     * @param node           被查询节点的id
     * @param childNodeLists 被查询节点的子节点集合
     */
    private static <T extends TreeNode> void  buildChildNodes(List<T> totalNodes, T node, List<T> childNodeLists) {
        if (totalNodes == null || node == null) {
            return;
        }

        List<T> nodeSubLists = getSubChildsLevelOne(totalNodes, node);

        if (nodeSubLists.size() == 0) {

        } else {
            for (T nodeSubList : nodeSubLists) {
                buildChildNodes(totalNodes, nodeSubList, new ArrayList<>());
            }
        }

        childNodeLists.addAll(nodeSubLists);
        node.setChildrens(childNodeLists);
    }

    /**
     * 获取子一级节点的集合
     *
     * @param list 所有节点的集合
     * @param node 被查询节点的model
     * @author fengshuonan
     */
    private static <T extends TreeNode> List<T> getSubChildsLevelOne(List<T> list, T node) {
        List<T> nodeList = new ArrayList<>();
        for (T nodeItem : list) {
            if (nodeItem.getParentId() == node.getId()) {
                nodeList.add(nodeItem);
            }
        }
        return nodeList;
    }

    private static <T extends TreeNode> List<T> beforeBuild(List<T> nodes) {
        //默认不进行前置处理,直接返回
        return nodes;
    }

    private static <T extends TreeNode> List<T> executeBuilding(List<T> nodes) {
        for (T treeNode : nodes) {
            buildChildNodes(nodes, treeNode, new ArrayList<>());
        }
        return nodes;
    }

    private static <T extends TreeNode> List<T> afterBuild(List<T> nodes) {
        //去掉所有的二级节点
        ArrayList<T> results = new ArrayList<>();
        for (T node : nodes) {
            if (node.getParentId() == ROOT_PARENT_ID) {
                results.add(node);
            }
        }
        setChildsWithNullIfSizeEqualToZero(results);
        return results;
    }
    
    private static <T extends TreeNode> void setChildsWithNullIfSizeEqualToZero(List<T> nodes) {
    	if(null != nodes) {
    		for(T node:nodes) {
    			if(null == node.getChildrens() || node.getChildrens().size() == 0) {
    				node.setChildrens(null);
    			}else {
    				List<T> subs = node.getChildrens();
    				setChildsWithNullIfSizeEqualToZero(subs);
    			}
    		}
    	}
    }
}