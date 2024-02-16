/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.gantt;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.gantt.GanttPackage
 * @generated
 */
public interface GanttFactory extends EFactory {
    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    GanttFactory eINSTANCE = org.eclipse.sirius.components.view.gantt.impl.GanttFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Description</em>'.
     * @generated
     */
    GanttDescription createGanttDescription();

    /**
     * Returns a new object of class '<em>Task Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Task Description</em>'.
     * @generated
     */
    TaskDescription createTaskDescription();

    /**
     * Returns a new object of class '<em>Conditional Task Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Task Style</em>'.
     * @generated
     */
    ConditionalTaskStyle createConditionalTaskStyle();

    /**
     * Returns a new object of class '<em>Create Task Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Create Task Tool</em>'.
     * @generated
     */
    CreateTaskTool createCreateTaskTool();

    /**
     * Returns a new object of class '<em>Edit Task Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Edit Task Tool</em>'.
     * @generated
     */
    EditTaskTool createEditTaskTool();

    /**
     * Returns a new object of class '<em>Delete Task Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Delete Task Tool</em>'.
     * @generated
     */
    DeleteTaskTool createDeleteTaskTool();

    /**
     * Returns a new object of class '<em>Drop Task Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Drop Task Tool</em>'.
     * @generated
     */
    DropTaskTool createDropTaskTool();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    GanttPackage getGanttPackage();

} // GanttFactory
