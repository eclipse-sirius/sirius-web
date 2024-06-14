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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.gantt.GanttFactory
 * @model kind="package"
 * @generated
 */
public interface GanttPackage extends EPackage {
    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "gantt";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/sirius-web/gantt";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "gantt";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    GanttPackage eINSTANCE = org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl
     * <em>Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl
     * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getGanttDescription()
     * @generated
     */
    int GANTT_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GANTT_DESCRIPTION__NAME = ViewPackage.REPRESENTATION_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GANTT_DESCRIPTION__DOMAIN_TYPE = ViewPackage.REPRESENTATION_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GANTT_DESCRIPTION__PRECONDITION_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int GANTT_DESCRIPTION__TITLE_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION;

    /**
     * The feature id for the '<em><b>Task Element Descriptions</b></em>' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Create Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GANTT_DESCRIPTION__CREATE_TOOL = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GANTT_DESCRIPTION__EDIT_TOOL = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Delete Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GANTT_DESCRIPTION__DELETE_TOOL = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Drop Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GANTT_DESCRIPTION__DROP_TOOL = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Create Task Dependency Tool</b></em>' containment reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Date Rounding Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GANTT_DESCRIPTION__DATE_ROUNDING_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int GANTT_DESCRIPTION_FEATURE_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GANTT_DESCRIPTION_OPERATION_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.gantt.impl.TaskDescriptionImpl <em>Task
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.gantt.impl.TaskDescriptionImpl
     * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getTaskDescription()
     * @generated
     */
    int TASK_DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_DESCRIPTION__DOMAIN_TYPE = 1;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = 2;

    /**
     * The feature id for the '<em><b>Name Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_DESCRIPTION__NAME_EXPRESSION = 3;

    /**
     * The feature id for the '<em><b>Description Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_DESCRIPTION__DESCRIPTION_EXPRESSION = 4;

    /**
     * The feature id for the '<em><b>Start Time Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_DESCRIPTION__START_TIME_EXPRESSION = 5;

    /**
     * The feature id for the '<em><b>End Time Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TASK_DESCRIPTION__END_TIME_EXPRESSION = 6;

    /**
     * The feature id for the '<em><b>Progress Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TASK_DESCRIPTION__PROGRESS_EXPRESSION = 7;

    /**
     * The feature id for the '<em><b>Compute Start End Dynamically Expression</b></em>' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_DESCRIPTION__COMPUTE_START_END_DYNAMICALLY_EXPRESSION = 8;

    /**
     * The feature id for the '<em><b>Task Dependencies Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_DESCRIPTION__TASK_DEPENDENCIES_EXPRESSION = 9;

    /**
     * The feature id for the '<em><b>Sub Task Element Descriptions</b></em>' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_DESCRIPTION__SUB_TASK_ELEMENT_DESCRIPTIONS = 10;

    /**
     * The feature id for the '<em><b>Reused Task Element Descriptions</b></em>' reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_DESCRIPTION__REUSED_TASK_ELEMENT_DESCRIPTIONS = 11;

    /**
     * The number of structural features of the '<em>Task Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_DESCRIPTION_FEATURE_COUNT = 12;

    /**
     * The number of operations of the '<em>Task Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.gantt.impl.TaskToolImpl <em>Task
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.gantt.impl.TaskToolImpl
     * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getTaskTool()
     * @generated
     */
    int TASK_TOOL = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_TOOL__NAME = 0;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_TOOL__BODY = 1;

    /**
     * The number of structural features of the '<em>Task Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TASK_TOOL_FEATURE_COUNT = 2;

    /**
     * The number of operations of the '<em>Task Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TASK_TOOL_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.gantt.impl.CreateTaskToolImpl <em>Create
     * Task Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.gantt.impl.CreateTaskToolImpl
     * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getCreateTaskTool()
     * @generated
     */
    int CREATE_TASK_TOOL = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_TASK_TOOL__NAME = TASK_TOOL__NAME;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_TASK_TOOL__BODY = TASK_TOOL__BODY;

    /**
     * The number of structural features of the '<em>Create Task Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_TASK_TOOL_FEATURE_COUNT = TASK_TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Create Task Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_TASK_TOOL_OPERATION_COUNT = TASK_TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.gantt.impl.EditTaskToolImpl <em>Edit Task
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.gantt.impl.EditTaskToolImpl
     * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getEditTaskTool()
     * @generated
     */
    int EDIT_TASK_TOOL = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDIT_TASK_TOOL__NAME = TASK_TOOL__NAME;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDIT_TASK_TOOL__BODY = TASK_TOOL__BODY;

    /**
     * The number of structural features of the '<em>Edit Task Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDIT_TASK_TOOL_FEATURE_COUNT = TASK_TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Edit Task Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDIT_TASK_TOOL_OPERATION_COUNT = TASK_TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.gantt.impl.DeleteTaskToolImpl <em>Delete
     * Task Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.gantt.impl.DeleteTaskToolImpl
     * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getDeleteTaskTool()
     * @generated
     */
    int DELETE_TASK_TOOL = 5;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_TASK_TOOL__NAME = TASK_TOOL__NAME;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_TASK_TOOL__BODY = TASK_TOOL__BODY;

    /**
     * The number of structural features of the '<em>Delete Task Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_TASK_TOOL_FEATURE_COUNT = TASK_TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Delete Task Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_TASK_TOOL_OPERATION_COUNT = TASK_TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.gantt.impl.DropTaskToolImpl <em>Drop Task
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.gantt.impl.DropTaskToolImpl
     * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getDropTaskTool()
     * @generated
     */
    int DROP_TASK_TOOL = 6;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_TASK_TOOL__NAME = TASK_TOOL__NAME;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_TASK_TOOL__BODY = TASK_TOOL__BODY;

    /**
     * The number of structural features of the '<em>Drop Task Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_TASK_TOOL_FEATURE_COUNT = TASK_TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Drop Task Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_TASK_TOOL_OPERATION_COUNT = TASK_TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.gantt.impl.CreateTaskDependencyToolImpl
     * <em>Create Task Dependency Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.gantt.impl.CreateTaskDependencyToolImpl
     * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getCreateTaskDependencyTool()
     * @generated
     */
    int CREATE_TASK_DEPENDENCY_TOOL = 7;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_TASK_DEPENDENCY_TOOL__NAME = TASK_TOOL__NAME;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_TASK_DEPENDENCY_TOOL__BODY = TASK_TOOL__BODY;

    /**
     * The number of structural features of the '<em>Create Task Dependency Tool</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_TASK_DEPENDENCY_TOOL_FEATURE_COUNT = TASK_TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Create Task Dependency Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_TASK_DEPENDENCY_TOOL_OPERATION_COUNT = TASK_TOOL_OPERATION_COUNT + 0;

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.gantt.GanttDescription
     * <em>Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Description</em>'.
     * @see org.eclipse.sirius.components.view.gantt.GanttDescription
     * @generated
     */
    EClass getGanttDescription();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getTaskElementDescriptions <em>Task Element
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Task Element Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.gantt.GanttDescription#getTaskElementDescriptions()
     * @see #getGanttDescription()
     * @generated
     */
    EReference getGanttDescription_TaskElementDescriptions();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getCreateTool <em>Create Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Create Tool</em>'.
     * @see org.eclipse.sirius.components.view.gantt.GanttDescription#getCreateTool()
     * @see #getGanttDescription()
     * @generated
     */
    EReference getGanttDescription_CreateTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getEditTool <em>Edit Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Edit Tool</em>'.
     * @see org.eclipse.sirius.components.view.gantt.GanttDescription#getEditTool()
     * @see #getGanttDescription()
     * @generated
     */
    EReference getGanttDescription_EditTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getDeleteTool <em>Delete Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Delete Tool</em>'.
     * @see org.eclipse.sirius.components.view.gantt.GanttDescription#getDeleteTool()
     * @see #getGanttDescription()
     * @generated
     */
    EReference getGanttDescription_DeleteTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getDropTool <em>Drop Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Drop Tool</em>'.
     * @see org.eclipse.sirius.components.view.gantt.GanttDescription#getDropTool()
     * @see #getGanttDescription()
     * @generated
     */
    EReference getGanttDescription_DropTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getCreateTaskDependencyTool <em>Create Task
     * Dependency Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Create Task Dependency Tool</em>'.
     * @see org.eclipse.sirius.components.view.gantt.GanttDescription#getCreateTaskDependencyTool()
     * @see #getGanttDescription()
     * @generated
     */
    EReference getGanttDescription_CreateTaskDependencyTool();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getDateRoundingExpression <em>Date Rounding
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Date Rounding Expression</em>'.
     * @see org.eclipse.sirius.components.view.gantt.GanttDescription#getDateRoundingExpression()
     * @see #getGanttDescription()
     * @generated
     */
    EAttribute getGanttDescription_DateRoundingExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.gantt.TaskDescription <em>Task
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Task Description</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskDescription
     * @generated
     */
    EClass getTaskDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getName <em>Name</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskDescription#getName()
     * @see #getTaskDescription()
     * @generated
     */
    EAttribute getTaskDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getDomainType <em>Domain Type</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Domain Type</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskDescription#getDomainType()
     * @see #getTaskDescription()
     * @generated
     */
    EAttribute getTaskDescription_DomainType();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getSemanticCandidatesExpression <em>Semantic
     * Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskDescription#getSemanticCandidatesExpression()
     * @see #getTaskDescription()
     * @generated
     */
    EAttribute getTaskDescription_SemanticCandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getNameExpression <em>Name Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name Expression</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskDescription#getNameExpression()
     * @see #getTaskDescription()
     * @generated
     */
    EAttribute getTaskDescription_NameExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getDescriptionExpression <em>Description
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Description Expression</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskDescription#getDescriptionExpression()
     * @see #getTaskDescription()
     * @generated
     */
    EAttribute getTaskDescription_DescriptionExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getStartTimeExpression <em>Start Time
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Start Time Expression</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskDescription#getStartTimeExpression()
     * @see #getTaskDescription()
     * @generated
     */
    EAttribute getTaskDescription_StartTimeExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getEndTimeExpression <em>End Time
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>End Time Expression</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskDescription#getEndTimeExpression()
     * @see #getTaskDescription()
     * @generated
     */
    EAttribute getTaskDescription_EndTimeExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getProgressExpression <em>Progress
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Progress Expression</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskDescription#getProgressExpression()
     * @see #getTaskDescription()
     * @generated
     */
    EAttribute getTaskDescription_ProgressExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getComputeStartEndDynamicallyExpression
     * <em>Compute Start End Dynamically Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Compute Start End Dynamically Expression</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskDescription#getComputeStartEndDynamicallyExpression()
     * @see #getTaskDescription()
     * @generated
     */
    EAttribute getTaskDescription_ComputeStartEndDynamicallyExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getTaskDependenciesExpression <em>Task
     * Dependencies Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Task Dependencies Expression</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskDescription#getTaskDependenciesExpression()
     * @see #getTaskDescription()
     * @generated
     */
    EAttribute getTaskDescription_TaskDependenciesExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getSubTaskElementDescriptions <em>Sub Task
     * Element Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Sub Task Element Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskDescription#getSubTaskElementDescriptions()
     * @see #getTaskDescription()
     * @generated
     */
    EReference getTaskDescription_SubTaskElementDescriptions();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getReusedTaskElementDescriptions <em>Reused Task
     * Element Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Reused Task Element Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskDescription#getReusedTaskElementDescriptions()
     * @see #getTaskDescription()
     * @generated
     */
    EReference getTaskDescription_ReusedTaskElementDescriptions();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.gantt.TaskTool <em>Task Tool</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Task Tool</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskTool
     * @generated
     */
    EClass getTaskTool();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.gantt.TaskTool#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskTool#getName()
     * @see #getTaskTool()
     * @generated
     */
    EAttribute getTaskTool_Name();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.gantt.TaskTool#getBody <em>Body</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.gantt.TaskTool#getBody()
     * @see #getTaskTool()
     * @generated
     */
    EReference getTaskTool_Body();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.gantt.CreateTaskTool <em>Create Task
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Create Task Tool</em>'.
     * @see org.eclipse.sirius.components.view.gantt.CreateTaskTool
     * @generated
     */
    EClass getCreateTaskTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.gantt.EditTaskTool <em>Edit Task
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edit Task Tool</em>'.
     * @see org.eclipse.sirius.components.view.gantt.EditTaskTool
     * @generated
     */
    EClass getEditTaskTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.gantt.DeleteTaskTool <em>Delete Task
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Delete Task Tool</em>'.
     * @see org.eclipse.sirius.components.view.gantt.DeleteTaskTool
     * @generated
     */
    EClass getDeleteTaskTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.gantt.DropTaskTool <em>Drop Task
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Drop Task Tool</em>'.
     * @see org.eclipse.sirius.components.view.gantt.DropTaskTool
     * @generated
     */
    EClass getDropTaskTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.gantt.CreateTaskDependencyTool
     * <em>Create Task Dependency Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Create Task Dependency Tool</em>'.
     * @see org.eclipse.sirius.components.view.gantt.CreateTaskDependencyTool
     * @generated
     */
    EClass getCreateTaskDependencyTool();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    GanttFactory getGanttFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each operation of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     *
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl
         * <em>Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl
         * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getGanttDescription()
         * @generated
         */
        EClass GANTT_DESCRIPTION = eINSTANCE.getGanttDescription();

        /**
         * The meta object literal for the '<em><b>Task Element Descriptions</b></em>' containment reference list
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS = eINSTANCE.getGanttDescription_TaskElementDescriptions();

        /**
         * The meta object literal for the '<em><b>Create Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference GANTT_DESCRIPTION__CREATE_TOOL = eINSTANCE.getGanttDescription_CreateTool();

        /**
         * The meta object literal for the '<em><b>Edit Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference GANTT_DESCRIPTION__EDIT_TOOL = eINSTANCE.getGanttDescription_EditTool();

        /**
         * The meta object literal for the '<em><b>Delete Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference GANTT_DESCRIPTION__DELETE_TOOL = eINSTANCE.getGanttDescription_DeleteTool();

        /**
         * The meta object literal for the '<em><b>Drop Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference GANTT_DESCRIPTION__DROP_TOOL = eINSTANCE.getGanttDescription_DropTool();

        /**
         * The meta object literal for the '<em><b>Create Task Dependency Tool</b></em>' containment reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL = eINSTANCE.getGanttDescription_CreateTaskDependencyTool();

        /**
         * The meta object literal for the '<em><b>Date Rounding Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute GANTT_DESCRIPTION__DATE_ROUNDING_EXPRESSION = eINSTANCE.getGanttDescription_DateRoundingExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.gantt.impl.TaskDescriptionImpl
         * <em>Task Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.gantt.impl.TaskDescriptionImpl
         * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getTaskDescription()
         * @generated
         */
        EClass TASK_DESCRIPTION = eINSTANCE.getTaskDescription();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute TASK_DESCRIPTION__NAME = eINSTANCE.getTaskDescription_Name();

        /**
         * The meta object literal for the '<em><b>Domain Type</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute TASK_DESCRIPTION__DOMAIN_TYPE = eINSTANCE.getTaskDescription_DomainType();

        /**
         * The meta object literal for the '<em><b>Semantic Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = eINSTANCE.getTaskDescription_SemanticCandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Name Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TASK_DESCRIPTION__NAME_EXPRESSION = eINSTANCE.getTaskDescription_NameExpression();

        /**
         * The meta object literal for the '<em><b>Description Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TASK_DESCRIPTION__DESCRIPTION_EXPRESSION = eINSTANCE.getTaskDescription_DescriptionExpression();

        /**
         * The meta object literal for the '<em><b>Start Time Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TASK_DESCRIPTION__START_TIME_EXPRESSION = eINSTANCE.getTaskDescription_StartTimeExpression();

        /**
         * The meta object literal for the '<em><b>End Time Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TASK_DESCRIPTION__END_TIME_EXPRESSION = eINSTANCE.getTaskDescription_EndTimeExpression();

        /**
         * The meta object literal for the '<em><b>Progress Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TASK_DESCRIPTION__PROGRESS_EXPRESSION = eINSTANCE.getTaskDescription_ProgressExpression();

        /**
         * The meta object literal for the '<em><b>Compute Start End Dynamically Expression</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TASK_DESCRIPTION__COMPUTE_START_END_DYNAMICALLY_EXPRESSION = eINSTANCE.getTaskDescription_ComputeStartEndDynamicallyExpression();

        /**
         * The meta object literal for the '<em><b>Task Dependencies Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TASK_DESCRIPTION__TASK_DEPENDENCIES_EXPRESSION = eINSTANCE.getTaskDescription_TaskDependenciesExpression();

        /**
         * The meta object literal for the '<em><b>Sub Task Element Descriptions</b></em>' containment reference list
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TASK_DESCRIPTION__SUB_TASK_ELEMENT_DESCRIPTIONS = eINSTANCE.getTaskDescription_SubTaskElementDescriptions();

        /**
         * The meta object literal for the '<em><b>Reused Task Element Descriptions</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TASK_DESCRIPTION__REUSED_TASK_ELEMENT_DESCRIPTIONS = eINSTANCE.getTaskDescription_ReusedTaskElementDescriptions();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.gantt.impl.TaskToolImpl <em>Task
         * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.gantt.impl.TaskToolImpl
         * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getTaskTool()
         * @generated
         */
        EClass TASK_TOOL = eINSTANCE.getTaskTool();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute TASK_TOOL__NAME = eINSTANCE.getTaskTool_Name();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TASK_TOOL__BODY = eINSTANCE.getTaskTool_Body();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.gantt.impl.CreateTaskToolImpl
         * <em>Create Task Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.gantt.impl.CreateTaskToolImpl
         * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getCreateTaskTool()
         * @generated
         */
        EClass CREATE_TASK_TOOL = eINSTANCE.getCreateTaskTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.gantt.impl.EditTaskToolImpl
         * <em>Edit Task Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.gantt.impl.EditTaskToolImpl
         * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getEditTaskTool()
         * @generated
         */
        EClass EDIT_TASK_TOOL = eINSTANCE.getEditTaskTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.gantt.impl.DeleteTaskToolImpl
         * <em>Delete Task Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.gantt.impl.DeleteTaskToolImpl
         * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getDeleteTaskTool()
         * @generated
         */
        EClass DELETE_TASK_TOOL = eINSTANCE.getDeleteTaskTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.gantt.impl.DropTaskToolImpl
         * <em>Drop Task Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.gantt.impl.DropTaskToolImpl
         * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getDropTaskTool()
         * @generated
         */
        EClass DROP_TASK_TOOL = eINSTANCE.getDropTaskTool();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.gantt.impl.CreateTaskDependencyToolImpl <em>Create Task Dependency
         * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.gantt.impl.CreateTaskDependencyToolImpl
         * @see org.eclipse.sirius.components.view.gantt.impl.GanttPackageImpl#getCreateTaskDependencyTool()
         * @generated
         */
        EClass CREATE_TASK_DEPENDENCY_TOOL = eINSTANCE.getCreateTaskDependencyTool();

    }

} // GanttPackage
