package com.example.pfe.constant;

public class Authority {
    public static final String [] MEMBRE_AUTHORITIES={"user:read","projet:read","tasks:create"};
    public static final String [] CHEF_AUTHORITIES={"user:read","user:update","projet:read","tasks:create"};
    public static final String [] PRODUCT_OWNER_AUTHORITIES={"user:read","projet:read","tasks:create"};
    public static final String [] SCRUM_MASTER_AUTHORITIES={"user:read","user:create","user:update","projet:read","projet:create","tasks:create"};
    public static final String [] ADMIN_AUTHORITIES={"user:read","user:create","user:update","user:delete","tasks:create"};


}
