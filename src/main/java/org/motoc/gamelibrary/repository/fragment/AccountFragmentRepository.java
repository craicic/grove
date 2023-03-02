package org.motoc.gamelibrary.repository.fragment;

import org.motoc.gamelibrary.domain.model.Account;

public interface AccountFragmentRepository {


    /**
     * Find an account, fetch join active Loan and Account for full detail
     *
     * @param id account id
     * @return Account
     */
    Account find(Long id);
}
