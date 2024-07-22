package top.zsmile.pay.service.impl;

import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.pay.domain.SysTransaction;
import top.zsmile.pay.domain.SysTransactionRefund;
import top.zsmile.pay.mapper.SysTransactionMapper;
import top.zsmile.pay.mapper.SysTransactionRefundMapper;
import top.zsmile.pay.service.ISysTransactionRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付系统退款Service业务层处理
 *
 * @author B.Smile
 * @date 2024-03-29
 */
@Service
public class SysTransactionRefundServiceImpl extends BaseServiceImpl<SysTransactionRefundMapper, SysTransactionRefund> implements ISysTransactionRefundService {
    @Autowired
    private SysTransactionRefundMapper sysTransactionRefundMapper;

    /**
     * 查询支付系统退款
     *
     * @param id 支付系统退款主键
     * @return 支付系统退款
     */
    @Override
    public SysTransactionRefund selectSysTransactionRefundById(Long id) {
        return sysTransactionRefundMapper.selectSysTransactionRefundById(id);
    }

    /**
     * 查询支付系统退款列表
     *
     * @param sysTransactionRefund 支付系统退款
     * @return 支付系统退款
     */
    @Override
    public List<SysTransactionRefund> selectSysTransactionRefundList(SysTransactionRefund sysTransactionRefund) {
        return sysTransactionRefundMapper.selectSysTransactionRefundList(sysTransactionRefund);
    }

    /**
     * 新增支付系统退款
     *
     * @param sysTransactionRefund 支付系统退款
     * @return 结果
     */
    @Override
    public int insertSysTransactionRefund(SysTransactionRefund sysTransactionRefund) {
        return sysTransactionRefundMapper.insertSysTransactionRefund(sysTransactionRefund);
    }

    /**
     * 修改支付系统退款
     *
     * @param sysTransactionRefund 支付系统退款
     * @return 结果
     */
    @Override
    public int updateSysTransactionRefund(SysTransactionRefund sysTransactionRefund) {
        return sysTransactionRefundMapper.updateSysTransactionRefund(sysTransactionRefund);
    }

    /**
     * 批量删除支付系统退款
     *
     * @param ids 需要删除的支付系统退款主键
     * @return 结果
     */
    @Override
    public int deleteSysTransactionRefundByIds(Long[] ids) {
        return sysTransactionRefundMapper.deleteSysTransactionRefundByIds(ids);
    }

    /**
     * 删除支付系统退款信息
     *
     * @param id 支付系统退款主键
     * @return 结果
     */
    @Override
    public int deleteSysTransactionRefundById(Long id) {
        return sysTransactionRefundMapper.deleteSysTransactionRefundById(id);
    }
}
