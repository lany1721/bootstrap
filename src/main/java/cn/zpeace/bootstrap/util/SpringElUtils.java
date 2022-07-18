package cn.zpeace.bootstrap.util;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import java.util.Map;

/**
 * Created on 2022-7-12.
 *
 * @author skiya
 */
public class SpringElUtils {

    public static <T> T parse(String expression, Class<T> resultType) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expression);
        return exp.getValue(resultType);
    }

    /**
     * 把变量替换成值
     * template = "hello #{#name}, #{#age}"
     * variables = {"name": "skiya", "age": "18"}
     * result = "hello skiya, 18"
     *
     * @param template  模板
     * @param variables 变量
     * @return {@link String}
     */
    public static String format(String template, Map<String, Object> variables) {
        ExpressionParser parser = new SpelExpressionParser();
        TemplateParserContext templateParserContext = new TemplateParserContext();
        Expression exp = parser.parseExpression(template, templateParserContext);
        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();
        variables.forEach(context::setVariable);
        return exp.getValue(context, String.class);
    }

    /**
     * 把变量替换成值
     * template = "hello #{name}, #{age}"
     * valueObject = "name": "skiya", "age": "18"
     * result = "hello skiya, 18"
     *
     * @param template    模板
     * @param valueObject 值对象
     * @return {@link String}
     */
    public static String format(String template, Object valueObject) {
        ExpressionParser parser = new SpelExpressionParser();
        TemplateParserContext templateParserContext = new TemplateParserContext();
        Expression exp = parser.parseExpression(template, templateParserContext);
        return exp.getValue(valueObject, String.class);
    }

    /**
     * 把 map 的 变量占位符 替换成 值
     * map = {"hello": "#{#name}"}
     * variables = {"name": "skiya", "age": "18"}
     * result = {"hello": "skiya"}
     *
     * @param map       待格式化的 map
     * @param variables 变量
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public static Map<String, String> formatMapValue(Map<String, String> map, Map<String, Object> variables) {
        ExpressionParser parser = new SpelExpressionParser();
        TemplateParserContext templateParserContext = new TemplateParserContext();
        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();
        variables.forEach(context::setVariable);
        map.forEach((key, value) -> {
            Expression exp = parser.parseExpression(value, templateParserContext);
            map.put(key, exp.getValue(context, String.class));
        });
        return map;
    }

    /**
     * 把 map 的 变量占位符 替换成 值
     * map = {"hello": "#{name}"}
     * valueObject = "name": "skiya", "age": "18"
     * result = {"hello": "skiya"}
     *
     * @param map         待格式化的 map
     * @param valueObject 值对象
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public static Map<String, String> formatMapValue(Map<String, String> map, Object valueObject) {
        ExpressionParser parser = new SpelExpressionParser();
        TemplateParserContext templateParserContext = new TemplateParserContext();
        map.forEach((key, value) -> {
            Expression exp = parser.parseExpression(value, templateParserContext);
            map.put(key, exp.getValue(valueObject, String.class));
        });
        return map;
    }

}
