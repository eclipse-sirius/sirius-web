/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.graphql.GraphQLField;
import org.eclipse.sirius.components.annotations.graphql.GraphQLID;
import org.eclipse.sirius.components.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input of the tree event subscription.
 *
 * @author sbegaudeau
 */
@GraphQLInputObjectType
public final class TreeEventInput implements IInput {
    private UUID id;

    private String editingContextId;

    private List<String> expanded;

    public TreeEventInput() {
        // Used by Jackson
    }

    public TreeEventInput(UUID id, String editingContextId, List<String> expanded) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.expanded = Objects.requireNonNull(expanded);
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

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull String> getExpanded() {
        return this.expanded;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, expanded: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.expanded);
    }
}
