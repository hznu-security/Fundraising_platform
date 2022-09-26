package org.CSLab.demo.mapper;

import org.CSLab.demo.bean.Contribution;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContributionMapper {
    //插入捐款
    int insertContribution(Contribution contribution);
    //更新捐款
    int updateContribution(Integer contributionId,String contributionKey);
}
