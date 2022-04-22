/*******************************************************************************
 * Copyright (c) 2019, 2020, 2022 Obeo.
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
package org.eclipse.sirius.components.forms.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.GroupDisplayMode;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description used to create groups of a page.
 *
 * @author sbegaudeau
 */
@Immutable
public final class GroupDescription {
    private String id;

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, GroupDisplayMode> displayModeProvider;

    private Function<VariableManager, List<?>> semanticElementsProvider;

    private List<AbstractControlDescription> controlDescriptions;

    private GroupDescription() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, GroupDisplayMode> getDisplayModeProvider() {
        return this.displayModeProvider;
    }

    public Function<VariableManager, List<?>> getSemanticElementsProvider() {
        return this.semanticElementsProvider;
    }

    public List<AbstractControlDescription> getControlDescriptions() {
        return this.controlDescriptions;
    }

    public static Builder newGroupDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * The builder used to create the group description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, GroupDisplayMode> displayModeProvider = variableManager -> GroupDisplayMode.LIST;

        private Function<VariableManager, List<?>> semanticElementsProvider;

        private List<AbstractControlDescription> controlDescriptions;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder displayModeProvider(Function<VariableManager, GroupDisplayMode> displayModeProvider) {
            this.displayModeProvider = Objects.requireNonNull(displayModeProvider);
            return this;
        }

        public Builder semanticElementsProvider(Function<VariableManager, List<?>> semanticElementsProvider) {
            this.semanticElementsProvider = Objects.requireNonNull(semanticElementsProvider);
            return this;
        }

        public Builder controlDescriptions(List<AbstractControlDescription> controlDescriptions) {
            this.controlDescriptions = Objects.requireNonNull(controlDescriptions);
            return this;
        }

        public GroupDescription build() {
            GroupDescription groupDescription = new GroupDescription();
            groupDescription.id = Objects.requireNonNull(this.id);
            groupDescription.idProvider = Objects.requireNonNull(this.idProvider);
            groupDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            groupDescription.displayModeProvider = Objects.requireNonNull(this.displayModeProvider);
            groupDescription.semanticElementsProvider = Objects.requireNonNull(this.semanticElementsProvider);
            groupDescription.controlDescriptions = Objects.requireNonNull(this.controlDescriptions);
            return groupDescription;
        }
    }
}
