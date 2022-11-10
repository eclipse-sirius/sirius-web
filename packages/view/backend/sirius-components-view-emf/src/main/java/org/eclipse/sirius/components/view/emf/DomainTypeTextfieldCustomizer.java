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
package org.eclipse.sirius.components.view.emf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.forms.CompletionProposal;
import org.eclipse.sirius.components.forms.CompletionRequest;
import org.eclipse.sirius.components.forms.TextareaStyle;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.ViewPackage;
import org.springframework.stereotype.Component;

/**
 * Provides specific styling and behavior for text fields which represent domain types.
 *
 * @author pcdavid
 */
@Component
public class DomainTypeTextfieldCustomizer implements ITextfieldCustomizer {
    /**
     * The background color used to visually distinguish domain types.
     */
    private static final String BACKGROUND_COLOR = "#e6f4ee"; //$NON-NLS-1$

    private static final TextareaStyle STYLE = TextareaStyle.newTextareaStyle().backgroundColor(BACKGROUND_COLOR).build();

    @Override
    public boolean handles(EAttribute eAttribute) {
        return eAttribute.getEType() == ViewPackage.Literals.DOMAIN_TYPE;
    }

    @Override
    public Function<VariableManager, TextareaStyle> getStyleProvider() {
        return variableManager -> STYLE;
    }

    @Override
    public Function<VariableManager, List<CompletionProposal>> getCompletionProposalsProvider() {
        return (VariableManager variableManager) -> {
            String currentText = variableManager.get(CompletionRequest.CURRENT_TEXT, String.class).orElse(""); //$NON-NLS-1$
            int cursorPosition = variableManager.get(CompletionRequest.CURSOR_POSITION, Integer.class).orElse(0);

            List<String> choices = List.of();
            Optional<EditingContext> optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, EditingContext.class);
            if (optionalEditingContext.isPresent()) {
                ResourceSet resourceSet = optionalEditingContext.get().getDomain().getResourceSet();
                List<EPackage> ePackages = this.getEPackagesFromRegistry(resourceSet.getPackageRegistry());
                // @formatter:off
                choices = ePackages.stream().flatMap(ePackage -> {
                    return ePackage.getEClassifiers().stream()
                            .filter(EClass.class::isInstance)
                            .map(eClassifier -> String.format("%s::%s", ePackage.getName(), eClassifier.getName())) //$NON-NLS-1$
                            .distinct();
                }).distinct().sorted().collect(Collectors.toList());
                // @formatter:on
            }
            String prefix = currentText.substring(0, cursorPosition);
            // @formatter:off
            return choices.stream()
                    .filter(choice -> choice.startsWith(prefix))
                    .map(choice -> new CompletionProposal("Choice " + choice, choice, prefix.length())) //$NON-NLS-1$
                    .collect(Collectors.toList());
            // @formatter:on
        };
    }

    private List<EPackage> getEPackagesFromRegistry(EPackage.Registry ePackageRegistry) {
        List<EPackage> allEPackage = new ArrayList<>();

        // @formatter:off
        List<EPackage> ePackages = ePackageRegistry.entrySet().stream()
                .map(Entry::getValue)
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .collect(Collectors.toList());
        allEPackage.addAll(ePackages);

        ePackages.stream()
                .map(this::getSubPackages)
                .forEach(allEPackage::addAll);
        // @formatter:on
        return allEPackage;
    }

    private List<EPackage> getSubPackages(EPackage ePackage) {
        List<EPackage> allEPackage = new ArrayList<>();

        EList<EPackage> eSubpackages = ePackage.getESubpackages();
        allEPackage.addAll(eSubpackages);

        // @formatter:off
        eSubpackages.stream()
            .map(this::getSubPackages)
            .forEach(allEPackage::addAll);
        // @formatter:on

        return allEPackage;
    }

}
