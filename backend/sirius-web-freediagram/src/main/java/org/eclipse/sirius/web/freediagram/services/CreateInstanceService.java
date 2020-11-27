/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.freediagram.services;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.web.freediagram.behaviors.ICreateInstanceBehavior;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;

/**
 * Create instance service.
 *
 * @author hmarchadour
 */
public class CreateInstanceService {

    private final ICreateInstanceBehavior createInstanceBehavior;

    public CreateInstanceService(ICreateInstanceBehavior createInstanceBehavior) {
        this.createInstanceBehavior = Objects.requireNonNull(createInstanceBehavior);
    }

    public Optional<Object> createInstance(VariableManager variableManager) {
        Optional<Object> result = Optional.empty();
     // @formatter:off
        Optional<EditingDomain> optionalEditingDomain = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
            .map(IEditingContext::getDomain)
            .filter(EditingDomain.class::isInstance)
            .map(EditingDomain.class::cast);
        // @formatter:on
        if (optionalEditingDomain.isPresent()) {
            EditingDomain editingDomain = optionalEditingDomain.get();
            Optional<EObject> optionalNewInstance = this.createInstanceBehavior.createEObject(variableManager);
            if (optionalNewInstance.isPresent()) {
                EObject newInstance = optionalNewInstance.get();
                Optional<EObject> optionalOwner = this.createInstanceBehavior.getOwner(variableManager);
                if (optionalOwner.isPresent()) {
                    VariableManager childVariableManager = variableManager.createChild();
                    childVariableManager.put(VariableManager.SELF, optionalOwner.get());
                    Optional<EReference> optionalContainmentFeature = this.createInstanceBehavior.getContainmentFeature(childVariableManager);
                    if (optionalContainmentFeature.isPresent()) {
                        EObject owner = optionalOwner.get();
                        EReference containmentFeature = optionalContainmentFeature.get();
                        result = this.createChildInstance(editingDomain, owner, containmentFeature, newInstance);
                    } else {
                        result = this.createRootInstance(editingDomain, optionalOwner.map(EObject::eResource), newInstance);
                    }
                } else {
                    result = this.createRootInstance(editingDomain, variableManager.get(VariableManager.SELF, EObject.class).map(EObject::eResource), newInstance);
                }
            }
        }
        return result;
    }

    private Optional<Object> createChildInstance(EditingDomain editingDomain, EObject owner, EReference containmentFeature, EObject newInstance) {
        Optional<Object> result = Optional.empty();
        CommandParameter commandParameter = new CommandParameter(owner, containmentFeature, newInstance);
        Command command = CreateChildCommand.create(editingDomain, owner, commandParameter, Collections.singletonList(owner));
        editingDomain.getCommandStack().execute(command);
        Collection<?> commandResult = command.getResult();
        if (!commandResult.isEmpty()) {
            result = Optional.of(commandResult.iterator().next());
        }
        return result;
    }

    private Optional<Object> createRootInstance(EditingDomain editingDomain, Optional<Resource> owner, EObject newInstance) {
        Optional<Object> result = Optional.empty();
        if (owner.isPresent()) {
            Resource eResourceOwner = owner.get();
            AddCommand command = new AddCommand(editingDomain, eResourceOwner.getContents(), newInstance);
            editingDomain.getCommandStack().execute(command);
            Collection<?> commandResult = command.getResult();
            if (!commandResult.isEmpty()) {
                result = Optional.of(commandResult.iterator().next());
            }
        }
        return result;
    }
}
