package top.zsmile.pay.service;

import top.zsmile.pay.domain.SysTransactionRefund;

import java.util.List;

/**
 * 支付系统退款Service接口
 * 
 * @author B.Smile
 * @date 2024-03-29
 */
public interface ISysTransactionRefundService 
{
    /**
     * 查询支付系统退款
     * 
     * @param id 支付系统退款主键
     * @return 支付系统退款
     */
    public SysTransactionRefund selectSysTransactionRefundById(Long id);

    /**
     * 查询支付系统退款列表
     * 
     * @param sysTransactionRefund 支付系统退款
     * @return 支付系统退款集合
     */
    public List<SysTransactionRefund> selectSysTransactionRefundList(SysTransactionRefund sysTransactionRefund);

    /**
     * 新增支付系统退款
     * 
     * @param sysTransactionRefund 支付系统退款
     * @return 结果
     */
    public int insertSysTransactionRefund(SysTransactionRefund sysTransactionRefund);

    /**
     * 修改支付系统退款
     * 
     * @param sysTransactionRefund 支付系统退款
     * @return 结果
     */
    public int updateSysTransactionRefund(SysTransactionRefund sysTransactionRefund);

    /**
     * 批量删除支付系统退款
     * 
     * @param ids 需要删除的支付系统退款主键集合
     * @return 结果
     */
    public int deleteSysTransactionRefundByIds(Long[] ids);

    /**
     * 删除支付系统退款信息
     * 
     * @param id 支付系统退款主键
     * @return 结果
     */
    public int deleteSysTransactionRefundById(Long id);
}
