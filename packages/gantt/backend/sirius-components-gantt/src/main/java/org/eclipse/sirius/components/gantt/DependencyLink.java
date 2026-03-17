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
package org.eclipse.sirius.components.gantt;

import java.util.Objects;

/**
 * Concept of Dependency Link. A target task depends on a source task.
 * <p> The parameters follow the direction of the arrow in the Gantt chart:
 * the arrow starts from the source task and points to the target task.
 * @author ncouvert
 */

public record DependencyLink(StartOrEnd source, StartOrEnd target, String sourceDependencyId) {

    public DependencyLink {
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);
        Objects.requireNonNull(sourceDependencyId);
    }
}
