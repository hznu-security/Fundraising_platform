package org.CSLab.demo.dto;

import lombok.Data;
import org.CSLab.demo.bean.Contribution;

@Data
public class ContributionDTO {
    Contribution contribution;
    String error;
    String result;
}
