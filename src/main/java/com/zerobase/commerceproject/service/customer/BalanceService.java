package com.zerobase.commerceproject.service.customer;

import com.zerobase.commerceproject.domain.ChangeBalanceForm;
import com.zerobase.commerceproject.domain.emtity.BalanceHistory;
import com.zerobase.commerceproject.exception.CustomException;
import com.zerobase.commerceproject.exception.ErrorCode;
import com.zerobase.commerceproject.repository.BalanceHistoryRepository;
import com.zerobase.commerceproject.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.zerobase.commerceproject.exception.ErrorCode.NOT_ENOUGH_BALANCE;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceHistoryRepository balanceHistoryRepository;
    private final CustomerRepository customerRepository;

    @Transactional(noRollbackFor = {CustomException.class})
    public BalanceHistory changeBalance(Long userId, ChangeBalanceForm form) throws CustomException {

        //customerBalanceHistory 값을 가져오는 데 값이 없으면(orElse)
        //0원으로 빌드해준다
        BalanceHistory customerBalanceHistory =
                balanceHistoryRepository.findFirstByUser_IdOrderByIdDesc(userId)
                        .orElse(BalanceHistory.builder()
                                .changeMoney(0)
                                .currentMoney(0)
                                .user(customerRepository.findById(userId)
                                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER))
                                ).build());

        if(customerBalanceHistory.getCurrentMoney() + form.getMoney() < 0) {
            throw new CustomException(NOT_ENOUGH_BALANCE);
        }

        customerBalanceHistory = BalanceHistory.builder()
                .changeMoney(form.getMoney())
                .currentMoney(customerBalanceHistory.getCurrentMoney() + form.getMoney())
                .description(form.getMessage())
                .fromMessage(form.getFrom())
                .user(customerBalanceHistory.getUser())
                .build();
        customerBalanceHistory.getUser().setBalance(customerBalanceHistory.getCurrentMoney());

        return balanceHistoryRepository.save(customerBalanceHistory);
    }
}
