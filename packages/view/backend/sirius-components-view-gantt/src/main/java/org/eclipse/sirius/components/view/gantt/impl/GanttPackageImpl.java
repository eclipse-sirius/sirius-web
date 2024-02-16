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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.gantt.ConditionalTaskStyle;
import org.eclipse.sirius.components.view.gantt.CreateTaskTool;
import org.eclipse.sirius.components.view.gantt.DeleteTaskTool;
import org.eclipse.sirius.components.view.gantt.DropTaskTool;
import org.eclipse.sirius.components.view.gantt.EditTaskTool;
import org.eclipse.sirius.components.view.gantt.GanttDescription;
import org.eclipse.sirius.components.view.gantt.GanttFactory;
import org.eclipse.sirius.components.view.gantt.GanttPackage;
import org.eclipse.sirius.components.view.gantt.TaskDescription;
import org.eclipse.sirius.components.view.gantt.TaskStyleDescription;
import org.eclipse.sirius.components.view.gantt.TaskTool;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class GanttPackageImpl extends EPackageImpl implements GanttPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass ganttDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass taskDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass taskStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalTaskStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass taskToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass createTaskToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass editTaskToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass deleteTaskToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dropTaskToolEClass = null;

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
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link GanttPackage#eINSTANCE} when that field is accessed. Clients should not
     * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static GanttPackage init() {
        if (isInited)
            return (GanttPackage) EPackage.Registry.INSTANCE.getEPackage(GanttPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredGanttPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        GanttPackageImpl theGanttPackage = registeredGanttPackage instanceof GanttPackageImpl ? (GanttPackageImpl) registeredGanttPackage : new GanttPackageImpl();

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
     *
     * @generated
     */
    @Override
    public EClass getGanttDescription() {
        return this.ganttDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getGanttDescription_TaskElementDescriptions() {
        return (EReference) this.ganttDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getGanttDescription_BackgroundColor() {
        return (EReference) this.ganttDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getGanttDescription_CreateTool() {
        return (EReference) this.ganttDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getGanttDescription_EditTool() {
        return (EReference) this.ganttDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getGanttDescription_DeleteTool() {
        return (EReference) this.ganttDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getGanttDescription_DropTool() {
        return (EReference) this.ganttDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTaskDescription() {
        return this.taskDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskDescription_Name() {
        return (EAttribute) this.taskDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskDescription_SemanticCandidatesExpression() {
        return (EAttribute) this.taskDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskDescription_NameExpression() {
        return (EAttribute) this.taskDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskDescription_DescriptionExpression() {
        return (EAttribute) this.taskDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskDescription_StartTimeExpression() {
        return (EAttribute) this.taskDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskDescription_EndTimeExpression() {
        return (EAttribute) this.taskDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskDescription_ProgressExpression() {
        return (EAttribute) this.taskDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskDescription_ComputeStartEndDynamicallyExpression() {
        return (EAttribute) this.taskDescriptionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskDescription_DependenciesExpression() {
        return (EAttribute) this.taskDescriptionEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTaskDescription_Style() {
        return (EReference) this.taskDescriptionEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTaskDescription_ConditionalStyles() {
        return (EReference) this.taskDescriptionEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTaskDescription_SubTaskElementDescriptions() {
        return (EReference) this.taskDescriptionEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTaskDescription_ReusedTaskElementDescriptions() {
        return (EReference) this.taskDescriptionEClass.getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTaskStyleDescription() {
        return this.taskStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskStyleDescription_LabelColorExpression() {
        return (EAttribute) this.taskStyleDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskStyleDescription_BackgroundColorExpression() {
        return (EAttribute) this.taskStyleDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskStyleDescription_ProgressColorExpression() {
        return (EAttribute) this.taskStyleDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalTaskStyle() {
        return this.conditionalTaskStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getConditionalTaskStyle_Style() {
        return (EReference) this.conditionalTaskStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTaskTool() {
        return this.taskToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskTool_Name() {
        return (EAttribute) this.taskToolEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskTool_PreconditionExpression() {
        return (EAttribute) this.taskToolEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTaskTool_Body() {
        return (EReference) this.taskToolEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCreateTaskTool() {
        return this.createTaskToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEditTaskTool() {
        return this.editTaskToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDeleteTaskTool() {
        return this.deleteTaskToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDropTaskTool() {
        return this.dropTaskToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public GanttFactory getGanttFactory() {
        return (GanttFactory) this.getEFactoryInstance();
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
        this.ganttDescriptionEClass = this.createEClass(GANTT_DESCRIPTION);
        this.createEReference(this.ganttDescriptionEClass, GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS);
        this.createEReference(this.ganttDescriptionEClass, GANTT_DESCRIPTION__BACKGROUND_COLOR);
        this.createEReference(this.ganttDescriptionEClass, GANTT_DESCRIPTION__CREATE_TOOL);
        this.createEReference(this.ganttDescriptionEClass, GANTT_DESCRIPTION__EDIT_TOOL);
        this.createEReference(this.ganttDescriptionEClass, GANTT_DESCRIPTION__DELETE_TOOL);
        this.createEReference(this.ganttDescriptionEClass, GANTT_DESCRIPTION__DROP_TOOL);

        this.taskDescriptionEClass = this.createEClass(TASK_DESCRIPTION);
        this.createEAttribute(this.taskDescriptionEClass, TASK_DESCRIPTION__NAME);
        this.createEAttribute(this.taskDescriptionEClass, TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
        this.createEAttribute(this.taskDescriptionEClass, TASK_DESCRIPTION__NAME_EXPRESSION);
        this.createEAttribute(this.taskDescriptionEClass, TASK_DESCRIPTION__DESCRIPTION_EXPRESSION);
        this.createEAttribute(this.taskDescriptionEClass, TASK_DESCRIPTION__START_TIME_EXPRESSION);
        this.createEAttribute(this.taskDescriptionEClass, TASK_DESCRIPTION__END_TIME_EXPRESSION);
        this.createEAttribute(this.taskDescriptionEClass, TASK_DESCRIPTION__PROGRESS_EXPRESSION);
        this.createEAttribute(this.taskDescriptionEClass, TASK_DESCRIPTION__COMPUTE_START_END_DYNAMICALLY_EXPRESSION);
        this.createEAttribute(this.taskDescriptionEClass, TASK_DESCRIPTION__DEPENDENCIES_EXPRESSION);
        this.createEReference(this.taskDescriptionEClass, TASK_DESCRIPTION__STYLE);
        this.createEReference(this.taskDescriptionEClass, TASK_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEReference(this.taskDescriptionEClass, TASK_DESCRIPTION__SUB_TASK_ELEMENT_DESCRIPTIONS);
        this.createEReference(this.taskDescriptionEClass, TASK_DESCRIPTION__REUSED_TASK_ELEMENT_DESCRIPTIONS);

        this.taskStyleDescriptionEClass = this.createEClass(TASK_STYLE_DESCRIPTION);
        this.createEAttribute(this.taskStyleDescriptionEClass, TASK_STYLE_DESCRIPTION__LABEL_COLOR_EXPRESSION);
        this.createEAttribute(this.taskStyleDescriptionEClass, TASK_STYLE_DESCRIPTION__BACKGROUND_COLOR_EXPRESSION);
        this.createEAttribute(this.taskStyleDescriptionEClass, TASK_STYLE_DESCRIPTION__PROGRESS_COLOR_EXPRESSION);

        this.conditionalTaskStyleEClass = this.createEClass(CONDITIONAL_TASK_STYLE);
        this.createEReference(this.conditionalTaskStyleEClass, CONDITIONAL_TASK_STYLE__STYLE);

        this.taskToolEClass = this.createEClass(TASK_TOOL);
        this.createEAttribute(this.taskToolEClass, TASK_TOOL__NAME);
        this.createEAttribute(this.taskToolEClass, TASK_TOOL__PRECONDITION_EXPRESSION);
        this.createEReference(this.taskToolEClass, TASK_TOOL__BODY);

        this.createTaskToolEClass = this.createEClass(CREATE_TASK_TOOL);

        this.editTaskToolEClass = this.createEClass(EDIT_TASK_TOOL);

        this.deleteTaskToolEClass = this.createEClass(DELETE_TASK_TOOL);

        this.dropTaskToolEClass = this.createEClass(DROP_TASK_TOOL);
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

        // Obtain other dependent packages
        ViewPackage theViewPackage = (ViewPackage) EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.ganttDescriptionEClass.getESuperTypes().add(theViewPackage.getRepresentationDescription());
        this.taskStyleDescriptionEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.conditionalTaskStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.createTaskToolEClass.getESuperTypes().add(this.getTaskTool());
        this.editTaskToolEClass.getESuperTypes().add(this.getTaskTool());
        this.deleteTaskToolEClass.getESuperTypes().add(this.getTaskTool());
        this.dropTaskToolEClass.getESuperTypes().add(this.getTaskTool());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.ganttDescriptionEClass, GanttDescription.class, "GanttDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getGanttDescription_TaskElementDescriptions(), this.getTaskDescription(), null, "taskElementDescriptions", null, 0, -1, GanttDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getGanttDescription_BackgroundColor(), theViewPackage.getUserColor(), null, "backgroundColor", null, 0, 1, GanttDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getGanttDescription_CreateTool(), this.getCreateTaskTool(), null, "createTool", null, 0, 1, GanttDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getGanttDescription_EditTool(), this.getEditTaskTool(), null, "editTool", null, 0, 1, GanttDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getGanttDescription_DeleteTool(), this.getDeleteTaskTool(), null, "deleteTool", null, 0, 1, GanttDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getGanttDescription_DropTool(), this.getDropTaskTool(), null, "dropTool", null, 0, 1, GanttDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.taskDescriptionEClass, TaskDescription.class, "TaskDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTaskDescription_Name(), theViewPackage.getIdentifier(), "name", "", 1, 1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTaskDescription_SemanticCandidatesExpression(), theViewPackage.getInterpretedExpression(), "semanticCandidatesExpression", "aql:self", 1, 1, TaskDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTaskDescription_NameExpression(), theViewPackage.getInterpretedExpression(), "nameExpression", "aql:self.name", 0, 1, TaskDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTaskDescription_DescriptionExpression(), theViewPackage.getInterpretedExpression(), "descriptionExpression", "aql:self.description", 0, 1, TaskDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTaskDescription_StartTimeExpression(), theViewPackage.getInterpretedExpression(), "startTimeExpression", "aql:self.startTime", 0, 1, TaskDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTaskDescription_EndTimeExpression(), theViewPackage.getInterpretedExpression(), "endTimeExpression", "aql:self.endTime", 0, 1, TaskDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTaskDescription_ProgressExpression(), theViewPackage.getInterpretedExpression(), "progressExpression", "aql:self.progress", 0, 1, TaskDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTaskDescription_ComputeStartEndDynamicallyExpression(), theViewPackage.getInterpretedExpression(), "computeStartEndDynamicallyExpression",
                "aql:self.computeStartEndDynamically", 0, 1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTaskDescription_DependenciesExpression(), theViewPackage.getInterpretedExpression(), "dependenciesExpression", "aql:self.dependencies", 0, 1, TaskDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTaskDescription_Style(), this.getTaskStyleDescription(), null, "style", null, 1, 1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTaskDescription_ConditionalStyles(), this.getConditionalTaskStyle(), null, "conditionalStyles", null, 0, -1, TaskDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTaskDescription_SubTaskElementDescriptions(), this.getTaskDescription(), null, "subTaskElementDescriptions", null, 0, -1, TaskDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTaskDescription_ReusedTaskElementDescriptions(), this.getTaskDescription(), null, "reusedTaskElementDescriptions", null, 0, -1, TaskDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.taskStyleDescriptionEClass, TaskStyleDescription.class, "TaskStyleDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTaskStyleDescription_LabelColorExpression(), theViewPackage.getInterpretedExpression(), "labelColorExpression", "aql:self", 0, 1, TaskStyleDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTaskStyleDescription_BackgroundColorExpression(), theViewPackage.getInterpretedExpression(), "backgroundColorExpression", "aql:self", 0, 1,
                TaskStyleDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTaskStyleDescription_ProgressColorExpression(), theViewPackage.getInterpretedExpression(), "progressColorExpression", "aql:self", 0, 1, TaskStyleDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalTaskStyleEClass, ConditionalTaskStyle.class, "ConditionalTaskStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getConditionalTaskStyle_Style(), this.getTaskStyleDescription(), null, "style", null, 1, 1, ConditionalTaskStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.taskToolEClass, TaskTool.class, "TaskTool", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTaskTool_Name(), theViewPackage.getIdentifier(), "name", "", 1, 1, TaskTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTaskTool_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", "", 0, 1, TaskTool.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTaskTool_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, TaskTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.createTaskToolEClass, CreateTaskTool.class, "CreateTaskTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.editTaskToolEClass, EditTaskTool.class, "EditTaskTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.deleteTaskToolEClass, DeleteTaskTool.class, "DeleteTaskTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.dropTaskToolEClass, DropTaskTool.class, "DropTaskTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        this.createResource(eNS_URI);
    }

} // GanttPackageImpl
