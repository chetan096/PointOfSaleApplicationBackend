package com.nagarro.pointofsaleapplication.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.pointofsaleapplication.DAO.ICashDrawerDao;
import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.models.CashDrawerEO;
import com.nagarro.pointofsaleapplication.services.ICashDrawerService;

/**
 * @author chetanmahajan implementation of interface providing cash drawer related services
 */
@Service
public class CashDrawerServiceImpl implements ICashDrawerService {

    @Autowired
    private ICashDrawerDao cashDrawerDao;

    /*
     * (non-Javadoc)
     * @see
     * com.nagarro.pointofsaleapplication.services.ICashDrawerService#addCash(com.nagarro.pointofsaleapplication.models.
     * CashDrawerEO, int)
     */
    @Override
    public Response<CashDrawerEO> addCash(CashDrawerEO cashDrawer, final int emp_id) {
        return cashDrawerDao.addCash(cashDrawer, emp_id);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.nagarro.pointofsaleapplication.services.ICashDrawerService#updateCash(com.nagarro.pointofsaleapplication.
     * models.CashDrawerEO, int)
     */
    @Override
    public Response<CashDrawerEO> updateCash(final float amount, final int id, final String orderDate) {
        return cashDrawerDao.updateCash(amount, id, orderDate);
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.ICashDrawerService#getCashdrawerByEmployeeId(int)
     */
    @Override
    public CashDrawerEO getCashdrawerByEmployeeId(final int emp_id, final String orderDate) {
        return cashDrawerDao.getCashdrawerByEmployeeId(emp_id, orderDate);
    }

    @Override
    public CashDrawerEO getCashdrawerByEmployeeId(final int emp_id) {
        return cashDrawerDao.getCashdrawerByEmployeeId(emp_id);
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.ICashDrawerService#getCashDrawerEntries(int)
     */
    @Override
    public Response<List<CashDrawerEO>> getCashDrawerEntries(final int id) {
        return cashDrawerDao.getCashDrawerEntries(id);
    }

}
