package com.wolves.service.pay;

import com.wolves.dto.OrderDTO;
import com.wolves.dto.pay.WxResultDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xulu
 * @date 2019/2/28
 * @link https://github.com/xulu163
 */
public class AliPayService implements PayService {


    @Override
    public WxResultDTO pay(HttpServletRequest request, String token, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void reback(HttpServletRequest request, HttpServletResponse response) {

    }
}
