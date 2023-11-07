/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.emf.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.CreateChildCommand.Helper;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IDefaultEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.emf.services.messages.IEMFMessageService;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.emfjson.resource.JsonResourceImpl;
import org.springframework.stereotype.Service;

/**
 * Used to provide support for the default edition of EMF models.
 *
 * @author sbegaudeau
 * @author lfasani
 */
@Service
public class DefaultEditService implements IDefaultEditService {

    private final IEMFKindService emfKindService;

    private final ComposedAdapterFactory composedAdapterFactory;

    private final ISuggestedRootObjectTypesProvider suggestedRootObjectTypesProvider;

    private final IObjectService objectService;

    private final IFeedbackMessageService feedbackMessageService;

    private final IEMFMessageService messageService;

    public DefaultEditService(IEMFKindService emfKindService, ComposedAdapterFactory composedAdapterFactory, ISuggestedRootObjectTypesProvider suggestedRootObjectsProvider, IObjectService objectService, IFeedbackMessageService feedbackMessageService, IEMFMessageService messageService) {
        this.emfKindService = Objects.requireNonNull(emfKindService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.suggestedRootObjectTypesProvider = Objects.requireNonNull(suggestedRootObjectsProvider);
        this.objectService = Objects.requireNonNull(objectService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    private Optional<EClass> getEClass(EPackage.Registry ePackageRegistry, String kind) {
        String ePackageName = this.emfKindService.getEPackageName(kind);
        String eClassName = this.emfKindService.getEClassName(kind);

        return this.emfKindService.findEPackage(ePackageRegistry, ePackageName)
                .map(ePackage -> ePackage.getEClassifier(eClassName))
                .filter(EClass.class::isInstance)
                .map(EClass.class::cast);
    }

    private Optional<EPackage.Registry> getPackageRegistry(IEditingContext editingContext) {
        return Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain)
                .map(EditingDomain::getResourceSet)
                .map(ResourceSet::getPackageRegistry);
    }

    @Override
    public List<ChildCreationDescription> getChildCreationDescriptions(IEditingContext editingContext, String kind, String referenceKind) {
        List<ChildCreationDescription> childCreationDescriptions = new ArrayList<>();

        this.getPackageRegistry(editingContext).ifPresent(ePackageRegistry -> {

            AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(this.composedAdapterFactory, new BasicCommandStack());
            ResourceSet resourceSet = editingDomain.getResourceSet();
            resourceSet.setPackageRegistry(ePackageRegistry);
            Resource resource = new JsonResourceImpl(URI.createURI("inmemory"), Map.of());
            resourceSet.getResources().add(resource);

            var optionalEClass = this.getEClass(ePackageRegistry, kind)
                    .filter(eClass -> !eClass.isAbstract() && !eClass.isInterface());

            Optional<EClass> optionalEClassReference;
            if (referenceKind != null) {
                optionalEClassReference = this.getEClass(ePackageRegistry, referenceKind);
            } else {
                optionalEClassReference = Optional.empty();
            }


            if (optionalEClass.isPresent()) {
                EClass eClass = optionalEClass.get();
                EObject eObject = EcoreUtil.create(eClass);
                resource.getContents().add(eObject);

                Collection<?> newChildDescriptors = editingDomain.getNewChildDescriptors(eObject, null);

                List<CommandParameter> commandParameters = newChildDescriptors.stream()
                        .filter(CommandParameter.class::isInstance)
                        .map(CommandParameter.class::cast)
                        .filter(commandParameter -> optionalEClassReference.map(eClassReference -> eClassReference.isInstance(commandParameter.getValue()))
                                .orElse(true))
                        .toList();

                Adapter adapter = editingDomain.getAdapterFactory().adapt(eObject, IEditingDomainItemProvider.class);

                if (adapter instanceof IEditingDomainItemProvider editingDomainItemProvider) {
                    if (editingDomainItemProvider instanceof Helper helper) {
                        for (CommandParameter commandParameter : commandParameters) {
                            String text = helper.getCreateChildText(eObject, commandParameter.getFeature(), commandParameter.getValue(), null);
                            List<String> iconURL = this.objectService.getImagePath(commandParameter.getValue());
                            ChildCreationDescription childCreationDescription = new ChildCreationDescription(text, text, iconURL);
                            childCreationDescriptions.add(childCreationDescription);
                        }
                    }
                }
            }
        });

        return childCreationDescriptions;
    }

    @Override
    public Optional<Object> createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId) {
        var optionalEditingDomain = Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain);

        Optional<EObject> optionalEObject = Optional.of(object)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);

        if (optionalEditingDomain.isPresent() && optionalEObject.isPresent()) {
            AdapterFactoryEditingDomain editingDomain = optionalEditingDomain.get();
            EObject eObject = optionalEObject.get();

            Collection<?> newChildDescriptors = editingDomain.getNewChildDescriptors(eObject, null);

            List<CommandParameter> commandParameters = newChildDescriptors.stream()
                    .filter(CommandParameter.class::isInstance)
                    .map(CommandParameter.class::cast)
                    .toList();

            Adapter adapter = editingDomain.getAdapterFactory().adapt(eObject, IEditingDomainItemProvider.class);
            if (adapter instanceof IEditingDomainItemProvider editingDomainItemProvider) {
                if (editingDomainItemProvider instanceof Helper helper) {
                    for (CommandParameter commandParameter : commandParameters) {
                        String text = helper.getCreateChildText(eObject, commandParameter.getFeature(), commandParameter.getValue(), null);

                        if (childCreationDescriptionId.equals(text)) {
                            return this.createObject(editingDomain, eObject, commandParameter);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }

    private Optional<Object> createObject(AdapterFactoryEditingDomain editingDomain, EObject eObject, CommandParameter commandParameter) {
        Optional<Object> objectOptional = Optional.empty();

        if (commandParameter.getEStructuralFeature() instanceof EReference ref && ref.isContainment() && !ref.isMany() && eObject.eGet(ref) != null) {
            this.feedbackMessageService
                    .addFeedbackMessage(new Message(this.messageService.upperBoundaryReached(commandParameter.getEValue().eClass()
                            .getName(), commandParameter.getEStructuralFeature().getName()), MessageLevel.WARNING));
            return Optional.empty();
        }

        Command createChildCommand = CreateChildCommand.create(editingDomain, eObject, commandParameter, Collections.singletonList(eObject));
        editingDomain.getCommandStack().execute(createChildCommand);
        Collection<?> result = createChildCommand.getResult();
        if (result.size() == 1) {
            Object child = result.iterator().next();
            if (child instanceof EObject && EcoreUtil.isAncestor(eObject, (EObject) child)) {
                objectOptional = Optional.of(child);
            }
        }
        return objectOptional;
    }

    @Override
    public void delete(Object object) {
        Optional<EObject> optionalEObject = Optional.of(object)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);

        optionalEObject.ifPresent(eObject -> EcoreUtil.deleteAll(Collections.singleton(eObject), true));
    }

    @Override
    public List<ChildCreationDescription> getRootCreationDescriptions(IEditingContext editingContext, String domainId, boolean suggested, String referenceKind) {
        List<ChildCreationDescription> rootObjectCreationDescription = new ArrayList<>();

        this.getPackageRegistry(editingContext).ifPresent(ePackageRegistry -> {

            EPackage ePackage = ePackageRegistry.getEPackage(domainId);
            if (ePackage != null) {
                List<EClass> classes;
                if (suggested) {
                    classes = this.suggestedRootObjectTypesProvider.getSuggestedRootObjectTypes(ePackage);
                    if (classes.isEmpty()) {
                        classes = this.getConcreteClasses(ePackage);
                    }
                } else {
                    classes = this.getConcreteClasses(ePackage);
                }
                for (EClass suggestedClass : classes) {
                    if (referenceKind == null || this.getEClass(ePackageRegistry, referenceKind).map(eClassReference -> eClassReference.isSuperTypeOf(suggestedClass))
                            .orElse(true)) {
                        List<String> iconURL = this.objectService.getImagePath(EcoreUtil.create(suggestedClass));
                        rootObjectCreationDescription.add(new ChildCreationDescription(suggestedClass.getName(), suggestedClass.getName(), iconURL));
                    }
                }
            }
        });
        return rootObjectCreationDescription;
    }

    private List<EClass> getConcreteClasses(EPackage ePackage) {
        return ePackage.getEClassifiers().stream()
                .filter(EClass.class::isInstance)
                .map(EClass.class::cast)
                .filter(eClass -> !eClass.isAbstract() && !eClass.isInterface())
                .toList();
    }

    @Override
    public Optional<Object> createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId) {
        Optional<Object> createdObjectOptional = Optional.empty();

        var optionalEClass = this.getMatchingEClass(editingContext, domainId, rootObjectCreationDescriptionId);

        var optionalEditingDomain = Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain);

        if (optionalEClass.isPresent() && optionalEditingDomain.isPresent()) {
            EClass eClass = optionalEClass.get();
            AdapterFactoryEditingDomain editingDomain = optionalEditingDomain.get();

            var optionalResource = editingDomain.getResourceSet().getResources().stream()
                    .filter(resource -> documentId.toString().equals(resource.getURI().path().substring(1)))
                    .findFirst();

            if (optionalResource.isPresent()) {
                EObject eObject = EcoreUtil.create(eClass);

                var resource = optionalResource.get();
                AddCommand command = new AddCommand(editingDomain, resource.getContents(), eObject);
                editingDomain.getCommandStack().execute(command);

                createdObjectOptional = Optional.of(eObject);
            }
        }
        return createdObjectOptional;
    }

    private Optional<EClass> getMatchingEClass(IEditingContext editingContext, String domainId, String rootObjectCreationDescriptionId) {
        return Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain)
                .map(EditingDomain::getResourceSet)
                .map(ResourceSet::getPackageRegistry)
                .map(packageRegistry -> packageRegistry.getEPackage(domainId))
                .map(ePackage -> ePackage.getEClassifier(rootObjectCreationDescriptionId))
                .filter(EClass.class::isInstance)
                .map(EClass.class::cast)
                .filter(eClass -> !eClass.isAbstract() && !eClass.isInterface());
    }

    @Override
    public void editLabel(Object object, String labelField, String newValue) {
        if (object instanceof EObject) {
            EClass eClass = ((EObject) object).eClass();
            EStructuralFeature eStructuralFeature = eClass.getEStructuralFeature(labelField);
            if (eStructuralFeature instanceof EAttribute
                    && !eStructuralFeature.isMany()
                    && eStructuralFeature.isChangeable()
                    && eStructuralFeature.getEType().getInstanceClass() == String.class) {
                ((EObject) object).eSet(eStructuralFeature, newValue);
            }
        }
    }
}
