/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to test that all messages from the message service are working properly.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageServiceTests extends AbstractIntegrationTests {

    @Autowired
    private IMessageService messageService;

    @Test
    @DisplayName("Given the message service, when asked for a value, then the english value is returned")
    public void givenTheMessageServiceWhenAskedForValueThenTheEnglishValueIsReturned() {
        assertThat(this.messageService.revealSelectedFadedElements()).isNotBlank();
        assertThat(this.messageService.collapseSelectedElements()).isNotBlank();
        assertThat(this.messageService.expandSelectedElements()).isNotBlank();
        assertThat(this.messageService.fadeSelectedElements()).isNotBlank();
        assertThat(this.messageService.hideSelectedElements()).isNotBlank();
        assertThat(this.messageService.invalidName()).isNotBlank();
        assertThat(this.messageService.notFound()).isNotBlank();
        assertThat(this.messageService.pinSelectedElements()).isNotBlank();
        assertThat(this.messageService.showSelectedElements()).isNotBlank();
        assertThat(this.messageService.unexpectedError()).isNotBlank();
        assertThat(this.messageService.unpinSelectedElements()).isNotBlank();
    }


}
