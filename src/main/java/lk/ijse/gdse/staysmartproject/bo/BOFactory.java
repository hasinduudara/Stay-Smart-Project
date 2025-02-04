package lk.ijse.gdse.staysmartproject.bo;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        USER, TENANT, RENT_PAYMENT, MAINTAINS, HOUSE, FINANCES
    }

    public SuperBO getBO(BOFactory.BOTypes types) {
        switch (types) {
            case USER:
                return new UserBOImpl();
            case TENANT:
                return new TenantBOImpl();
            case RENT_PAYMENT:
                return new RentPaymentBOImpl();
            case MAINTAINS:
                return new MaintainsBOImpl();
            case HOUSE:
                return new HouseBOImpl();
            case FINANCES:
                return new FinancesBOImpl();
            default:
                return null;
        }
    }

}
