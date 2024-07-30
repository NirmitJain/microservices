package com.eazybytes.loans.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("loanAuditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    /**
     * Returns the current auditor of the application
     * @return current auditor
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("LOANS_MS");
    }
}
