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
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.widget.reference.dto.CreateElementInReferenceHandlerParameters;
import org.eclipse.sirius.components.widget.reference.dto.MoveReferenceValueHandlerParameters;

/**
 * A widget to view/edit an EMF reference.
 *
 * @author pcdavid
 */
@Immutable
public final class ReferenceWidget extends AbstractWidget {

    private List<ReferenceValue> referenceValues;

    private Supplier<List<ReferenceValue>> referenceOptionsProvider;

    private String ownerKind;

    private String referenceKind;

    private boolean containment;

    private boolean many;

    private ReferenceWidgetStyle style;

    private String ownerId;

    private Supplier<IStatus> clearHandler;

    private Function<Object, IStatus> setHandler;

    private Function<List<?>, IStatus> addHandler;

    private Function<CreateElementInReferenceHandlerParameters, Object> createElementHandler;

    private Function<MoveReferenceValueHandlerParameters, IStatus> moveHandler;

    private String descriptionId;

    private ReferenceWidget() {
        // Prevent instantiation
    }

    public static Builder newReferenceWidget(String id) {
        return new Builder(id);
    }

    public List<ReferenceValue> getReferenceValues() {
        return this.referenceValues;
    }

    public Supplier<List<ReferenceValue>> getReferenceOptionsProvider() {
        return this.referenceOptionsProvider;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public String getReferenceKind() {
        return this.referenceKind;
    }

    public String getOwnerKind() {
        return this.ownerKind;
    }

    public boolean isContainment() {
        return this.containment;
    }

    public boolean isMany() {
        return this.many;
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

    public Function<CreateElementInReferenceHandlerParameters, Object> getCreateElementHandler() {
        return this.createElementHandler;
    }

    public Function<MoveReferenceValueHandlerParameters, IStatus> getMoveHandler() {
        return this.moveHandler;
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

        private List<String> iconURL = List.of();

        private List<Diagnostic> diagnostics;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private List<ReferenceValue> referenceValues;

        private Supplier<List<ReferenceValue>> referenceOptionsProvider;

        private String ownerKind;

        private String referenceKind;

        private boolean containment;

        private boolean many;

        private ReferenceWidgetStyle style;

        private String ownerId;

        private Supplier<IStatus> clearHandler;

        private Function<Object, IStatus> setHandler;

        private Function<List<?>, IStatus> addHandler;

        private Function<CreateElementInReferenceHandlerParameters, Object> createElementHandler;

        private Function<MoveReferenceValueHandlerParameters, IStatus> moveHandler;

        private String descriptionId;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(List<String> iconURL) {
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

        public Builder referenceOptionsProvider(Supplier<List<ReferenceValue>> optionsProvider) {
            this.referenceOptionsProvider = Objects.requireNonNull(optionsProvider);
            return this;
        }

        public Builder ownerKind(String ownerKind) {
            this.ownerKind = Objects.requireNonNull(ownerKind);
            return this;
        }

        public Builder referenceKind(String referenceKind) {
            this.referenceKind = Objects.requireNonNull(referenceKind);
            return this;
        }

        public Builder containment(boolean containment) {
            this.containment = containment;
            return this;
        }

        public Builder many(boolean many) {
            this.many = many;
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

        public Builder createElementHandler(Function<CreateElementInReferenceHandlerParameters, Object> createElementHandler) {
            this.createElementHandler = Objects.requireNonNull(createElementHandler);
            return this;
        }

        public Builder moveHandler(Function<MoveReferenceValueHandlerParameters, IStatus> moveHandler) {
            this.moveHandler = Objects.requireNonNull(moveHandler);
            return this;
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public ReferenceWidget build() {
            ReferenceWidget referenceWidget = new ReferenceWidget();
            referenceWidget.id = Objects.requireNonNull(this.id);
            referenceWidget.descriptionId = Objects.requireNonNull(this.descriptionId);
            referenceWidget.label = Objects.requireNonNull(this.label);
            referenceWidget.iconURL = Objects.requireNonNull(this.iconURL);
            referenceWidget.diagnostics = Objects.requireNonNull(this.diagnostics);
            referenceWidget.referenceValues = Objects.requireNonNull(this.referenceValues);
            referenceWidget.referenceOptionsProvider = Objects.requireNonNull(this.referenceOptionsProvider);
            referenceWidget.ownerKind = Objects.requireNonNull(this.ownerKind);
            referenceWidget.referenceKind = Objects.requireNonNull(this.referenceKind);
            referenceWidget.containment = this.containment;
            referenceWidget.many = this.many;
            referenceWidget.helpTextProvider = this.helpTextProvider; // Optional on purpose
            referenceWidget.readOnly = this.readOnly;
            referenceWidget.style = this.style; // Optional on purpose
            referenceWidget.ownerId = Objects.requireNonNull(this.ownerId);
            referenceWidget.clearHandler = this.clearHandler; // Optional on purpose
            referenceWidget.setHandler = this.setHandler; // Optional on purpose
            referenceWidget.addHandler = this.addHandler; // Optional on purpose
            referenceWidget.createElementHandler = this.createElementHandler; // Optional on purpose
            referenceWidget.moveHandler = this.moveHandler; // Optional on purpose
            return referenceWidget;
        }
    }

}
