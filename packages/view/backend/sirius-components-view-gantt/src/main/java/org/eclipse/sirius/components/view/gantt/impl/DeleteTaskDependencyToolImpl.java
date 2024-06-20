/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.view.gantt.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.sirius.components.view.gantt.DeleteTaskDependencyTool;
import org.eclipse.sirius.components.view.gantt.GanttPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Delete Task Dependency Tool</b></em>'. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class DeleteTaskDependencyToolImpl extends TaskToolImpl implements DeleteTaskDependencyTool {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DeleteTaskDependencyToolImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return GanttPackage.Literals.DELETE_TASK_DEPENDENCY_TOOL;
    }

} // DeleteTaskDependencyToolImpl
