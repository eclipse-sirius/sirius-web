/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.components.collaborative.trees.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.graphql.GraphQLField;
import org.eclipse.sirius.components.annotations.graphql.GraphQLID;
import org.eclipse.sirius.components.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;

/**
 * The input for the renaming of a tree item.
 *
 * @author pcdavid
 */
@GraphQLInputObjectType
public final class RenameTreeItemInput implements ITreeInput {
    private UUID id;

    private String editingContextId;

    private String representationId;

    private UUID treeItemId;

    private String newLabel;

    public RenameTreeItemInput() {
        // Used by Jackson
    }

    public RenameTreeItemInput(UUID id, String editingContextId, String representationId, UUID treeItemId, String newLabel) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.representationId = Objects.requireNonNull(representationId);
        this.treeItemId = Objects.requireNonNull(treeItemId);
        this.newLabel = Objects.requireNonNull(newLabel);
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getId() {
        return this.id;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getEditingContextId() {
        return this.editingContextId;
    }

    @GraphQLID
    @GraphQLField
    @Override
    public String getRepresentationId() {
        return this.representationId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getTreeItemId() {
        return this.treeItemId;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getNewLabel() {
        return this.newLabel;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, treeItemId: {4}, newLabel: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.treeItemId, this.newLabel);
    }
}
