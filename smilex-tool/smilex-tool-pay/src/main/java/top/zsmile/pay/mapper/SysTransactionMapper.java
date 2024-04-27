package top.zsmile.pay.mapper;

import top.zsmile.common.mybatis.dao.BaseMapper;
import top.zsmile.pay.domain.SysTransaction;

import java.util.List;

/**
 * 支付系统Mapper接口
 *
 * @author B.Smile
 * @date 2024-03-29
 */
public interface SysTransactionMapper extends BaseMapper<SysTransaction> {
    /**
     * 查询支付系统
     *
     * @param id 支付系统主键
     * @return 支付系统
     */
    public SysTransaction selectSysTransactionById(Long id);

    /**
     * 查询支付系统列表
     *
     * @param sysTransaction 支付系统
     * @return 支付系统集合
     */
    public List<SysTransaction> selectSysTransactionList(SysTransaction sysTransaction);

    /**
     * 新增支付系统
     *
     * @param sysTransaction 支付系统
     * @return 结果
     */
    public int insertSysTransaction(SysTransaction sysTransaction);

    /**
     * 修改支付系统
     *
     * @param sysTransaction 支付系统
     * @return 结果
     */
    public int updateSysTransaction(SysTransaction sysTransaction);

    /**
     * 删除支付系统
     *
     * @param id 支付系统主键
     * @return 结果
     */
    public int deleteSysTransactionById(Long id);

    /**
     * 批量删除支付系统
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysTransactionByIds(Long[] ids);

    /**
     * 查询订单
     *
     * @param orderNo 订单号
     * @return 订单
     */
    SysTransaction selectSysTransactionByOrderNo(String orderNo);
}
