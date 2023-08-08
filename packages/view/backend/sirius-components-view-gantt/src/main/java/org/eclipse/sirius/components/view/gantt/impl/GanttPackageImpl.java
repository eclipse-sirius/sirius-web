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
package org.eclipse.sirius.components.view.gantt.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.gantt.TaskDetail;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.gantt.ConditionalTaskStyle;
import org.eclipse.sirius.components.view.gantt.GanttDescription;
import org.eclipse.sirius.components.view.gantt.GanttFactory;
import org.eclipse.sirius.components.view.gantt.GanttPackage;
import org.eclipse.sirius.components.view.gantt.TaskDescription;
import org.eclipse.sirius.components.view.gantt.TaskStyleDescription;

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
    private EDataType taskDetailEDataType = null;

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
    public EClass getTaskDescription() {
        return this.taskDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskDescription_SemanticCandidatesExpression() {
        return (EAttribute) this.taskDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTaskDescription_TaskDetailExpression() {
        return (EAttribute) this.taskDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTaskDescription_Style() {
        return (EReference) this.taskDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTaskDescription_ConditionalStyles() {
        return (EReference) this.taskDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTaskDescription_SubTaskElementDescriptions() {
        return (EReference) this.taskDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTaskDescription_ReusedTaskElementDescriptions() {
        return (EReference) this.taskDescriptionEClass.getEStructuralFeatures().get(5);
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
    public EDataType getTaskDetail() {
        return this.taskDetailEDataType;
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

        this.taskDescriptionEClass = this.createEClass(TASK_DESCRIPTION);
        this.createEAttribute(this.taskDescriptionEClass, TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
        this.createEAttribute(this.taskDescriptionEClass, TASK_DESCRIPTION__TASK_DETAIL_EXPRESSION);
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

        // Create data types
        this.taskDetailEDataType = this.createEDataType(TASK_DETAIL);
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

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.ganttDescriptionEClass, GanttDescription.class, "GanttDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getGanttDescription_TaskElementDescriptions(), this.getTaskDescription(), null, "taskElementDescriptions", null, 0, -1, GanttDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getGanttDescription_BackgroundColor(), theViewPackage.getUserColor(), null, "backgroundColor", null, 0, 1, GanttDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.taskDescriptionEClass, TaskDescription.class, "TaskDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTaskDescription_SemanticCandidatesExpression(), theViewPackage.getInterpretedExpression(), "semanticCandidatesExpression", "aql:self", 1, 1, TaskDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTaskDescription_TaskDetailExpression(), theViewPackage.getInterpretedExpression(), "taskDetailExpression", "aql:self", 0, 1, TaskDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
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

        // Initialize data types
        this.initEDataType(this.taskDetailEDataType, TaskDetail.class, "TaskDetail", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        this.createResource(eNS_URI);
    }

} // GanttPackageImpl
