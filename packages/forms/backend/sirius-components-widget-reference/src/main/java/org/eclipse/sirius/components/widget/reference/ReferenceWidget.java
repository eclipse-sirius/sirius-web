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
package org.eclipse.sirius.components.widget.reference;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * A widget to view/edit an EMF reference.
 *
 * @author pcdavid
 */
@Immutable
public final class ReferenceWidget extends AbstractWidget {

    private List<ReferenceValue> referenceValues;

    private List<ReferenceValue> referenceOptions;

    private Setting setting;

    private ReferenceWidgetStyle style;

    private String ownerId;

    private Supplier<IStatus> clearHandler;

    private ReferenceWidget() {
        // Prevent instantiation
    }

    public static Builder newReferenceWidget(String id) {
        return new Builder(id);
    }

    public List<ReferenceValue> getReferenceValues() {
        return this.referenceValues;
    }

    public List<ReferenceValue> getReferenceOptions() {
        return this.referenceOptions;
    }

    public Setting getSetting() {
        return this.setting;
    }

    public ReferenceWidgetStyle getStyle() {
        return this.style;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public Supplier<IStatus> getClearHandler() {
        return this.clearHandler;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * Builder used to create the MultiValuedReferenceWidget.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private String iconURL;

        private List<Diagnostic> diagnostics;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private List<ReferenceValue> referenceValues;

        private List<ReferenceValue> referenceOptions;

        private Setting setting;

        private ReferenceWidgetStyle style;

        private String ownerId;

        private Supplier<IStatus> clearHandler;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(String iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder helpTextProvider(Supplier<String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }

        public Builder referenceValues(List<ReferenceValue> values) {
            this.referenceValues = Objects.requireNonNull(values);
            return this;
        }

        public Builder referenceOptions(List<ReferenceValue> options) {
            this.referenceOptions = Objects.requireNonNull(options);
            return this;
        }

        public Builder setting(Setting setting) {
            this.setting = Objects.requireNonNull(setting);
            return this;
        }

        public Builder style(ReferenceWidgetStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder ownerId(String ownerId) {
            this.ownerId = Objects.requireNonNull(ownerId);
            return this;
        }

        public Builder clearHandler(Supplier<IStatus> clearHandler) {
            this.clearHandler = Objects.requireNonNull(clearHandler);
            return this;
        }

        public ReferenceWidget build() {
            ReferenceWidget referenceWidget = new ReferenceWidget();
            referenceWidget.id = Objects.requireNonNull(this.id);
            referenceWidget.label = Objects.requireNonNull(this.label);
            referenceWidget.iconURL = this.iconURL;
            referenceWidget.diagnostics = Objects.requireNonNull(this.diagnostics);
            referenceWidget.referenceValues = Objects.requireNonNull(this.referenceValues);
            referenceWidget.referenceOptions = Objects.requireNonNull(this.referenceOptions);
            referenceWidget.setting = Objects.requireNonNull(this.setting);
            referenceWidget.helpTextProvider = this.helpTextProvider; // Optional on purpose
            referenceWidget.readOnly = this.readOnly;
            referenceWidget.style = this.style; // Optional on purpose
            referenceWidget.ownerId = Objects.requireNonNull(this.ownerId);
            referenceWidget.clearHandler = Objects.requireNonNull(this.clearHandler);
            return referenceWidget;
        }
    }

}
