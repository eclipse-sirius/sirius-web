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
package org.eclipse.sirius.components.forms;

import java.util.Objects;

/**
 * All flexbox container directions.
 *
 * @author arichard
 */
public enum FlexDirection {
    row("row"), rowReverse("row-reverse"), column("column"), columnReverse("column-reverse");

    private final String flexDirection;

    FlexDirection(String flexDirection) {
        this.flexDirection = Objects.requireNonNull(flexDirection);
    }

    @Override
    public String toString() {
        return this.flexDirection;
    }
}
