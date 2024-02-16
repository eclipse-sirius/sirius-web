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
package org.eclipse.sirius.components.view.gantt.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.sirius.components.view.gantt.ConditionalTaskStyle;
import org.eclipse.sirius.components.view.gantt.CreateTaskTool;
import org.eclipse.sirius.components.view.gantt.DeleteTaskTool;
import org.eclipse.sirius.components.view.gantt.DropTaskTool;
import org.eclipse.sirius.components.view.gantt.EditTaskTool;
import org.eclipse.sirius.components.view.gantt.GanttDescription;
import org.eclipse.sirius.components.view.gantt.GanttFactory;
import org.eclipse.sirius.components.view.gantt.GanttPackage;
import org.eclipse.sirius.components.view.gantt.TaskDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class GanttFactoryImpl extends EFactoryImpl implements GanttFactory {
    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static GanttFactory init() {
        try {
            GanttFactory theGanttFactory = (GanttFactory) EPackage.Registry.INSTANCE.getEFactory(GanttPackage.eNS_URI);
            if (theGanttFactory != null) {
                return theGanttFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new GanttFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public GanttFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case GanttPackage.GANTT_DESCRIPTION:
                return this.createGanttDescription();
            case GanttPackage.TASK_DESCRIPTION:
                return this.createTaskDescription();
            case GanttPackage.CONDITIONAL_TASK_STYLE:
                return this.createConditionalTaskStyle();
            case GanttPackage.CREATE_TASK_TOOL:
                return this.createCreateTaskTool();
            case GanttPackage.EDIT_TASK_TOOL:
                return this.createEditTaskTool();
            case GanttPackage.DELETE_TASK_TOOL:
                return this.createDeleteTaskTool();
            case GanttPackage.DROP_TASK_TOOL:
                return this.createDropTaskTool();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public GanttDescription createGanttDescription() {
        GanttDescriptionImpl ganttDescription = new GanttDescriptionImpl();
        return ganttDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TaskDescription createTaskDescription() {
        TaskDescriptionImpl taskDescription = new TaskDescriptionImpl();
        return taskDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalTaskStyle createConditionalTaskStyle() {
        ConditionalTaskStyleImpl conditionalTaskStyle = new ConditionalTaskStyleImpl();
        return conditionalTaskStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CreateTaskTool createCreateTaskTool() {
        CreateTaskToolImpl createTaskTool = new CreateTaskToolImpl();
        return createTaskTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EditTaskTool createEditTaskTool() {
        EditTaskToolImpl editTaskTool = new EditTaskToolImpl();
        return editTaskTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeleteTaskTool createDeleteTaskTool() {
        DeleteTaskToolImpl deleteTaskTool = new DeleteTaskToolImpl();
        return deleteTaskTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DropTaskTool createDropTaskTool() {
        DropTaskToolImpl dropTaskTool = new DropTaskToolImpl();
        return dropTaskTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public GanttPackage getGanttPackage() {
        return (GanttPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static GanttPackage getPackage() {
        return GanttPackage.eINSTANCE;
    }

} // GanttFactoryImpl
