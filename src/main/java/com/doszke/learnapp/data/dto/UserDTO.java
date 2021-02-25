package com.doszke.learnapp.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * to front - with no credentials
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {

    private String name;


}
