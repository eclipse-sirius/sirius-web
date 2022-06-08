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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorInput;

/**
 * The input for the Form Description Editor add widget mutation.
 *
 * @author arichard
 */
public class MoveWidgetInput implements IFormDescriptionEditorInput {

    private UUID id;

    private String editingContextId;

    private String representationId;

    private String containerId;

    private String widgetId;

    private int index;

    public MoveWidgetInput() {
        // Used by jackson
    }

    public MoveWidgetInput(UUID id, String editingContextId, String representationId, String containerId, String widgetId, int index) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.representationId = Objects.requireNonNull(representationId);
        this.containerId = containerId;
        this.widgetId = Objects.requireNonNull(widgetId);
        this.index = Objects.requireNonNull(index);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getRepresentationId() {
        return this.representationId;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public String getContainerId() {
        return this.containerId;
    }

    public String getWidgetId() {
        return this.widgetId;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, containerId: {4}, widgetId: {5}, index: {6}}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId, this.containerId, this.widgetId, this.index);
    }

}
