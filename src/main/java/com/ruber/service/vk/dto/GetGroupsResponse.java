package com.ruber.service.vk.dto;

import com.ruber.controller.dto.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetGroupsResponse {
    private Group[] groups;
    private Integer totalCount;
}
