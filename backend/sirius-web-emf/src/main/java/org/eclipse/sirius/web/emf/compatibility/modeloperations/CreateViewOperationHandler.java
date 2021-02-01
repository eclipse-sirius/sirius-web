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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.diagram.description.tool.CreateView;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.web.compat.api.IModelOperationHandler;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.IDiagramContext;
import org.eclipse.sirius.web.diagrams.ViewCreationRequest;
import org.eclipse.sirius.web.emf.compatibility.EPackageService;
import org.eclipse.sirius.web.emf.services.IDAdapter;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.persistence.entities.IdMappingEntity;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the create instance model operation.
 *
 * @author sbegaudeau
 */
public class CreateViewOperationHandler implements IModelOperationHandler {

    /**
     * The pattern used to match the separator used by both Sirius and AQL.
     */
    private static final Pattern SEPARATOR = Pattern.compile("(::?|\\.)"); //$NON-NLS-1$

    private static final String ID_SEPARATOR = "#"; //$NON-NLS-1$

    private final Logger logger = LoggerFactory.getLogger(CreateViewOperationHandler.class);

    private final AQLInterpreter interpreter;

    private final EPackageService ePackageService;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final CreateView createView;

    public CreateViewOperationHandler(AQLInterpreter interpreter, EPackageService ePackageService, ChildModelOperationHandler childModelOperationHandler, CreateView createView) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.ePackageService = Objects.requireNonNull(ePackageService);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.createView = Objects.requireNonNull(createView);
    }

    @Override
    public Status handle(Map<String, Object> variables) {

        Map<String, Object> childVariables = new HashMap<>(variables);

        // @formatter:off
        Optional<String> optionalSelfId = Optional.ofNullable(variables.get(VariableManager.SELF))
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .map(this::getId);

        Optional<IDiagramContext> optionalDiagramContext = Optional.of(variables.get(IDiagramContext.DIAGRAM_CONTEXT))
                .filter(IDiagramContext.class::isInstance)
                .map(IDiagramContext.class::cast);
        // @formatter:on

        if (optionalSelfId.isPresent() && optionalDiagramContext.isPresent()) {
            IDiagramContext diagramContext = optionalDiagramContext.get();
            Diagram diagram = diagramContext.getDiagram();
            UUID parentElementId = diagram.getId();
            UUID descriptionId = UUID.fromString(this.identifierProvider.getIdentifier(this.createView.getMapping()));
            Optional<Object> optionalObject = this.interpreter.evaluateExpression(variables, this.createView.getContainerViewExpression()).asObject();
            if (optionalObject.isPresent()) {
                // TODO
            }
            // @formatter:off
            ViewCreationRequest viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                    .descriptionId(descriptionId)
                    .parentElementId(parentElementId)
                    .targetObjectId(optionalSelfId.get())
                    .build();
            // @formatter:on
            diagramContext.getViewCreationRequests().add(viewCreationRequest);
        }

        List<ModelOperation> subModelOperations = this.createView.getSubModelOperations();
        return this.childModelOperationHandler.handle(this.interpreter, childVariables, subModelOperations);
    }

    public String getIdentifier(Object element) {
        // @formatter:off
        String vsmElementId = Optional.of(element).filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .map(EcoreUtil::getURI)
                .map(Object::toString)
                .orElse(""); //$NON-NLS-1$

        Optional<IdMappingEntity> optional = this.getOrFetchByExternalId(vsmElementId);
        return optional.orElseGet(() -> this.newIdMapping(vsmElementId))
               .getId()
               .toString();
        // @formatter:on
    }

    private Optional<IdMappingEntity> getOrFetchByExternalId(String vsmElementId) {
        try {
            Callable<? extends IdMappingEntity> loader = () -> {
                return this.repository.findByExternalId(vsmElementId).orElseThrow(() -> this.loadingError(vsmElementId));
            };
            IdMappingEntity idMapping = this.idMappingByExternalId.get(vsmElementId, loader);
            return Optional.of(this.cached(idMapping));
        } catch (ExecutionException e) {
            // Do not log: it is expected not to find a mapping in the repo the first time.
            return Optional.empty();
        }
    }

    public String getId(Object object) {
        String id = null;
        if (object instanceof EObject) {
            EObject eObject = (EObject) object;

            id = this.getIdFromIDAdapter(eObject);
            if (id == null) {
                id = this.getIdFromURIFragment(eObject);
            }
        }
        return id;

    }

    private String getIdFromIDAdapter(EObject eObject) {
        // @formatter:off
        return eObject.eAdapters().stream()
                .filter(IDAdapter.class::isInstance)
                .map(IDAdapter.class::cast)
                .findFirst()
                .map(IDAdapter::getId)
                .map(Object::toString)
                .orElse(null);
        // @formatter:on
    }

    private String getIdFromURIFragment(EObject eObject) {
        Resource resource = eObject.eResource();
        String id = null;
        if (resource != null && resource.getURI() != null) {
            ResourceSet resourceSet = resource.getResourceSet();
            if (resourceSet != null && resourceSet.getResources().contains(resource)) {
                id = resource.getURI().lastSegment() + ID_SEPARATOR + resource.getURIFragment(eObject);
            } else {
                // In order to getObject method can retrieve the object from the id, we need to return the full URI for
                // resources that are in the PackageRegistry
                id = resource.getURI() + ID_SEPARATOR + resource.getURIFragment(eObject);
            }
        }
        return id;
    }

}
