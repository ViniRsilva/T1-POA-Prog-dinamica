package com.ages.volunteersmile.domain.admin.model;

import com.ages.volunteersmile.domain.global.model.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("ADMIN")
public class Admin extends User {

}
