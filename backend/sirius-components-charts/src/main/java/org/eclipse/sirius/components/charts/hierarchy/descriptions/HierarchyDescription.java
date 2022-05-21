/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.charts.hierarchy.descriptions;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.charts.descriptions.IChartDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Description of the hierarchy representation.
 *
 * @author sbegaudeau
 */
public class HierarchyDescription implements IChartDescription {
    public static final String LABEL = "label"; //$NON-NLS-1$

    private String id;

    private String label;

    private String kind;

    private Predicate<VariableManager> canCreatePredicate;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<Object>> childSemanticElementsProvider;

    public HierarchyDescription(String id, String label, String kind, Predicate<VariableManager> canCreatePredicate, Function<VariableManager, String> targetObjectIdProvider,
            Function<VariableManager, String> labelProvider, Function<VariableManager, List<Object>> childSemanticElementsProvider) {
        this.id = Objects.requireNonNull(id);
        this.label = Objects.requireNonNull(label);
        this.kind = Objects.requireNonNull(kind);
        this.canCreatePredicate = Objects.requireNonNull(canCreatePredicate);
        this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
        this.labelProvider = Objects.requireNonNull(labelProvider);
        this.childSemanticElementsProvider = Objects.requireNonNull(childSemanticElementsProvider);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public String getKind() {
        return this.kind;
    }

    @Override
    public Predicate<VariableManager> getCanCreatePredicate() {
        return this.canCreatePredicate;
    }

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, List<Object>> getChildSemanticElementsProvider() {
        return this.childSemanticElementsProvider;
    }
}
