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
package org.eclipse.sirius.web.domain.services.api;

/**
 * Used to compute internationalized messages.
 *
 * @author sbegaudeau
 */
public interface IMessageService {

    String invalidInput(String expectedInputTypeName, String receivedInputTypeName);

    String upperBoundaryReached(String newInstanceClass, String feature);

    String revealSelectedFadedElements();

    String collapseSelectedElements();

    String expandSelectedElements();

    String fadeSelectedElements();

    String hideSelectedElements();

    String invalidName();

    String notFound();

    String pinSelectedElements();

    String showSelectedElements();

    String unexpectedError();

    String unpinSelectedElements();

    String unavailableFeature();

    String alreadySetFeature();

    String invalidDroppedObject();

    String objectDuplicationFailed();

    String objectDoesNotExist(String objectId);

    String unauthorized();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author arichard
     */
    class NoOp implements IMessageService {

        @Override
        public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
            return "";
        }

        @Override
        public String upperBoundaryReached(String newInstanceClass, String feature) {
            return "";
        }

        @Override
        public String revealSelectedFadedElements() {
            return "";
        }

        @Override
        public String collapseSelectedElements() {
            return "";
        }

        @Override
        public String expandSelectedElements() {
            return "";
        }

        @Override
        public String fadeSelectedElements() {
            return "";
        }

        @Override
        public String hideSelectedElements() {
            return "";
        }

        @Override
        public String invalidName() {
            return "";
        }

        @Override
        public String notFound() {
            return "";
        }

        @Override
        public String pinSelectedElements() {
            return "";
        }

        @Override
        public String showSelectedElements() {
            return "";
        }

        @Override
        public String unexpectedError() {
            return "";
        }

        @Override
        public String unpinSelectedElements() {
            return "";
        }

        @Override
        public String unavailableFeature() {
            return "";
        }

        @Override
        public String alreadySetFeature() {
            return "";
        }

        @Override
        public String invalidDroppedObject() {
            return "";
        }

        @Override
        public String objectDuplicationFailed() {
            return "";
        }

        @Override
        public String objectDoesNotExist(String objectId) {
            return "";
        }

        @Override
        public String unauthorized() {
            return "";
        }
    }
}
