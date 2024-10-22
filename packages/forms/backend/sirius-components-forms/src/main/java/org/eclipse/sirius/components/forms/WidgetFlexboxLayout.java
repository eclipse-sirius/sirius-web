/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

package org.eclipse.sirius.components.forms;

import java.util.Objects;

/**
 * Class reprensenting flexbox properties for widget.
 * @author lfasani
 */
public record WidgetFlexboxLayout(String flexDirection, String gap, String labelFlex, String valueFlex) {
    public WidgetFlexboxLayout {
        Objects.requireNonNull(flexDirection);
        Objects.requireNonNull(gap);
        Objects.requireNonNull(labelFlex);
        Objects.requireNonNull(valueFlex);
    }

    public static Builder newWidgetFlexboxLayout() {
        return new WidgetFlexboxLayout.Builder();
    }

    /**
     * The builder of the WidgetFlexboxLayout.
     *
     * @author lfdsani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String flexDirection;

        private String gap;

        private String labelFlex;

        private String valueFlex;

        private Builder() {
        }

        public Builder flexDirection(String flexDirection) {
            this.flexDirection = Objects.requireNonNull(flexDirection);
            return this;
        }

        public Builder gap(String gap) {
            this.gap = Objects.requireNonNull(gap);
            return this;
        }

        public Builder labelFlex(String labelFlex) {
            this.labelFlex = Objects.requireNonNull(labelFlex);
            return this;
        }

        public Builder valueFlex(String valueFlex) {
            this.valueFlex = Objects.requireNonNull(valueFlex);
            return this;
        }

        public WidgetFlexboxLayout build() {
            WidgetFlexboxLayout widgetFlexboxLayout = new WidgetFlexboxLayout(this.flexDirection, this.gap, this.labelFlex, this.valueFlex);
            return widgetFlexboxLayout;
        }
    }
}
