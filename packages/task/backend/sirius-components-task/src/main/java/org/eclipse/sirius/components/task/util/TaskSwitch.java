/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.task.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.task.AbstractTask;
import org.eclipse.sirius.components.task.Company;
import org.eclipse.sirius.components.task.KeyResult;
import org.eclipse.sirius.components.task.Objective;
import org.eclipse.sirius.components.task.Person;
import org.eclipse.sirius.components.task.Project;
import org.eclipse.sirius.components.task.Resource;
import org.eclipse.sirius.components.task.Task;
import org.eclipse.sirius.components.task.TaskPackage;
import org.eclipse.sirius.components.task.TaskTag;
import org.eclipse.sirius.components.task.Team;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.task.TaskPackage
 * @generated
 */
public class TaskSwitch<T> extends Switch<T> {
    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static TaskPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public TaskSwitch() {
        if (modelPackage == null) {
            modelPackage = TaskPackage.eINSTANCE;
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
            case TaskPackage.RESOURCE: {
                Resource resource = (Resource) theEObject;
                T result = this.caseResource(resource);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TaskPackage.COMPANY: {
                Company company = (Company) theEObject;
                T result = this.caseCompany(company);
                if (result == null)
                    result = this.caseResource(company);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TaskPackage.TEAM: {
                Team team = (Team) theEObject;
                T result = this.caseTeam(team);
                if (result == null)
                    result = this.caseResource(team);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TaskPackage.PERSON: {
                Person person = (Person) theEObject;
                T result = this.casePerson(person);
                if (result == null)
                    result = this.caseResource(person);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TaskPackage.ABSTRACT_TASK: {
                AbstractTask abstractTask = (AbstractTask) theEObject;
                T result = this.caseAbstractTask(abstractTask);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TaskPackage.TASK: {
                Task task = (Task) theEObject;
                T result = this.caseTask(task);
                if (result == null)
                    result = this.caseAbstractTask(task);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TaskPackage.OBJECTIVE: {
                Objective objective = (Objective) theEObject;
                T result = this.caseObjective(objective);
                if (result == null)
                    result = this.caseAbstractTask(objective);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TaskPackage.KEY_RESULT: {
                KeyResult keyResult = (KeyResult) theEObject;
                T result = this.caseKeyResult(keyResult);
                if (result == null)
                    result = this.caseAbstractTask(keyResult);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TaskPackage.PROJECT: {
                Project project = (Project) theEObject;
                T result = this.caseProject(project);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TaskPackage.TASK_TAG: {
                TaskTag taskTag = (TaskTag) theEObject;
                T result = this.caseTaskTag(taskTag);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            default:
                return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Resource</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Resource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResource(Resource object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Company</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Company</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCompany(Company object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Team</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Team</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTeam(Team object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Person</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Person</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePerson(Person object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Abstract Task</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Abstract Task</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAbstractTask(AbstractTask object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTask(Task object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Objective</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Objective</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseObjective(Objective object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Key Result</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Key Result</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseKeyResult(KeyResult object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Project</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Project</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProject(Project object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Tag</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Tag</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTaskTag(TaskTag object) {
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

} // TaskSwitch
