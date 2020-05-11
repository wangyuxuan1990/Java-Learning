package com.wangyuxuan.mybatis.sqlnode;



import com.wangyuxuan.mybatis.sqlnode.iface.SqlNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/8 10:35
 * @description 封装平级的SqlNode集合信息
 */
public class MixedSqlNode implements SqlNode {

    private List<SqlNode> sqlNodes = new ArrayList<>();

    public MixedSqlNode(List<SqlNode> sqlNodes) {
        this.sqlNodes = sqlNodes;
    }

    @Override
    public void apply(DynamicContext context) {
        for (SqlNode sqlNode : sqlNodes) {
            sqlNode.apply(context);
        }
    }
}
