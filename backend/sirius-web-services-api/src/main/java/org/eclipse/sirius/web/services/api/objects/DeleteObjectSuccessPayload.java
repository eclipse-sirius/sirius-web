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

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.dto.IPayload;

/**
 * The payload returned by the delete object mutation.
 *
 * @author sbegaudeau
 */
@GraphQLObjectType
public final class DeleteObjectSuccessPayload implements IPayload {

    private final Document document;

    public DeleteObjectSuccessPayload(Document document) {
        this.document = document;
    }

    @GraphQLField
    public Document getDocument() {
        return this.document;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'document: '{'id: {1}, name: {2}'}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.document.getId(), this.document.getName());
    }
}
