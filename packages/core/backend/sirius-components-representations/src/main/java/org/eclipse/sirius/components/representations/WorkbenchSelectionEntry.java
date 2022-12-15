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
package org.eclipse.sirius.components.representations;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * Used to represent a selection entry of the workbench.
 *
 * @author arichard
 */
public class WorkbenchSelectionEntry {

    private String id;

    private String label;

    private String kind;

    public WorkbenchSelectionEntry() {
        // Used Jackson
    }

    public WorkbenchSelectionEntry(String id, String label, String kind) {
        this.id = Objects.requireNonNull(id);
        this.label = Objects.requireNonNull(label);
        this.kind = Objects.requireNonNull(kind);
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getKind() {
        return this.kind;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WorkbenchSelectionEntry) {
            WorkbenchSelectionEntry entry = (WorkbenchSelectionEntry) obj;

            boolean isEqual = this.id.equals(entry.id);
            isEqual = isEqual && this.label.equals(entry.label);
            isEqual = isEqual && this.kind.equals(entry.kind);
            return isEqual;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.label, this.kind);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, kind: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.kind);
    }
}
