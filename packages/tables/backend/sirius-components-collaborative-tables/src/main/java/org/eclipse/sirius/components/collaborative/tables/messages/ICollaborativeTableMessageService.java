/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.tables.messages;

/**
 * Interface of the collaborative table message service.
 *
 * @author arichard
 */
public interface ICollaborativeTableMessageService {

    String invalidInput(String expectedInputTypeName, String receivedInputTypeName);

    String noRowContextMenuEntryExecutor();

    String noHandlerFound();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author frouene
     */
    class NoOp implements ICollaborativeTableMessageService {

        @Override
        public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
            return "invalidInput";
        }

        @Override
        public String noRowContextMenuEntryExecutor() {
            return "noRowContextMenuEntryFound";
        }

        @Override
        public String noHandlerFound() {
            return "noHandlerFound";
        }
    }
}
