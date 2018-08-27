package com.cp.epa.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
 */
public class ArithUtil {
    private static final int DEF_DIV_SCALE = 10;
    
    /**
     * 两个Double数相加
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double add(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).doubleValue();
    }
    
    /**
     * 两个String数相加 返回String
     * @param v1
     * @param v2
     * @return Double
     */
    public static String add(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        Double _d =  b1.add(b2).doubleValue();
        return _d.toString();
    }
    
    /**
     * 两个String数相加 返回String 保留小说点 四舍五入
     * @param v1
     * @param v2
     * @return Double
     */
    public static String add(String v1,String v2,int scale){
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        Double _d =  b1.add(b2).doubleValue();
        return scale(_d.toString(),scale);
    }
    
    /**
     * 两个Double数相减
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double sub(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 两个Double数相减
     * @param v1
     * @param v2
     * @return Double
     */
    public static String sub(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).toString();
    }
    /**
     * 两个Double数相减保留小数 四舍五入
     * @param v1
     * @param v2
     * @return Double
     */
    public static String sub(String v1,String v2,int scale){
    	if(scale<0)
            throw new IllegalArgumentException("保留小说位不能为负数");
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        b2 = b1.subtract(b2);
        return b2.setScale(scale,BigDecimal.ROUND_HALF_UP).toString();
    }
    
    /**
     * 两个Double数相乘
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double mul(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).doubleValue();
    }
    
    /**
     * 两个Double数相乘
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double mul(Double v1,Double v2,int scale){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        double value = ((b1.multiply(b2)).setScale(scale,RoundingMode.HALF_UP)).doubleValue();
        return value;
    }
    
    /**
     * 两个String数相乘 返回String 
     * @param v1
     * @param v2
     * @return Double
     */
    public static String mul(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        Double _d =  b1.multiply(b2).doubleValue();
        return _d.toString();
    }
    
    /**
     * 两个String数相乘 返回String，保留小数 四舍五入
     * @param v1
     * @param v2
     * @return Double
     */
    public static String mul(String v1,String v2,int scale){
    	if(scale<0)
            throw new IllegalArgumentException("保留小说位不能为负数");
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        String value = ((b1.multiply(b2)).setScale(scale,RoundingMode.HALF_UP)).toString();
        return value;
    }
    
    /**
     * 两个String数相乘 返回String，保留小数 四舍五入
     * @param v1
     * @param v2
     * @return Double
     */
    public static double mulDouble(String v1,String v2,int scale){
    	if(scale<0)
            throw new IllegalArgumentException("保留小说位不能为负数");
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        double value = ((b1.multiply(b2)).setScale(scale,RoundingMode.HALF_UP)).doubleValue();
        return value;
    }
    /**
     * 两个String数相乘 返回String
     * @param v1
     * @param v2
     * @return Double
     */
    public static float mulFloat(String v1,String v2,int scale){
    	if(scale<0)
            throw new IllegalArgumentException("保留小说位不能为负数");
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return ((b1.multiply(b2)).setScale(scale,RoundingMode.HALF_UP)).floatValue();
    }
    
    /**
     * 两个Double数相除 四舍五入
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double div(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2,DEF_DIV_SCALE,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 两个Double数相除，并保留scale位小数 四舍五入
     * @param v1
     * @param v2
     * @param scale
     * @return Double
     */
    public static Double div(Double v1,Double v2,int scale){
    	if(scale<0)
            throw new IllegalArgumentException("保留小说位不能为负数");
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 两个String数相除 四舍五入
     * @param v1
     * @param v2
     * @return Double
     */
    public static String div(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2,DEF_DIV_SCALE,BigDecimal.ROUND_HALF_UP).toString();
    }
    
    /**
     * 两个String数相除保留小数 四舍五入
     * @param v1
     * @param v2
     * @return Double
     */
    public static String div(String v1,String v2,int scale){
    	if(scale<0)
            throw new IllegalArgumentException("保留小说位不能为负数");
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue()+"";
    }
    
    /**
     * 两个String数相除保留小数 
     * @param v1
     * @param v2
     * @return Double
     */
    public static double divDouble(String v1,String v2,int scale){
    	if(scale<0)
            throw new IllegalArgumentException("保留小说位不能为负数");
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 两个String数相除保留小数 
     * @param v1
     * @param v2
     * @return Double
     */
    public static float divFloat(String v1,String v2,int scale){
    	if(scale<0)
            throw new IllegalArgumentException("保留小说位不能为负数");
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).floatValue();
    }
    /**
     * 保留scale位小数  四舍五入
     * @param v1
     * @param scale
     * @return Double
     */
    public static String scale(String v1,int scale){
        if(scale<0){
            throw new IllegalArgumentException("保留小数点位数的值不能小于0");
        }
        BigDecimal b1 = new BigDecimal(v1);
        return b1.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }
    
    /**
     * 并保留scale位小数  直接舍弃
     * @param v1
     * @param scale
     * @return Double
     */
    public static String scaleDown(String v1,int scale){
        if(scale<0){
            throw new IllegalArgumentException("保留小数点位数的值不能小于0");
        }
        BigDecimal b1 = new BigDecimal(v1);
        return b1.setScale(scale, BigDecimal.ROUND_DOWN).toString();
    }
    
    
    /**
     * 保留scale位小数  四舍五入
     * @param v1
     * @param scale
     * @return Double
     */
    public static double scale(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException("保留小数点位数的值不能小于0");
        }
        BigDecimal b1 = new BigDecimal(v);
        return b1.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * 将字符串类型的浮点数转化为百分比，并保留几位小说
     * 功能：<br/>
     *
     * @author 杜中良
     * @version 2015-3-14 上午09:43:14 <br/>
     */
    public static String percent(String value,int scale){
    	  if(scale<0){
              throw new IllegalArgumentException("保留小数点位数的值不能小于0");
          }
    	  NumberFormat nf = NumberFormat.getPercentInstance();
    	  nf.setMaximumFractionDigits(scale);
    	  return nf.format(Double.parseDouble(value));
    }
    
    /**
     * 两个数值相除保留小数点位数      直接舍弃的方式
     * 功能：<br/>
     *
     * @author 杜中良
     * @version 2015-11-9 下午03:11:40 <br/>
     */
    public static String divDown(String v1,String v2,int scale){
    	if(scale<0)
            throw new IllegalArgumentException("保留小说位不能为负数");
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2,scale,BigDecimal.ROUND_DOWN).doubleValue()+"";
    }
    
    /**
     * 两个String数相乘 返回String，直接舍弃的方式
     * @param v1
     * @param v2
     * @return Double
     */
    public static String mulDown(String v1,String v2,int scale){
    	if(scale<0)
            throw new IllegalArgumentException("保留小说位不能为负数");
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        String value = ((b1.multiply(b2)).setScale(scale,RoundingMode.FLOOR)).toString();
        return value;
    }
    
    /**
     * 两个String数相加 返回String 保留小说点 直接舍弃的方式
     * @param v1
     * @param v2
     * @return Double
     */
    public static String addDown(String v1,String v2,int scale){
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        b2 = b1.add(b2);
        return b2.setScale(scale,BigDecimal.ROUND_DOWN).toString();
    }
    
    /**
     * 两个Double数相减保留小数 直接舍弃的方式
     * @param v1
     * @param v2
     * @return Double
     */
    public static String subDown(String v1,String v2,int scale){
    	if(scale<0)
            throw new IllegalArgumentException("保留小说位不能为负数");
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        b2 = b1.subtract(b2);
        return b2.setScale(scale,BigDecimal.ROUND_DOWN).toString();
    }
   
    /**
     * 
     * 功能：<br/> String 类型的数值比较大小
     *
     * @author 孟雪勤
     * @version 2015-10-26 上午11:41:54 <br/>
     */
    public static int comparison(String p1,String p2){
    	BigDecimal b1 = new BigDecimal(p1);
        BigDecimal b2 = new BigDecimal(p2);
        
        return b1.compareTo(b2);
    }
    
    /**
     * 求绝对值
     * @param v1
     * @param v2
     * @return Double
     */
    public static String abs(String v){
        BigDecimal b1 = new BigDecimal(v);
        return b1.abs()+"";
    }
    
    //
    public static void main(String[] args){
    	System.out.println(comparison("18","20"));
    
    	System.out.println(scale("0.23",4));
    }

}