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
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.widget.reference.dto.MoveReferenceValueHandlerParameters;

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

    private List<String> iconURL;

    private Supplier<String> helpTextProvider;

    private boolean readOnly;

    private List<ReferenceValue> values;

    private Supplier<List<ReferenceValue>> optionsProvider;

    private String ownerKind;

    private String referenceKind;

    private boolean containment;

    private String descriptionId;

    private boolean many;

    private ReferenceWidgetStyle style;

    private String ownerId;

    private List<Element> children;

    private Supplier<IStatus> clearHandler;

    private Function<Object, IStatus> setHandler;

    private Function<List<?>, IStatus> addHandler;

    private Function<MoveReferenceValueHandlerParameters, IStatus> moveHandler;

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

    public List<String> getIconURL() {
        return this.iconURL;
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

    public Supplier<List<ReferenceValue>> getOptionsProvider() {
        return this.optionsProvider;
    }

    public String getOwnerKind() {
        return this.ownerKind;
    }

    public String getReferenceKind() {
        return this.referenceKind;
    }

    public boolean isContainment() {
        return this.containment;
    }

    public boolean isMany() {
        return this.many;
    }

    public String getDescriptionId() {
        return this.descriptionId;
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

    public Function<MoveReferenceValueHandlerParameters, IStatus> getMoveHandler() {
        return this.moveHandler;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
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

        private List<String> iconURL;
        private boolean readOnly;
        private Supplier<String> helpTextProvider;

        private List<ReferenceValue> values;

        private Supplier<List<ReferenceValue>> optionsProvider;

        private String ownerKind;

        private String referenceKind;

        private boolean containment;

        private boolean many;

        private ReferenceWidgetStyle style;

        private String ownerId;

        private List<Element> children;

        private Supplier<IStatus> clearHandler;

        private Function<Object, IStatus> setHandler;

        private Function<List<?>, IStatus> addHandler;

        private Function<MoveReferenceValueHandlerParameters, IStatus> moveHandler;

        private String descriptionId;

        private Builder(String id) {
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

        public Builder values(List<ReferenceValue> values) {
            this.values = Objects.requireNonNull(values);
            return this;
        }

        public Builder optionsProvider(Supplier<List<ReferenceValue>> optionsProvider) {
            this.optionsProvider = Objects.requireNonNull(optionsProvider);
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

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
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

        public Builder moveHandler(Function<MoveReferenceValueHandlerParameters, IStatus> moveHandler) {
            this.moveHandler = Objects.requireNonNull(moveHandler);
            return this;
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public ReferenceElementProps build() {
            ReferenceElementProps referenceElementProps = new ReferenceElementProps();
            referenceElementProps.id = Objects.requireNonNull(this.id);
            referenceElementProps.descriptionId = Objects.requireNonNull(this.descriptionId);
            referenceElementProps.label = Objects.requireNonNull(this.label);
            referenceElementProps.iconURL = this.iconURL;
            referenceElementProps.readOnly = this.readOnly;
            referenceElementProps.values = Objects.requireNonNull(this.values);
            referenceElementProps.optionsProvider = Objects.requireNonNull(this.optionsProvider);
            referenceElementProps.ownerKind = Objects.requireNonNull(this.ownerKind);
            referenceElementProps.referenceKind = Objects.requireNonNull(this.referenceKind);
            referenceElementProps.containment = this.containment;
            referenceElementProps.many = this.many;
            referenceElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            referenceElementProps.style = this.style; // Optional on purpose
            referenceElementProps.ownerId = Objects.requireNonNull(this.ownerId);
            referenceElementProps.children = Objects.requireNonNull(this.children);
            referenceElementProps.clearHandler = this.clearHandler; // Optional on purpose
            referenceElementProps.setHandler = this.setHandler; // Optional on purpose
            referenceElementProps.addHandler = this.addHandler; // Optional on purpose
            referenceElementProps.moveHandler = this.moveHandler;  // Optional on purpose
            return referenceElementProps;
        }
    }
}
