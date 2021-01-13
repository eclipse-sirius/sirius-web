/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.services.api.document;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.core.api.IInput;

/**
 * The input of the delete document mutation.
 *
 * @author sbegaudeau
 */
@GraphQLInputObjectType
public final class DeleteDocumentInput implements IInput {
    private UUID documentId;

    public DeleteDocumentInput() {
        // Used by Jackson
    }

    public DeleteDocumentInput(UUID documentId) {
        this.documentId = Objects.requireNonNull(documentId);
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getDocumentId() {
        return this.documentId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'documentId: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.documentId);
    }
}
