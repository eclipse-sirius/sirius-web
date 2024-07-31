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
package org.eclipse.sirius.web.domain.services.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link IMessageService}.
 *
 * @author arichard
 */
public class MessageServiceTests {

    @Test
    void testIMessageServiceNoOp() {
        IMessageService messageService = new IMessageService.NoOp();
        assertEquals("", messageService.collapseSelectedElements());
        assertEquals("", messageService.expandSelectedElements());
        assertEquals("", messageService.fadeSelectedElements());
        assertEquals("", messageService.hideSelectedElements());
        assertEquals("", messageService.invalidName());
        assertEquals("", messageService.notFound());
        assertEquals("", messageService.pinSelectedElements());
        assertEquals("", messageService.revealSelectedFadedElements());
        assertEquals("", messageService.showSelectedElements());
        assertEquals("", messageService.unexpectedError());
        assertEquals("", messageService.unpinSelectedElements());
    }
}
