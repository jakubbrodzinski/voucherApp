package pwr.groupproject.vouchers.bean.enums.security;

public enum UserProfileType {
    USER("USER"),ADMIN("ADMIN");

    private String profileType;

    private UserProfileType(String profileType) {
        this.profileType = profileType;
    }

    @Override
    public String toString() {
        return "ROLE_"+profileType;
    }
}
