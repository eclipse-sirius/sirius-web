/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.messages;

/**
 * Interface of the collaborative message service.
 *
 * @author sbegaudeau
 */
public interface ICollaborativeMessageService {
    String invalidInput(String expectedInputTypeName, String receivedInputTypeName);

    String objectCreationFailed();

    String timeout();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements ICollaborativeMessageService {

        @Override
        public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String objectCreationFailed() {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String timeout() {
            return ""; //$NON-NLS-1$
        }

    }
}
