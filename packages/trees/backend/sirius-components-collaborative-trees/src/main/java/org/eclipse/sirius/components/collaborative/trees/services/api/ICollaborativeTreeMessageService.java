/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.trees.services.api;

/**
 * Interface of the collaborative tree message service.
 *
 * @author sbegaudeau
 */
public interface ICollaborativeTreeMessageService {

    String invalidInput(String expectedInputTypeName, String receivedInputTypeName);

    String noSingleClickTreeItemExecutor();

    String noDropHandler();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements ICollaborativeTreeMessageService {

        @Override
        public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
            return "";
        }

        @Override
        public String noSingleClickTreeItemExecutor() {
            return "";
        }

        @Override
        public String noDropHandler() {
            return "";
        }

    }
}
