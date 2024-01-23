/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.portals.services;

/**
 * Interface of the collaborative tree message service.
 *
 * @author pcdavid
 */
public interface ICollaborativePortalMessageService {
    String invalidInput(String receivedInputTypeName, String expectedInputTypeName);

    String forbiddenLoop();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author pcdavid
     */
    class NoOp implements ICollaborativePortalMessageService {

        @Override
        public String invalidInput(String receivedInputTypeName, String expectedInputTypeName) {
            return "";
        }

        @Override
        public String forbiddenLoop() {
            return "";
        }

    }
}
