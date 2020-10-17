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
import org.eclipse.sirius.web.collaborative.forms.api.IFormInput;

/**
 * The input object for the select edition mutation.
 *
 * @author lfasani
 */
@GraphQLInputObjectType
public final class EditSelectInput implements IFormInput {
    private UUID projectId;

    private UUID representationId;

    private String selectId;

    private String newValue;

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getProjectId() {
        return this.projectId;
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getRepresentationId() {
        return this.representationId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getSelectId() {
        return this.selectId;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getNewValue() {
        return this.newValue;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'projectId: {1}, representationId: {2}, selectId: {3}, newValue: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.projectId, this.representationId, this.selectId, this.newValue);
    }
}
