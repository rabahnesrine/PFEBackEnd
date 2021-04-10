package com.example.pfe.enumeration;

import com.example.pfe.constant.Authority;

public enum Role {
    //definir les roles


    ROLE_MEMBRE(Authority.MEMBRE_AUTHORITIES),
    ROLE_CHEF(Authority.CHEF_AUTHORITIES),
    ROLE_PRODUCT_OWNER(Authority.PRODUCT_OWNER_AUTHORITIES),
    ROLE_SCRUM_MASTER(Authority.SCRUM_MASTER_AUTHORITIES),
    ROLE_ADMIN(Authority.ADMIN_AUTHORITIES);

    private String[] authorities;


  Role(String...authorities){
      this.authorities=authorities;

  }

  public String[] getAuthorities(){
      return authorities;
  }

}
