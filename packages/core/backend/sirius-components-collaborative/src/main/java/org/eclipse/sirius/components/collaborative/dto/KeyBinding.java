/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.dto;

import java.util.Objects;

/**
 * A key binding.
 *
 * @author gdaniel
 */
public record KeyBinding(boolean isCtrl, boolean isMeta, boolean isAlt, String key) {

    public KeyBinding {
        Objects.requireNonNull(key);
    }

    public static Builder newKeyBinding() {
        return new Builder();
    }

    /**
     * The builder used to create a key binding.
     *
     * @author gdaniel
     */
    @SuppressWarnings("checkstyle:HiddenField")
    @org.eclipse.sirius.components.annotations.Builder
    public static final class Builder {

        private boolean isCtrl;

        private boolean isMeta;

        private boolean isAlt;

        private String key;

        private Builder() {

        }

        public Builder isCtrl(boolean isCtrl) {
            this.isCtrl = isCtrl;
            return this;
        }

        public Builder isMeta(boolean isMeta) {
            this.isMeta = isMeta;
            return this;
        }

        public Builder isAlt(boolean isAlt) {
            this.isAlt = isAlt;
            return this;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public KeyBinding build() {
            return new KeyBinding(this.isCtrl, this.isMeta, this.isAlt, this.key);
        }
    }
}
