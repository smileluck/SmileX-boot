package top.zsmile.pay.service;

import top.zsmile.common.mybatis.service.BaseService;
import top.zsmile.pay.domain.TapUserTransaction;
import top.zsmile.pay.dto.TapUserCenterRechargeDTO;
import top.zsmile.pay.dto.TapUserTransactionQueryDTO;
import top.zsmile.pay.vo.NaivePrepayVO;
import top.zsmile.pay.vo.TapUserTransactionVO;

import java.util.List;

/**
 * 用户交易Service接口
 *
 * @author B.Smile
 * @date 2024-03-29
 */
public interface ITapUserTransactionService extends BaseService<TapUserTransaction> {
    /**
     * 查询用户交易
     *
     * @param id 用户交易主键
     * @return 用户交易
     */
    public TapUserTransaction selectTapUserTransactionById(Long id);

    /**
     * 查询用户交易列表
     *
     * @param tapUserTransaction 用户交易
     * @return 用户交易集合
     */
    public List<TapUserTransactionVO> selectTapUserTransactionList(TapUserTransactionQueryDTO tapUserTransaction);

    /**
     * 新增用户交易
     *
     * @param tapUserTransaction 用户交易
     * @return 结果
     */
    public int insertTapUserTransaction(TapUserTransaction tapUserTransaction);

    /**
     * 修改用户交易
     *
     * @param tapUserTransaction 用户交易
     * @return 结果
     */
    public int updateTapUserTransaction(TapUserTransaction tapUserTransaction);

    /**
     * 批量删除用户交易
     *
     * @param ids 需要删除的用户交易主键集合
     * @return 结果
     */
    public int deleteTapUserTransactionByIds(Long[] ids);

    /**
     * 删除用户交易信息
     *
     * @param id 用户交易主键
     * @return 结果
     */
    public int deleteTapUserTransactionById(Long id);


    /**
     * 用户积分充值
     *
     * @return 二维码路径
     */
    NaivePrepayVO rechargeUserCreditNaivePay(Long userId, TapUserCenterRechargeDTO dto);


    /**
     * 用户积分查询
     *
     * @return 充值状态
     */
    String rechargeUserCreditInfo(String id);
}
