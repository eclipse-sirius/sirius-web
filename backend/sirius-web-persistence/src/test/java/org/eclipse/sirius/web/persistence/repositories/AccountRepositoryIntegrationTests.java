/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.sirius.web.persistence.entities.AccountEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Integration tests of the account repository.
 *
 * @author sbegaudeau
 */
@Testcontainers
@SpringBootTest
@ContextConfiguration(classes = PersistenceTestConfiguration.class)
public class AccountRepositoryIntegrationTests extends AbstractIntegrationTests {

    private static final String FIRST_USERNAME = "AzureDiamond"; //$NON-NLS-1$

    private static final String FIRST_PASSWORD = "hunter2"; //$NON-NLS-1$

    private static final String SECOND_USERNAME = "Cthon98"; //$NON-NLS-1$

    private static final String SECOND_PASSWORD = "*********"; //$NON-NLS-1$

    private static final String USER = "user"; //$NON-NLS-1$

    @Autowired
    private IAccountRepository accountRepository;

    @DynamicPropertySource
    public static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl); //$NON-NLS-1$
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword); //$NON-NLS-1$
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername); //$NON-NLS-1$
    }

    @Test
    @Transactional
    public void testInsertAndRetrieveAnAccount() {
        AccountEntity account = new AccountEntity();
        account.setUsername(FIRST_USERNAME);
        account.setPassword(FIRST_PASSWORD);
        account.setRole(USER);

        AccountEntity savedAccount = this.accountRepository.save(account);

        assertThat(savedAccount.getId()).isNotNull();
        var optionalAccountFound = this.accountRepository.findById(savedAccount.getId());
        assertThat(optionalAccountFound.isPresent()).isTrue();

        optionalAccountFound.ifPresent(accountFound -> {
            assertThat(accountFound.getId()).isNotNull();
            assertThat(accountFound.getUsername()).isEqualTo(FIRST_USERNAME);
            assertThat(accountFound.getPassword()).isEqualTo(FIRST_PASSWORD);
        });
    }

    @Test
    @Transactional
    public void testFindByUsername() {
        AccountEntity account = new AccountEntity();
        account.setUsername(SECOND_USERNAME);
        account.setPassword(SECOND_PASSWORD);
        account.setRole(USER);

        this.accountRepository.save(account);

        var optionalAccountFound = this.accountRepository.findByUsername(SECOND_USERNAME);
        assertThat(optionalAccountFound.isPresent()).isTrue();

        optionalAccountFound.ifPresent(accountFound -> {
            assertThat(accountFound.getId()).isNotNull();
            assertThat(accountFound.getUsername()).isEqualTo(SECOND_USERNAME);
            assertThat(accountFound.getPassword()).isEqualTo(SECOND_PASSWORD);
        });
    }

}
