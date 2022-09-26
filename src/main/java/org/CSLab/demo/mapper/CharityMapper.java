package org.CSLab.demo.mapper;


import org.CSLab.demo.bean.Charity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CharityMapper  {
    //根据机构名查询机构
    Charity getCharityByCharityName(String charityName);
    Integer certify(Integer charityId, String idNumber,String address, String introduction, String certificationPath, String logoPath);
    Integer audit(Integer charityId, Integer auditResult);
    //查询待审核机构
    List<Charity> getUnderAuditedCharity();

    //插入机构
    void insertCharity(Charity charity);
}