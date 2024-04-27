package top.zsmile.pay.service.impl;

import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.pay.domain.SysTransaction;
import top.zsmile.pay.mapper.SysTransactionMapper;
import top.zsmile.pay.service.ISysTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付系统Service业务层处理
 *
 * @author B.Smile
 * @date 2024-03-29
 */
@Service
public class SysTransactionServiceImpl extends BaseServiceImpl<SysTransactionMapper, SysTransaction> implements ISysTransactionService {
    @Autowired
    private SysTransactionMapper sysTransactionMapper;

    /**
     * 查询支付系统
     *
     * @param id 支付系统主键
     * @return 支付系统
     */
    @Override
    public SysTransaction selectSysTransactionById(Long id) {
        return sysTransactionMapper.selectSysTransactionById(id);
    }

    @Override
    public SysTransaction selectSysTransactionByOrderNo(String orderNo) {
        return sysTransactionMapper.selectSysTransactionByOrderNo(orderNo);
    }

    /**
     * 查询支付系统列表
     *
     * @param sysTransaction 支付系统
     * @return 支付系统
     */
    @Override
    public List<SysTransaction> selectSysTransactionList(SysTransaction sysTransaction) {
        return sysTransactionMapper.selectSysTransactionList(sysTransaction);
    }

    /**
     * 新增支付系统
     *
     * @param sysTransaction 支付系统
     * @return 结果
     */
    @Override
    public int insertSysTransaction(SysTransaction sysTransaction) {
        return sysTransactionMapper.insertSysTransaction(sysTransaction);
    }

    /**
     * 修改支付系统
     *
     * @param sysTransaction 支付系统
     * @return 结果
     */
    @Override
    public int updateSysTransaction(SysTransaction sysTransaction) {
        return sysTransactionMapper.updateSysTransaction(sysTransaction);
    }

    /**
     * 批量删除支付系统
     *
     * @param ids 需要删除的支付系统主键
     * @return 结果
     */
    @Override
    public int deleteSysTransactionByIds(Long[] ids) {
        return sysTransactionMapper.deleteSysTransactionByIds(ids);
    }

    /**
     * 删除支付系统信息
     *
     * @param id 支付系统主键
     * @return 结果
     */
    @Override
    public int deleteSysTransactionById(Long id) {
        return sysTransactionMapper.deleteSysTransactionById(id);
    }
}
