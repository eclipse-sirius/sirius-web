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
package org.eclipse.sirius.web.services;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Representation description used during integration tests.
 *
 * @author sbegaudeau
 */
public class TestRepresentationDescription implements IRepresentationDescription {
    @Override
    public String getId() {
        return UUID.nameUUIDFromBytes("testDescriptionId".getBytes(StandardCharsets.UTF_8)).toString();
    }

    @Override
    public String getLabel() {
        return "Test";
    }

    @Override
    public Predicate<VariableManager> getCanCreatePredicate() {
        return variableManager -> true;
    }
}
