/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.messages;

/**
 * Interface of the collaborative diagram message service.
 *
 * @author sbegaudeau
 */
public interface ICollaborativeDiagramMessageService {

    String invalidInput(String expectedInputTypeName, String receivedInputTypeName);

    String edgeNotFound(String id);

    String nodeNotFound(String id);

    String deleteEdgeFailed(String id);

    String deleteNodeFailed(String id);

    String deleteFailed();

    String semanticObjectNotFound(String id);

    String nodeDescriptionNotFound(String id);

    String edgeDescriptionNotFound(String id);

    String invalidDrop();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements ICollaborativeDiagramMessageService {

        @Override
        public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String edgeNotFound(String id) {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String nodeNotFound(String id) {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String deleteEdgeFailed(String id) {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String deleteNodeFailed(String id) {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String deleteFailed() {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String semanticObjectNotFound(String id) {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String nodeDescriptionNotFound(String id) {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String edgeDescriptionNotFound(String id) {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String invalidDrop() {
            return ""; //$NON-NLS-1$
        }

    }
}
