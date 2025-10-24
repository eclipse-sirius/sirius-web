/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.ICompletionResult;
import org.eclipse.acceleo.query.runtime.QueryCompletion;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.forms.CompletionProposal;
import org.eclipse.sirius.components.forms.CompletionRequest;
import org.eclipse.sirius.components.forms.TextareaStyle;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.NodeLabelStyle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Provides specific styling and behavior for text fields which represent AQL expressions.
 *
 * @author pcdavid
 */
@Component
public class AQLTextfieldCustomizer implements ITextfieldCustomizer {

    /**
     * The background color used to visually distinguish AQL expressions.
     */
    private static final String BACKGROUND_COLOR = "#fff8e5";

    private static final TextareaStyle STYLE = TextareaStyle.newTextareaStyle().backgroundColor(BACKGROUND_COLOR).build();

    private static final String AQL_PREFIX = "aql:";

    private final ApplicationContext applicationContext;

    private final List<IJavaServiceProvider> javaServiceProviders;

    public AQLTextfieldCustomizer(ApplicationContext applicationContext, List<IJavaServiceProvider> javaServiceProviders) {
        this.applicationContext = Objects.requireNonNull(applicationContext);
        this.javaServiceProviders = Objects.requireNonNull(javaServiceProviders);
    }

    @Override
    public boolean handles(EAttribute eAttribute, EObject eObject) {
        return eAttribute.getEType() == ViewPackage.Literals.INTERPRETED_EXPRESSION && !(eAttribute.getName().equals("maxWidthExpression") && eObject instanceof NodeLabelStyle);
    }

    @Override
    public Function<VariableManager, TextareaStyle> getStyleProvider() {
        return variableManager -> STYLE;
    }

    @Override
    public Function<VariableManager, List<CompletionProposal>> getCompletionProposalsProvider() {
        return variableManager -> {
            String currentText = variableManager.get(CompletionRequest.CURRENT_TEXT, String.class).orElse("");
            int cursorPosition = variableManager.get(CompletionRequest.CURSOR_POSITION, Integer.class).orElse(0);

            if (cursorPosition < AQL_PREFIX.length() && currentText.startsWith(AQL_PREFIX.substring(0, cursorPosition))) {
                return List.of(new CompletionProposal("AQL prefix", AQL_PREFIX, cursorPosition));
            }

            List<EPackage> visibleEPackages = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).map(this::getAccessibleEPackages).orElse(List.of());
            View view = variableManager.get(VariableManager.SELF, EObject.class).map(self -> {
                EObject current = self;
                while (current != null) {
                    if (current instanceof View) {
                        return (View) current;
                    } else {
                        current = current.eContainer();
                    }
                }
                return null;
            }).orElse(null);
            AQLInterpreter interpreter = this.createInterpreter(view, visibleEPackages);

            ICompletionResult completionResult = interpreter.getProposals(currentText.substring(AQL_PREFIX.length()), cursorPosition - AQL_PREFIX.length());
            Set<ICompletionProposal> aqlProposals = new LinkedHashSet<>(completionResult.getProposals(QueryCompletion.createBasicFilter(completionResult)));
            List<ICompletionProposal> proposals = aqlProposals.stream().toList();

            List<CompletionProposal> allProposals = proposals.stream()
                    .sorted(Comparator.comparing(ICompletionProposal::getProposal))
                    .map(proposal -> {
                        return new CompletionProposal(proposal.getDescription(), proposal.getProposal(), completionResult.getReplacementLength());
                    })
                    .toList();
            return allProposals.stream()
                    // Only keep the first proposal for a given textToInsert
                    .collect(Collectors.toMap(CompletionProposal::getTextToInsert, Function.identity(), (p1, p2) -> p1, LinkedHashMap::new)).values().stream()
                    .toList();
        };
    }

    @Override
    public Function<VariableManager, String> getHelpTextProvider() {
        return null;
    }

    private List<EPackage> getAccessibleEPackages(IEditingContext editingContext) {
        if (editingContext instanceof IEMFEditingContext) {
            Registry packageRegistry = ((IEMFEditingContext) editingContext).getDomain().getResourceSet().getPackageRegistry();
            return packageRegistry.values().stream()
                    .filter(EPackage.class::isInstance)
                    .map(EPackage.class::cast)
                    .toList();
        } else {
            return List.of();
        }
    }

    private AQLInterpreter createInterpreter(View view, List<EPackage> visibleEPackages) {
        AutowireCapableBeanFactory beanFactory = this.applicationContext.getAutowireCapableBeanFactory();
        List<Object> serviceInstances = this.javaServiceProviders.stream()
                .flatMap(provider -> provider.getServiceClasses(view).stream())
                .map(serviceClass -> {
                    try {
                        return beanFactory.createBean(serviceClass);
                    } catch (BeansException beansException) {
                        //this.logger.warn("Error while trying to instantiate Java service class " + serviceClass.getName(), beansException);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(Object.class::cast)
                .toList();
        return new AQLInterpreter(List.of(), serviceInstances, visibleEPackages);
    }

}
