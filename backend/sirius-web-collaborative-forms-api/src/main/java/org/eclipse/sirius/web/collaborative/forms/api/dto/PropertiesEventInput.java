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
package org.eclipse.sirius.web.collaborative.forms.api.dto;

import java.text.MessageFormat;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.services.api.dto.IInput;

/**
 * The input of the properties event subscription.
 *
 * @author hmarchadour
 */
@GraphQLInputObjectType
public final class PropertiesEventInput implements IInput {
    private UUID projectId;

    private String objectId;

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getProjectId() {
        return this.projectId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getObjectId() {
        return this.objectId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'projectId: {1}, objectId: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.projectId, this.objectId);
    }
}
