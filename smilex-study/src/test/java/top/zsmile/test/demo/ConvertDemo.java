package top.zsmile.test.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.zsmile.common.utils.NameStyleUtils;
import top.zsmile.common.utils.file.SpringFileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
@SpringBootTest
public class ConvertDemo {


    @Test
    public void test() throws IOException {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("ot_order_mode", NameStyleUtils.lineToHump("ot_order_mode"));
        map.put("def_repeat_day", NameStyleUtils.lineToHump("def_repeat_day"));
        map.put("ot_order_time", NameStyleUtils.lineToHump("ot_order_time"));
        map.put("reserve_order_freq", NameStyleUtils.lineToHump("reserve_order_freq"));
        map.put("buyer_order_freq", NameStyleUtils.lineToHump("buyer_order_freq"));
        map.put("refund_num_list", NameStyleUtils.lineToHump("refund_num_list"));
        map.put("refund_money_list", NameStyleUtils.lineToHump("refund_money_list"));
        map.put("def_max_price", NameStyleUtils.lineToHump("def_max_price"));
        map.put("more_dev_bind", NameStyleUtils.lineToHump("more_dev_bind"));
        map.put("more_wx_bind", NameStyleUtils.lineToHump("more_wx_bind"));
        map.put("store_rep_mode", NameStyleUtils.lineToHump("store_rep_mode"));
        map.put("api_rep_mode", NameStyleUtils.lineToHump("api_rep_mode"));
        map.put("zfb_rep_mode", NameStyleUtils.lineToHump("zfb_rep_mode"));
        map.put("cate_rep_mode", NameStyleUtils.lineToHump("cate_rep_mode"));
        map.put("zfb_black_mode", NameStyleUtils.lineToHump("zfb_black_mode"));
        map.put("store_black_mode", NameStyleUtils.lineToHump("store_black_mode"));
        map.put("unhand_buyer_mode", NameStyleUtils.lineToHump("unhand_buyer_mode"));
        map.put("sex_miss", NameStyleUtils.lineToHump("sex_miss"));
        map.put("mobile_rep_mode", NameStyleUtils.lineToHump("mobile_rep_mode"));
        map.put("addr_rep_mode", NameStyleUtils.lineToHump("addr_rep_mode"));
        map.put("partner_eva_mode", NameStyleUtils.lineToHump("partner_eva_mode"));
        map.put("pic_vague_status", NameStyleUtils.lineToHump("pic_vague_status"));
        map.put("ad_order_mode", NameStyleUtils.lineToHump("ad_order_mode"));
        map.put("ad_pack_mode", NameStyleUtils.lineToHump("ad_pack_mode"));
        map.put("account_auth", NameStyleUtils.lineToHump("account_auth"));
        map.put("store_auth", NameStyleUtils.lineToHump("store_auth"));
        map.put("desk_auth", NameStyleUtils.lineToHump("desk_auth"));
        map.put("export_auth", NameStyleUtils.lineToHump("export_auth"));
        map.put("eva_max_money", NameStyleUtils.lineToHump("eva_max_money"));
        map.put("zfb_refund_dly", NameStyleUtils.lineToHump("zfb_refund_dly"));
        map.put("max_alone_pack_refund", NameStyleUtils.lineToHump("max_alone_pack_refund"));
        map.put("max_refund_day", NameStyleUtils.lineToHump("max_refund_day"));
        map.put("modify_by_auth", NameStyleUtils.lineToHump("modify_by_auth"));
        map.put("recv_info_auth", NameStyleUtils.lineToHump("recv_info_auth"));
        map.put("word_pack_mode", NameStyleUtils.lineToHump("word_pack_mode"));
        map.put("word_type", NameStyleUtils.lineToHump("word_type"));
        map.put("def_refund_mode", NameStyleUtils.lineToHump("def_refund_mode"));
        map.put("strict_refund_mode", NameStyleUtils.lineToHump("strict_refund_mode"));
        map.put("abn_flag_mode", NameStyleUtils.lineToHump("abn_flag_mode"));
        map.put("end_flag_mode", NameStyleUtils.lineToHump("end_flag_mode"));
        map.put("serv_group_mode", NameStyleUtils.lineToHump("serv_group_mode"));
        map.put("recount_buyer_serv", NameStyleUtils.lineToHump("recount_buyer_serv"));
        map.put("recount_business_serv", NameStyleUtils.lineToHump("recount_business_serv"));
        map.put("recount_dev_serv", NameStyleUtils.lineToHump("recount_dev_serv"));
        map.put("kpi_money_range", NameStyleUtils.lineToHump("kpi_money_range"));

//        log.info("map => {}", map);

        String fileName = "D:\\test\\configure.vue";

        // 读取文件内容到Stream流中，按行读取
        Stream<String> lines = Files.lines(Paths.get(fileName));

        // 随机行顺序进行数据处理
        lines.forEach(ele -> {
            String temp = ele;
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                temp = temp.replace(entry.getKey(), entry.getValue());
            }
            System.out.println(temp);
        });


//        NameStyleUtils.lineToHump()
    }
}
