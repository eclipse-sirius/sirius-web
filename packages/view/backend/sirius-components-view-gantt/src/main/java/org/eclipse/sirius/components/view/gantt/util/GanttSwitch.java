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
package org.eclipse.sirius.components.view.gantt.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.gantt.ConditionalTaskStyle;
import org.eclipse.sirius.components.view.gantt.CreateTaskTool;
import org.eclipse.sirius.components.view.gantt.DeleteTaskTool;
import org.eclipse.sirius.components.view.gantt.DropTaskTool;
import org.eclipse.sirius.components.view.gantt.EditTaskTool;
import org.eclipse.sirius.components.view.gantt.GanttDescription;
import org.eclipse.sirius.components.view.gantt.GanttPackage;
import org.eclipse.sirius.components.view.gantt.TaskDescription;
import org.eclipse.sirius.components.view.gantt.TaskStyleDescription;
import org.eclipse.sirius.components.view.gantt.TaskTool;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.gantt.GanttPackage
 * @generated
 */
public class GanttSwitch<T> extends Switch<T> {
    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static GanttPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public GanttSwitch() {
        if (modelPackage == null) {
            modelPackage = GanttPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param ePackage
     *            the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
     * result. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case GanttPackage.GANTT_DESCRIPTION: {
                GanttDescription ganttDescription = (GanttDescription) theEObject;
                T result = this.caseGanttDescription(ganttDescription);
                if (result == null)
                    result = this.caseRepresentationDescription(ganttDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case GanttPackage.TASK_DESCRIPTION: {
                TaskDescription taskDescription = (TaskDescription) theEObject;
                T result = this.caseTaskDescription(taskDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case GanttPackage.TASK_STYLE_DESCRIPTION: {
                TaskStyleDescription taskStyleDescription = (TaskStyleDescription) theEObject;
                T result = this.caseTaskStyleDescription(taskStyleDescription);
                if (result == null)
                    result = this.caseLabelStyle(taskStyleDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case GanttPackage.CONDITIONAL_TASK_STYLE: {
                ConditionalTaskStyle conditionalTaskStyle = (ConditionalTaskStyle) theEObject;
                T result = this.caseConditionalTaskStyle(conditionalTaskStyle);
                if (result == null)
                    result = this.caseConditional(conditionalTaskStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case GanttPackage.TASK_TOOL: {
                TaskTool taskTool = (TaskTool) theEObject;
                T result = this.caseTaskTool(taskTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case GanttPackage.CREATE_TASK_TOOL: {
                CreateTaskTool createTaskTool = (CreateTaskTool) theEObject;
                T result = this.caseCreateTaskTool(createTaskTool);
                if (result == null)
                    result = this.caseTaskTool(createTaskTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case GanttPackage.EDIT_TASK_TOOL: {
                EditTaskTool editTaskTool = (EditTaskTool) theEObject;
                T result = this.caseEditTaskTool(editTaskTool);
                if (result == null)
                    result = this.caseTaskTool(editTaskTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case GanttPackage.DELETE_TASK_TOOL: {
                DeleteTaskTool deleteTaskTool = (DeleteTaskTool) theEObject;
                T result = this.caseDeleteTaskTool(deleteTaskTool);
                if (result == null)
                    result = this.caseTaskTool(deleteTaskTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case GanttPackage.DROP_TASK_TOOL: {
                DropTaskTool dropTaskTool = (DropTaskTool) theEObject;
                T result = this.caseDropTaskTool(dropTaskTool);
                if (result == null)
                    result = this.caseTaskTool(dropTaskTool);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            default:
                return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Description</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGanttDescription(GanttDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTaskDescription(TaskDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task Style Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task Style Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTaskStyleDescription(TaskStyleDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Task Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Task Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalTaskStyle(ConditionalTaskStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task Tool</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTaskTool(TaskTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Create Task Tool</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Create Task Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCreateTaskTool(CreateTaskTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Edit Task Tool</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Edit Task Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEditTaskTool(EditTaskTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete Task Tool</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Task Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteTaskTool(DeleteTaskTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Drop Task Tool</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Drop Task Tool</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDropTaskTool(DropTaskTool object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Representation Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Representation Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRepresentationDescription(RepresentationDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Label Style</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Label Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLabelStyle(LabelStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditional(Conditional object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch, but this is the last case
     * anyway. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} // GanttSwitch
