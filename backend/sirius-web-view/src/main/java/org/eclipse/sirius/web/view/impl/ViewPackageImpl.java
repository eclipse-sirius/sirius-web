/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.view.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.web.view.DiagramDescription;
import org.eclipse.sirius.web.view.DiagramElementDescription;
import org.eclipse.sirius.web.view.EdgeDescription;
import org.eclipse.sirius.web.view.NodeDescription;
import org.eclipse.sirius.web.view.NodeStyle;
import org.eclipse.sirius.web.view.RepresentationDescription;
import org.eclipse.sirius.web.view.Style;
import org.eclipse.sirius.web.view.View;
import org.eclipse.sirius.web.view.ViewFactory;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class ViewPackageImpl extends EPackageImpl implements ViewPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass viewEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass representationDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass diagramDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass diagramElementDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass nodeDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass edgeDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass styleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass nodeStyleEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.web.view.ViewPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ViewPackageImpl() {
        super(eNS_URI, ViewFactory.eINSTANCE);
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
     * This method is used to initialize {@link ViewPackage#eINSTANCE} when that field is accessed. Clients should not
     * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ViewPackage init() {
        if (isInited)
            return (ViewPackage) EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredViewPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        ViewPackageImpl theViewPackage = registeredViewPackage instanceof ViewPackageImpl ? (ViewPackageImpl) registeredViewPackage : new ViewPackageImpl();

        isInited = true;

        // Create package meta-data objects
        theViewPackage.createPackageContents();

        // Initialize created meta-data
        theViewPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theViewPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ViewPackage.eNS_URI, theViewPackage);
        return theViewPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getView() {
        return this.viewEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getView_Descriptions() {
        return (EReference) this.viewEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRepresentationDescription() {
        return this.representationDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRepresentationDescription_Name() {
        return (EAttribute) this.representationDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRepresentationDescription_DomainType() {
        return (EAttribute) this.representationDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRepresentationDescription_TitleExpression() {
        return (EAttribute) this.representationDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDiagramDescription() {
        return this.diagramDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramDescription_NodeDescriptions() {
        return (EReference) this.diagramDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramDescription_EdgeDescriptions() {
        return (EReference) this.diagramDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDiagramElementDescription() {
        return this.diagramElementDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramElementDescription_DomainType() {
        return (EAttribute) this.diagramElementDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramElementDescription_SemanticCandidatesExpression() {
        return (EAttribute) this.diagramElementDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramElementDescription_LabelExpression() {
        return (EAttribute) this.diagramElementDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNodeDescription() {
        return this.nodeDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_ChildrenDescriptions() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_Style() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEdgeDescription() {
        return this.edgeDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeDescription_IsDomainBasedEdge() {
        return (EAttribute) this.edgeDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeDescription_SourceNodeDescription() {
        return (EReference) this.edgeDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeDescription_TargetNodeDescription() {
        return (EReference) this.edgeDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeDescription_SourceNodesExpression() {
        return (EAttribute) this.edgeDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeDescription_TargetNodesExpression() {
        return (EAttribute) this.edgeDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeDescription_Style() {
        return (EReference) this.edgeDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getStyle() {
        return this.styleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getStyle_Color() {
        return (EAttribute) this.styleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNodeStyle() {
        return this.nodeStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeStyle_Shape() {
        return (EAttribute) this.nodeStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ViewFactory getViewFactory() {
        return (ViewFactory) this.getEFactoryInstance();
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
        this.viewEClass = this.createEClass(VIEW);
        this.createEReference(this.viewEClass, VIEW__DESCRIPTIONS);

        this.representationDescriptionEClass = this.createEClass(REPRESENTATION_DESCRIPTION);
        this.createEAttribute(this.representationDescriptionEClass, REPRESENTATION_DESCRIPTION__NAME);
        this.createEAttribute(this.representationDescriptionEClass, REPRESENTATION_DESCRIPTION__DOMAIN_TYPE);
        this.createEAttribute(this.representationDescriptionEClass, REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION);

        this.diagramDescriptionEClass = this.createEClass(DIAGRAM_DESCRIPTION);
        this.createEReference(this.diagramDescriptionEClass, DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS);
        this.createEReference(this.diagramDescriptionEClass, DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS);

        this.diagramElementDescriptionEClass = this.createEClass(DIAGRAM_ELEMENT_DESCRIPTION);
        this.createEAttribute(this.diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE);
        this.createEAttribute(this.diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
        this.createEAttribute(this.diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION);

        this.nodeDescriptionEClass = this.createEClass(NODE_DESCRIPTION);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__STYLE);

        this.edgeDescriptionEClass = this.createEClass(EDGE_DESCRIPTION);
        this.createEAttribute(this.edgeDescriptionEClass, EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE);
        this.createEReference(this.edgeDescriptionEClass, EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTION);
        this.createEReference(this.edgeDescriptionEClass, EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTION);
        this.createEAttribute(this.edgeDescriptionEClass, EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION);
        this.createEAttribute(this.edgeDescriptionEClass, EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION);
        this.createEReference(this.edgeDescriptionEClass, EDGE_DESCRIPTION__STYLE);

        this.styleEClass = this.createEClass(STYLE);
        this.createEAttribute(this.styleEClass, STYLE__COLOR);

        this.nodeStyleEClass = this.createEClass(NODE_STYLE);
        this.createEAttribute(this.nodeStyleEClass, NODE_STYLE__SHAPE);
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
        this.diagramDescriptionEClass.getESuperTypes().add(this.getRepresentationDescription());
        this.nodeDescriptionEClass.getESuperTypes().add(this.getDiagramElementDescription());
        this.edgeDescriptionEClass.getESuperTypes().add(this.getDiagramElementDescription());
        this.nodeStyleEClass.getESuperTypes().add(this.getStyle());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.viewEClass, View.class, "View", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        this.initEReference(this.getView_Descriptions(), this.getRepresentationDescription(), null, "descriptions", null, 0, -1, View.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, //$NON-NLS-1$
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.representationDescriptionEClass, RepresentationDescription.class, "RepresentationDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        this.initEAttribute(this.getRepresentationDescription_Name(), this.ecorePackage.getEString(), "name", null, 0, 1, RepresentationDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getRepresentationDescription_DomainType(), this.ecorePackage.getEString(), "domainType", null, 0, 1, RepresentationDescription.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getRepresentationDescription_TitleExpression(), this.ecorePackage.getEString(), "titleExpression", null, 0, 1, RepresentationDescription.class, !IS_TRANSIENT, //$NON-NLS-1$
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.diagramDescriptionEClass, DiagramDescription.class, "DiagramDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        this.initEReference(this.getDiagramDescription_NodeDescriptions(), this.getNodeDescription(), null, "nodeDescriptions", null, 0, -1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDiagramDescription_EdgeDescriptions(), this.getEdgeDescription(), null, "edgeDescriptions", null, 0, -1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.diagramElementDescriptionEClass, DiagramElementDescription.class, "DiagramElementDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        this.initEAttribute(this.getDiagramElementDescription_DomainType(), this.ecorePackage.getEString(), "domainType", null, 0, 1, DiagramElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDiagramElementDescription_SemanticCandidatesExpression(), this.ecorePackage.getEString(), "semanticCandidatesExpression", null, 0, 1, //$NON-NLS-1$
                DiagramElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDiagramElementDescription_LabelExpression(), this.ecorePackage.getEString(), "labelExpression", null, 0, 1, DiagramElementDescription.class, !IS_TRANSIENT, //$NON-NLS-1$
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.nodeDescriptionEClass, NodeDescription.class, "NodeDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        this.initEReference(this.getNodeDescription_ChildrenDescriptions(), this.getNodeDescription(), null, "childrenDescriptions", null, 0, -1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_Style(), this.getNodeStyle(), null, "style", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, //$NON-NLS-1$
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.edgeDescriptionEClass, EdgeDescription.class, "EdgeDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        this.initEAttribute(this.getEdgeDescription_IsDomainBasedEdge(), this.ecorePackage.getEBoolean(), "isDomainBasedEdge", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgeDescription_SourceNodeDescription(), this.getNodeDescription(), null, "sourceNodeDescription", null, 1, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgeDescription_TargetNodeDescription(), this.getNodeDescription(), null, "targetNodeDescription", null, 1, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeDescription_SourceNodesExpression(), this.ecorePackage.getEString(), "sourceNodesExpression", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeDescription_TargetNodesExpression(), this.ecorePackage.getEString(), "targetNodesExpression", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgeDescription_Style(), this.getStyle(), null, "style", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, //$NON-NLS-1$
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.styleEClass, Style.class, "Style", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        this.initEAttribute(this.getStyle_Color(), this.ecorePackage.getEString(), "color", null, 0, 1, Style.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, //$NON-NLS-1$
                !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.nodeStyleEClass, NodeStyle.class, "NodeStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        this.initEAttribute(this.getNodeStyle_Shape(), this.ecorePackage.getEString(), "shape", null, 1, 1, NodeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, //$NON-NLS-1$
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        this.createResource(eNS_URI);
    }

} // ViewPackageImpl
