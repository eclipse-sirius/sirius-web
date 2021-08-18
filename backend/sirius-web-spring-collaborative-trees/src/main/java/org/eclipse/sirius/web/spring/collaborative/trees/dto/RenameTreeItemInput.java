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
package org.eclipse.sirius.web.spring.collaborative.trees.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.core.api.IInput;

/**
 * The input for the renaming of a tree item.
 *
 * @author pcdavid
 */
@GraphQLInputObjectType
public final class RenameTreeItemInput implements IInput {
    private UUID id;

    private UUID editingContextId;

    private UUID treeItemId;

    private String kind;

    private String newName;

    public RenameTreeItemInput() {
        // Used by Jackson
    }

    public RenameTreeItemInput(UUID id, UUID editingContextId, UUID treeItemId, String kind, String newName) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.treeItemId = Objects.requireNonNull(treeItemId);
        this.kind = Objects.requireNonNull(kind);
        this.newName = Objects.requireNonNull(newName);
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
    public UUID getEditingContextId() {
        return this.editingContextId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getTreeItemId() {
        return this.treeItemId;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getKind() {
        return this.kind;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getNewName() {
        return this.newName;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, treeItemId: {3}, kind: {4}, newName: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.treeItemId, this.kind, this.newName);
    }
}
