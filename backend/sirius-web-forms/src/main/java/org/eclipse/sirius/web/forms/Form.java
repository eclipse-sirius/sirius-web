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
package org.eclipse.sirius.web.forms;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.representations.IRepresentation;

/**
 * Root concept of the form representation.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Immutable
@GraphQLObjectType
public final class Form implements IRepresentation {

    public static final String KIND = "Form"; //$NON-NLS-1$

    private String id;

    private String label;

    private String targetObjectId;

    private List<Page> pages;

    private Form() {
        // Prevent instantiation
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getId() {
        return this.id;
    }

    @GraphQLNonNull
    @GraphQLField
    public String getLabel() {
        return this.label;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @GraphQLNonNull
    @GraphQLField
    public List<@GraphQLNonNull Page> getPages() {
        return this.pages;
    }

    public static Builder newForm(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, targetObjectId: {3}, pageCount: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.targetObjectId, this.pages.size());
    }

    /**
     * The builder used to create the form.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String targetObjectId;

        private List<Page> pages;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder pages(List<Page> pages) {
            this.pages = pages;
            return this;
        }

        public Form build() {
            Form form = new Form();
            form.id = Objects.requireNonNull(this.id);
            form.label = Objects.requireNonNull(this.label);
            form.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            form.pages = Objects.requireNonNull(this.pages);
            return form;
        }
    }
}
