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
package org.eclipse.sirius.web.selection;

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
 * The root concept of the selection representation.
 *
 * @author arichard
 */
@Immutable
@GraphQLObjectType
public final class Selection implements IRepresentation {

    public static final String KIND = "Selection"; //$NON-NLS-1$

    private String id;

    private String targetObjectId;

    private String message;

    private List<SelectionObject> objects;

    private Selection() {
        // Prevent instantiation
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getId() {
        return this.id;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @GraphQLField
    public String getMessage() {
        return this.message;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull SelectionObject> getObjects() {
        return this.objects;
    }

    public static Builder newSelection(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, descriptionId: {2}, message: {3}, objectsCount: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.message, this.objects.size());
    }

    /**
     * The builder used to create the selection.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String targetObjectId;

        private String message;

        private List<SelectionObject> objects;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder objects(List<SelectionObject> objects) {
            this.objects = Objects.requireNonNull(objects);
            return this;
        }

        public Selection build() {
            Selection selection = new Selection();
            selection.id = Objects.requireNonNull(this.id);
            selection.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            selection.message = this.message;
            selection.objects = Objects.requireNonNull(this.objects);
            return selection;
        }
    }
}
