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
package org.eclipse.sirius.components.task.impl;

import static org.eclipse.sirius.components.task.TaskPackage.RESOURCE;

import java.time.Instant;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.task.AbstractTask;
import org.eclipse.sirius.components.task.Company;
import org.eclipse.sirius.components.task.KeyResult;
import org.eclipse.sirius.components.task.Objective;
import org.eclipse.sirius.components.task.Person;
import org.eclipse.sirius.components.task.Project;
import org.eclipse.sirius.components.task.Resource;
import org.eclipse.sirius.components.task.Task;
import org.eclipse.sirius.components.task.TaskFactory;
import org.eclipse.sirius.components.task.TaskPackage;
import org.eclipse.sirius.components.task.TaskTag;
import org.eclipse.sirius.components.task.Team;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class TaskPackageImpl extends EPackageImpl implements TaskPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass resourceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass companyEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass teamEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass personEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass abstractTaskEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass taskEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass objectiveEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass keyResultEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass projectEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass taskTagEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EDataType instantEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.components.task.TaskPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private TaskPackageImpl() {
        super(eNS_URI, TaskFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link TaskPackage#eINSTANCE} when that field is accessed. Clients should not
     * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static TaskPackage init() {
        if (isInited)
            return (TaskPackage) EPackage.Registry.INSTANCE.getEPackage(TaskPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredTaskPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        TaskPackageImpl theTaskPackage = registeredTaskPackage instanceof TaskPackageImpl ? (TaskPackageImpl) registeredTaskPackage : new TaskPackageImpl();

        isInited = true;

        // Create package meta-data objects
        theTaskPackage.createPackageContents();

        // Initialize created meta-data
        theTaskPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theTaskPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(TaskPackage.eNS_URI, theTaskPackage);
        return theTaskPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getResource() {
        return this.resourceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getResource_Name() {
        return (EAttribute) this.resourceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCompany() {
        return this.companyEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCompany_OwnedTeams() {
        return (EReference) this.companyEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCompany_OwnedPersons() {
        return (EReference) this.companyEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCompany_OwnedProjects() {
        return (EReference) this.companyEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTeam() {
        return this.teamEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTeam_Members() {
        return (EReference) this.teamEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPerson() {
        return this.personEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPerson_Alias() {
        return (EAttribute) this.personEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPerson_Biography() {
        return (EAttribute) this.personEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPerson_ImageUrl() {
        return (EAttribute) this.personEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getAbstractTask() {
        return this.abstractTaskEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getAbstractTask_Name() {
        return (EAttribute) this.abstractTaskEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getAbstractTask_Description() {
        return (EAttribute) this.abstractTaskEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getAbstractTask_StartTime() {
        return (EAttribute) this.abstractTaskEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getAbstractTask_EndTime() {
        return (EAttribute) this.abstractTaskEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getAbstractTask_Progress() {
        return (EAttribute) this.abstractTaskEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getAbstractTask_ComputeStartEndDynamically() {
        return (EAttribute) this.abstractTaskEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAbstractTask_Tags() {
        return (EReference) this.abstractTaskEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAbstractTask_Dependencies() {
        return (EReference) this.abstractTaskEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAbstractTask_AssignedPersons() {
        return (EReference) this.abstractTaskEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAbstractTask_AssignedTeams() {
        return (EReference) this.abstractTaskEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAbstractTask_SubTasks() {
        return (EReference) this.abstractTaskEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTask() {
        return this.taskEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getObjective() {
        return this.objectiveEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getObjective_OwnedKeyResults() {
        return (EReference) this.objectiveEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getKeyResult() {
        return this.keyResultEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getProject() {
        return this.projectEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getProject_Name() {
        return (EAttribute) this.projectEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getProject_OwnedTasks() {
        return (EReference) this.projectEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getProject_OwnedObjectives() {
        return (EReference) this.projectEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getProject_OwnedTags() {
        return (EReference) this.projectEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTaskTag() {
        return this.taskTagEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskTag_Prefix() {
        return (EAttribute) this.taskTagEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskTag_Suffix() {
        return (EAttribute) this.taskTagEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EDataType getInstant() {
        return this.instantEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TaskFactory getTaskFactory() {
        return (TaskFactory) this.getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
     * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void createPackageContents() {
        if (this.isCreated)
            return;
        this.isCreated = true;

        // Create classes and their features
        this.resourceEClass = this.createEClass(RESOURCE);
        this.createEAttribute(this.resourceEClass, RESOURCE__NAME);

        this.companyEClass = this.createEClass(COMPANY);
        this.createEReference(this.companyEClass, COMPANY__OWNED_TEAMS);
        this.createEReference(this.companyEClass, COMPANY__OWNED_PERSONS);
        this.createEReference(this.companyEClass, COMPANY__OWNED_PROJECTS);

        this.teamEClass = this.createEClass(TEAM);
        this.createEReference(this.teamEClass, TEAM__MEMBERS);

        this.personEClass = this.createEClass(PERSON);
        this.createEAttribute(this.personEClass, PERSON__ALIAS);
        this.createEAttribute(this.personEClass, PERSON__BIOGRAPHY);
        this.createEAttribute(this.personEClass, PERSON__IMAGE_URL);

        this.abstractTaskEClass = this.createEClass(ABSTRACT_TASK);
        this.createEAttribute(this.abstractTaskEClass, ABSTRACT_TASK__NAME);
        this.createEAttribute(this.abstractTaskEClass, ABSTRACT_TASK__DESCRIPTION);
        this.createEAttribute(this.abstractTaskEClass, ABSTRACT_TASK__START_TIME);
        this.createEAttribute(this.abstractTaskEClass, ABSTRACT_TASK__END_TIME);
        this.createEAttribute(this.abstractTaskEClass, ABSTRACT_TASK__PROGRESS);
        this.createEAttribute(this.abstractTaskEClass, ABSTRACT_TASK__COMPUTE_START_END_DYNAMICALLY);
        this.createEReference(this.abstractTaskEClass, ABSTRACT_TASK__TAGS);
        this.createEReference(this.abstractTaskEClass, ABSTRACT_TASK__DEPENDENCIES);
        this.createEReference(this.abstractTaskEClass, ABSTRACT_TASK__ASSIGNED_PERSONS);
        this.createEReference(this.abstractTaskEClass, ABSTRACT_TASK__ASSIGNED_TEAMS);
        this.createEReference(this.abstractTaskEClass, ABSTRACT_TASK__SUB_TASKS);

        this.taskEClass = this.createEClass(TASK);

        this.objectiveEClass = this.createEClass(OBJECTIVE);
        this.createEReference(this.objectiveEClass, OBJECTIVE__OWNED_KEY_RESULTS);

        this.keyResultEClass = this.createEClass(KEY_RESULT);

        this.projectEClass = this.createEClass(PROJECT);
        this.createEAttribute(this.projectEClass, PROJECT__NAME);
        this.createEReference(this.projectEClass, PROJECT__OWNED_TASKS);
        this.createEReference(this.projectEClass, PROJECT__OWNED_OBJECTIVES);
        this.createEReference(this.projectEClass, PROJECT__OWNED_TAGS);

        this.taskTagEClass = this.createEClass(TASK_TAG);
        this.createEAttribute(this.taskTagEClass, TASK_TAG__PREFIX);
        this.createEAttribute(this.taskTagEClass, TASK_TAG__SUFFIX);

        // Create data types
        this.instantEDataType = this.createEDataType(INSTANT);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
     * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void initializePackageContents() {
        if (this.isInitialized)
            return;
        this.isInitialized = true;

        // Initialize package
        this.setName(eNAME);
        this.setNsPrefix(eNS_PREFIX);
        this.setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.companyEClass.getESuperTypes().add(this.getResource());
        this.teamEClass.getESuperTypes().add(this.getResource());
        this.personEClass.getESuperTypes().add(this.getResource());
        this.taskEClass.getESuperTypes().add(this.getAbstractTask());
        this.objectiveEClass.getESuperTypes().add(this.getAbstractTask());
        this.keyResultEClass.getESuperTypes().add(this.getAbstractTask());

        // Initialize classes and features; add operations and parameters
        this.initEClass(this.resourceEClass, Resource.class, "Resource", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getResource_Name(), this.ecorePackage.getEString(), "name", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.companyEClass, Company.class, "Company", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getCompany_OwnedTeams(), this.getTeam(), null, "ownedTeams", null, 0, -1, Company.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getCompany_OwnedPersons(), this.getPerson(), null, "ownedPersons", null, 0, -1, Company.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getCompany_OwnedProjects(), this.getProject(), null, "ownedProjects", null, 0, -1, Company.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.teamEClass, Team.class, "Team", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getTeam_Members(), this.getPerson(), null, "members", null, 0, -1, Team.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.personEClass, Person.class, "Person", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getPerson_Alias(), this.ecorePackage.getEString(), "alias", null, 0, 1, Person.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPerson_Biography(), this.ecorePackage.getEString(), "biography", null, 0, 1, Person.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPerson_ImageUrl(), this.ecorePackage.getEString(), "imageUrl", null, 0, 1, Person.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.abstractTaskEClass, AbstractTask.class, "AbstractTask", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getAbstractTask_Name(), this.ecorePackage.getEString(), "name", null, 0, 1, AbstractTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getAbstractTask_Description(), this.ecorePackage.getEString(), "description", null, 0, 1, AbstractTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getAbstractTask_StartTime(), this.getInstant(), "startTime", null, 0, 1, AbstractTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getAbstractTask_EndTime(), this.getInstant(), "endTime", null, 0, 1, AbstractTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getAbstractTask_Progress(), this.ecorePackage.getEInt(), "progress", null, 0, 1, AbstractTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getAbstractTask_ComputeStartEndDynamically(), this.ecorePackage.getEBoolean(), "computeStartEndDynamically", "true", 0, 1, AbstractTask.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getAbstractTask_Tags(), this.getTaskTag(), null, "tags", null, 0, -1, AbstractTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getAbstractTask_Dependencies(), this.getTask(), null, "dependencies", null, 0, -1, AbstractTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getAbstractTask_AssignedPersons(), this.getPerson(), null, "assignedPersons", null, 0, -1, AbstractTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getAbstractTask_AssignedTeams(), this.getTeam(), null, "assignedTeams", null, 0, -1, AbstractTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getAbstractTask_SubTasks(), this.getTask(), null, "subTasks", null, 0, -1, AbstractTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.taskEClass, Task.class, "Task", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.objectiveEClass, Objective.class, "Objective", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getObjective_OwnedKeyResults(), this.getKeyResult(), null, "ownedKeyResults", null, 0, -1, Objective.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.keyResultEClass, KeyResult.class, "KeyResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.projectEClass, Project.class, "Project", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getProject_Name(), this.ecorePackage.getEString(), "name", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getProject_OwnedTasks(), this.getTask(), null, "ownedTasks", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getProject_OwnedObjectives(), this.getObjective(), null, "ownedObjectives", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getProject_OwnedTags(), this.getTaskTag(), null, "ownedTags", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.taskTagEClass, TaskTag.class, "TaskTag", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTaskTag_Prefix(), this.ecorePackage.getEString(), "prefix", null, 0, 1, TaskTag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTaskTag_Suffix(), this.ecorePackage.getEString(), "suffix", null, 0, 1, TaskTag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize data types
        this.initEDataType(this.instantEDataType, Instant.class, "Instant", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        this.createResource(eNS_URI);
    }

} // TaskPackageImpl
