package com.periodTracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long profile_id;


@OneToOne
@JoinColumn(name = "userId",nullable = false)
private User user;
private int age;
private double height;
private double weight;
private int cycleLength;
private int periodDuration;

}
