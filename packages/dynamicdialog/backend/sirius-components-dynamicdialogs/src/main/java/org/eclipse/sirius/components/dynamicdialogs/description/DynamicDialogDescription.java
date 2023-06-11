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
package org.eclipse.sirius.components.dynamicdialogs.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.annotations.PublicApi;
import org.eclipse.sirius.components.core.api.IDynamicDialogDescription;
import org.eclipse.sirius.components.dynamicdialogs.DynamicDialogValidationMessage;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The root concept of the description of a dynamic dialog.
 *
 * @author lfasani
 */
@PublicApi
@Immutable
public final class DynamicDialogDescription implements IDynamicDialogDescription {
    private String id;

    private Function<VariableManager, IStatus> applyDialogProvider;

    private Function<VariableManager, List<DynamicDialogValidationMessage>> validationMessagesProvider;

    private Function<VariableManager, String> titleProvider;

    private Function<VariableManager, String> descriptionProvider;

    private List<AbstractDWidgetDescription> widgetDescriptions;

    private DynamicDialogDescription() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    public Function<VariableManager, String> getTitleProvider() {
        return this.titleProvider;
    }

    public Function<VariableManager, String> getDescriptionProvider() {
        return this.descriptionProvider;
    }

    public List<AbstractDWidgetDescription> getWidgetDescriptions() {
        return this.widgetDescriptions;
    }

    public Function<VariableManager, IStatus> getApplyDialogProvider() {
        return this.applyDialogProvider;
    }

    public Function<VariableManager, List<DynamicDialogValidationMessage>> getValidationMessageProvider() {
        return this.validationMessagesProvider;
    }

    public static Builder newDynamicDialogDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * Builder used to create the dynamic dialog description.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private Function<VariableManager, String> titleProvider;

        private Function<VariableManager, String> descriptionProvider;

        private List<AbstractDWidgetDescription> widgetDescriptions;

        private Function<VariableManager, IStatus> applyDialogProvider;

        private Function<VariableManager, List<DynamicDialogValidationMessage>> validationMessagesProvider;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder titleProvider(Function<VariableManager, String> titleProvider) {
            this.titleProvider = Objects.requireNonNull(titleProvider);
            return this;
        }

        public Builder descriptionProvider(Function<VariableManager, String> descriptionProvider) {
            this.descriptionProvider = Objects.requireNonNull(descriptionProvider);
            return this;
        }

        public Builder dynamicWidgetDescriptions(List<AbstractDWidgetDescription> dynamicWidgetDescriptions) {
            this.widgetDescriptions = Objects.requireNonNull(dynamicWidgetDescriptions);
            return this;
        }

        public Builder applyDialogProvider(Function<VariableManager, IStatus> applyDialogProvider) {
            this.applyDialogProvider = Objects.requireNonNull(applyDialogProvider);
            return this;
        }

        public Builder validationMessagesProvider(Function<VariableManager, List<DynamicDialogValidationMessage>> validationMessagesProvider) {
            this.validationMessagesProvider = Objects.requireNonNull(validationMessagesProvider);
            return this;
        }

        public DynamicDialogDescription build() {
            DynamicDialogDescription dynamicDialogDescription = new DynamicDialogDescription();
            dynamicDialogDescription.id = Objects.requireNonNull(this.id);
            dynamicDialogDescription.titleProvider = Objects.requireNonNull(this.titleProvider);
            dynamicDialogDescription.descriptionProvider = Objects.requireNonNull(this.descriptionProvider);
            dynamicDialogDescription.widgetDescriptions = Objects.requireNonNull(this.widgetDescriptions);
            dynamicDialogDescription.applyDialogProvider = Objects.requireNonNull(this.applyDialogProvider);
            dynamicDialogDescription.validationMessagesProvider = Objects.requireNonNull(this.validationMessagesProvider);
            return dynamicDialogDescription;
        }
    }

}
