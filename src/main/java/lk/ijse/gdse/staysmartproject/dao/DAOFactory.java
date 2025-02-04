package lk.ijse.gdse.staysmartproject.dao;

import lk.ijse.gdse.staysmartproject.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        HOUSE, USER, TENANT, RENT_PAYMENT, MAINTENANCE, FINANCE, QUERY
    }

    public SuperDAO getDAO(DAOTypes daoType) {
        switch (daoType) {
            case HOUSE:
                return new HouseDAOImpl();
            case USER:
                return new UserDAOImpl();
            case TENANT:
                return new TenantDAOImpl();
            case RENT_PAYMENT:
                return new RentPaymentDAOImpl();
            case MAINTENANCE:
                return new MaintenanceDAOImpl();
            case FINANCE:
                return new FinanceDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }
}
