package com.wangyuxuan.mybatis.sqlnode;


import com.wangyuxuan.mybatis.sqlnode.iface.SqlNode;
import com.wangyuxuan.mybatis.utils.GenericTokenParser;
import com.wangyuxuan.mybatis.utils.OgnlUtils;
import com.wangyuxuan.mybatis.utils.SimpleTypeRegistry;
import com.wangyuxuan.mybatis.utils.TokenHandler;

/**
 * @author wangyuxuan
 * @date 2020/5/8 10:35
 * @description 封装带有${}的SQL文本信息
 */
public class TextSqlNode implements SqlNode {

    private String sqlText;

    public TextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    public boolean isDynamic() {
        if (sqlText.indexOf("${") > -1) {
            return true;
        }
        return false;
    }

    @Override
    public void apply(DynamicContext context) {
        // 主要来处理${}中的参数名称，从入参对象中获取对应的参数值
        TokenHandler handler = new BindingTokenHandler(context);
        // 根据${ 和 }去截取字符串，最终匹配到的${}中的内容，交给TokenHandler去处理
        GenericTokenParser tokenParser = new GenericTokenParser("${", "}", handler);
        // 执行解析过程，返回值是处理完${}之后的值
        sqlText = tokenParser.parse(sqlText);
        context.appendSql(sqlText);
    }

    class BindingTokenHandler implements TokenHandler {

        private DynamicContext context;

        public BindingTokenHandler(DynamicContext context) {
            this.context = context;
        }

        @Override
        public String handleToken(String content) {
            Object parameterObject = context.getBindings().get("_parameter");
            if (parameterObject == null) {
                return "";
            } else if (SimpleTypeRegistry.isSimpleType(parameterObject.getClass())) {
                return parameterObject.toString();
            }
            Object value = OgnlUtils.getValue(content, parameterObject);
            return value == null ? "" : value.toString();
        }
    }
}
