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
import java.util.Objects;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.forms.Form;

/**
 * Payload used to indicate that the form has been refreshed.
 *
 * @author sbegaudeau
 */
@GraphQLObjectType
public final class FormRefreshedEventPayload implements IPayload {
    private final Form form;

    public FormRefreshedEventPayload(Form form) {
        this.form = Objects.requireNonNull(form);
    }

    @GraphQLField
    @GraphQLNonNull
    public Form getForm() {
        return this.form;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'form: '{'id: {1}, label: {2}'}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.form.getId(), this.form.getLabel());
    }
}
