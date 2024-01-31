package com.ecommerce.db.account;

import com.ecommerce.db.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@SuperBuilder //부모로 부터 상속 받은 변수도 포함 즉, builder 패턴으로 부모인 BaseEtity의 id 지정 가능
@Data
@EqualsAndHashCode(callSuper = true) //부모에게 있는 값까지 포함해서 비교
@Entity
@Table(name = "account")
public class AccountEntity extends BaseEntity {
}
