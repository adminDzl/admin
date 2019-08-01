package com.wolves.service.system.payment;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.wolves.dto.pay.CompantYearPayDTO;
import com.wolves.dto.pay.PayMentDTO;
import com.wolves.dto.user.ManagerUserDTO;
import com.wolves.dto.user.ToMsgDTO;
import com.wolves.service.system.payorder.PayOrderService;
import com.wolves.service.system.tipmsg.TipMsgService;
import com.wolves.service.system.user.UserService;
import org.springframework.stereotype.Service;
import com.wolves.dao.DaoSupport;
import com.wolves.entity.system.Page;
import com.wolves.util.PageData;

@Service("paymentService")
public class PaymentService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
    @Resource(name="payorderService")
    private PayOrderService payorderService;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="tipmsgService")
	private TipMsgService tipmsgService;
	
	/**
	* 新增
	*/
	public void save(PageData pd){
		dao.save("PaymentMapper.save", pd);
	}
	
	/**
	* 删除
	*/
	public void delete(PageData pd){
		dao.delete("PaymentMapper.delete", pd);
	}
	
	/**
	* 修改
	*/
	public void edit(PageData pd){
		dao.update("PaymentMapper.edit", pd);
	}
	
	/**
	*列表
	*/
	public List<PageData> list(Page page){
		return (List<PageData>)dao.findForList("PaymentMapper.datalistPage", page);
	}
	
	/**
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd){
		return (List<PageData>)dao.findForList("PaymentMapper.listAll", pd);
	}
	
	/**
	* 通过id获取数据
	*/
	public PageData findById(PageData pd){
		return (PageData)dao.findForObject("PaymentMapper.findById", pd);
	}
	
	/**
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS){
		dao.delete("PaymentMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查询缴费记录
	 * @param companyId
	 * @return
	 */
	public List<PayMentDTO> selectPayMentById(String companyId){

		return (List<PayMentDTO>)dao.findForList("PaymentMapper.selectPayMentById", companyId);
	}

    /**
     * 当年查询多家公司待缴费记录列表
     * @param page
     * @return
     */
    public List<PageData> selectSumByTime(Page page){

        return (List<PageData>)dao.findForList("PaymentMapper.selectPayMentSumByTime", page);
    }

    /**
     * 一家公司全部代缴费用
     * @param companyId
     * @return
     */
    public PageData selectAllWaitAmount(String companyId){

        return (PageData)dao.findForObject("PaymentMapper.selectAllWaitAmount", companyId);
    }

	/**
	 * 查询一家公司年度缴费情况
	 * @param params
	 * @return
	 */
	public CompantYearPayDTO selectPaymentByCompanyId(Map<String, Object> params){
        CompantYearPayDTO compantYearPayDTO = (CompantYearPayDTO)dao.findForObject("PaymentMapper.selectPaymentByCompanyId", params);
        if (compantYearPayDTO != null && compantYearPayDTO.getCompanyId() != null){
            String companyId = compantYearPayDTO.getCompanyId();

            //当年待缴纳费用
            BigDecimal waitYearAmount = new BigDecimal("0");
            if (compantYearPayDTO.getYearTotalPay()!= null){
                waitYearAmount = new BigDecimal(compantYearPayDTO.getYearTotalPay());
            }

            //当年共需缴纳费用 = 全部待缴纳费用 - 全部已缴纳费用
            //全部待缴纳费用
            PageData wait = this.selectAllWaitAmount(companyId+"");
            BigDecimal waitAmount = new BigDecimal("0");
            if (wait != null && wait.get("waitTotalAmount") != null){
                waitAmount = new BigDecimal(wait.get("waitTotalAmount").toString());
            }
            //全部已缴纳费用
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("companyId", companyId);
            PageData yet = payorderService.selectAllAmount(maps);
            BigDecimal yetAmount = new BigDecimal("0");
            if (yet != null && yet.get("yetTotalAmout") != null){
                yetAmount = new BigDecimal(yet.get("yetTotalAmout").toString());
            }
            compantYearPayDTO.setYearTotalPay(waitAmount.subtract(yetAmount).toString());

            //查询当年已经缴纳费用
            maps.put("time", params.get("time"));
            PageData yetYear = payorderService.selectAllAmount(params);

            BigDecimal yetYearAmount = new BigDecimal("0");
            if (yetYear != null && yetYear.get("yetTotalAmout") != null){
                yetYearAmount = new BigDecimal(yetYear.get("yetTotalAmout").toString());
            }
            compantYearPayDTO.setYearYetPay(yetYearAmount.toString());

            //当年未缴纳 = 当年待缴纳费用-当年已经缴纳费用
            compantYearPayDTO.setYearWaitPay(waitYearAmount.subtract(yetYearAmount).toString());
        }
		return compantYearPayDTO;
	}

	/**
	 * 查询汇总数据
	 * @param page
	 * @param pd
	 * @return
	 */
	public List<PageData> selectSummary(Page page, PageData pd){
		List<PageData> varList = this.selectSumByTime(page);
		if (varList != null && !varList.isEmpty()){
			for (PageData pageData : varList){
				Object company_id = pageData.get("COMPANY_ID");
				if (company_id != null){
					//当年待缴纳费用
					BigDecimal waitYearAmount = new BigDecimal("0");
					if (pageData.get("waitTotalAmount") != null){
						waitYearAmount = new BigDecimal(pageData.get("waitTotalAmount").toString());
					}

					//当年共需缴纳费用 = 全部待缴纳费用 - 全部已缴纳费用
					//全部待缴纳费用
					PageData wait = this.selectAllWaitAmount(company_id+"");
					BigDecimal waitAmount = new BigDecimal("0");
					if (wait != null && wait.get("waitTotalAmount") != null){
						waitAmount = new BigDecimal(wait.get("waitTotalAmount").toString());
					}
					//全部已缴纳费用
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("companyId", company_id+"");
					PageData yet = payorderService.selectAllAmount(params);
					BigDecimal yetAmount = new BigDecimal("0");
					if (yet != null && yet.get("yetTotalAmout") != null){
						yetAmount = new BigDecimal(yet.get("yetTotalAmout").toString());
					}
                    //查询当年已经缴纳费用
                    params.put("time", pd.get("TIME"));
                    PageData yetYear = payorderService.selectAllAmount(params);
                    BigDecimal yetYearAmount = new BigDecimal("0");
                    if (yetYear != null && yetYear.get("yetTotalAmout") != null){
                        yetYearAmount = new BigDecimal(yetYear.get("yetTotalAmout").toString());
                    }
                    BigDecimal totalAmount = waitAmount.subtract(yetAmount).add(yetYearAmount);
					pageData.put("T", waitAmount.subtract(yetAmount).add(yetYearAmount));

					pageData.put("Y", yetYearAmount);
					//当年未缴纳 = 当年待缴纳费用-当年已经缴纳费用
					pageData.put("D", totalAmount.subtract(yetYearAmount));

				}
			}
		}
		return varList;
	}

	/**
	 * 查询未缴费公司名单
	 * @return
	 */
	public List<PageData> queryNoPayCompany(){
		Page page = new Page();
		PageData pd = new PageData();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		pd.put("TIME", df.format(System.currentTimeMillis()));

		List<PageData> pageDataList = this.selectSummary(page, pd);
		List<PageData> pageDatas = new ArrayList<PageData>();
		if (pageDataList != null && !pageDataList.isEmpty()){
			for (PageData pageData : pageDataList){
				Object wpay = pageData.get("D");
				if (wpay != null){
					BigDecimal waitPay = new BigDecimal(pageData.get("D").toString());
					if (waitPay.compareTo(BigDecimal.ZERO) > 0){
						pageDatas.add(pageData);
					}
				}
			}
		}
		return pageDatas;
	}

	/**
	 * 一键催缴
	 * @return
	 */
	public PageData worthMsg(){
		PageData pd = new PageData();
		List<PageData> pageDatas = this.queryNoPayCompany();
		List<ToMsgDTO> toMsgDTOs = new ArrayList<ToMsgDTO>();
		if (pageDatas != null && !pageDatas.isEmpty()){
			for (PageData pageData : pageDatas){
				Object companyId = pageData.get("COMPANY_ID");
				Object companyName = pageData.get("COMPANY_NAME");
				Object totalAmount = pageData.get("D");
				if (companyId == null){
					pd.put("msg", companyName+"无该企业信息");
					pd.put("status", "1");
					return pd;
				}

				List<ManagerUserDTO> managerUserDTOs = userService.selectManagerUserByCompanyId(companyId.toString());
				if (managerUserDTOs != null && !managerUserDTOs.isEmpty()){
					for (ManagerUserDTO managerUserDTO : managerUserDTOs){
						if (managerUserDTO.getUserId() == null){
							pd.put("msg", companyName+",该企业下的负责人不存在");
							pd.put("status", "1");
							return pd;
						}
                        //查询所属负责人
                        ToMsgDTO toMsgDTO = new ToMsgDTO();
                        toMsgDTO.setType(1);
                        toMsgDTO.setMsgType(1);
                        toMsgDTO.setTitle("你公司当前有未缴费的费用");
                        toMsgDTO.setContent("您好，您的公司当前仍有未缴纳的费用需要结清，请点击【缴纳费用】进行支付。");
                        toMsgDTO.setUserId(managerUserDTO.getUserId());
                        toMsgDTOs.add(toMsgDTO);
					}
				}else {
					pd.put("msg", companyName+",该企业没有设置公司负责人角色");
					pd.put("status", "1");
					return pd;
				}
			}
		}
		tipmsgService.addTipMsg(toMsgDTOs);
		pd.put("status", "0");
		pd.put("msg", "ok");
		return pd;
	}
}

