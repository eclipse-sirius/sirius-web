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

    String reconnectEdgeSameEdgeEnd();

    String nodeNotFound(String id);

    String deleteEdgeFailed(String id);

    String deleteNodeFailed(String id);

    String deleteFailed();

    String semanticObjectNotFound(String id);

    String nodeDescriptionNotFound(String id);

    String edgeDescriptionNotFound(String id);

    String invalidDrop();

    String invalidNewValue(String newValue);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements ICollaborativeDiagramMessageService {

        @Override
        public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
            return "";
        }

        @Override
        public String edgeNotFound(String id) {
            return "";
        }

        @Override
        public String reconnectEdgeSameEdgeEnd() {
            return "";
        }

        @Override
        public String nodeNotFound(String id) {
            return "";
        }

        @Override
        public String deleteEdgeFailed(String id) {
            return "";
        }

        @Override
        public String deleteNodeFailed(String id) {
            return "";
        }

        @Override
        public String deleteFailed() {
            return "";
        }

        @Override
        public String semanticObjectNotFound(String id) {
            return "";
        }

        @Override
        public String nodeDescriptionNotFound(String id) {
            return "";
        }

        @Override
        public String edgeDescriptionNotFound(String id) {
            return "";
        }

        @Override
        public String invalidDrop() {
            return "";
        }

        @Override
        public String invalidNewValue(String newValue) {
            return "";
        }
    }
}
