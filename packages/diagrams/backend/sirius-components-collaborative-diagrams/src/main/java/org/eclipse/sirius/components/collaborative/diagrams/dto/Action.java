/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.util.List;
import java.util.Objects;

/**
 * An Action triggered by a single click on a diagram element.
 *
 * @author arichard
 */
public record Action(String id, String name, List<String> iconURLs, String tooltip, boolean readOnlyVisible, boolean remoteExecution, boolean localExecution) {

    public Action {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(iconURLs);
        Objects.requireNonNull(tooltip);
    }
}
