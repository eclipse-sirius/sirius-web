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
package org.eclipse.sirius.web.services.api.projects;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInputObjectType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.services.api.dto.IInput;

/**
 * The input object of the create project mutation.
 *
 * @author wpiers
 */
@GraphQLInputObjectType
public final class CreateProjectInput implements IInput {

    private String name;

    private Visibility visibility;

    public CreateProjectInput() {
        // Used by Jackson
    }

    public CreateProjectInput(String name, Visibility visibility) {
        this.name = Objects.requireNonNull(name);
        this.visibility = visibility;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getName() {
        return this.name;
    }

    @GraphQLField
    @GraphQLNonNull
    public Visibility getVisibility() {
        return this.visibility;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'name: {1}, visibility: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.name, this.visibility);
    }

}
