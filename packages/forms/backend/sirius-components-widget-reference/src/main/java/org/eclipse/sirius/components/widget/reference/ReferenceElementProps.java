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
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties for the multi-valued reference widget element.
 *
 * @author pcdavid
 */
@Immutable
public final class ReferenceElementProps implements IProps {

    public static final String TYPE = ReferenceWidget.class.getSimpleName();

    private String id;

    private String label;

    private String iconURL;

    private List<Diagnostic> diagnostics;

    private Supplier<String> helpTextProvider;

    private boolean readOnly;

    private List<ReferenceValue> values;

    private Setting setting;

    private ReferenceWidgetStyle style;

    private ReferenceElementProps() {
        // Prevent instantiation
    }

    public static Builder newReferenceElementProps(String id) {
        return new Builder(id);
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getIconURL() {
        return this.iconURL;
    }

    public List<Diagnostic> getDiagnostics() {
        return this.diagnostics;
    }

    public Supplier<String> getHelpTextProvider() {
        return this.helpTextProvider;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public List<ReferenceValue> getValues() {
        return this.values;
    }

    public Setting getSetting() {
        return this.setting;
    }

    public ReferenceWidgetStyle getStyle() {
        return this.style;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * The builder of the reference element props.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private String iconURL;
        private boolean readOnly;
        private Supplier<String> helpTextProvider;

        private List<Diagnostic> diagnostics;

        private List<ReferenceValue> values;

        private Setting setting;

        private ReferenceWidgetStyle style;

        private Builder(String id) {
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

        public Builder values(List<ReferenceValue> values) {
            this.values = Objects.requireNonNull(values);
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

        public ReferenceElementProps build() {
            ReferenceElementProps referenceElementProps = new ReferenceElementProps();
            referenceElementProps.id = Objects.requireNonNull(this.id);
            referenceElementProps.label = Objects.requireNonNull(this.label);
            referenceElementProps.iconURL = this.iconURL;
            referenceElementProps.diagnostics = this.diagnostics;
            referenceElementProps.readOnly = this.readOnly;
            referenceElementProps.values = Objects.requireNonNull(this.values);
            referenceElementProps.setting = Objects.requireNonNull(this.setting);
            referenceElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            referenceElementProps.style = this.style; // Optional on purpose
            return referenceElementProps;
        }
    }
}
