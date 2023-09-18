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
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.widget.reference.dto.CreateElementHandlerInput;
import org.eclipse.sirius.components.widget.reference.dto.MoveReferenceValueHandlerInput;

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

    private List<ReferenceValue> options;

    private String typeName;

    private String referenceKind;

    private boolean isContainment;

    private boolean isMany;

    private ReferenceWidgetStyle style;

    private String ownerId;

    private Supplier<IStatus> clearHandler;

    private Function<Object, IStatus> setHandler;

    private Function<List<?>, IStatus> addHandler;

    private Function<CreateElementHandlerInput, Object> createElementHandler;

    private Function<MoveReferenceValueHandlerInput, IStatus> moveHandler;

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

    public List<ReferenceValue> getOptions() {
        return this.options;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public String getReferenceKind() {
        return this.referenceKind;
    }

    public boolean isContainment() {
        return this.isContainment;
    }

    public boolean isMany() {
        return this.isMany;
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

    public Function<Object, IStatus> getSetHandler() {
        return this.setHandler;
    }

    public Function<List<?>, IStatus> getAddHandler() {
        return this.addHandler;
    }

    public Function<CreateElementHandlerInput, Object> getCreateElementHandler() {
        return this.createElementHandler;
    }

    public Function<MoveReferenceValueHandlerInput, IStatus> getMoveHandler() {
        return this.moveHandler;
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

        private List<ReferenceValue> options;

        private String typeName;

        private String referenceKind;

        private boolean isContainment;

        private boolean isMany;

        private ReferenceWidgetStyle style;

        private String ownerId;

        private Supplier<IStatus> clearHandler;

        private Function<Object, IStatus> setHandler;

        private Function<List<?>, IStatus> addHandler;

        private Function<CreateElementHandlerInput, Object> createElementHandler;

        private Function<MoveReferenceValueHandlerInput, IStatus> moveHandler;

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

        public Builder options(List<ReferenceValue> options) {
            this.options = Objects.requireNonNull(options);
            return this;
        }

        public Builder typeName(String typeName) {
            this.typeName = Objects.requireNonNull(typeName);
            return this;
        }

        public Builder referenceKind(String referenceKind) {
            this.referenceKind = Objects.requireNonNull(referenceKind);
            return this;
        }

        public Builder isContainment(boolean isContainment) {
            this.isContainment = isContainment;
            return this;
        }

        public Builder isMany(boolean isMany) {
            this.isMany = isMany;
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

        public Builder setHandler(Function<Object, IStatus> setHandler) {
            this.setHandler = Objects.requireNonNull(setHandler);
            return this;
        }

        public Builder addHandler(Function<List<?>, IStatus> addHandler) {
            this.addHandler = Objects.requireNonNull(addHandler);
            return this;
        }

        public Builder createElementHandler(Function<CreateElementHandlerInput, Object> createElementHandler) {
            this.createElementHandler = Objects.requireNonNull(createElementHandler);
            return this;
        }

        public Builder moveHandler(Function<MoveReferenceValueHandlerInput, IStatus> moveHandler) {
            this.moveHandler = Objects.requireNonNull(moveHandler);
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
            referenceElementProps.options = Objects.requireNonNull(this.options);
            referenceElementProps.typeName = Objects.requireNonNull(this.typeName);
            referenceElementProps.referenceKind = Objects.requireNonNull(this.referenceKind);
            referenceElementProps.isContainment = this.isContainment;
            referenceElementProps.isMany = this.isMany;
            referenceElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            referenceElementProps.style = this.style; // Optional on purpose
            referenceElementProps.ownerId = Objects.requireNonNull(this.ownerId);
            referenceElementProps.clearHandler = Objects.requireNonNull(this.clearHandler);
            referenceElementProps.setHandler = this.setHandler; // Optional on purpose
            referenceElementProps.addHandler = this.addHandler; // Optional on purpose
            referenceElementProps.createElementHandler = this.createElementHandler;  // Optional on purpose
            referenceElementProps.moveHandler = this.moveHandler;  // Optional on purpose
            return referenceElementProps;
        }
    }
}
