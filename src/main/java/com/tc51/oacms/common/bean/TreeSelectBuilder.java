package com.tc51.oacms.common.bean;

import java.util.ArrayList;
import java.util.List;

public class TreeSelectBuilder {



    /**
     * 把没有层级关系的集合变成有层级关系的
     * @param treeNodes
     * @param topPid
     * @return
     */
    public static List<TreeSelect> build(List<TreeSelect> treeNodes, Integer topPid){
        List<TreeSelect> nodes=new ArrayList<>();
        for (TreeSelect n1 : treeNodes) {
            if(n1.getPid().equals(topPid)) {
                nodes.add(n1);
            }

            for (TreeSelect n2 : treeNodes) {

                if(n1.getId().equals(n2.getPid())) {
                    if(n1.getChildren() ==null)
                    {
                        n1.setChildren(new ArrayList<>());
                    }
                    n1.getChildren().add(n2);
                }
            }
        }
        return nodes;
    }
}
