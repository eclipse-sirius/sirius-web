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
package org.eclipse.sirius.components.core.api;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

/**
 * Used to select some objects after the execution of a tool in a representation.
 *
 * @author arichard
 */
public class WorkbenchSelection {

    private final List<WorkbenchSelectionEntry> entries;

    public WorkbenchSelection(List<WorkbenchSelectionEntry> entries) {
        this.entries = Objects.requireNonNull(entries);
    }

    public List<WorkbenchSelectionEntry> getEntries() {
        return this.entries;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WorkbenchSelection) {
            WorkbenchSelection selection = (WorkbenchSelection) obj;
            return this.entries.equals(selection.entries);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.entries);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'entries: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.entries);
    }
}
