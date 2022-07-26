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
package org.eclipse.sirius.components.diagrams.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;

/**
 * Used to perform tests on a position.
 *
 * @author sbegaudeau
 */
public class PositionAssert extends AbstractAssert<PositionAssert, Position> {

    public PositionAssert(Position position) {
        super(position, PositionAssert.class);
    }

    public void isInside(Size size) {
        assertThat(this.actual.getX()).isGreaterThanOrEqualTo(0).isLessThanOrEqualTo(size.getWidth());
        assertThat(this.actual.getY()).isGreaterThanOrEqualTo(0).isLessThanOrEqualTo(size.getHeight());
    }

}
