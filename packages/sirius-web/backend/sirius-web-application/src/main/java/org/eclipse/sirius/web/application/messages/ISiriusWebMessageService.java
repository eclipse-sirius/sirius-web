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
package org.eclipse.sirius.web.application.messages;

/**
 * Interface of the sirius web message service.
 *
 * @author frouene
 */
public interface ISiriusWebMessageService {

    String unavailableFeature();

    String alreadySetFeature();

    String invalidDroppedObject();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author frouene
     */
    class NoOp implements ISiriusWebMessageService {

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

    }
}
