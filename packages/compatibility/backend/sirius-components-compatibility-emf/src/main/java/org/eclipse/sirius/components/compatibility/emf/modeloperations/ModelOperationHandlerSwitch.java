/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo and others.
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
package org.eclipse.sirius.components.compatibility.emf.modeloperations;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandler;
import org.eclipse.sirius.components.compatibility.emf.EPackageService;
import org.eclipse.sirius.components.compatibility.emf.api.IExternalJavaActionProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.diagram.description.tool.CreateView;
import org.eclipse.sirius.diagram.description.tool.Navigation;
import org.eclipse.sirius.viewpoint.description.tool.ChangeContext;
import org.eclipse.sirius.viewpoint.description.tool.CreateInstance;
import org.eclipse.sirius.viewpoint.description.tool.DeleteView;
import org.eclipse.sirius.viewpoint.description.tool.ExternalJavaAction;
import org.eclipse.sirius.viewpoint.description.tool.For;
import org.eclipse.sirius.viewpoint.description.tool.If;
import org.eclipse.sirius.viewpoint.description.tool.Let;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.viewpoint.description.tool.MoveElement;
import org.eclipse.sirius.viewpoint.description.tool.RemoveElement;
import org.eclipse.sirius.viewpoint.description.tool.SetObject;
import org.eclipse.sirius.viewpoint.description.tool.SetValue;
import org.eclipse.sirius.viewpoint.description.tool.Switch;
import org.eclipse.sirius.viewpoint.description.tool.Unset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The switch used to create the proper model operation handler.
 *
 * @author sbegaudeau
 */
public class ModelOperationHandlerSwitch implements Function<ModelOperation, Optional<IModelOperationHandler>> {

    private final Logger logger = LoggerFactory.getLogger(ModelOperationHandlerSwitch.class);

    private final IObjectService objectService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IIdentifierProvider identifierProvider;

    private final List<IExternalJavaActionProvider> externalJavaActionProviders;

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    public ModelOperationHandlerSwitch(IObjectService objectService, IRepresentationMetadataSearchService representationMetadataSearchService, IIdentifierProvider identifierProvider,
            List<IExternalJavaActionProvider> externalJavaActionProviders, AQLInterpreter interpreter) {
        this.objectService = Objects.requireNonNull(objectService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.externalJavaActionProviders = Objects.requireNonNull(externalJavaActionProviders);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = new ChildModelOperationHandler(this.externalJavaActionProviders);
    }

    @Override
    @SuppressWarnings("checkstyle:JavaNCSS")
    public Optional<IModelOperationHandler> apply(ModelOperation modelOperation) {
        Optional<IModelOperationHandler> optionalModelOperationHandler = Optional.empty();
        if (modelOperation instanceof ChangeContext) {
            optionalModelOperationHandler = this.caseChangeContext((ChangeContext) modelOperation);
        } else if (modelOperation instanceof CreateInstance) {
            optionalModelOperationHandler = this.caseCreateInstance((CreateInstance) modelOperation);
        } else if (modelOperation instanceof CreateView) {
            optionalModelOperationHandler = this.caseCreateView((CreateView) modelOperation);
        } else if (modelOperation instanceof DeleteView) {
            optionalModelOperationHandler = this.caseDeleteView((DeleteView) modelOperation);
        } else if (modelOperation instanceof For) {
            optionalModelOperationHandler = this.caseFor((For) modelOperation);
        } else if (modelOperation instanceof If) {
            optionalModelOperationHandler = this.caseIf((If) modelOperation);
        } else if (modelOperation instanceof Let) {
            optionalModelOperationHandler = this.caseLet((Let) modelOperation);
        } else if (modelOperation instanceof MoveElement) {
            optionalModelOperationHandler = this.caseMoveElement((MoveElement) modelOperation);
        } else if (modelOperation instanceof Navigation) {
            optionalModelOperationHandler = this.caseNavigation((Navigation) modelOperation);
        } else if (modelOperation instanceof RemoveElement) {
            optionalModelOperationHandler = this.caseRemoveElement((RemoveElement) modelOperation);
        } else if (modelOperation instanceof SetObject) {
            optionalModelOperationHandler = this.caseSetObject((SetObject) modelOperation);
        } else if (modelOperation instanceof SetValue) {
            optionalModelOperationHandler = this.caseSetValue((SetValue) modelOperation);
        } else if (modelOperation instanceof Unset) {
            optionalModelOperationHandler = this.caseUnset((Unset) modelOperation);
        } else if (modelOperation instanceof Switch) {
            optionalModelOperationHandler = this.caseSwitch((Switch) modelOperation);
        } else if (modelOperation instanceof ExternalJavaAction) {
            optionalModelOperationHandler = this.caseExternalJavaAction((ExternalJavaAction) modelOperation);
        } else if (modelOperation instanceof Navigation) {
            optionalModelOperationHandler = this.caseNavigation((Navigation) modelOperation);
        }

        if (optionalModelOperationHandler.isEmpty()) {
            String pattern = "The model operation {} is not currently supported"; //$NON-NLS-1$
            this.logger.warn(pattern, modelOperation.getClass().getSimpleName());
        }

        return optionalModelOperationHandler;
    }

    private Optional<IModelOperationHandler> caseChangeContext(ChangeContext changeContextOperation) {
        return Optional.of(new ChangeContextOperationHandler(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, this.childModelOperationHandler,
                changeContextOperation));
    }

    private Optional<IModelOperationHandler> caseCreateInstance(CreateInstance createInstanceOperation) {
        return Optional.of(new CreateInstanceOperationHandler(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, new EPackageService(),
                this.childModelOperationHandler, createInstanceOperation));
    }

    private Optional<IModelOperationHandler> caseCreateView(CreateView createViewOperation) {
        return Optional.of(new CreateViewOperationHandler(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, this.childModelOperationHandler,
                createViewOperation));
    }

    private Optional<IModelOperationHandler> caseDeleteView(DeleteView deleteViewOperation) {
        return Optional.of(new DeleteViewOperationHandler(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, this.childModelOperationHandler,
                deleteViewOperation));
    }

    private Optional<IModelOperationHandler> caseFor(For forOperation) {
        return Optional
                .of(new ForOperationHandler(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, this.childModelOperationHandler, forOperation));
    }

    private Optional<IModelOperationHandler> caseIf(If ifOperation) {
        return Optional
                .of(new IfOperationHandler(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, this.childModelOperationHandler, ifOperation));
    }

    private Optional<IModelOperationHandler> caseLet(Let letOperation) {
        return Optional
                .of(new LetOperationHandler(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, this.childModelOperationHandler, letOperation));
    }

    private Optional<IModelOperationHandler> caseMoveElement(MoveElement moveElementOperation) {
        return Optional.of(new MoveElementOperationHandler(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, this.childModelOperationHandler,
                moveElementOperation));
    }

    private Optional<IModelOperationHandler> caseNavigation(Navigation navigationOperation) {
        return Optional.of(new NavigationOperationHandler(this.objectService, this.identifierProvider, this.representationMetadataSearchService, this.interpreter, navigationOperation));
    }

    private Optional<IModelOperationHandler> caseRemoveElement(RemoveElement removeElementOperation) {
        return Optional.of(new RemoveElementOperationHandler(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, this.childModelOperationHandler,
                removeElementOperation));
    }

    private Optional<IModelOperationHandler> caseSetObject(SetObject setObjectOperation) {
        return Optional.empty();
    }

    private Optional<IModelOperationHandler> caseSetValue(SetValue setValueOperation) {
        return Optional.of(new SetValueOperationHandler(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, this.childModelOperationHandler,
                setValueOperation));
    }

    private Optional<IModelOperationHandler> caseUnset(Unset unsetOperation) {
        return Optional.of(
                new UnsetOperationHandler(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, this.childModelOperationHandler, unsetOperation));
    }

    private Optional<IModelOperationHandler> caseSwitch(Switch switchOperation) {
        return Optional.of(
                new SwitchOperationHandler(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, this.childModelOperationHandler, switchOperation));
    }

    private Optional<IModelOperationHandler> caseExternalJavaAction(ExternalJavaAction externalJavaAction) {
        return Optional.of(new ExternalJavaActionOperationHandler(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter,
                this.childModelOperationHandler, this.externalJavaActionProviders, externalJavaAction));
    }

}
