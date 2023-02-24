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
package org.eclipse.sirius.components.representations;

import java.util.Map;

/**
 * Used to validate the variables given for the execution of an operation.
 *
 * @author sbegaudeau
 */
public interface IOperationValidator {
    void validate(String operationName, Map<String, Object> variables);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IOperationValidator {

        @Override
        public void validate(String operationName, Map<String, Object> variables) {
            // Do nothing
        }
    }
}
