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

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.diagram.description.tool.CreateView;
import org.eclipse.sirius.diagram.description.tool.Navigation;
import org.eclipse.sirius.viewpoint.description.tool.ChangeContext;
import org.eclipse.sirius.viewpoint.description.tool.CreateInstance;
import org.eclipse.sirius.viewpoint.description.tool.DeleteView;
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
import org.eclipse.sirius.web.compat.api.IIdentifierProvider;
import org.eclipse.sirius.web.compat.api.IModelOperationHandler;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.emf.compatibility.EPackageService;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The switch used to create the proper model operation handler.
 *
 * @author sbegaudeau
 */
public class ModelOperationHandlerSwitch implements Function<ModelOperation, Optional<IModelOperationHandler>> {

    private final Logger logger = LoggerFactory.getLogger(ModelOperationHandlerSwitch.class);

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final IIdentifierProvider identifierProvider;

    private final IObjectService objectService;

    public ModelOperationHandlerSwitch(AQLInterpreter interpreter, IIdentifierProvider identifierProvider, IObjectService objectService) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.objectService = Objects.requireNonNull(objectService);
        this.childModelOperationHandler = new ChildModelOperationHandler();
    }

    @Override
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
        }

        if (optionalModelOperationHandler.isEmpty()) {
            String pattern = "The model operation {0} is not currently supported"; //$NON-NLS-1$
            this.logger.warn(MessageFormat.format(pattern, modelOperation.getClass().getSimpleName()));
        }

        return optionalModelOperationHandler;
    }

    private Optional<IModelOperationHandler> caseChangeContext(ChangeContext changeContextOperation) {
        return Optional.of(new ChangeContextOperationHandler(this.interpreter, this.childModelOperationHandler, changeContextOperation));
    }

    private Optional<IModelOperationHandler> caseCreateInstance(CreateInstance createInstanceOperation) {
        return Optional.of(new CreateInstanceOperationHandler(this.interpreter, new EPackageService(), this.childModelOperationHandler, createInstanceOperation));
    }

    private Optional<IModelOperationHandler> caseCreateView(CreateView createViewOperation) {
        return Optional.of(new CreateViewOperationHandler(this.interpreter, new EPackageService(), this.objectService, this.identifierProvider, this.childModelOperationHandler, createViewOperation));
    }

    private Optional<IModelOperationHandler> caseDeleteView(DeleteView deleteViewOperation) {
        return Optional.empty();
    }

    private Optional<IModelOperationHandler> caseFor(For forOperation) {
        return Optional.of(new ForOperationHandler(this.interpreter, this.childModelOperationHandler, forOperation));
    }

    private Optional<IModelOperationHandler> caseIf(If ifOperation) {
        return Optional.of(new IfOperationHandler(this.interpreter, this.childModelOperationHandler, ifOperation));
    }

    private Optional<IModelOperationHandler> caseLet(Let letOperation) {
        return Optional.of(new LetOperationHandler(this.interpreter, this.childModelOperationHandler, letOperation));
    }

    private Optional<IModelOperationHandler> caseMoveElement(MoveElement moveElementOperation) {
        return Optional.of(new MoveElementOperationHandler(this.interpreter, this.childModelOperationHandler, moveElementOperation));
    }

    private Optional<IModelOperationHandler> caseNavigation(Navigation navigationOperation) {
        return Optional.empty();
    }

    private Optional<IModelOperationHandler> caseRemoveElement(RemoveElement removeElementOperation) {
        return Optional.of(new RemoveElementOperationHandler(this.interpreter, this.childModelOperationHandler, removeElementOperation));
    }

    private Optional<IModelOperationHandler> caseSetObject(SetObject setObjectOperation) {
        return Optional.empty();
    }

    private Optional<IModelOperationHandler> caseSetValue(SetValue setValueOperation) {
        return Optional.of(new SetValueOperationHandler(this.interpreter, this.childModelOperationHandler, setValueOperation));
    }

    private Optional<IModelOperationHandler> caseUnset(Unset unsetOperation) {
        return Optional.of(new UnsetOperationHandler(this.interpreter, this.childModelOperationHandler, unsetOperation));
    }

    private Optional<IModelOperationHandler> caseSwitch(Switch switchOperation) {
        return Optional.of(new SwitchOperationHandler(this.interpreter, this.childModelOperationHandler, switchOperation));
    }

}
