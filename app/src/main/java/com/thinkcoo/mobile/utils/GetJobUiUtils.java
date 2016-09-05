package com.thinkcoo.mobile.utils;

import android.text.TextUtils;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class GetJobUiUtils {

    @Inject
    public GetJobUiUtils() {
    }

    public String getFormateAddr(String addr) {
        if (addr == null)
            return null;
        if (addr.contains("北京市") || addr.contains("天津市")
                || addr.contains("重庆市") || addr.contains("上海市"))// 直辖市不作处理
            return addr;
        if (addr.contains("新疆维吾尔自治区") || addr.contains("宁夏回族自治区")
                || addr.contains("广西壮族自治区") || addr.contains("内蒙古自治区")
                || addr.contains("西藏自治区"))
            return addr.replace("新疆维吾尔自治区", "").replace("宁夏回族自治区", "")
                    .replace("广西壮族自治区", "").replace("内蒙古自治区", "")
                    .replace("西藏自治区", "");
        if (addr.contains("省"))//从第一个省字开始截取字符串
            return addr.substring(getFirstProvinceIndex(addr)+1);

        return  doNext(addr);

    }

    public String getSalaryRange(String salaryRange){

        if (TextUtils.isEmpty(salaryRange)){
            return "面议";
        }
        return salaryRange + "/月";
    }

    private String doNext(String address) {

        if (address.contains("市辖区")) {
            String qc_sxq = address.replace("市辖区", "");
            if (qc_sxq.contains("市") && qc_sxq.length() > 3) {
                return qc_sxq.replace("市", "-");// 地址
            } else {
                return  qc_sxq;// 地址
            }
        } else if (address.contains("市") && address.length() > 3) {
            return address.replace("市", "-");// 地址
        } else {
            return address;// 地址
        }
    }

    /**
     * 获取地址中第一个省的位置 Description: 自己填写
     *
     * @param addr
     * @return
     */
    public int getFirstProvinceIndex(String addr) {
        char[] chars = addr.toCharArray();
        int k = 0;// 跳出循环标识
        for (int i = 0; i < chars.length && k == 0; i++) {
            if (String.valueOf(chars[i]).equals("省")) {
                k = 1;
                return i;
            }
        }
        return 0;
    }
}
