package top.zsmile.pay.service;

import top.zsmile.pay.domain.SysTransactionBank;

import java.util.List;

/**
 * 支付系统银行类型Service接口
 * 
 * @author B.Smile
 * @date 2024-03-29
 */
public interface ISysTransactionBankService 
{
    /**
     * 查询支付系统银行类型
     * 
     * @param id 支付系统银行类型主键
     * @return 支付系统银行类型
     */
    public SysTransactionBank selectSysTransactionBankById(Long id);

    /**
     * 查询支付系统银行类型列表
     * 
     * @param sysTransactionBank 支付系统银行类型
     * @return 支付系统银行类型集合
     */
    public List<SysTransactionBank> selectSysTransactionBankList(SysTransactionBank sysTransactionBank);

    /**
     * 新增支付系统银行类型
     * 
     * @param sysTransactionBank 支付系统银行类型
     * @return 结果
     */
    public int insertSysTransactionBank(SysTransactionBank sysTransactionBank);

    /**
     * 修改支付系统银行类型
     * 
     * @param sysTransactionBank 支付系统银行类型
     * @return 结果
     */
    public int updateSysTransactionBank(SysTransactionBank sysTransactionBank);

    /**
     * 批量删除支付系统银行类型
     * 
     * @param ids 需要删除的支付系统银行类型主键集合
     * @return 结果
     */
    public int deleteSysTransactionBankByIds(Long[] ids);

    /**
     * 删除支付系统银行类型信息
     * 
     * @param id 支付系统银行类型主键
     * @return 结果
     */
    public int deleteSysTransactionBankById(Long id);
}
