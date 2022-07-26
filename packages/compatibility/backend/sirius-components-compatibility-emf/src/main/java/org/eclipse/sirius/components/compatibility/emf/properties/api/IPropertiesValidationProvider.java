/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.components.compatibility.emf.properties.api;

import java.util.List;
import java.util.function.Function;

import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to provide validation support to all the widgets of the default properties description.
 *
 * @author sbegaudeau
 */
public interface IPropertiesValidationProvider {
    Function<VariableManager, List<?>> getDiagnosticsProvider();

    Function<Object, String> getKindProvider();

    Function<Object, String> getMessageProvider();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author gcoutable
     */
    class NoOp implements IPropertiesValidationProvider {

        @Override
        public Function<VariableManager, List<?>> getDiagnosticsProvider() {
            return variableManager -> List.of();
        }

        @Override
        public Function<Object, String> getKindProvider() {
            return object -> ""; //$NON-NLS-1$
        }

        @Override
        public Function<Object, String> getMessageProvider() {
            return object -> ""; //$NON-NLS-1$
        }

    }
}
