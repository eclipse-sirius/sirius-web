/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.representations;

import java.util.UUID;
import java.util.function.Function;

/**
 * IdProvider used to compute a new random id or to return the previously created if retrieved from the variable
 * manager.
 *
 * @author sbegaudeau
 */
public class GetOrCreateRandomIdProvider implements Function<VariableManager, String> {

    public static final String PREVIOUS_REPRESENTATION_ID = "previousRepresentationId"; //$NON-NLS-1$

    @Override
    public String apply(VariableManager variableManager) {
        // @formatter:off
        return variableManager.get(PREVIOUS_REPRESENTATION_ID, String.class)
        .orElseGet(() -> UUID.randomUUID().toString());
        // @formatter:on
    }

}
