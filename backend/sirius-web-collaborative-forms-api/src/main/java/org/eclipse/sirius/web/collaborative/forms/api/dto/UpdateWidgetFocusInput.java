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
 * The input of the update widget focus mutation.
 *
 * @author sbegaudeau
 */
@GraphQLInputObjectType
public final class UpdateWidgetFocusInput implements IFormInput {

    private UUID projectId;

    private UUID representationId;

    private String widgetId;

    private boolean selected;

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
    public String getWidgetId() {
        return this.widgetId;
    }

    @GraphQLField
    @GraphQLNonNull
    public boolean isSelected() {
        return this.selected;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'projectId: {1}, representationId: {2}, widgetId: {3}, selected: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.projectId, this.representationId, this.widgetId, this.selected);
    }

}
