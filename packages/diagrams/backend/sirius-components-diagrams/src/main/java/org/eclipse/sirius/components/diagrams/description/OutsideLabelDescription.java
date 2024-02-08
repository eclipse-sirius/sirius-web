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
package org.eclipse.sirius.components.diagrams.description;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.OutsideLabelLocation;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The outside label description.
 *
 * @author frouene
 */
@Immutable
public final class OutsideLabelDescription {

    /**
     * The name of the variable passed to an outside label id provider so that the outside label's own id can include the id of it's
     * owner/parent diagram element.
     */
    public static final String OWNER_ID = "ownerId";

    /**
     * The suffix used to build an outside label's id given its owner's.
     */
    public static final String OUTSIDE_LABEL_SUFFIX = "_outsideLabel";

    private String id;

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> textProvider;

    private Function<VariableManager, LabelStyleDescription> styleDescriptionProvider;

    private OutsideLabelLocation outsideLabelLocation;

    private OutsideLabelDescription() {
        // Prevent instantiation
    }

    public static Builder newOutsideLabelDescription(String id) {
        return new Builder(id);
    }

    public String getId() {
        return this.id;
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getTextProvider() {
        return this.textProvider;
    }

    public Function<VariableManager, LabelStyleDescription> getStyleDescriptionProvider() {
        return this.styleDescriptionProvider;
    }

    public OutsideLabelLocation getOutsideLabelLocation() {
        return this.outsideLabelLocation;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * The builder used to create a new outside label description.
     *
     * @author frouene
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> textProvider;

        private Function<VariableManager, LabelStyleDescription> styleDescriptionProvider;

        private OutsideLabelLocation outsideLabelLocation;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder textProvider(Function<VariableManager, String> textProvider) {
            this.textProvider = Objects.requireNonNull(textProvider);
            return this;
        }

        public Builder styleDescriptionProvider(Function<VariableManager, LabelStyleDescription> styleDescriptionProvider) {
            this.styleDescriptionProvider = Objects.requireNonNull(styleDescriptionProvider);
            return this;
        }

        public Builder outsideLabelLocation(OutsideLabelLocation outsideLabelLocation) {
            this.outsideLabelLocation = Objects.requireNonNull(outsideLabelLocation);
            return this;
        }

        public OutsideLabelDescription build() {
            OutsideLabelDescription labelDescription = new OutsideLabelDescription();
            labelDescription.id = Objects.requireNonNull(this.id);
            labelDescription.idProvider = Objects.requireNonNull(this.idProvider);
            labelDescription.textProvider = Objects.requireNonNull(this.textProvider);
            labelDescription.styleDescriptionProvider = Objects.requireNonNull(this.styleDescriptionProvider);
            labelDescription.outsideLabelLocation = Objects.requireNonNull(this.outsideLabelLocation);
            return labelDescription;
        }
    }
}
