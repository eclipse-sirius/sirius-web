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

    private List<ReferenceValue> values;

    private boolean container;

    private boolean manyValued;

    private Setting setting;

    private ReferenceElementProps() {
        // Prevent instantiation
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

    public List<ReferenceValue> getValues() {
        return this.values;
    }

    public boolean isContainer() {
        return this.container;
    }

    public boolean isManyValued() {
        return this.manyValued;
    }

    public Setting getSetting() {
        return this.setting;
    }

    public static Builder newMultiValuedReferenceElementProps(String id) {
        return new Builder(id);
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
        private String id;

        private String label;

        private String iconURL;

        private Supplier<String> helpTextProvider;

        private List<Diagnostic> diagnostics;

        private List<ReferenceValue> values;

        private boolean container;

        private boolean manyValued;

        private Setting setting;

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

        public Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }

        public Builder values(List<ReferenceValue> values) {
            this.values = Objects.requireNonNull(values);
            return this;
        }

        public Builder container(boolean containment) {
            this.container = Objects.requireNonNull(containment);
            return this;
        }

        public Builder manyValued(boolean manyValued) {
            this.manyValued = manyValued;
            return this;
        }

        public Builder setting(Setting setting) {
            this.setting = Objects.requireNonNull(setting);
            return this;
        }

        public ReferenceElementProps build() {
            ReferenceElementProps multiValuedReferenceProps = new ReferenceElementProps();
            multiValuedReferenceProps.id = Objects.requireNonNull(this.id);
            multiValuedReferenceProps.label = Objects.requireNonNull(this.label);
            multiValuedReferenceProps.iconURL = this.iconURL;
            multiValuedReferenceProps.diagnostics = this.diagnostics;
            multiValuedReferenceProps.container = this.container;
            multiValuedReferenceProps.manyValued = this.manyValued;
            multiValuedReferenceProps.values = Objects.requireNonNull(this.values);
            multiValuedReferenceProps.setting = Objects.requireNonNull(this.setting);
            multiValuedReferenceProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return multiValuedReferenceProps;
        }
    }
}
