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
 * The input for the Form Description Editor add group mutation.
 *
 * @author arichard
 */
public class MoveGroupInput implements IFormDescriptionEditorInput {

    private UUID id;

    private String editingContextId;

    private String representationId;

    private String groupId;

    private int index;

    public MoveGroupInput() {
        // Used by jackson
    }

    public MoveGroupInput(UUID id, String editingContextId, String representationId, String groupId, int index) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.representationId = Objects.requireNonNull(representationId);
        this.groupId = Objects.requireNonNull(groupId);
        this.index = index;
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

    public String getGroupId() {
        return this.groupId;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, groupId: {4}, index: {5}}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId, this.groupId, this.index);
    }

}
