package com.nagarro.pointofsaleapplication.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationConstants;
import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationMessages;
import com.nagarro.pointofsaleapplication.dto.MyError;
import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.models.OrderEO;
import com.nagarro.pointofsaleapplication.services.IOrderService;

/**
 * @author chetanmahajan Resource will provide order related information
 */
@RestController
@CrossOrigin
public class OrderResource {

    @Autowired
    private IOrderService orderService;

    /**
     * @param orderId
     *            id of the order which we want to delete
     * @param key
     *            api key
     * @return deleted order and error if order deleted method return null
     */
    @RequestMapping(value = "/orders/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@PathVariable
    final int orderId, @RequestHeader("apikey")
    final String key) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        Response<OrderEO> response = orderService.deleteOrder(orderId);
        return new ResponseEntity<Response<OrderEO>>(response, response.getResponseStatus());
    }
}
