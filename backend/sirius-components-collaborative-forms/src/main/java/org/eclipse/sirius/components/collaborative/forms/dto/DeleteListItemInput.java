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
package org.eclipse.sirius.components.collaborative.forms.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.graphql.GraphQLField;
import org.eclipse.sirius.components.annotations.graphql.GraphQLID;
import org.eclipse.sirius.components.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;

/**
 * The input object for the list item deletion mutation.
 *
 * @author gcoutable
 */
@GraphQLInputObjectType
public final class DeleteListItemInput implements IFormInput {
    private UUID id;

    private String representationId;

    private String editingContextId;

    private String listId;

    private String listItemId;

    public DeleteListItemInput() {
        // Used by Jackson
    }

    public DeleteListItemInput(UUID id, String representationId, String editingContextId, String listId, String listItemId) {
        this.id = Objects.requireNonNull(id);
        this.representationId = Objects.requireNonNull(representationId);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.listId = Objects.requireNonNull(listId);
        this.listItemId = Objects.requireNonNull(listItemId);
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getId() {
        return this.id;
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getRepresentationId() {
        return this.representationId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getEditingContextId() {
        return this.editingContextId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getListId() {
        return this.listId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getListItemId() {
        return this.listItemId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, representationId: {2}, editingContextId: {3}, listId: {5}, listItemId: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.representationId, this.editingContextId, this.listId, this.listItemId);
    }

}
