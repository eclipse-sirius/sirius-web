/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.diagrams;

import java.util.Objects;

/**
 * A node decorator.
 *
 * @author gdaniel
 */
public record NodeDecorator(String label, NodeDecoratorPosition position, String iconURL) {

    public NodeDecorator {
        Objects.requireNonNull(label);
        Objects.requireNonNull(position);
        Objects.requireNonNull(iconURL);
    }
}
