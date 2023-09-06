/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.widget.reference.messages;

/**
 * Interface of the reference wodget message service.
 *
 * @author Jerome Gout
 */
public interface IReferenceMessageService {

    String invalidInput(String expectedInputTypeName, String receivedInputTypeName);

    String invalidIds();

    String unableToEditReadOnlyWidget();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author Jerome Gout
     */
    class NoOp implements IReferenceMessageService {

        @Override
        public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
            return "";
        }

        @Override
        public String invalidIds() {
            return "";
        }

        @Override
        public String unableToEditReadOnlyWidget() {
            return "";
        }

    }
}
