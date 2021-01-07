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
 * The input object for the {@code publishModeler} mutation.
 *
 * @author pcdavid
 */
@GraphQLInputObjectType
public final class PublishModelerInput implements IInput {
    private UUID modelerId;

    public PublishModelerInput() {
        // Used by Jackson
    }

    public PublishModelerInput(UUID modelerId) {
        this.modelerId = Objects.requireNonNull(modelerId);
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getModelerId() {
        return this.modelerId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{' modelerId: {1} '}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.modelerId);
    }

}
