/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

package org.eclipse.sirius.web.services.gantt;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.interpreter.SimpleCrossReferenceProvider;
import org.eclipse.sirius.components.papaya.DependencyLink;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.StartOrEnd;
import org.eclipse.sirius.components.papaya.Task;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;

/**
 * Java Service for the Papaya's task related views.
 *
 * @author ncouvert
 */
public class PapayaGanttJavaService {

    private final SimpleCrossReferenceProvider simpleCrossReferenceProvider = new SimpleCrossReferenceProvider();

    private final IFeedbackMessageService feedbackMessageService;

    public PapayaGanttJavaService(IFeedbackMessageService feedbackMessageService) {
        this.feedbackMessageService = feedbackMessageService;
    }

    public void deleteTask(EObject context) {
        if (context instanceof Task sourceTask) {
            deleteTasksRecursive(sourceTask);
            EcoreUtil.delete(sourceTask, true);
        }
    }

    private void deleteTasksRecursive(Task task) {

        Collection<EStructuralFeature.Setting> inverseReferences = simpleCrossReferenceProvider.getInverseReferences(task);
        for (EStructuralFeature.Setting inverseReference : inverseReferences) {
            if (inverseReference.getEObject() instanceof DependencyLink dep) {
                EcoreUtil.delete(dep, true);
            }
        }
        for (Task subTask : task.getTasks()) {
            this.deleteTasksRecursive(subTask);
        }
    }

    public void deleteDependencyLink(EObject target, EObject source) {
        if (target instanceof Task targetTask) {
            if (source instanceof Task sourceTask) {
                targetTask.getDependencies().removeIf(dep -> dep.getSource().equals(sourceTask));
            }
        }
    }


    public void createDependencyLink(EObject target, EObject source, org.eclipse.sirius.components.gantt.StartOrEnd sourceStartOrEnd, org.eclipse.sirius.components.gantt.StartOrEnd targetStartOrEnd) {
        DependencyLink dependencyLink = PapayaFactory.eINSTANCE.createDependencyLink();
        if (sourceStartOrEnd.equals(org.eclipse.sirius.components.gantt.StartOrEnd.END)) {
            dependencyLink.setSourceKind(StartOrEnd.END);
        } else {
            dependencyLink.setSourceKind(StartOrEnd.START);
        }
        if (targetStartOrEnd.equals(org.eclipse.sirius.components.gantt.StartOrEnd.START)) {
            dependencyLink.setTargetKind(StartOrEnd.START);
        } else {
            dependencyLink.setTargetKind(StartOrEnd.END);
        }
        if (source instanceof Task sourceTask) {
            dependencyLink.setSource(sourceTask);
            if (target instanceof Task targetTask) {
                targetTask.getDependencies().add(dependencyLink);
            } else {
                this.feedbackMessageService.addFeedbackMessage(new Message("Wrong creation", MessageLevel.ERROR));
            }
        }
    }
}
