/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.omnibox.dto;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommand;

/**
 * A command that can be executed in the context of a workbench.
 *
 * @author gdaniel
 */
// GH comment: move to sirius web? Not sure the concept of workbench is pertinent in sirius-components-core.
public record WorkbenchCommand(String id, String label, List<String> iconURLs, String description) implements IOmniboxCommand {

    public WorkbenchCommand {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(iconURLs);
        Objects.requireNonNull(description);
    }

}
