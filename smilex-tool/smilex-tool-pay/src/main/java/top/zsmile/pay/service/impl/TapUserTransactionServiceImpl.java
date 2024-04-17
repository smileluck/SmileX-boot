package top.zsmile.pay.service.impl;

import top.zsmile.common.mybatis.service.impl.BaseServiceImpl;
import top.zsmile.pay.constant.TradeConstant;
import top.zsmile.pay.constant.TradeHandlerConstant;
import top.zsmile.pay.constant.TradeIdEnums;
import top.zsmile.pay.constant.TradeRateConstant;
import top.zsmile.pay.domain.SysTransaction;
import top.zsmile.pay.domain.TapUserTransaction;
import top.zsmile.pay.dto.TapUserCenterRechargeDTO;
import top.zsmile.pay.dto.TapUserTransactionQueryDTO;
import top.zsmile.pay.mapper.TapUserTransactionMapper;
import top.zsmile.pay.service.ITapUserTransactionService;
import top.zsmile.pay.service.ITransactionService;
import top.zsmile.pay.service.IWechatPayService;
import top.zsmile.pay.service.IWechatStorageService;
import top.zsmile.pay.vo.NaivePrepayVO;
import top.zsmile.pay.vo.TapUserTransactionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户交易Service业务层处理
 *
 * @author B.Smile
 * @date 2024-03-29
 */
@Service
public class TapUserTransactionServiceImpl extends BaseServiceImpl<TapUserTransactionMapper, TapUserTransaction> implements ITapUserTransactionService {
    @Autowired
    private TapUserTransactionMapper tapUserTransactionMapper;

    @Resource
    private IWechatPayService wechatPayService;

    @Resource
    private ITransactionService transactionService;

    @Resource
    private IWechatStorageService wechatStorageService;

    /**
     * 查询用户交易
     *
     * @param id 用户交易主键
     * @return 用户交易
     */
    @Override
    public TapUserTransaction selectTapUserTransactionById(Long id) {
        return tapUserTransactionMapper.selectTapUserTransactionById(id);
    }

    /**
     * 查询用户交易列表
     *
     * @param tapUserTransaction 用户交易
     * @return 用户交易
     */
    @Override
    public List<TapUserTransactionVO> selectTapUserTransactionList(TapUserTransactionQueryDTO tapUserTransaction) {
        return tapUserTransactionMapper.selectTapUserTransactionList(tapUserTransaction);
    }

    /**
     * 新增用户交易
     *
     * @param tapUserTransaction 用户交易
     * @return 结果
     */
    @Override
    public int insertTapUserTransaction(TapUserTransaction tapUserTransaction) {
        return tapUserTransactionMapper.insertTapUserTransaction(tapUserTransaction);
    }

    /**
     * 修改用户交易
     *
     * @param tapUserTransaction 用户交易
     * @return 结果
     */
    @Override
    public int updateTapUserTransaction(TapUserTransaction tapUserTransaction) {
        return tapUserTransactionMapper.updateTapUserTransaction(tapUserTransaction);
    }

    /**
     * 批量删除用户交易
     *
     * @param ids 需要删除的用户交易主键
     * @return 结果
     */
    @Override
    public int deleteTapUserTransactionByIds(Long[] ids) {
        return tapUserTransactionMapper.deleteTapUserTransactionByIds(ids);
    }

    /**
     * 删除用户交易信息
     *
     * @param id 用户交易主键
     * @return 结果
     */
    @Override
    public int deleteTapUserTransactionById(Long id) {
        return tapUserTransactionMapper.deleteTapUserTransactionById(id);
    }

    @Override
    @Transactional
    public NaivePrepayVO rechargeUserCreditNaivePay(Long userId, TapUserCenterRechargeDTO dto) {
        SysTransaction transaction = transactionService.create(TradeIdEnums.MAIN.getId(), TradeHandlerConstant.RECHARGE, dto.getPayType(), 5, "用户积分充值", TradeConstant.TradeType.NATIVE, 1, dto.getMoney(), TradeRateConstant.RATE_994);
        TapUserTransaction tapUserTransaction = new TapUserTransaction();
        tapUserTransaction.setUserId(userId);
        tapUserTransaction.setTransactionId(transaction.getId());
        tapUserTransaction.setCreateTime(transaction.getCreateTime());
        tapUserTransactionMapper.insertTapUserTransaction(tapUserTransaction);

        NaivePrepayVO naivePrepayVO = transactionService.scanPrepay(TradeIdEnums.MAIN.getId(), transaction);
        transactionService.save(transaction);
        return naivePrepayVO;
    }

    @Override
    public String rechargeUserCreditInfo(String id) {
        return wechatStorageService.getTransactionStatus(id);
    }
}
