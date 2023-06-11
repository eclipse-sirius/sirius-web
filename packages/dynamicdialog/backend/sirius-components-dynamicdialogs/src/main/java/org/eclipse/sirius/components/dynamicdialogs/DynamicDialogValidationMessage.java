/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.dynamicdialogs;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * Validation message for the dynamic dialog.
 *
 * @author lfasani
 */
@Immutable
public final class DynamicDialogValidationMessage {

    private String message;

    private Boolean blocksApplyDialog;

    private MessageSeverityEnum severity;

    private DynamicDialogValidationMessage() {
        // Prevent instantiation
    }

    public String getMessage() {
        return this.message;
    }

    public Boolean getBlocksApplyDialog() {
        return this.blocksApplyDialog;
    }

    public MessageSeverityEnum getWidgets() {
        return this.severity;
    }

    public static Builder newDynamicDialogValidationMessage() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, severity: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.message, this.blocksApplyDialog, this.severity);
    }

    /**
     * The builder used to create the dynamic dialog validation message.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String message;

        private Boolean blocksApplyDialog;

        private MessageSeverityEnum severity;

        private Builder() {
        }

        public Builder message(String message) {
            this.message = Objects.requireNonNull(message);
            return this;
        }

        public Builder blocksApplyDialog(Boolean blocksApplyDialog) {
            this.blocksApplyDialog = Objects.requireNonNull(blocksApplyDialog);
            return this;
        }

        public Builder severity(MessageSeverityEnum severity) {
            this.severity = severity;
            return this;
        }

        public DynamicDialogValidationMessage build() {
            DynamicDialogValidationMessage form = new DynamicDialogValidationMessage();
            form.message = Objects.requireNonNull(this.message);
            form.blocksApplyDialog = Objects.requireNonNull(this.blocksApplyDialog);
            form.severity = Objects.requireNonNull(this.severity);
            return form;
        }
    }
}
