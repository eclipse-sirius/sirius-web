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
package org.eclipse.sirius.web.services.api.modelers;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.core.api.IInput;

/**
 * The input object of the {@code renameModeler} mutation.
 *
 * @author pcdavid
 */
@GraphQLInputObjectType
public final class RenameModelerInput implements IInput {
    private UUID modelerId;

    private String newName;

    public RenameModelerInput() {
        // Used by Jackson
    }

    public RenameModelerInput(UUID modelerId, String newName) {
        this.modelerId = Objects.requireNonNull(modelerId);
        this.newName = Objects.requireNonNull(newName);
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getModelerId() {
        return this.modelerId;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getNewName() {
        return this.newName;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'modelerId: {1}, newName: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.modelerId, this.newName);
    }

}
