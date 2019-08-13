package com.scetc.web.common;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 类描述: POI工具类
 *
 * @author wsz
 * @create 2019/5/21 14:50
 * @version 2.0
 * @since 1.8
 */
public class PoiUtil {

    /**
     * 获取工作簿的内容
     *
     * @param workbook 工作博
     * @param clazz 类型
     * @param startIndex 数据行开始处
     * @param fields 列对应的字段名称
     * @return 表格数据
     */
    public static <T> List<T> getWorkbookContent(Workbook workbook, Class<T> clazz, String[] fields, int startIndex) {
        // 目前支持，第一个工作页
        Sheet firstSheet = workbook.getSheetAt(0);
        List<T> data = new ArrayList<>(firstSheet.getPhysicalNumberOfRows() - startIndex);
        for (int i = startIndex; i < firstSheet.getPhysicalNumberOfRows(); i++) {
            T rowValue = getRowValue(firstSheet.getRow(i), clazz, fields);
            if (rowValue != null) {
                data.add(rowValue);
            }
        }
        return data;
    }

    /**
     * 获取行数据
     *
     * @param row   行
     * @param clazz 类型
     * @return 行数据
     */
    private static <T> T getRowValue(Row row, Class<T> clazz, String[] fields) {
        try {
            T t = clazz.newInstance();
            for (int i = 0; i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                setFiledValue(fields[i], clazz, t, getCellValue(cell));
            }
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 方法功能描述: 设置对象的某个字段内容
     *
     * @author wsz
     * @date 2019/5/21 15:55
     * @param filedName  需要获取值的字段
     * @param clazz      类型
     * @param target     目标对象
     * @param value      请求参数
     */
    private static void setFiledValue(String filedName, Class clazz, Object target, String value) {
        Class filedClass = getFiledClass(filedName, clazz);
        Method method = ReflectionUtils.findMethod(clazz, getMethodName(filedName), filedClass);
        ReflectionUtils.invokeMethod(Objects.requireNonNull(method), target, convertStringToTargetType(value, filedClass));
    }

    /**
     * 方法功能描述: 字符串转为目标类型
     *
     * @author wsz
     * @date 2019/5/22 8:58
     * @param value 需要转换为其他类型的字符串
     * @param targetType 目标类型
     * @return 转换后的类型
     */
    private static Object convertStringToTargetType(String value, Class targetType) {

        // 防止""转换为其他类型失败的处理方式
        if (isEmpty(value)) {
            if (String.class.equals(targetType)) {
                return value;
            } else {
                return null;
            }
        }

        if (Integer.class.equals(targetType)) {
            return Integer.valueOf(value);
        } else if (Boolean.class.equals(targetType)) {
            return Boolean.valueOf(value);
        } else if (Double.class.equals(targetType)) {
            return Double.valueOf(value);
        } else if (Float.class.equals(targetType)) {
            return Float.valueOf(value);
        } else if (Short.class.equals(targetType)) {
            return Short.valueOf(value);
        } else if (Long.class.equals(targetType)) {
            return Long.valueOf(value);
        } else if (BigDecimal.class.equals(targetType)) {
            return BigDecimal.valueOf(Double.valueOf(value));
        } else if (BigInteger.class.equals(targetType)) {
            return BigInteger.valueOf(Long.valueOf(value));
        } else {
            return value;
        }
    }

    public static boolean isEmpty(String value) {
        return (value == null || "".equals(value));
    }

    /**
     * 方法功能描述: 获取字段类型
     *
     * @author wsz
     * @date 2019/5/21 17:41
     * @param filedName  需要获取值的字段
     * @param clazz      类型
     * @return 字段类型
     */
    private static Class getFiledClass(String filedName, Class clazz) {
        return Objects.requireNonNull(ReflectionUtils.findField(clazz, filedName)).getType();
    }

    /**
     * 方法功能描述: 拼接字段的get方法名称
     *
     * @author wsz
     * @date 2019/5/21 15:55
     * @param fieldName 字段名称
     * @return 字段的get方法名称
     */
    private static String getMethodName(String fieldName) {
        return"set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 获取单元格的值
     *
     * @param cell 单元格内容
     * @return 单元格内容
     */
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

            return String.valueOf(cell.getStringCellValue());

        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

            return String.valueOf(cell.getBooleanCellValue());

        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

            return cell.getCellFormula();

        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            DecimalFormat df = new DecimalFormat("0");
            return df.format(cell.getNumericCellValue());
        } else {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            return cell.getStringCellValue();
        }
    }

}