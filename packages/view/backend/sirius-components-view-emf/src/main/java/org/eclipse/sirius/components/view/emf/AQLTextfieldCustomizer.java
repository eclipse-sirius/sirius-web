/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.ICompletionResult;
import org.eclipse.acceleo.query.runtime.QueryCompletion;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.emf.DomainConverter;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.forms.CompletionProposal;
import org.eclipse.sirius.components.forms.CompletionRequest;
import org.eclipse.sirius.components.forms.TextareaStyle;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
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

    /**
     * The pattern used to match the separator used by both Sirius and AQL.
     */
    private static final Pattern SEPARATOR = Pattern.compile("(::?|\\.)");

    private static final String AQL_PREFIX = "aql:";

    private final ApplicationContext applicationContext;

    private final List<IJavaServiceProvider> javaServiceProviders;

    public AQLTextfieldCustomizer(ApplicationContext applicationContext, List<IJavaServiceProvider> javaServiceProviders) {
        this.applicationContext = Objects.requireNonNull(applicationContext);
        this.javaServiceProviders = Objects.requireNonNull(javaServiceProviders);
    }

    @Override
    public boolean handles(EAttribute eAttribute) {
        return eAttribute.getEType() == ViewPackage.Literals.INTERPRETED_EXPRESSION;
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
            EAttribute expressionAttribute = variableManager.get(ViewPropertiesDescriptionRegistryConfigurer.ESTRUCTURAL_FEATURE, EAttribute.class).orElse(null);

            if (cursorPosition < AQL_PREFIX.length() && currentText.startsWith(AQL_PREFIX.substring(0, cursorPosition))) {
                return List.of(new CompletionProposal("AQL prefix", AQL_PREFIX, cursorPosition));
            }

            Map<String, EPackage> visibleEPackages = new HashMap<>();
            Optional<IEditingContext> optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
            for (EPackage ePackage : optionalEditingContext.map(this::getAccessibleEPackages).orElse(List.of())) {
                visibleEPackages.put(ePackage.getName(), ePackage);
            }
            optionalEditingContext.filter(EditingContext.class::isInstance).map(EditingContext.class::cast).ifPresent(editingContext -> {
                for (Resource resource : editingContext.getDomain().getResourceSet().getResources()) {
                    List<Domain> localDomains = resource.getContents().stream().filter(Domain.class::isInstance).map(Domain.class::cast).toList();
                    new DomainConverter().convert(localDomains).forEach(ePackage -> visibleEPackages.put(ePackage.getName(), ePackage));
                }

            });
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
            AQLInterpreter interpreter = this.createInterpreter(view, List.copyOf(visibleEPackages.values()));


            Optional<EClass> domainType = variableManager.get(VariableManager.SELF, EObject.class).flatMap((EObject self) -> {
                if (List.of(ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION, ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION).contains(expressionAttribute)) {
                    // These expressions are evaluated in the context of the parent
                    return this.findDomainType(self.eContainer(), List.copyOf(visibleEPackages.values()));
                } else {
                    // These expressions are evaluated in the context of the instance
                    return this.findDomainType(self, List.copyOf(visibleEPackages.values()));
                }
            });

            ICompletionResult completionResult = interpreter.getProposals(currentText.substring(AQL_PREFIX.length()), cursorPosition - AQL_PREFIX.length(), domainType);
            Set<ICompletionProposal> aqlProposals = new LinkedHashSet<>(completionResult.getProposals(QueryCompletion.createBasicFilter(completionResult)));
            List<ICompletionProposal> proposals = aqlProposals.stream().toList();

            // @formatter:off
            List<CompletionProposal> allProposals = proposals.stream()
                    .filter(proposal -> currentText.substring(AQL_PREFIX.length(), cursorPosition).endsWith(proposal.getProposal().substring(0, completionResult.getReplacementLength())))
                    .sorted(new Comparator<ICompletionProposal>() {
                        @Override
                        public int compare(ICompletionProposal o1, ICompletionProposal o2) {
                            String serviceCallSuffix = "()"; //$NON-NLS-1$
                            int result = 0;
                            if (o1.getProposal().endsWith(serviceCallSuffix) && !o2.getProposal().endsWith(serviceCallSuffix)) {
                                result = 1;
                            } else if (o2.getProposal().endsWith(serviceCallSuffix) && !o1.getProposal().endsWith(serviceCallSuffix)) {
                                result = -1;
                            } else {
                                result = o1.getProposal().compareTo(o2.getProposal());
                            }
                            return result;
                        }
                    }.thenComparing(ICompletionProposal::getProposal))
                    .map(proposal -> {
                        return new CompletionProposal(proposal.getDescription(), proposal.getProposal(), completionResult.getReplacementLength());
                    })
                    .toList();
            return allProposals.stream()
                    // Only keep the first proposal for a given textToInsert
                    .collect(Collectors.toMap(CompletionProposal::getTextToInsert, Function.identity(), (p1, p2) -> p1, LinkedHashMap::new)).values().stream()
                    .toList();
            // @formatter:on
        };
    }

    private Optional<EClass> findDomainType(EObject self, List<EPackage> ePackages) {
        EObject current = self;
        while (current != null) {
            Optional<EClass> candidate = this.getLocalDomainType(current, ePackages);
            if (candidate.isPresent()) {
                return candidate;
            }
            current = current.eContainer();
        }
        return Optional.empty();
    }

    private Optional<EClass> getLocalDomainType(EObject current, List<EPackage> ePackages) {
        EClass currentType = current.eClass();
        EStructuralFeature domainTypeFeature = currentType.getEStructuralFeature("domainType"); //$NON-NLS-1$
        if (domainTypeFeature instanceof EAttribute && ((EAttribute) domainTypeFeature).getEAttributeType() == ViewPackage.Literals.DOMAIN_TYPE) {
            String domainTypeValue = (String) current.eGet(domainTypeFeature);
            if (domainTypeValue != null && !domainTypeValue.isBlank()) {
                Matcher matcher = SEPARATOR.matcher(domainTypeValue);
                if (matcher.find()) {
                    String packageName = domainTypeValue.substring(0, matcher.start());
                    String className = domainTypeValue.substring(matcher.end());
                    // @formatter:off
                    Optional<EPackage> optionalPackage = ePackages.stream()
                            .filter(ePackage -> Objects.equals(packageName, ePackage.getName()))
                            .findFirst();
                    return optionalPackage.flatMap(ePackage -> Optional.ofNullable(ePackage.getEClassifier(className)))
                            .filter(EClass.class::isInstance)
                            .map(EClass.class::cast);
                    // @formatter:on
                }
            }
        }
        return Optional.empty();
    }

    private List<EPackage> getAccessibleEPackages(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext) {
            Registry packageRegistry = ((EditingContext) editingContext).getDomain().getResourceSet().getPackageRegistry();
            // @formatter:off
            return packageRegistry.values().stream()
                    .filter(EPackage.class::isInstance)
                    .map(EPackage.class::cast)
                    .toList();
            // @formatter:on
        } else {
            return List.of();
        }
    }

    private AQLInterpreter createInterpreter(View view, List<EPackage> visibleEPackages) {
        AutowireCapableBeanFactory beanFactory = this.applicationContext.getAutowireCapableBeanFactory();
        // @formatter:off
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
        // @formatter:on
        return new AQLInterpreter(List.of(), serviceInstances, visibleEPackages);
    }

}
