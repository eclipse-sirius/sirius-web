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
package org.eclipse.sirius.components.task.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.sirius.components.task.Company;
import org.eclipse.sirius.components.task.KeyResult;
import org.eclipse.sirius.components.task.Objective;
import org.eclipse.sirius.components.task.Person;
import org.eclipse.sirius.components.task.Project;
import org.eclipse.sirius.components.task.Task;
import org.eclipse.sirius.components.task.TaskFactory;
import org.eclipse.sirius.components.task.TaskPackage;
import org.eclipse.sirius.components.task.TaskTag;
import org.eclipse.sirius.components.task.Team;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class TaskFactoryImpl extends EFactoryImpl implements TaskFactory {
    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static TaskFactory init() {
        try {
            TaskFactory theTaskFactory = (TaskFactory) EPackage.Registry.INSTANCE.getEFactory(TaskPackage.eNS_URI);
            if (theTaskFactory != null) {
                return theTaskFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new TaskFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public TaskFactoryImpl() {
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
            case TaskPackage.COMPANY:
                return this.createCompany();
            case TaskPackage.TEAM:
                return this.createTeam();
            case TaskPackage.PERSON:
                return this.createPerson();
            case TaskPackage.TASK:
                return this.createTask();
            case TaskPackage.OBJECTIVE:
                return this.createObjective();
            case TaskPackage.KEY_RESULT:
                return this.createKeyResult();
            case TaskPackage.PROJECT:
                return this.createProject();
            case TaskPackage.TASK_TAG:
                return this.createTaskTag();
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
    public Company createCompany() {
        CompanyImpl company = new CompanyImpl();
        return company;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Team createTeam() {
        TeamImpl team = new TeamImpl();
        return team;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Person createPerson() {
        PersonImpl person = new PersonImpl();
        return person;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Task createTask() {
        TaskImpl task = new TaskImpl();
        return task;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Objective createObjective() {
        ObjectiveImpl objective = new ObjectiveImpl();
        return objective;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public KeyResult createKeyResult() {
        KeyResultImpl keyResult = new KeyResultImpl();
        return keyResult;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Project createProject() {
        ProjectImpl project = new ProjectImpl();
        return project;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TaskTag createTaskTag() {
        TaskTagImpl taskTag = new TaskTagImpl();
        return taskTag;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TaskPackage getTaskPackage() {
        return (TaskPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static TaskPackage getPackage() {
        return TaskPackage.eINSTANCE;
    }

} // TaskFactoryImpl
