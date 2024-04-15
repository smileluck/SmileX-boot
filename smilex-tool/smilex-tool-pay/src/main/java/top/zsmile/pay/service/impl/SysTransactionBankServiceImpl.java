package top.zsmile.pay.service.impl;

import top.zsmile.pay.domain.SysTransactionBank;
import top.zsmile.pay.mapper.SysTransactionBankMapper;
import top.zsmile.pay.service.ISysTransactionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付系统银行类型Service业务层处理
 * 
 * @author B.Smile
 * @date 2024-03-29
 */
@Service
public class SysTransactionBankServiceImpl implements ISysTransactionBankService 
{
    @Autowired
    private SysTransactionBankMapper sysTransactionBankMapper;

    /**
     * 查询支付系统银行类型
     * 
     * @param id 支付系统银行类型主键
     * @return 支付系统银行类型
     */
    @Override
    public SysTransactionBank selectSysTransactionBankById(Long id)
    {
        return sysTransactionBankMapper.selectSysTransactionBankById(id);
    }

    /**
     * 查询支付系统银行类型列表
     * 
     * @param sysTransactionBank 支付系统银行类型
     * @return 支付系统银行类型
     */
    @Override
    public List<SysTransactionBank> selectSysTransactionBankList(SysTransactionBank sysTransactionBank)
    {
        return sysTransactionBankMapper.selectSysTransactionBankList(sysTransactionBank);
    }

    /**
     * 新增支付系统银行类型
     * 
     * @param sysTransactionBank 支付系统银行类型
     * @return 结果
     */
    @Override
    public int insertSysTransactionBank(SysTransactionBank sysTransactionBank)
    {
        return sysTransactionBankMapper.insertSysTransactionBank(sysTransactionBank);
    }

    /**
     * 修改支付系统银行类型
     * 
     * @param sysTransactionBank 支付系统银行类型
     * @return 结果
     */
    @Override
    public int updateSysTransactionBank(SysTransactionBank sysTransactionBank)
    {
        return sysTransactionBankMapper.updateSysTransactionBank(sysTransactionBank);
    }

    /**
     * 批量删除支付系统银行类型
     * 
     * @param ids 需要删除的支付系统银行类型主键
     * @return 结果
     */
    @Override
    public int deleteSysTransactionBankByIds(Long[] ids)
    {
        return sysTransactionBankMapper.deleteSysTransactionBankByIds(ids);
    }

    /**
     * 删除支付系统银行类型信息
     * 
     * @param id 支付系统银行类型主键
     * @return 结果
     */
    @Override
    public int deleteSysTransactionBankById(Long id)
    {
        return sysTransactionBankMapper.deleteSysTransactionBankById(id);
    }
}
