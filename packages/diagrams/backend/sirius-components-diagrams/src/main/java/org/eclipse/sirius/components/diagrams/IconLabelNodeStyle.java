/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.diagrams;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The list container item node style.
 *
 * @author gcoutable
 */
@Immutable
public final class IconLabelNodeStyle implements INodeStyle {

    private String background;

    private IconLabelNodeStyle() {
        // Prevent instantiation
    }

    public static Builder newIconLabelNodeStyle() {
        return new Builder();
    }

    public String getBackground() {
        return this.background;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'background: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.background);
    }

    /**
     * The builder used to create the list container item node style.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String background;

        private Builder() {
            // Prevent instantiation
        }

        public Builder background(String background) {
            this.background = Objects.requireNonNull(background);
            return this;
        }

        public IconLabelNodeStyle build() {
            IconLabelNodeStyle nodeStyleDescription = new IconLabelNodeStyle();
            nodeStyleDescription.background = Objects.requireNonNull(this.background);
            return nodeStyleDescription;
        }
    }
}
