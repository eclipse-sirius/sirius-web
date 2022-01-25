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
package org.eclipse.sirius.components.collaborative.validation.dto;

import java.text.MessageFormat;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.graphql.GraphQLField;
import org.eclipse.sirius.components.annotations.graphql.GraphQLID;
import org.eclipse.sirius.components.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input for the validation event subscription.
 *
 * @author gcoutable
 */
@GraphQLInputObjectType
public final class ValidationEventInput implements IInput {

    private UUID id;

    private String editingContextId;

    public ValidationEventInput() {
        // Used by Jackson
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

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId);
    }

}
