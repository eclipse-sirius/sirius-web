/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.diagrams;

import java.text.MessageFormat;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;

/**
 * The position of an element.
 *
 * @author sbegaudeau
 */
@Immutable
@GraphQLObjectType
public final class Position {
    public static final Position UNDEFINED = Position.at(-1, -1);

    private double x;

    private double y;

    private Position() {
        // Prevent instantiation
    }

    public static Position at(double x, double y) {
        Position position = new Position();
        position.x = x;
        position.y = y;
        return position;
    }

    @GraphQLField
    @GraphQLNonNull
    public double getX() {
        return this.x;
    }

    @GraphQLField
    @GraphQLNonNull
    public double getY() {
        return this.y;
    }

    public static Builder newPosition() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'x: {1}, y: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.x, this.y);
    }

    /**
     * The builder used to create a new position.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private double x;

        private double y;

        private Builder() {
            // Prevent instantiation
        }

        public Builder x(double x) {
            this.x = x;
            return this;
        }

        public Builder y(double y) {
            this.y = y;
            return this;
        }

        public Position build() {
            Position position = new Position();
            position.x = this.x;
            position.y = this.y;
            return position;
        }
    }
}
