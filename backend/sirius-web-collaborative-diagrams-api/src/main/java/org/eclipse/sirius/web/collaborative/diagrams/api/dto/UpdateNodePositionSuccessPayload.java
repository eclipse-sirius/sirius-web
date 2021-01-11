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
package org.eclipse.sirius.web.collaborative.diagrams.api.dto;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.diagrams.Diagram;

/**
 * The payload of the "Update Node Position" mutation returned on success.
 *
 * @author fbarbin
 */
@GraphQLObjectType
public final class UpdateNodePositionSuccessPayload implements IPayload {
    private final Diagram diagram;

    public UpdateNodePositionSuccessPayload(Diagram diagram) {
        this.diagram = Objects.requireNonNull(diagram);
    }

    @GraphQLField
    @GraphQLNonNull
    public Diagram getDiagram() {
        return this.diagram;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'diagram: '{'id: {1}, label: {2}'}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.diagram.getId(), this.diagram.getLabel());
    }
}
