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
package org.eclipse.sirius.web.services.api.objects;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.services.api.dto.IPayload;

/**
 * The payload returned by the rename object mutation.
 *
 * @author arichard
 */
@GraphQLObjectType
public final class RenameObjectSuccessPayload implements IPayload {

    private final String objectId;

    private final String newName;

    public RenameObjectSuccessPayload(String objectId, String newName) {
        this.objectId = Objects.requireNonNull(objectId);
        this.newName = Objects.requireNonNull(newName);
    }

    @GraphQLField
    @GraphQLNonNull
    public String getObjectId() {
        return this.objectId;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getNewName() {
        return this.newName;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'objectId: {1}, newName: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.objectId, this.newName);
    }
}
