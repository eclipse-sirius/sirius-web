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

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Reference widget description. Works for both single-valued and multi-valued references (the actual Widget
 * implementation created will be different in the two cases).
 *
 * @author pcdavid
 */
@Immutable
public final class ReferenceWidgetDescription extends AbstractWidgetDescription {

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, String> iconURLProvider;

    private Function<VariableManager, List<?>> itemsProvider;

    private Function<VariableManager, List<?>> optionsProvider;

    private Function<VariableManager, String> itemIdProvider;

    private Function<VariableManager, String> itemLabelProvider;

    private Function<VariableManager, String> itemKindProvider;

    private Function<VariableManager, String> itemImageURLProvider;

    private Function<VariableManager, Setting> settingProvider;

    private Function<VariableManager, IStatus> itemClickHandlerProvider;

    private Function<VariableManager, ReferenceWidgetStyle> styleProvider;

    private Function<VariableManager, String> ownerIdProvider;

    private Function<VariableManager, IStatus> clearHandlerProvider;

    private Function<VariableManager, IStatus> itemRemoveHandlerProvider;

    private Function<VariableManager, IStatus> setHandlerProvider;

    private ReferenceWidgetDescription() {
        // Prevent instantiation
    }

    public static Builder newReferenceWidgetDescription(String id) {
        return new Builder(id);
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, String> getIconURLProvider() {
        return this.iconURLProvider;
    }

    public Function<VariableManager, List<?>> getItemsProvider() {
        return this.itemsProvider;
    }

    public Function<VariableManager, List<?>> getOptionsProvider() {
        return this.optionsProvider;
    }

    public Function<VariableManager, String> getItemIdProvider() {
        return this.itemIdProvider;
    }

    public Function<VariableManager, String> getItemLabelProvider() {
        return this.itemLabelProvider;
    }

    public Function<VariableManager, String> getItemKindProvider() {
        return this.itemKindProvider;
    }

    public Function<VariableManager, String> getItemImageURLProvider() {
        return this.itemImageURLProvider;
    }

    public Function<VariableManager, Setting> getSettingProvider() {
        return this.settingProvider;
    }

    public Function<VariableManager, IStatus> getItemClickHandlerProvider() {
        return this.itemClickHandlerProvider;
    }

    public Function<VariableManager, ReferenceWidgetStyle> getStyleProvider() {
        return this.styleProvider;
    }

    public Function<VariableManager, String> getOwnerIdProvider() {
        return this.ownerIdProvider;
    }

    public Function<VariableManager, IStatus> getClearHandlerProvider() {
        return this.clearHandlerProvider;
    }

    public Function<VariableManager, IStatus> getItemRemoveHandlerProvider() {
        return this.itemRemoveHandlerProvider;
    }

    public Function<VariableManager, IStatus> getSetHandlerProvider() {
        return this.setHandlerProvider;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the ReferenceWidgetDescription.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, String> iconURLProvider = variableManager -> null;

        private Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> false;

        private Function<VariableManager, List<?>> itemsProvider;

        private Function<VariableManager, List<?>> optionsProvider;

        private Function<VariableManager, String> itemIdProvider;

        private Function<VariableManager, String> itemLabelProvider;

        private Function<VariableManager, String> itemKindProvider;

        private Function<VariableManager, String> itemImageURLProvider;

        private Function<VariableManager, Setting> settingProvider;

        private Function<VariableManager, String> helpTextProvider;

        private Function<VariableManager, IStatus> itemClickHandlerProvider;

        private Function<VariableManager, ReferenceWidgetStyle> styleProvider;

        private Function<VariableManager, String> ownerIdProvider;

        private Function<VariableManager, IStatus> clearHandlerProvider;

        private Function<VariableManager, IStatus> itemRemoveHandlerProvider;

        private Function<VariableManager, IStatus> setHandlerProvider;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder iconURLProvider(Function<VariableManager, String> iconURLProvider) {
            this.iconURLProvider = Objects.requireNonNull(iconURLProvider);
            return this;
        }

        public Builder isReadOnlyProvider(Function<VariableManager, Boolean> isReadOnlyProvider) {
            this.isReadOnlyProvider = Objects.requireNonNull(isReadOnlyProvider);
            return this;
        }

        public Builder itemsProvider(Function<VariableManager, List<?>> itemsProvider) {
            this.itemsProvider = Objects.requireNonNull(itemsProvider);
            return this;
        }

        public Builder optionsProvider(Function<VariableManager, List<?>> optionsProvider) {
            this.optionsProvider = Objects.requireNonNull(optionsProvider);
            return this;
        }

        public Builder itemIdProvider(Function<VariableManager, String> itemIdProvider) {
            this.itemIdProvider = Objects.requireNonNull(itemIdProvider);
            return this;
        }

        public Builder itemLabelProvider(Function<VariableManager, String> itemLabelProvider) {
            this.itemLabelProvider = Objects.requireNonNull(itemLabelProvider);
            return this;
        }

        public Builder itemKindProvider(Function<VariableManager, String> itemKindProvider) {
            this.itemKindProvider = Objects.requireNonNull(itemKindProvider);
            return this;
        }

        public Builder itemImageURLProvider(Function<VariableManager, String> itemImageURLProvider) {
            this.itemImageURLProvider = Objects.requireNonNull(itemImageURLProvider);
            return this;
        }

        public Builder settingProvider(Function<VariableManager, Setting> settingProvider) {
            this.settingProvider = Objects.requireNonNull(settingProvider);
            return this;
        }

        public Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Builder itemClickHandlerProvider(Function<VariableManager, IStatus> itemClickHandlerProvider) {
            this.itemClickHandlerProvider = Objects.requireNonNull(itemClickHandlerProvider);
            return this;
        }

        public Builder styleProvider(Function<VariableManager, ReferenceWidgetStyle> styleProvider) {
            this.styleProvider = Objects.requireNonNull(styleProvider);
            return this;
        }

        public Builder ownerIdProvider(Function<VariableManager, String> ownerIdProvider) {
            this.ownerIdProvider = Objects.requireNonNull(ownerIdProvider);
            return this;
        }

        public Builder clearHandlerProvider(Function<VariableManager, IStatus> clearHandlerProvider) {
            this.clearHandlerProvider = Objects.requireNonNull(clearHandlerProvider);
            return this;
        }

        public Builder itemRemoveHandlerProvider(Function<VariableManager, IStatus> itemRemoveHandlerProvider) {
            this.itemRemoveHandlerProvider = Objects.requireNonNull(itemRemoveHandlerProvider);
            return this;
        }

        public Builder setHandlerProvider(Function<VariableManager, IStatus> setHandlerProvider) {
            this.setHandlerProvider = Objects.requireNonNull(setHandlerProvider);
            return this;
        }

        public ReferenceWidgetDescription build() {
            ReferenceWidgetDescription referenceWidgetDescription = new ReferenceWidgetDescription();
            referenceWidgetDescription.id = Objects.requireNonNull(this.id);
            referenceWidgetDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            referenceWidgetDescription.idProvider = Objects.requireNonNull(this.idProvider);
            referenceWidgetDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            referenceWidgetDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            referenceWidgetDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            referenceWidgetDescription.itemsProvider = Objects.requireNonNull(this.itemsProvider);
            referenceWidgetDescription.optionsProvider = Objects.requireNonNull(this.optionsProvider);
            referenceWidgetDescription.itemIdProvider = Objects.requireNonNull(this.itemIdProvider);
            referenceWidgetDescription.itemLabelProvider = Objects.requireNonNull(this.itemLabelProvider);
            referenceWidgetDescription.itemKindProvider = Objects.requireNonNull(this.itemKindProvider);
            referenceWidgetDescription.itemImageURLProvider = Objects.requireNonNull(this.itemImageURLProvider);
            referenceWidgetDescription.settingProvider = Objects.requireNonNull(this.settingProvider);
            referenceWidgetDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            referenceWidgetDescription.itemClickHandlerProvider = this.itemClickHandlerProvider; // Optional on purpose
            referenceWidgetDescription.styleProvider = Objects.requireNonNull(this.styleProvider);
            referenceWidgetDescription.ownerIdProvider = Objects.requireNonNull(this.ownerIdProvider);
            referenceWidgetDescription.clearHandlerProvider = Objects.requireNonNull(this.clearHandlerProvider);
            referenceWidgetDescription.itemRemoveHandlerProvider = this.itemRemoveHandlerProvider; // Optional on purpose
            referenceWidgetDescription.setHandlerProvider = this.setHandlerProvider; // Optional on purpose
            return referenceWidgetDescription;
        }
    }
}
