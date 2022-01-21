/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
 * The image node style.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Immutable
public final class ImageNodeStyle implements INodeStyle {
    private String imageURL;

    private int scalingFactor;

    private ImageNodeStyle() {
        // Prevent instantiation
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public int getScalingFactor() {
        return this.scalingFactor;
    }

    public static Builder newImageNodeStyle() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'imageURL: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.imageURL);
    }

    /**
     * The builder used to create the image node style description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String imageURL;

        private int scalingFactor;

        private Builder() {
            // Prevent instantiation
        }

        public Builder imageURL(String imageURL) {
            this.imageURL = Objects.requireNonNull(imageURL);
            return this;
        }

        public Builder scalingFactor(int scalingFactor) {
            this.scalingFactor = Objects.requireNonNull(scalingFactor);
            return this;
        }

        public ImageNodeStyle build() {
            ImageNodeStyle style = new ImageNodeStyle();
            style.imageURL = Objects.requireNonNull(this.imageURL);
            style.scalingFactor = Objects.requireNonNull(this.scalingFactor);
            return style;
        }
    }
}
