/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.gantt.CreateTaskDependencyTool;
import org.eclipse.sirius.components.view.gantt.CreateTaskTool;
import org.eclipse.sirius.components.view.gantt.DeleteTaskDependencyTool;
import org.eclipse.sirius.components.view.gantt.DeleteTaskTool;
import org.eclipse.sirius.components.view.gantt.DropTaskTool;
import org.eclipse.sirius.components.view.gantt.EditTaskTool;
import org.eclipse.sirius.components.view.gantt.GanttDescription;
import org.eclipse.sirius.components.view.gantt.GanttFactory;
import org.eclipse.sirius.components.view.gantt.GanttPackage;
import org.eclipse.sirius.components.view.gantt.TaskDescription;
import org.eclipse.sirius.components.view.gantt.TaskTool;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * @generated
 */
public class GanttPackageImpl extends EPackageImpl implements GanttPackage {
    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass ganttDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass taskDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass taskToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass createTaskToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass editTaskToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass deleteTaskToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass dropTaskToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass createTaskDependencyToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass deleteTaskDependencyToolEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private GanttPackageImpl() {
		super(eNS_URI, GanttFactory.eINSTANCE);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private static boolean isInited = false;

    /**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link GanttPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
    public static GanttPackage init() {
		if (isInited) return (GanttPackage)EPackage.Registry.INSTANCE.getEPackage(GanttPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredGanttPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		GanttPackageImpl theGanttPackage = registeredGanttPackage instanceof GanttPackageImpl ? (GanttPackageImpl)registeredGanttPackage : new GanttPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		ViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theGanttPackage.createPackageContents();

		// Initialize created meta-data
		theGanttPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGanttPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(GanttPackage.eNS_URI, theGanttPackage);
		return theGanttPackage;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getGanttDescription() {
		return ganttDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getGanttDescription_TaskElementDescriptions() {
		return (EReference)ganttDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getGanttDescription_CreateTool() {
		return (EReference)ganttDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getGanttDescription_EditTool() {
		return (EReference)ganttDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getGanttDescription_DeleteTool() {
		return (EReference)ganttDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getGanttDescription_DropTool() {
		return (EReference)ganttDescriptionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getGanttDescription_CreateTaskDependencyTool() {
		return (EReference)ganttDescriptionEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getGanttDescription_DeleteTaskDependencyTool() {
		return (EReference)ganttDescriptionEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getGanttDescription_DateRoundingExpression() {
		return (EAttribute)ganttDescriptionEClass.getEStructuralFeatures().get(7);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getTaskDescription() {
		return taskDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTaskDescription_Name() {
		return (EAttribute)taskDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTaskDescription_DomainType() {
		return (EAttribute)taskDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTaskDescription_SemanticCandidatesExpression() {
		return (EAttribute)taskDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTaskDescription_NameExpression() {
		return (EAttribute)taskDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTaskDescription_DescriptionExpression() {
		return (EAttribute)taskDescriptionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTaskDescription_StartTimeExpression() {
		return (EAttribute)taskDescriptionEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTaskDescription_EndTimeExpression() {
		return (EAttribute)taskDescriptionEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTaskDescription_ProgressExpression() {
		return (EAttribute)taskDescriptionEClass.getEStructuralFeatures().get(7);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTaskDescription_ComputeStartEndDynamicallyExpression() {
		return (EAttribute)taskDescriptionEClass.getEStructuralFeatures().get(8);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTaskDescription_TaskDependenciesExpression() {
		return (EAttribute)taskDescriptionEClass.getEStructuralFeatures().get(9);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getTaskDescription_SubTaskElementDescriptions() {
		return (EReference)taskDescriptionEClass.getEStructuralFeatures().get(10);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getTaskDescription_ReusedTaskElementDescriptions() {
		return (EReference)taskDescriptionEClass.getEStructuralFeatures().get(11);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getTaskTool() {
		return taskToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTaskTool_Name() {
		return (EAttribute)taskToolEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getTaskTool_Body() {
		return (EReference)taskToolEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getCreateTaskTool() {
		return createTaskToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getEditTaskTool() {
		return editTaskToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDeleteTaskTool() {
		return deleteTaskToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDropTaskTool() {
		return dropTaskToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getCreateTaskDependencyTool() {
		return createTaskDependencyToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDeleteTaskDependencyTool() {
		return deleteTaskDependencyToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public GanttFactory getGanttFactory() {
		return (GanttFactory)getEFactoryInstance();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private boolean isCreated = false;

    /**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		ganttDescriptionEClass = createEClass(GANTT_DESCRIPTION);
		createEReference(ganttDescriptionEClass, GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS);
		createEReference(ganttDescriptionEClass, GANTT_DESCRIPTION__CREATE_TOOL);
		createEReference(ganttDescriptionEClass, GANTT_DESCRIPTION__EDIT_TOOL);
		createEReference(ganttDescriptionEClass, GANTT_DESCRIPTION__DELETE_TOOL);
		createEReference(ganttDescriptionEClass, GANTT_DESCRIPTION__DROP_TOOL);
		createEReference(ganttDescriptionEClass, GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL);
		createEReference(ganttDescriptionEClass, GANTT_DESCRIPTION__DELETE_TASK_DEPENDENCY_TOOL);
		createEAttribute(ganttDescriptionEClass, GANTT_DESCRIPTION__DATE_ROUNDING_EXPRESSION);

		taskDescriptionEClass = createEClass(TASK_DESCRIPTION);
		createEAttribute(taskDescriptionEClass, TASK_DESCRIPTION__NAME);
		createEAttribute(taskDescriptionEClass, TASK_DESCRIPTION__DOMAIN_TYPE);
		createEAttribute(taskDescriptionEClass, TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
		createEAttribute(taskDescriptionEClass, TASK_DESCRIPTION__NAME_EXPRESSION);
		createEAttribute(taskDescriptionEClass, TASK_DESCRIPTION__DESCRIPTION_EXPRESSION);
		createEAttribute(taskDescriptionEClass, TASK_DESCRIPTION__START_TIME_EXPRESSION);
		createEAttribute(taskDescriptionEClass, TASK_DESCRIPTION__END_TIME_EXPRESSION);
		createEAttribute(taskDescriptionEClass, TASK_DESCRIPTION__PROGRESS_EXPRESSION);
		createEAttribute(taskDescriptionEClass, TASK_DESCRIPTION__COMPUTE_START_END_DYNAMICALLY_EXPRESSION);
		createEAttribute(taskDescriptionEClass, TASK_DESCRIPTION__TASK_DEPENDENCIES_EXPRESSION);
		createEReference(taskDescriptionEClass, TASK_DESCRIPTION__SUB_TASK_ELEMENT_DESCRIPTIONS);
		createEReference(taskDescriptionEClass, TASK_DESCRIPTION__REUSED_TASK_ELEMENT_DESCRIPTIONS);

		taskToolEClass = createEClass(TASK_TOOL);
		createEAttribute(taskToolEClass, TASK_TOOL__NAME);
		createEReference(taskToolEClass, TASK_TOOL__BODY);

		createTaskToolEClass = createEClass(CREATE_TASK_TOOL);

		editTaskToolEClass = createEClass(EDIT_TASK_TOOL);

		deleteTaskToolEClass = createEClass(DELETE_TASK_TOOL);

		dropTaskToolEClass = createEClass(DROP_TASK_TOOL);

		createTaskDependencyToolEClass = createEClass(CREATE_TASK_DEPENDENCY_TOOL);

		deleteTaskDependencyToolEClass = createEClass(DELETE_TASK_DEPENDENCY_TOOL);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private boolean isInitialized = false;

    /**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ViewPackage theViewPackage = (ViewPackage)EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		ganttDescriptionEClass.getESuperTypes().add(theViewPackage.getRepresentationDescription());
		createTaskToolEClass.getESuperTypes().add(this.getTaskTool());
		editTaskToolEClass.getESuperTypes().add(this.getTaskTool());
		deleteTaskToolEClass.getESuperTypes().add(this.getTaskTool());
		dropTaskToolEClass.getESuperTypes().add(this.getTaskTool());
		createTaskDependencyToolEClass.getESuperTypes().add(this.getTaskTool());
		deleteTaskDependencyToolEClass.getESuperTypes().add(this.getTaskTool());

		// Initialize classes, features, and operations; add parameters
		initEClass(ganttDescriptionEClass, GanttDescription.class, "GanttDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGanttDescription_TaskElementDescriptions(), this.getTaskDescription(), null, "taskElementDescriptions", null, 0, -1, GanttDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getGanttDescription_TaskElementDescriptions().getEKeys().add(this.getTaskDescription_Name());
		initEReference(getGanttDescription_CreateTool(), this.getCreateTaskTool(), null, "createTool", null, 0, 1, GanttDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGanttDescription_EditTool(), this.getEditTaskTool(), null, "editTool", null, 0, 1, GanttDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGanttDescription_DeleteTool(), this.getDeleteTaskTool(), null, "deleteTool", null, 0, 1, GanttDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGanttDescription_DropTool(), this.getDropTaskTool(), null, "dropTool", null, 0, 1, GanttDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGanttDescription_CreateTaskDependencyTool(), this.getCreateTaskDependencyTool(), null, "createTaskDependencyTool", null, 0, 1, GanttDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGanttDescription_DeleteTaskDependencyTool(), this.getDeleteTaskDependencyTool(), null, "deleteTaskDependencyTool", null, 0, 1, GanttDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGanttDescription_DateRoundingExpression(), theViewPackage.getInterpretedExpression(), "dateRoundingExpression", "12H", 0, 1, GanttDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(taskDescriptionEClass, TaskDescription.class, "TaskDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTaskDescription_Name(), theViewPackage.getIdentifier(), "name", "", 1, 1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskDescription_DomainType(), theViewPackage.getDomainType(), "domainType", null, 0, 1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskDescription_SemanticCandidatesExpression(), theViewPackage.getInterpretedExpression(), "semanticCandidatesExpression", "aql:self", 1, 1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskDescription_NameExpression(), theViewPackage.getInterpretedExpression(), "nameExpression", "aql:self.name", 0, 1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskDescription_DescriptionExpression(), theViewPackage.getInterpretedExpression(), "descriptionExpression", "aql:self.description", 0, 1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskDescription_StartTimeExpression(), theViewPackage.getInterpretedExpression(), "startTimeExpression", "aql:self.startTime", 0, 1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskDescription_EndTimeExpression(), theViewPackage.getInterpretedExpression(), "endTimeExpression", "aql:self.endTime", 0, 1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskDescription_ProgressExpression(), theViewPackage.getInterpretedExpression(), "progressExpression", "aql:self.progress", 0, 1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskDescription_ComputeStartEndDynamicallyExpression(), theViewPackage.getInterpretedExpression(), "computeStartEndDynamicallyExpression", "aql:self.computeStartEndDynamically", 0, 1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskDescription_TaskDependenciesExpression(), theViewPackage.getInterpretedExpression(), "taskDependenciesExpression", "aql:self.dependencies", 0, 1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTaskDescription_SubTaskElementDescriptions(), this.getTaskDescription(), null, "subTaskElementDescriptions", null, 0, -1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getTaskDescription_SubTaskElementDescriptions().getEKeys().add(this.getTaskDescription_Name());
		initEReference(getTaskDescription_ReusedTaskElementDescriptions(), this.getTaskDescription(), null, "reusedTaskElementDescriptions", null, 0, -1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(taskToolEClass, TaskTool.class, "TaskTool", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTaskTool_Name(), theViewPackage.getIdentifier(), "name", "", 1, 1, TaskTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTaskTool_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, TaskTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(createTaskToolEClass, CreateTaskTool.class, "CreateTaskTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(editTaskToolEClass, EditTaskTool.class, "EditTaskTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(deleteTaskToolEClass, DeleteTaskTool.class, "DeleteTaskTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dropTaskToolEClass, DropTaskTool.class, "DropTaskTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(createTaskDependencyToolEClass, CreateTaskDependencyTool.class, "CreateTaskDependencyTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(deleteTaskDependencyToolEClass, DeleteTaskDependencyTool.class, "DeleteTaskDependencyTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // GanttPackageImpl
