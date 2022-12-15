/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.compatibility.messages;

/**
 * Interface of the compatibility message service.
 *
 * @author gcoutable
 */
public interface ICompatibilityMessageService {

    String noReconnectionToolDefined();

    String toolExecutionError();

    String reconnectionToolCannotBeHandled();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author gcoutable
     */
    class NoOp implements ICompatibilityMessageService {

        @Override
        public String noReconnectionToolDefined() {
            return "";
        }

        @Override
        public String toolExecutionError() {
            return "";
        }

        @Override
        public String reconnectionToolCannotBeHandled() {
            return "";
        }

    }

}
