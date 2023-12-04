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
package org.eclipse.sirius.components.portals.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.portals.PortalView;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Description of a Portal representation.
 *
 * @author pcdavid
 */
@Immutable
public final class PortalDescription implements IRepresentationDescription {
    private String id;

    private String label;

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Predicate<VariableManager> canCreatePredicate;

    private Function<VariableManager, List<PortalView>> viewsProvider;

    private PortalDescription() {
        // Prevent instantiation
    }

    public static Builder newPortalDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public Predicate<VariableManager> getCanCreatePredicate() {
        return this.canCreatePredicate;
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    public Function<VariableManager, List<PortalView>> getViewsProvider() {
        return this.viewsProvider;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * Builder used to create the portal description.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private Predicate<VariableManager> canCreatePredicate;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, List<PortalView>> viewsProvider;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder canCreatePredicate(Predicate<VariableManager> canCreatePredicate) {
            this.canCreatePredicate = Objects.requireNonNull(canCreatePredicate);
            return this;
        }

        public Builder viewsProvider(Function<VariableManager, List<PortalView>> viewsProvider) {
            this.viewsProvider = Objects.requireNonNull(viewsProvider);
            return this;
        }

        public PortalDescription build() {
            PortalDescription portalDescripion = new PortalDescription();
            portalDescripion.id = Objects.requireNonNull(this.id);
            portalDescripion.label = Objects.requireNonNull(this.label);
            portalDescripion.idProvider = Objects.requireNonNull(this.idProvider);
            portalDescripion.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            portalDescripion.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            portalDescripion.viewsProvider = Objects.requireNonNull(this.viewsProvider);
            portalDescripion.labelProvider = Objects.requireNonNull(this.labelProvider);
            return portalDescripion;
        }
    }
}
