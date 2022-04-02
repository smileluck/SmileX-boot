package top.zsmile.test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo2 {

    public static void main(String[] args) {

        List<Map<String,String>> table = addressResolution("宁波市鄞州区慧和大厦813室");
        System.out.println(table);
        System.out.println(table.get(0).get("province"));
        System.out.println(table.get(0).get("city"));
        System.out.println(table.get(0).get("county"));
        System.out.println(table.get(0).get("town"));
        System.out.println(table.get(0).get("village"));

//
//        String[] a = Demo2.converDetailAddressToProvinceAndCityAndCountyAndStreet("云南昆明盘龙区溪畔丽景7栋2402");
//
//        System.out.println(a);
//
//        for (int i = 0; i < a.length; i++) {
//            System.out.println(a[i]);
//        }
//        广东省
//        深圳市
//        福田区
//        新闻路2023号
    }

    /**
     * 解析地址
     * @param address
     * @return
     */
    public static List<Map<String,String>> addressResolution(String address){
        /*
         * java.util.regex是一个用正则表达式所订制的模式来对字符串进行匹配工作的类库包。它包括两个类：Pattern和Matcher Pattern
         *    一个Pattern是一个正则表达式经编译后的表现模式。 Matcher
         *    一个Matcher对象是一个状态机器，它依据Pattern对象做为匹配模式对字符串展开匹配检查。
         *    首先一个Pattern实例订制了一个所用语法与PERL的类似的正则表达式经编译后的模式，然后一个Matcher实例在这个给定的Pattern实例的模式控制下进行		   *	字符串的匹配工作。
         */
        String regex="(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)?(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)?(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
        //(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)?表示一个模块 最后的问号表示可以为空
        Matcher m= Pattern.compile(regex).matcher(address);
        String province=null,city=null,county=null,town=null,village=null;
        List<Map<String,String>> table=new ArrayList<Map<String,String>>();
        Map<String,String> row=null;
        while(m.find()){
            row=new LinkedHashMap<String,String>();
            province=m.group("province");
            row.put("province", province==null?"":province.trim());
            city=m.group("city");
            row.put("city", city==null?"":city.trim());
            county=m.group("county");
            row.put("county", county==null?"":county.trim());
            town=m.group("town");
            row.put("town", town==null?"":town.trim());
            village=m.group("village");
            row.put("village", village==null?"":village.trim());
            table.add(row);
        }
        return table;
    }
    /**
     * 处理详细地址拆分省 市 区 地址的转换关系
     *
     * @param detailAddress
     * @return
     */
    public static String[] converDetailAddressToProvinceAndCityAndCountyAndStreet(
            String detailAddress) {
        String[] r = new String[4];
        try {

            String tempStr = detailAddress;
            String province = null;
            int provinceIdx = processProvince(tempStr);
            if (provinceIdx > -1) {
                province = tempStr.substring(0, provinceIdx + 1);
                tempStr = tempStr.substring(provinceIdx + 1);
            }
            ;

            String city = null;
            int cityIdx = processCity(tempStr);
            if (cityIdx > -1) {
                city = tempStr.substring(0, cityIdx + 1);
                tempStr = tempStr.substring(cityIdx + 1);
            }

            String county = null;
            int countyIdx = processCounty(tempStr);
            if (countyIdx > -1) {
                county = tempStr.substring(0, countyIdx + 1);
                tempStr = tempStr.substring(countyIdx + 1);
            }
            ;

            String street = tempStr;

            r[0] = province;
            r[1] = city;
            r[2] = county;
            r[3] = street;
        } catch (Exception e) {
            // 报错就直接返回r 为空即可。无法正常转义
        }

        return r;
    }

    // (?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)
    private static int processProvince(String s) {
        int[] idxs = new int[3];
        int provinceIdx = -1;
        if ((provinceIdx = s.indexOf("省")) > -1)
            idxs[0] = provinceIdx;
        provinceIdx = -1;
        if ((provinceIdx = s.indexOf("市")) > -1)
            idxs[1] = provinceIdx;
        provinceIdx = -1;
        if ((provinceIdx = s.indexOf("区")) > -1)
            idxs[2] = provinceIdx;

        Arrays.sort(idxs);

        for (int i = 0; i < idxs.length; i++) {
            int j = idxs[i];
            if (j > 0) {
                return j;
            }
        }

        return provinceIdx;
    }

    // (?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)
    private static int processCity(String s) {
        int[] idxs = new int[7];
        int cityIdx = -1;
        if ((cityIdx = s.indexOf("县")) > -1)
            idxs[0] = cityIdx;
        cityIdx = -1;
        if ((cityIdx = s.indexOf("自治州")) > -1)
            idxs[1] = cityIdx + 2;
        cityIdx = -1;
        if ((cityIdx = s.indexOf("市辖区")) > -1)
            idxs[2] = cityIdx + 2;
        cityIdx = -1;
        if ((cityIdx = s.indexOf("市")) > -1)
            idxs[3] = cityIdx;
        cityIdx = -1;
        if ((cityIdx = s.indexOf("区")) > -1)
            idxs[4] = cityIdx;
        cityIdx = -1;
        if ((cityIdx = s.indexOf("地区")) > -1)
            idxs[5] = cityIdx + 1;
        cityIdx = -1;
        if ((cityIdx = s.indexOf("盟")) > -1)
            idxs[6] = cityIdx;

        Arrays.sort(idxs);

        for (int i = 0; i < idxs.length; i++) {
            int j = idxs[i];
            if (j > 0) {
                return j;
            }
        }

        return cityIdx;
    }

    // (?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)
    private static int processCounty(String s) {
        int[] idxs = new int[6];
        int countyIdx = -1;
        if ((countyIdx = s.indexOf("县")) > -1)
            idxs[0] = countyIdx;
        countyIdx = -1;
        if ((countyIdx = s.indexOf("旗")) > -1)
            idxs[1] = countyIdx;
        countyIdx = -1;
        if ((countyIdx = s.indexOf("海域")) > -1)
            idxs[2] = countyIdx + 1;
        countyIdx = -1;
        if ((countyIdx = s.indexOf("市")) > -1)
            idxs[3] = countyIdx;
        countyIdx = -1;
        if ((countyIdx = s.indexOf("区")) > -1)
            idxs[4] = countyIdx;
        countyIdx = -1;
        if ((countyIdx = s.indexOf("岛")) > -1)
            idxs[5] = countyIdx;

        Arrays.sort(idxs);

        for (int i = 0; i < idxs.length; i++) {
            int j = idxs[i];
            if (j > 0) {
                return j;
            }
        }

        return countyIdx;
    }
}
