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

import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.ISemanticRepresentation;
import org.eclipse.sirius.web.TestIdentifiers;

/**
 * Test representation.
 *
 * @author sbegaudeau
 */
public class TestRepresentation implements IRepresentation, ISemanticRepresentation {
    @Override
    public String getId() {
        return UUID.nameUUIDFromBytes("testId".getBytes(StandardCharsets.UTF_8)).toString();
    }

    @Override
    public String getDescriptionId() {
        return new TestRepresentationDescription().getId();
    }

    @Override
    public String getLabel() {
        return "Test";
    }

    @Override
    public String getKind() {
        return IRepresentation.KIND_PREFIX + this.getDescriptionId();
    }

    @Override
    public String getTargetObjectId() {
        return TestIdentifiers.EPACKAGE_OBJECT.toString();
    }
}
