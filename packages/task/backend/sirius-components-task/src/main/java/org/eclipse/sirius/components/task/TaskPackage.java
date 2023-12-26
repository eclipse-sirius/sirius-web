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
package org.eclipse.sirius.components.task;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.task.TaskFactory
 * @model kind="package"
 * @generated
 */
public interface TaskPackage extends EPackage {
    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "task";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/sirius-web/task";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "task";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    TaskPackage eINSTANCE = org.eclipse.sirius.components.task.impl.TaskPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.task.impl.ResourceImpl <em>Resource</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.task.impl.ResourceImpl
     * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getResource()
     * @generated
     */
    int RESOURCE = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE__NAME = 0;

    /**
     * The number of structural features of the '<em>Resource</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RESOURCE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.task.impl.CompanyImpl <em>Company</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.task.impl.CompanyImpl
     * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getCompany()
     * @generated
     */
    int COMPANY = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMPANY__NAME = RESOURCE__NAME;

    /**
     * The feature id for the '<em><b>Owned Teams</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMPANY__OWNED_TEAMS = RESOURCE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Owned Persons</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMPANY__OWNED_PERSONS = RESOURCE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Owned Projects</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMPANY__OWNED_PROJECTS = RESOURCE_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Company</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COMPANY_FEATURE_COUNT = RESOURCE_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.task.impl.TeamImpl <em>Team</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.task.impl.TeamImpl
     * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getTeam()
     * @generated
     */
    int TEAM = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEAM__NAME = RESOURCE__NAME;

    /**
     * The feature id for the '<em><b>Members</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEAM__MEMBERS = RESOURCE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Team</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEAM_FEATURE_COUNT = RESOURCE_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.task.impl.PersonImpl <em>Person</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.task.impl.PersonImpl
     * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getPerson()
     * @generated
     */
    int PERSON = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PERSON__NAME = RESOURCE__NAME;

    /**
     * The feature id for the '<em><b>Alias</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PERSON__ALIAS = RESOURCE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Biography</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PERSON__BIOGRAPHY = RESOURCE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Image Url</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PERSON__IMAGE_URL = RESOURCE_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Person</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PERSON_FEATURE_COUNT = RESOURCE_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl <em>Abstract
     * Task</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.task.impl.AbstractTaskImpl
     * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getAbstractTask()
     * @generated
     */
    int ABSTRACT_TASK = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ABSTRACT_TASK__NAME = 0;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ABSTRACT_TASK__DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Start Time</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ABSTRACT_TASK__START_TIME = 2;

    /**
     * The feature id for the '<em><b>End Time</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ABSTRACT_TASK__END_TIME = 3;

    /**
     * The feature id for the '<em><b>Progress</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ABSTRACT_TASK__PROGRESS = 4;

    /**
     * The feature id for the '<em><b>Compute Start End Dynamically</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ABSTRACT_TASK__COMPUTE_START_END_DYNAMICALLY = 5;

    /**
     * The feature id for the '<em><b>Tags</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ABSTRACT_TASK__TAGS = 6;

    /**
     * The feature id for the '<em><b>Dependencies</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ABSTRACT_TASK__DEPENDENCIES = 7;

    /**
     * The feature id for the '<em><b>Assigned Persons</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ABSTRACT_TASK__ASSIGNED_PERSONS = 8;

    /**
     * The feature id for the '<em><b>Assigned Teams</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ABSTRACT_TASK__ASSIGNED_TEAMS = 9;

    /**
     * The feature id for the '<em><b>Sub Tasks</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ABSTRACT_TASK__SUB_TASKS = 10;

    /**
     * The number of structural features of the '<em>Abstract Task</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ABSTRACT_TASK_FEATURE_COUNT = 11;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.task.impl.TaskImpl <em>Task</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.task.impl.TaskImpl
     * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getTask()
     * @generated
     */
    int TASK = 5;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK__NAME = ABSTRACT_TASK__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK__DESCRIPTION = ABSTRACT_TASK__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Start Time</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK__START_TIME = ABSTRACT_TASK__START_TIME;

    /**
     * The feature id for the '<em><b>End Time</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK__END_TIME = ABSTRACT_TASK__END_TIME;

    /**
     * The feature id for the '<em><b>Progress</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK__PROGRESS = ABSTRACT_TASK__PROGRESS;

    /**
     * The feature id for the '<em><b>Compute Start End Dynamically</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK__COMPUTE_START_END_DYNAMICALLY = ABSTRACT_TASK__COMPUTE_START_END_DYNAMICALLY;

    /**
     * The feature id for the '<em><b>Tags</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK__TAGS = ABSTRACT_TASK__TAGS;

    /**
     * The feature id for the '<em><b>Dependencies</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TASK__DEPENDENCIES = ABSTRACT_TASK__DEPENDENCIES;

    /**
     * The feature id for the '<em><b>Assigned Persons</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK__ASSIGNED_PERSONS = ABSTRACT_TASK__ASSIGNED_PERSONS;

    /**
     * The feature id for the '<em><b>Assigned Teams</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TASK__ASSIGNED_TEAMS = ABSTRACT_TASK__ASSIGNED_TEAMS;

    /**
     * The feature id for the '<em><b>Sub Tasks</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK__SUB_TASKS = ABSTRACT_TASK__SUB_TASKS;

    /**
     * The number of structural features of the '<em>Task</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_FEATURE_COUNT = ABSTRACT_TASK_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.task.impl.ObjectiveImpl <em>Objective</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.task.impl.ObjectiveImpl
     * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getObjective()
     * @generated
     */
    int OBJECTIVE = 6;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OBJECTIVE__NAME = ABSTRACT_TASK__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OBJECTIVE__DESCRIPTION = ABSTRACT_TASK__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Start Time</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OBJECTIVE__START_TIME = ABSTRACT_TASK__START_TIME;

    /**
     * The feature id for the '<em><b>End Time</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OBJECTIVE__END_TIME = ABSTRACT_TASK__END_TIME;

    /**
     * The feature id for the '<em><b>Progress</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OBJECTIVE__PROGRESS = ABSTRACT_TASK__PROGRESS;

    /**
     * The feature id for the '<em><b>Compute Start End Dynamically</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OBJECTIVE__COMPUTE_START_END_DYNAMICALLY = ABSTRACT_TASK__COMPUTE_START_END_DYNAMICALLY;

    /**
     * The feature id for the '<em><b>Tags</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OBJECTIVE__TAGS = ABSTRACT_TASK__TAGS;

    /**
     * The feature id for the '<em><b>Dependencies</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OBJECTIVE__DEPENDENCIES = ABSTRACT_TASK__DEPENDENCIES;

    /**
     * The feature id for the '<em><b>Assigned Persons</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OBJECTIVE__ASSIGNED_PERSONS = ABSTRACT_TASK__ASSIGNED_PERSONS;

    /**
     * The feature id for the '<em><b>Assigned Teams</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OBJECTIVE__ASSIGNED_TEAMS = ABSTRACT_TASK__ASSIGNED_TEAMS;

    /**
     * The feature id for the '<em><b>Sub Tasks</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OBJECTIVE__SUB_TASKS = ABSTRACT_TASK__SUB_TASKS;

    /**
     * The feature id for the '<em><b>Owned Key Results</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OBJECTIVE__OWNED_KEY_RESULTS = ABSTRACT_TASK_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Objective</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OBJECTIVE_FEATURE_COUNT = ABSTRACT_TASK_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.task.impl.KeyResultImpl <em>Key Result</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.task.impl.KeyResultImpl
     * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getKeyResult()
     * @generated
     */
    int KEY_RESULT = 7;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int KEY_RESULT__NAME = ABSTRACT_TASK__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int KEY_RESULT__DESCRIPTION = ABSTRACT_TASK__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Start Time</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int KEY_RESULT__START_TIME = ABSTRACT_TASK__START_TIME;

    /**
     * The feature id for the '<em><b>End Time</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int KEY_RESULT__END_TIME = ABSTRACT_TASK__END_TIME;

    /**
     * The feature id for the '<em><b>Progress</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int KEY_RESULT__PROGRESS = ABSTRACT_TASK__PROGRESS;

    /**
     * The feature id for the '<em><b>Compute Start End Dynamically</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int KEY_RESULT__COMPUTE_START_END_DYNAMICALLY = ABSTRACT_TASK__COMPUTE_START_END_DYNAMICALLY;

    /**
     * The feature id for the '<em><b>Tags</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int KEY_RESULT__TAGS = ABSTRACT_TASK__TAGS;

    /**
     * The feature id for the '<em><b>Dependencies</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int KEY_RESULT__DEPENDENCIES = ABSTRACT_TASK__DEPENDENCIES;

    /**
     * The feature id for the '<em><b>Assigned Persons</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int KEY_RESULT__ASSIGNED_PERSONS = ABSTRACT_TASK__ASSIGNED_PERSONS;

    /**
     * The feature id for the '<em><b>Assigned Teams</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int KEY_RESULT__ASSIGNED_TEAMS = ABSTRACT_TASK__ASSIGNED_TEAMS;

    /**
     * The feature id for the '<em><b>Sub Tasks</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int KEY_RESULT__SUB_TASKS = ABSTRACT_TASK__SUB_TASKS;

    /**
     * The number of structural features of the '<em>Key Result</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int KEY_RESULT_FEATURE_COUNT = ABSTRACT_TASK_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.task.impl.ProjectImpl <em>Project</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.task.impl.ProjectImpl
     * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getProject()
     * @generated
     */
    int PROJECT = 8;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROJECT__NAME = 0;

    /**
     * The feature id for the '<em><b>Owned Tasks</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROJECT__OWNED_TASKS = 1;

    /**
     * The feature id for the '<em><b>Owned Objectives</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROJECT__OWNED_OBJECTIVES = 2;

    /**
     * The feature id for the '<em><b>Owned Tags</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROJECT__OWNED_TAGS = 3;

    /**
     * The number of structural features of the '<em>Project</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROJECT_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.task.impl.TaskTagImpl <em>Tag</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.task.impl.TaskTagImpl
     * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getTaskTag()
     * @generated
     */
    int TASK_TAG = 9;

    /**
     * The feature id for the '<em><b>Prefix</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_TAG__PREFIX = 0;

    /**
     * The feature id for the '<em><b>Suffix</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_TAG__SUFFIX = 1;

    /**
     * The number of structural features of the '<em>Tag</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_TAG_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '<em>Instant</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see java.time.Instant
     * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getInstant()
     * @generated
     */
    int INSTANT = 10;

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.task.Resource <em>Resource</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Resource</em>'.
     * @see org.eclipse.sirius.components.task.Resource
     * @generated
     */
    EClass getResource();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.task.Resource#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.task.Resource#getName()
     * @see #getResource()
     * @generated
     */
    EAttribute getResource_Name();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.task.Company <em>Company</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Company</em>'.
     * @see org.eclipse.sirius.components.task.Company
     * @generated
     */
    EClass getCompany();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.task.Company#getOwnedTeams <em>Owned Teams</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Owned Teams</em>'.
     * @see org.eclipse.sirius.components.task.Company#getOwnedTeams()
     * @see #getCompany()
     * @generated
     */
    EReference getCompany_OwnedTeams();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.task.Company#getOwnedPersons <em>Owned Persons</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Owned Persons</em>'.
     * @see org.eclipse.sirius.components.task.Company#getOwnedPersons()
     * @see #getCompany()
     * @generated
     */
    EReference getCompany_OwnedPersons();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.task.Company#getOwnedProjects <em>Owned Projects</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Owned Projects</em>'.
     * @see org.eclipse.sirius.components.task.Company#getOwnedProjects()
     * @see #getCompany()
     * @generated
     */
    EReference getCompany_OwnedProjects();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.task.Team <em>Team</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Team</em>'.
     * @see org.eclipse.sirius.components.task.Team
     * @generated
     */
    EClass getTeam();

    /**
     * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.task.Team#getMembers
     * <em>Members</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Members</em>'.
     * @see org.eclipse.sirius.components.task.Team#getMembers()
     * @see #getTeam()
     * @generated
     */
    EReference getTeam_Members();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.task.Person <em>Person</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Person</em>'.
     * @see org.eclipse.sirius.components.task.Person
     * @generated
     */
    EClass getPerson();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.task.Person#getAlias
     * <em>Alias</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Alias</em>'.
     * @see org.eclipse.sirius.components.task.Person#getAlias()
     * @see #getPerson()
     * @generated
     */
    EAttribute getPerson_Alias();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.task.Person#getBiography
     * <em>Biography</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Biography</em>'.
     * @see org.eclipse.sirius.components.task.Person#getBiography()
     * @see #getPerson()
     * @generated
     */
    EAttribute getPerson_Biography();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.task.Person#getImageUrl <em>Image
     * Url</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Image Url</em>'.
     * @see org.eclipse.sirius.components.task.Person#getImageUrl()
     * @see #getPerson()
     * @generated
     */
    EAttribute getPerson_ImageUrl();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.task.AbstractTask <em>Abstract
     * Task</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Abstract Task</em>'.
     * @see org.eclipse.sirius.components.task.AbstractTask
     * @generated
     */
    EClass getAbstractTask();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.task.AbstractTask#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.task.AbstractTask#getName()
     * @see #getAbstractTask()
     * @generated
     */
    EAttribute getAbstractTask_Name();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.task.AbstractTask#getDescription
     * <em>Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see org.eclipse.sirius.components.task.AbstractTask#getDescription()
     * @see #getAbstractTask()
     * @generated
     */
    EAttribute getAbstractTask_Description();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.task.AbstractTask#getStartTime
     * <em>Start Time</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Start Time</em>'.
     * @see org.eclipse.sirius.components.task.AbstractTask#getStartTime()
     * @see #getAbstractTask()
     * @generated
     */
    EAttribute getAbstractTask_StartTime();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.task.AbstractTask#getEndTime
     * <em>End Time</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>End Time</em>'.
     * @see org.eclipse.sirius.components.task.AbstractTask#getEndTime()
     * @see #getAbstractTask()
     * @generated
     */
    EAttribute getAbstractTask_EndTime();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.task.AbstractTask#getProgress
     * <em>Progress</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Progress</em>'.
     * @see org.eclipse.sirius.components.task.AbstractTask#getProgress()
     * @see #getAbstractTask()
     * @generated
     */
    EAttribute getAbstractTask_Progress();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.task.AbstractTask#isComputeStartEndDynamically <em>Compute Start End
     * Dynamically</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Compute Start End Dynamically</em>'.
     * @see org.eclipse.sirius.components.task.AbstractTask#isComputeStartEndDynamically()
     * @see #getAbstractTask()
     * @generated
     */
    EAttribute getAbstractTask_ComputeStartEndDynamically();

    /**
     * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.task.AbstractTask#getTags
     * <em>Tags</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Tags</em>'.
     * @see org.eclipse.sirius.components.task.AbstractTask#getTags()
     * @see #getAbstractTask()
     * @generated
     */
    EReference getAbstractTask_Tags();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.task.AbstractTask#getDependencies <em>Dependencies</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Dependencies</em>'.
     * @see org.eclipse.sirius.components.task.AbstractTask#getDependencies()
     * @see #getAbstractTask()
     * @generated
     */
    EReference getAbstractTask_Dependencies();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.task.AbstractTask#getAssignedPersons <em>Assigned Persons</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Assigned Persons</em>'.
     * @see org.eclipse.sirius.components.task.AbstractTask#getAssignedPersons()
     * @see #getAbstractTask()
     * @generated
     */
    EReference getAbstractTask_AssignedPersons();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.task.AbstractTask#getAssignedTeams <em>Assigned Teams</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Assigned Teams</em>'.
     * @see org.eclipse.sirius.components.task.AbstractTask#getAssignedTeams()
     * @see #getAbstractTask()
     * @generated
     */
    EReference getAbstractTask_AssignedTeams();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.task.AbstractTask#getSubTasks <em>Sub Tasks</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Sub Tasks</em>'.
     * @see org.eclipse.sirius.components.task.AbstractTask#getSubTasks()
     * @see #getAbstractTask()
     * @generated
     */
    EReference getAbstractTask_SubTasks();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.task.Task <em>Task</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Task</em>'.
     * @see org.eclipse.sirius.components.task.Task
     * @generated
     */
    EClass getTask();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.task.Objective <em>Objective</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Objective</em>'.
     * @see org.eclipse.sirius.components.task.Objective
     * @generated
     */
    EClass getObjective();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.task.Objective#getOwnedKeyResults <em>Owned Key Results</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Owned Key Results</em>'.
     * @see org.eclipse.sirius.components.task.Objective#getOwnedKeyResults()
     * @see #getObjective()
     * @generated
     */
    EReference getObjective_OwnedKeyResults();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.task.KeyResult <em>Key Result</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Key Result</em>'.
     * @see org.eclipse.sirius.components.task.KeyResult
     * @generated
     */
    EClass getKeyResult();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.task.Project <em>Project</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Project</em>'.
     * @see org.eclipse.sirius.components.task.Project
     * @generated
     */
    EClass getProject();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.task.Project#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.task.Project#getName()
     * @see #getProject()
     * @generated
     */
    EAttribute getProject_Name();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.task.Project#getOwnedTasks <em>Owned Tasks</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Owned Tasks</em>'.
     * @see org.eclipse.sirius.components.task.Project#getOwnedTasks()
     * @see #getProject()
     * @generated
     */
    EReference getProject_OwnedTasks();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.task.Project#getOwnedObjectives <em>Owned Objectives</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Owned Objectives</em>'.
     * @see org.eclipse.sirius.components.task.Project#getOwnedObjectives()
     * @see #getProject()
     * @generated
     */
    EReference getProject_OwnedObjectives();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.task.Project#getOwnedTags <em>Owned Tags</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Owned Tags</em>'.
     * @see org.eclipse.sirius.components.task.Project#getOwnedTags()
     * @see #getProject()
     * @generated
     */
    EReference getProject_OwnedTags();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.task.TaskTag <em>Tag</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Tag</em>'.
     * @see org.eclipse.sirius.components.task.TaskTag
     * @generated
     */
    EClass getTaskTag();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.task.TaskTag#getPrefix
     * <em>Prefix</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Prefix</em>'.
     * @see org.eclipse.sirius.components.task.TaskTag#getPrefix()
     * @see #getTaskTag()
     * @generated
     */
    EAttribute getTaskTag_Prefix();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.task.TaskTag#getSuffix
     * <em>Suffix</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Suffix</em>'.
     * @see org.eclipse.sirius.components.task.TaskTag#getSuffix()
     * @see #getTaskTag()
     * @generated
     */
    EAttribute getTaskTag_Suffix();

    /**
     * Returns the meta object for data type '{@link java.time.Instant <em>Instant</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for data type '<em>Instant</em>'.
     * @see java.time.Instant
     * @model instanceClass="java.time.Instant"
     * @generated
     */
    EDataType getInstant();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    TaskFactory getTaskFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     *
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.task.impl.ResourceImpl
         * <em>Resource</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.task.impl.ResourceImpl
         * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getResource()
         * @generated
         */
        EClass RESOURCE = eINSTANCE.getResource();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute RESOURCE__NAME = eINSTANCE.getResource_Name();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.task.impl.CompanyImpl
         * <em>Company</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.task.impl.CompanyImpl
         * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getCompany()
         * @generated
         */
        EClass COMPANY = eINSTANCE.getCompany();

        /**
         * The meta object literal for the '<em><b>Owned Teams</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMPANY__OWNED_TEAMS = eINSTANCE.getCompany_OwnedTeams();

        /**
         * The meta object literal for the '<em><b>Owned Persons</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMPANY__OWNED_PERSONS = eINSTANCE.getCompany_OwnedPersons();

        /**
         * The meta object literal for the '<em><b>Owned Projects</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMPANY__OWNED_PROJECTS = eINSTANCE.getCompany_OwnedProjects();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.task.impl.TeamImpl <em>Team</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.task.impl.TeamImpl
         * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getTeam()
         * @generated
         */
        EClass TEAM = eINSTANCE.getTeam();

        /**
         * The meta object literal for the '<em><b>Members</b></em>' reference list feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEAM__MEMBERS = eINSTANCE.getTeam_Members();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.task.impl.PersonImpl <em>Person</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.task.impl.PersonImpl
         * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getPerson()
         * @generated
         */
        EClass PERSON = eINSTANCE.getPerson();

        /**
         * The meta object literal for the '<em><b>Alias</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute PERSON__ALIAS = eINSTANCE.getPerson_Alias();

        /**
         * The meta object literal for the '<em><b>Biography</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute PERSON__BIOGRAPHY = eINSTANCE.getPerson_Biography();

        /**
         * The meta object literal for the '<em><b>Image Url</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute PERSON__IMAGE_URL = eINSTANCE.getPerson_ImageUrl();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.task.impl.AbstractTaskImpl <em>Abstract
         * Task</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.task.impl.AbstractTaskImpl
         * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getAbstractTask()
         * @generated
         */
        EClass ABSTRACT_TASK = eINSTANCE.getAbstractTask();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute ABSTRACT_TASK__NAME = eINSTANCE.getAbstractTask_Name();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute ABSTRACT_TASK__DESCRIPTION = eINSTANCE.getAbstractTask_Description();

        /**
         * The meta object literal for the '<em><b>Start Time</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute ABSTRACT_TASK__START_TIME = eINSTANCE.getAbstractTask_StartTime();

        /**
         * The meta object literal for the '<em><b>End Time</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute ABSTRACT_TASK__END_TIME = eINSTANCE.getAbstractTask_EndTime();

        /**
         * The meta object literal for the '<em><b>Progress</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute ABSTRACT_TASK__PROGRESS = eINSTANCE.getAbstractTask_Progress();

        /**
         * The meta object literal for the '<em><b>Compute Start End Dynamically</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ABSTRACT_TASK__COMPUTE_START_END_DYNAMICALLY = eINSTANCE.getAbstractTask_ComputeStartEndDynamically();

        /**
         * The meta object literal for the '<em><b>Tags</b></em>' reference list feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference ABSTRACT_TASK__TAGS = eINSTANCE.getAbstractTask_Tags();

        /**
         * The meta object literal for the '<em><b>Dependencies</b></em>' reference list feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ABSTRACT_TASK__DEPENDENCIES = eINSTANCE.getAbstractTask_Dependencies();

        /**
         * The meta object literal for the '<em><b>Assigned Persons</b></em>' reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ABSTRACT_TASK__ASSIGNED_PERSONS = eINSTANCE.getAbstractTask_AssignedPersons();

        /**
         * The meta object literal for the '<em><b>Assigned Teams</b></em>' reference list feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ABSTRACT_TASK__ASSIGNED_TEAMS = eINSTANCE.getAbstractTask_AssignedTeams();

        /**
         * The meta object literal for the '<em><b>Sub Tasks</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ABSTRACT_TASK__SUB_TASKS = eINSTANCE.getAbstractTask_SubTasks();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.task.impl.TaskImpl <em>Task</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.task.impl.TaskImpl
         * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getTask()
         * @generated
         */
        EClass TASK = eINSTANCE.getTask();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.task.impl.ObjectiveImpl
         * <em>Objective</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.task.impl.ObjectiveImpl
         * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getObjective()
         * @generated
         */
        EClass OBJECTIVE = eINSTANCE.getObjective();

        /**
         * The meta object literal for the '<em><b>Owned Key Results</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference OBJECTIVE__OWNED_KEY_RESULTS = eINSTANCE.getObjective_OwnedKeyResults();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.task.impl.KeyResultImpl <em>Key
         * Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.task.impl.KeyResultImpl
         * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getKeyResult()
         * @generated
         */
        EClass KEY_RESULT = eINSTANCE.getKeyResult();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.task.impl.ProjectImpl
         * <em>Project</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.task.impl.ProjectImpl
         * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getProject()
         * @generated
         */
        EClass PROJECT = eINSTANCE.getProject();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute PROJECT__NAME = eINSTANCE.getProject_Name();

        /**
         * The meta object literal for the '<em><b>Owned Tasks</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PROJECT__OWNED_TASKS = eINSTANCE.getProject_OwnedTasks();

        /**
         * The meta object literal for the '<em><b>Owned Objectives</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PROJECT__OWNED_OBJECTIVES = eINSTANCE.getProject_OwnedObjectives();

        /**
         * The meta object literal for the '<em><b>Owned Tags</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PROJECT__OWNED_TAGS = eINSTANCE.getProject_OwnedTags();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.task.impl.TaskTagImpl <em>Tag</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.task.impl.TaskTagImpl
         * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getTaskTag()
         * @generated
         */
        EClass TASK_TAG = eINSTANCE.getTaskTag();

        /**
         * The meta object literal for the '<em><b>Prefix</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute TASK_TAG__PREFIX = eINSTANCE.getTaskTag_Prefix();

        /**
         * The meta object literal for the '<em><b>Suffix</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute TASK_TAG__SUFFIX = eINSTANCE.getTaskTag_Suffix();

        /**
         * The meta object literal for the '<em>Instant</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see java.time.Instant
         * @see org.eclipse.sirius.components.task.impl.TaskPackageImpl#getInstant()
         * @generated
         */
        EDataType INSTANT = eINSTANCE.getInstant();

    }

} // TaskPackage
