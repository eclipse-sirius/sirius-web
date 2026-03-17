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

import pepper.peppermm.DependencyLink;
import pepper.peppermm.PepperFactory;
import pepper.peppermm.StartOrEnd;
import pepper.peppermm.Task;

import org.eclipse.emf.ecore.EObject;

/**
 * Java Service for the task related views, for tests.
 *
 * @author ncouvert
 */
public class PepperJavaService {

    public void deleteDependencyLink(EObject target, EObject source) {
        if (target instanceof Task targetTask) {
            if (source instanceof Task sourceTask) {
                targetTask.getDependencies().removeIf(dep -> (dep.getDependency() instanceof Task dependency) && dependency.equals(sourceTask));
            }
        }
    }


    public void createDependencyLink(EObject target, EObject source, org.eclipse.sirius.components.gantt.StartOrEnd sourceStartOrEnd, org.eclipse.sirius.components.gantt.StartOrEnd targetStartOrEnd) {
        DependencyLink dependencyLink = PepperFactory.eINSTANCE.createDependencyLink();
        if (sourceStartOrEnd.equals(org.eclipse.sirius.components.gantt.StartOrEnd.END)) {
            dependencyLink.setSource(StartOrEnd.END);
        } else {
            dependencyLink.setSource(StartOrEnd.START);
        }
        if (targetStartOrEnd.equals(org.eclipse.sirius.components.gantt.StartOrEnd.START)) {
            dependencyLink.setTarget(StartOrEnd.START);
        } else {
            dependencyLink.setTarget(StartOrEnd.END);
        }
        if (source instanceof Task sourceTask) {
            dependencyLink.setDependency(sourceTask);
            if (target instanceof Task targetTask) {
                //check that the dependency already exists to avoid duplication
                boolean isPresent = false;
                for (DependencyLink dep : targetTask.getDependencies()) {
                    if (dep.getDependency() instanceof Task dependency) {
                        if (dependency.equals(sourceTask)) {
                            isPresent = true;
                        }
                    }
                }
                if (!isPresent) {
                    targetTask.getDependencies().add(dependencyLink);
                }
            }
        }
    }

}
