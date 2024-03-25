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
package org.eclipse.sirius.components.diagrams.tests.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractObjectAssert;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;

/**
 * Custom assertion class used to perform some tests on a diagram.
 *
 * @author gdaniel
 */
public class DiagramAssert extends AbstractObjectAssert<DiagramAssert, Diagram> {

    public DiagramAssert(Diagram diagram) {
        super(diagram, DiagramAssert.class);
    }

    public DiagramAssert hasId(String id) {
        assertThat(this.actual.getId()).isEqualTo(id);
        return this;
    }

    public DiagramAssert hasKind(String kind) {
        assertThat(this.actual.getKind()).isEqualTo(kind);
        return this;
    }

    public DiagramAssert hasTargetObjectId(String targetObjectId) {
        assertThat(this.actual.getTargetObjectId()).isEqualTo(targetObjectId);
        return this;
    }

    public DiagramAssert hasDescriptionId(String descriptionId) {
        assertThat(this.actual.getDescriptionId()).isEqualTo(descriptionId);
        return this;
    }

    public DiagramAssert hasLabel(String label) {
        assertThat(this.actual.getLabel()).isEqualTo(label);
        return this;
    }

    public DiagramAssert hasPosition(Position position) {
        assertThat(this.actual.getPosition()).isEqualTo(position);
        return this;
    }

    public DiagramAssert hasSize(Size size) {
        assertThat(this.actual.getSize()).isEqualTo(size);
        return this;
    }
}
