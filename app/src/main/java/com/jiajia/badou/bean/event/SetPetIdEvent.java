package com.jiajia.badou.bean.event;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Lei on 2018/4/24.
 */
// @formatter:off
@Data
@NoArgsConstructor
// @formatter:on
public class SetPetIdEvent {
  @Getter @Setter private int petId;
}
