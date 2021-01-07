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

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.core.api.IPayload;

/**
 * The payload of the {@code publishModeler} mutation.
 *
 * @author pcdavid
 */
@GraphQLObjectType
public final class PublishModelerSuccessPayload implements IPayload {
    private final Modeler modeler;

    public PublishModelerSuccessPayload(Modeler modeler) {
        this.modeler = Objects.requireNonNull(modeler);
    }

    @GraphQLField
    @GraphQLNonNull
    public Modeler getModeler() {
        return this.modeler;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'modeler: '{'id: {1}, name: {2}, status: {3} '}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.modeler.getId(), this.modeler.getName(), this.modeler.getStatus());
    }

}
