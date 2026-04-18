package com.periodTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDTO {

private String username;
private String password;
private Integer age;
private Double height;
private Double weight;
private Integer cycleLength;
private Integer periodDuration;

}
