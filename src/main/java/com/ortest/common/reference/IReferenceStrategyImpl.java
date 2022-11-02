package com.ortest.common.reference;

import com.ortest.model.Orders;
import com.ortest.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class IReferenceStrategyImpl implements IReferenceStrategy{
    private final OrderRepo orderRepo;
    @Autowired
    public IReferenceStrategyImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public String generateReceiptNumber() {
        Orders orders = orderRepo.findMaxId();
        int num = 1;
        String numTemplate = String.format("%s%02d%02d-%02d", "RC", Calendar.getInstance().get(Calendar.DATE), Calendar.getInstance().get(Calendar.MONTH) + 1, num);

        if (orders != null) {

            String lastInsertedNum = orders.getReference();

            String[] splitedNum = lastInsertedNum.split("-");

            num = Integer.parseInt(splitedNum[1]);

            Integer lastNumMonth = Integer.parseInt(splitedNum[0].substring(4, 6));

            if (lastNumMonth != Calendar.getInstance().get(Calendar.MONTH) + 1) {
                num = 0;
            }
            numTemplate = String.format("%s%02d%02d-%02d", "RC", Calendar.getInstance().get(Calendar.DATE), Calendar.getInstance().get(Calendar.MONTH) + 1, ++num);
        }
        return numTemplate;
    }
}
