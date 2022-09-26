package org.CSLab.demo.dto;


import org.CSLab.demo.bean.Project;
import lombok.Data;

@Data
public class ProjectDTO {
    Project project;
    String error;
    String result;
}
