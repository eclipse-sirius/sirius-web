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
package org.eclipse.sirius.web.application.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.web.papaya.representations.table.CursorBasedNavigationServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Used to test the cursor based navigation services.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class CursorBasedNavigationServicesTests {

    @Test
    @DisplayName("Given an EMF model, when we ask to navigate in the model forward, then we receive the next EObjects")
    public void givenAnEMFModelWhenWeAskToNavigateInTheModelForwardThenWeReceiveTheNextEObjects() {
        var self = PapayaPackage.eINSTANCE;
        var cursor = PapayaPackage.eINSTANCE.getProject();
        var paginatedData = new CursorBasedNavigationServices().collect(self, cursor, "NEXT", 20);

        assertThat(paginatedData).isNotNull();

        var names = paginatedData.rows().stream()
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .map(eObject -> {
                    var adapter = new EcoreItemProviderAdapterFactory().adapt(eObject, IItemLabelProvider.class);
                    if (adapter instanceof IItemLabelProvider itemLabelProvider) {
                        return itemLabelProvider.getText(eObject);
                    }
                    return "";
                })
                .toList();

        var expectedNames = List.of(
                "projects : Project",
                "Project",
                "components : Component",
                "Component",
                "allComponents : Component",
                "Component",
                "componentExchanges : ComponentExchange",
                "ComponentExchange",
                "iterations : Iteration",
                "Iteration",
                "tasks : Task",
                "Task",
                "contributions : Contribution",
                "Contribution",
                "NamedElement",
                "Iteration -> NamedElement [org.eclipse.sirius.components.papaya.Iteration]",
                "startDate : Instant",
                "Instant",
                "endDate : Instant",
                "Instant"
        );

        assertThat(names)
                .isNotEmpty()
                .containsSequence(expectedNames)
                .hasSize(expectedNames.size());

        assertThat(paginatedData.hasPreviousPage()).isTrue();
        assertThat(paginatedData.hasNextPage()).isTrue();
    }

    @Test
    @DisplayName("Given an EMF model, when we ask to navigate in the model forward with a predicate, then we receive the next EObjects filtered")
    public void givenAnEMFModelWhenWeAskToNavigateInTheModelForwardWithPredicateThenWeReceiveTheNextEObjectsFiltered() {
        var self = PapayaPackage.eINSTANCE;
        var cursor = PapayaPackage.eINSTANCE.getProject();
        Predicate<EObject> predicate = eObject -> {
            var adapter = new EcoreItemProviderAdapterFactory().adapt(eObject, IItemLabelProvider.class);
            if (adapter instanceof IItemLabelProvider itemLabelProvider) {
                return itemLabelProvider.getText(eObject).toLowerCase().contains("task");
            }
            return false;
        };
        var paginatedData = new CursorBasedNavigationServices().collect(self, cursor, "NEXT", 20, predicate);

        assertThat(paginatedData).isNotNull();

        var names = paginatedData.rows().stream()
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .map(eObject -> {
                    var adapter = new EcoreItemProviderAdapterFactory().adapt(eObject, IItemLabelProvider.class);
                    if (adapter instanceof IItemLabelProvider itemLabelProvider) {
                        return itemLabelProvider.getText(eObject);
                    }
                    return "";
                })
                .toList();

        var expectedNames = List.of(
                "tasks : Task",
                "Task",
                "tasks : Task",
                "Task",
                "Task -> NamedElement [org.eclipse.sirius.components.papaya.Task]",
                "tasks : Task",
                "Task",
                "dependencies : Task",
                "Task",
                "relatedTasks : Task",
                "Task"
        );

        assertThat(names)
                .isNotEmpty()
                .containsSequence(expectedNames)
                .hasSize(expectedNames.size());

        assertThat(paginatedData.hasPreviousPage()).isTrue();
        assertThat(paginatedData.hasNextPage()).isFalse();
    }

    @Test
    @DisplayName("Given an EMF model, when we ask to navigate in the model backward, then we receive the previous EObjects")
    public void givenAnEMFModelWhenWeAskToNavigateInTheModelBackwardThenWeReceiveThePreviousEObjects() {
        var self = PapayaPackage.eINSTANCE;
        var cursor = PapayaPackage.eINSTANCE.getProject();
        var paginatedData = new CursorBasedNavigationServices().collect(self, cursor, "PREV", 20);

        assertThat(paginatedData).isNotNull();
        assertThat(paginatedData.hasPreviousPage()).isFalse();
        assertThat(paginatedData.hasNextPage()).isTrue();

        var names = paginatedData.rows().stream()
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .map(eObject -> {
                    var adapter = new EcoreItemProviderAdapterFactory().adapt(eObject, IItemLabelProvider.class);
                    if (adapter instanceof IItemLabelProvider itemLabelProvider) {
                        return itemLabelProvider.getText(eObject);
                    }
                    return "";
                })
                .toList();

        var expectedNames = List.of(
                "papaya",
                "ModelElement [org.eclipse.sirius.components.papaya.ModelElement]",
                "tags : Tag",
                "Tag",
                "Tag [org.eclipse.sirius.components.papaya.Tag]",
                "key : EString",
                "EString",
                "value : EString",
                "EString",
                "NamedElement -> ModelElement [org.eclipse.sirius.components.papaya.NamedElement]",
                "name : EString",
                "EString",
                "description : EString",
                "EString",
                "ModelElement"
        );

        assertThat(names)
                .isNotEmpty()
                .containsSequence(expectedNames)
                .hasSize(expectedNames.size());
    }

    @Test
    @DisplayName("Given an EMF model, when we ask to navigate in the model backward with predicate, then we receive the previous EObjects filtered")
    public void givenAnEMFModelWhenWeAskToNavigateInTheModelBackwardWithPredicateThenWeReceiveThePreviousEObjectsFiltered() {
        var self = PapayaPackage.eINSTANCE;
        var cursor = PapayaPackage.eINSTANCE.getProject();
        Predicate<EObject> predicate = eObject -> {
            var adapter = new EcoreItemProviderAdapterFactory().adapt(eObject, IItemLabelProvider.class);
            if (adapter instanceof IItemLabelProvider itemLabelProvider) {
                return itemLabelProvider.getText(eObject).toLowerCase().contains("tag");
            }
            return false;
        };
        var paginatedData = new CursorBasedNavigationServices().collect(self, cursor, "PREV", 20, predicate);

        assertThat(paginatedData).isNotNull();
        assertThat(paginatedData.hasPreviousPage()).isFalse();
        assertThat(paginatedData.hasNextPage()).isTrue();

        var names = paginatedData.rows().stream()
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .map(eObject -> {
                    var adapter = new EcoreItemProviderAdapterFactory().adapt(eObject, IItemLabelProvider.class);
                    if (adapter instanceof IItemLabelProvider itemLabelProvider) {
                        return itemLabelProvider.getText(eObject);
                    }
                    return "";
                })
                .toList();

        var expectedNames = List.of(
                "tags : Tag", "Tag", "Tag [org.eclipse.sirius.components.papaya.Tag]"
        );

        assertThat(names)
                .isNotEmpty()
                .containsSequence(expectedNames)
                .hasSize(expectedNames.size());
    }
}
