/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.emf.compatibility.modeloperations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.ecore.extender.business.api.accessor.EcoreMetamodelDescriptor;
import org.eclipse.sirius.ecore.extender.business.internal.accessor.ecore.EcoreIntrinsicExtender;
import org.eclipse.sirius.viewpoint.description.tool.CreateInstance;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.web.compat.api.IIdentifierProvider;
import org.eclipse.sirius.web.compat.api.IModelOperationHandler;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.emf.compatibility.EPackageService;
import org.eclipse.sirius.web.emf.services.EditingContext;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.VariableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the create instance model operation.
 *
 * @author sbegaudeau
 */
public class CreateInstanceOperationHandler implements IModelOperationHandler {

    /**
     * The pattern used to match the separator used by both Sirius and AQL.
     */
    private static final Pattern SEPARATOR = Pattern.compile("(::?|\\.)"); //$NON-NLS-1$

    private final Logger logger = LoggerFactory.getLogger(CreateInstanceOperationHandler.class);

    private final IObjectService objectService;

    private final IIdentifierProvider identifierProvider;

    private final AQLInterpreter interpreter;

    private final EPackageService ePackageService;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final CreateInstance createInstance;

    public CreateInstanceOperationHandler(IObjectService objectService, IIdentifierProvider identifierProvider, AQLInterpreter interpreter, EPackageService ePackageService,
            ChildModelOperationHandler childModelOperationHandler, CreateInstance createInstance) {
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.ePackageService = Objects.requireNonNull(ePackageService);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.createInstance = Objects.requireNonNull(createInstance);
    }

    @Override
    public IStatus handle(Map<String, Object> variables) {
        String typeName = this.createInstance.getTypeName();
        String referenceName = this.createInstance.getReferenceName();
        String variableName = this.createInstance.getVariableName();

        Map<String, Object> childVariables = new HashMap<>(variables);
        if (typeName != null && !typeName.isBlank() && referenceName != null && !referenceName.isBlank()) {

            // @formatter:off
            Optional<EObject> optionalOwnerEObject = Optional.ofNullable(variables.get(VariableManager.SELF))
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast);

            Optional<EditingDomain> optionalEditingDomain = Optional.of(variables.get(IEditingContext.EDITING_CONTEXT))
                    .filter(EditingContext.class::isInstance)
                    .map(EditingContext.class::cast)
                    .map(EditingContext::getDomain);
            // @formatter:on

            Matcher matcher = SEPARATOR.matcher(typeName);
            if (optionalEditingDomain.isPresent() && optionalOwnerEObject.isPresent() && matcher.find()) {
                EditingDomain editingDomain = optionalEditingDomain.get();
                EObject ownerEObject = optionalOwnerEObject.get();

                String packageName = typeName.substring(0, matcher.start());
                String className = typeName.substring(matcher.end());

                Optional<EPackage> optionalEPackage = this.ePackageService.findEPackage(editingDomain.getResourceSet().getPackageRegistry(), packageName);

                EcoreIntrinsicExtender ecoreIntrinsicExtender = new EcoreIntrinsicExtender();
                if (optionalEPackage.isPresent()) {
                    EPackage ePackage = optionalEPackage.get();
                    ecoreIntrinsicExtender.updateMetamodels(Collections.singleton(new EcoreMetamodelDescriptor(ePackage)));
                }

                EObject createdInstance = ecoreIntrinsicExtender.createInstance(className);
                if (createdInstance == null) {
                    this.logger.warn("The creation of an instance of Type {} failed.", typeName); //$NON-NLS-1$
                } else {
                    Object addedObject = ecoreIntrinsicExtender.eAdd(ownerEObject, referenceName, createdInstance);
                    if (addedObject == null) {
                        this.logger.warn("The feature {} does not exist on {}.", referenceName, typeName); //$NON-NLS-1$
                    } else if (variableName != null && !variableName.isBlank()) {
                        childVariables.put(variableName, createdInstance);
                    }
                    // Sub-operations are executed in the context of the new element
                    childVariables.put(VariableManager.SELF, createdInstance);
                }
            }
        }

        List<ModelOperation> subModelOperations = this.createInstance.getSubModelOperations();
        return this.childModelOperationHandler.handle(this.objectService, this.identifierProvider, this.interpreter, childVariables, subModelOperations);
    }
}
