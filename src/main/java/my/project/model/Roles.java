package my.project.model;

public enum Roles {
    GUEST("guest"),
    ADMIN("admin"),
    MERCHANDISER("merchandiser"),
    CASHIER("cashier"),
    SENIOR_CASHIER("senior_cashier");

    public String name;

    Roles(String name){
        this.name = name;
    }
}
