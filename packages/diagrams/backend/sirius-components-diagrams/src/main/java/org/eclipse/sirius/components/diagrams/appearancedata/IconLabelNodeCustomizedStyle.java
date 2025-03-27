/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams.appearancedata;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.IconLabelNodeStyle;

/**
 * The list container item node style with nullable fields to store customizations.
 *
 * @author nvannier
 */
@Immutable
public final class IconLabelNodeCustomizedStyle implements INodeCustomizedStyle {

    private String background;

    private IconLabelNodeCustomizedStyle() {
        // Prevent instantiation
    }

    public static Builder newIconLabelNodeCustomizedStyle() {
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

    @Override
    public INodeStyle mergeInto(INodeStyle style) {
        if (style instanceof IconLabelNodeStyle iconLabelNodeStyle) {
            return IconLabelNodeStyle.newIconLabelNodeStyle()
                    .background(Optional.ofNullable(background).orElse(iconLabelNodeStyle.getBackground()))
                    .build();
        } else {
            return style;
        }
    }

    /**
     * Builder for an icon label node customized style.
     *
     * @author nvannier
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

        public IconLabelNodeCustomizedStyle build() {
            IconLabelNodeCustomizedStyle nodeCustomizedStyle = new IconLabelNodeCustomizedStyle();
            nodeCustomizedStyle.background = this.background;
            return nodeCustomizedStyle;
        }
    }
}
