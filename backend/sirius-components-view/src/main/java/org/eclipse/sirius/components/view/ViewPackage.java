/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.view;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.sirius.components.view.ViewFactory
 * @model kind="package"
 * @generated
 */
public interface ViewPackage extends EPackage {
    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "view"; //$NON-NLS-1$

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/sirius-web/view"; //$NON-NLS-1$

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "view"; //$NON-NLS-1$

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    ViewPackage eINSTANCE = org.eclipse.sirius.components.view.impl.ViewPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ViewImpl <em>View</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ViewImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getView()
     * @generated
     */
    int VIEW = 0;

    /**
     * The feature id for the '<em><b>Descriptions</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int VIEW__DESCRIPTIONS = 0;

    /**
     * The number of structural features of the '<em>View</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int VIEW_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>View</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int VIEW_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl
     * <em>Representation Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getRepresentationDescription()
     * @generated
     */
    int REPRESENTATION_DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REPRESENTATION_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REPRESENTATION_DESCRIPTION__DOMAIN_TYPE = 1;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REPRESENTATION_DESCRIPTION__PRECONDITION_EXPRESSION = 2;

    /**
     * The feature id for the '<em><b>Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION = 3;

    /**
     * The number of structural features of the '<em>Representation Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REPRESENTATION_DESCRIPTION_FEATURE_COUNT = 4;

    /**
     * The number of operations of the '<em>Representation Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REPRESENTATION_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.DiagramDescriptionImpl <em>Diagram
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.DiagramDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDiagramDescription()
     * @generated
     */
    int DIAGRAM_DESCRIPTION = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__NAME = REPRESENTATION_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__DOMAIN_TYPE = REPRESENTATION_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__PRECONDITION_EXPRESSION = REPRESENTATION_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__TITLE_EXPRESSION = REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION;

    /**
     * The feature id for the '<em><b>Auto Layout</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__AUTO_LAYOUT = REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Node Descriptions</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS = REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Edge Descriptions</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS = REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>On Drop</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__ON_DROP = REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Diagram Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION_FEATURE_COUNT = REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of operations of the '<em>Diagram Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION_OPERATION_COUNT = REPRESENTATION_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.DiagramElementDescriptionImpl
     * <em>Diagram Element Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.DiagramElementDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDiagramElementDescription()
     * @generated
     */
    int DIAGRAM_ELEMENT_DESCRIPTION = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE = 1;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = 2;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION = 3;

    /**
     * The feature id for the '<em><b>Delete Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__DELETE_TOOL = 4;

    /**
     * The feature id for the '<em><b>Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EDIT_TOOL = 5;

    /**
     * The feature id for the '<em><b>Synchronization Policy</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY = 6;

    /**
     * The number of structural features of the '<em>Diagram Element Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT = 7;

    /**
     * The number of operations of the '<em>Diagram Element Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.NodeDescriptionImpl <em>Node
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.NodeDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getNodeDescription()
     * @generated
     */
    int NODE_DESCRIPTION = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__NAME = DIAGRAM_ELEMENT_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__DOMAIN_TYPE = DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Delete Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__DELETE_TOOL = DIAGRAM_ELEMENT_DESCRIPTION__DELETE_TOOL;

    /**
     * The feature id for the '<em><b>Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__LABEL_EDIT_TOOL = DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EDIT_TOOL;

    /**
     * The feature id for the '<em><b>Synchronization Policy</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__SYNCHRONIZATION_POLICY = DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY;

    /**
     * The feature id for the '<em><b>Children Descriptions</b></em>' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Border Nodes Descriptions</b></em>' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__STYLE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Node Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__NODE_TOOLS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__CONDITIONAL_STYLES = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Node Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION_FEATURE_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of operations of the '<em>Node Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION_OPERATION_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.EdgeDescriptionImpl <em>Edge
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.EdgeDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getEdgeDescription()
     * @generated
     */
    int EDGE_DESCRIPTION = 5;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__NAME = DIAGRAM_ELEMENT_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__DOMAIN_TYPE = DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Delete Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__DELETE_TOOL = DIAGRAM_ELEMENT_DESCRIPTION__DELETE_TOOL;

    /**
     * The feature id for the '<em><b>Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__LABEL_EDIT_TOOL = DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EDIT_TOOL;

    /**
     * The feature id for the '<em><b>Synchronization Policy</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__SYNCHRONIZATION_POLICY = DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY;

    /**
     * The feature id for the '<em><b>Begin Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>End Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__END_LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Is Domain Based Edge</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Source Node Descriptions</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Target Node Descriptions</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Source Nodes Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Target Nodes Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__STYLE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Edge Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__EDGE_TOOLS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__CONDITIONAL_STYLES = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 9;

    /**
     * The number of structural features of the '<em>Edge Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION_FEATURE_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 10;

    /**
     * The number of operations of the '<em>Edge Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION_OPERATION_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.LabelStyleImpl <em>Label Style</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.LabelStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLabelStyle()
     * @generated
     */
    int LABEL_STYLE = 6;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_STYLE__FONT_SIZE = 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_STYLE__ITALIC = 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_STYLE__BOLD = 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_STYLE__UNDERLINE = 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_STYLE__STRIKE_THROUGH = 4;

    /**
     * The number of structural features of the '<em>Label Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LABEL_STYLE_FEATURE_COUNT = 5;

    /**
     * The number of operations of the '<em>Label Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_STYLE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.BorderStyle <em>Border Style</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.BorderStyle
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getBorderStyle()
     * @generated
     */
    int BORDER_STYLE = 7;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BORDER_STYLE__BORDER_COLOR = 0;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BORDER_STYLE__BORDER_RADIUS = 1;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BORDER_STYLE__BORDER_SIZE = 2;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BORDER_STYLE__BORDER_LINE_STYLE = 3;

    /**
     * The number of structural features of the '<em>Border Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BORDER_STYLE_FEATURE_COUNT = 4;

    /**
     * The number of operations of the '<em>Border Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BORDER_STYLE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.StyleImpl <em>Style</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.StyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getStyle()
     * @generated
     */
    int STYLE = 8;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int STYLE__COLOR = 0;

    /**
     * The number of structural features of the '<em>Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int STYLE_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int STYLE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.NodeStyleImpl <em>Node Style</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.NodeStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getNodeStyle()
     * @generated
     */
    int NODE_STYLE = 9;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE__COLOR = STYLE__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE__FONT_SIZE = STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE__ITALIC = STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE__BOLD = STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE__UNDERLINE = STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE__STRIKE_THROUGH = STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE__BORDER_COLOR = STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE__BORDER_RADIUS = STYLE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE__BORDER_SIZE = STYLE_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE__BORDER_LINE_STYLE = STYLE_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>List Mode</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE__LIST_MODE = STYLE_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Shape</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE__SHAPE = STYLE_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE__LABEL_COLOR = STYLE_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Size Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE__SIZE_COMPUTATION_EXPRESSION = STYLE_FEATURE_COUNT + 12;

    /**
     * The number of structural features of the '<em>Node Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_FEATURE_COUNT = STYLE_FEATURE_COUNT + 13;

    /**
     * The number of operations of the '<em>Node Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_OPERATION_COUNT = STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.EdgeStyleImpl <em>Edge Style</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.EdgeStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getEdgeStyle()
     * @generated
     */
    int EDGE_STYLE = 10;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__COLOR = STYLE__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__FONT_SIZE = STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__ITALIC = STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__BOLD = STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__UNDERLINE = STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__STRIKE_THROUGH = STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__LINE_STYLE = STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Source Arrow Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__SOURCE_ARROW_STYLE = STYLE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Target Arrow Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__TARGET_ARROW_STYLE = STYLE_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Edge Width</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__EDGE_WIDTH = STYLE_FEATURE_COUNT + 8;

    /**
     * The number of structural features of the '<em>Edge Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE_FEATURE_COUNT = STYLE_FEATURE_COUNT + 9;

    /**
     * The number of operations of the '<em>Edge Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE_OPERATION_COUNT = STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ToolImpl <em>Tool</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ToolImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getTool()
     * @generated
     */
    int TOOL = 11;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TOOL__NAME = 0;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TOOL__BODY = 1;

    /**
     * The number of structural features of the '<em>Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TOOL_FEATURE_COUNT = 2;

    /**
     * The number of operations of the '<em>Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TOOL_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.LabelEditToolImpl <em>Label Edit
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.LabelEditToolImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLabelEditTool()
     * @generated
     */
    int LABEL_EDIT_TOOL = 12;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_EDIT_TOOL__NAME = TOOL__NAME;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LABEL_EDIT_TOOL__BODY = TOOL__BODY;

    /**
     * The number of structural features of the '<em>Label Edit Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_EDIT_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 0;

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.DiagramDescription#isAutoLayout <em>Auto Layout</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Auto Layout</em>'.
     * @see org.eclipse.sirius.components.view.DiagramDescription#isAutoLayout()
     * @see #getDiagramDescription()
     * @generated
     */
    EAttribute getDiagramDescription_AutoLayout();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.DiagramDescription#getNodeDescriptions <em>Node Descriptions</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc --> >>>>>>> 025361a0 ([512] View DSL: Add support for layout mode
     * configuration)
     *
     * @generated
     * @ordered
     */
    int LABEL_EDIT_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.DeleteToolImpl <em>Delete Tool</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.DeleteToolImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDeleteTool()
     * @generated
     */
    int DELETE_TOOL = 13;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_TOOL__NAME = TOOL__NAME;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DELETE_TOOL__BODY = TOOL__BODY;

    /**
     * The number of structural features of the '<em>Delete Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DELETE_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Delete Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.NodeToolImpl <em>Node Tool</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.NodeToolImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getNodeTool()
     * @generated
     */
    int NODE_TOOL = 14;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL__NAME = TOOL__NAME;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL__BODY = TOOL__BODY;

    /**
     * The number of structural features of the '<em>Node Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Node Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.EdgeToolImpl <em>Edge Tool</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.EdgeToolImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getEdgeTool()
     * @generated
     */
    int EDGE_TOOL = 15;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL__NAME = TOOL__NAME;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL__BODY = TOOL__BODY;

    /**
     * The number of structural features of the '<em>Edge Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Edge Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.DropToolImpl <em>Drop Tool</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.DropToolImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDropTool()
     * @generated
     */
    int DROP_TOOL = 16;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_TOOL__NAME = TOOL__NAME;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_TOOL__BODY = TOOL__BODY;

    /**
     * The number of structural features of the '<em>Drop Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DROP_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Drop Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.OperationImpl <em>Operation</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.OperationImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getOperation()
     * @generated
     */
    int OPERATION = 17;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OPERATION__CHILDREN = 0;

    /**
     * The number of structural features of the '<em>Operation</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OPERATION_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Operation</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OPERATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ChangeContextImpl <em>Change
     * Context</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ChangeContextImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getChangeContext()
     * @generated
     */
    int CHANGE_CONTEXT = 18;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHANGE_CONTEXT__CHILDREN = OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHANGE_CONTEXT__EXPRESSION = OPERATION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Change Context</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHANGE_CONTEXT_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Change Context</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHANGE_CONTEXT_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.CreateInstanceImpl <em>Create
     * Instance</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.CreateInstanceImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getCreateInstance()
     * @generated
     */
    int CREATE_INSTANCE = 19;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_INSTANCE__CHILDREN = OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>Type Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_INSTANCE__TYPE_NAME = OPERATION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Reference Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_INSTANCE__REFERENCE_NAME = OPERATION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Variable Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_INSTANCE__VARIABLE_NAME = OPERATION_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Create Instance</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_INSTANCE_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Create Instance</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_INSTANCE_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.SetValueImpl <em>Set Value</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.SetValueImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getSetValue()
     * @generated
     */
    int SET_VALUE = 20;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SET_VALUE__CHILDREN = OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>Feature Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SET_VALUE__FEATURE_NAME = OPERATION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SET_VALUE__VALUE_EXPRESSION = OPERATION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Set Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SET_VALUE_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Set Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SET_VALUE_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.UnsetValueImpl <em>Unset Value</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.UnsetValueImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getUnsetValue()
     * @generated
     */
    int UNSET_VALUE = 21;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int UNSET_VALUE__CHILDREN = OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>Feature Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int UNSET_VALUE__FEATURE_NAME = OPERATION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Element Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int UNSET_VALUE__ELEMENT_EXPRESSION = OPERATION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Unset Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int UNSET_VALUE_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Unset Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int UNSET_VALUE_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.DeleteElementImpl <em>Delete
     * Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.DeleteElementImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDeleteElement()
     * @generated
     */
    int DELETE_ELEMENT = 22;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_ELEMENT__CHILDREN = OPERATION__CHILDREN;

    /**
     * The number of structural features of the '<em>Delete Element</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_ELEMENT_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Delete Element</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_ELEMENT_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.CreateViewImpl <em>Create View</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.CreateViewImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getCreateView()
     * @generated
     */
    int CREATE_VIEW = 23;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW__CHILDREN = OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>Parent View Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW__PARENT_VIEW_EXPRESSION = OPERATION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Element Description</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW__ELEMENT_DESCRIPTION = OPERATION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Semantic Element Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION = OPERATION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Variable Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW__VARIABLE_NAME = OPERATION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Create View</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 4;

    /**
     * The number of operations of the '<em>Create View</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.DeleteViewImpl <em>Delete View</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.DeleteViewImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDeleteView()
     * @generated
     */
    int DELETE_VIEW = 24;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_VIEW__CHILDREN = OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>View Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_VIEW__VIEW_EXPRESSION = OPERATION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Delete View</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DELETE_VIEW_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Delete View</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_VIEW_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ConditionalImpl <em>Conditional</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditional()
     * @generated
     */
    int CONDITIONAL = 25;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL__CONDITION = 0;

    /**
     * The number of structural features of the '<em>Conditional</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Conditional</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ConditionalNodeStyleImpl
     * <em>Conditional Node Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalNodeStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalNodeStyle()
     * @generated
     */
    int CONDITIONAL_NODE_STYLE = 26;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__COLOR = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__FONT_SIZE = CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__ITALIC = CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__BOLD = CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__UNDERLINE = CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__STRIKE_THROUGH = CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__BORDER_COLOR = CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__BORDER_RADIUS = CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__BORDER_SIZE = CONDITIONAL_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__BORDER_LINE_STYLE = CONDITIONAL_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>List Mode</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__LIST_MODE = CONDITIONAL_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Shape</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__SHAPE = CONDITIONAL_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__LABEL_COLOR = CONDITIONAL_FEATURE_COUNT + 12;

    /**
     * The feature id for the '<em><b>Size Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__SIZE_COMPUTATION_EXPRESSION = CONDITIONAL_FEATURE_COUNT + 13;

    /**
     * The number of structural features of the '<em>Conditional Node Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 14;

    /**
     * The number of operations of the '<em>Conditional Node Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE_OPERATION_COUNT = CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ConditionalEdgeStyleImpl
     * <em>Conditional Edge Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalEdgeStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalEdgeStyle()
     * @generated
     */
    int CONDITIONAL_EDGE_STYLE = 27;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__COLOR = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__FONT_SIZE = CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__ITALIC = CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__BOLD = CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__UNDERLINE = CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__STRIKE_THROUGH = CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__LINE_STYLE = CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Source Arrow Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__SOURCE_ARROW_STYLE = CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Target Arrow Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__TARGET_ARROW_STYLE = CONDITIONAL_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Edge Width</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__EDGE_WIDTH = CONDITIONAL_FEATURE_COUNT + 9;

    /**
     * The number of structural features of the '<em>Conditional Edge Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 10;

    /**
     * The number of operations of the '<em>Conditional Edge Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE_OPERATION_COUNT = CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.FormDescriptionImpl <em>Form
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.FormDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getFormDescription()
     * @generated
     */
    int FORM_DESCRIPTION = 28;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION__NAME = REPRESENTATION_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION__DOMAIN_TYPE = REPRESENTATION_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION__PRECONDITION_EXPRESSION = REPRESENTATION_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION__TITLE_EXPRESSION = REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION;

    /**
     * The feature id for the '<em><b>Widgets</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION__WIDGETS = REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Form Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION_FEATURE_COUNT = REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Form Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION_OPERATION_COUNT = REPRESENTATION_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.WidgetDescriptionImpl <em>Widget
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.WidgetDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getWidgetDescription()
     * @generated
     */
    int WIDGET_DESCRIPTION = 29;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int WIDGET_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int WIDGET_DESCRIPTION__LABEL_EXPRESSION = 1;

    /**
     * The number of structural features of the '<em>Widget Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int WIDGET_DESCRIPTION_FEATURE_COUNT = 2;

    /**
     * The number of operations of the '<em>Widget Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int WIDGET_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.TextfieldDescriptionImpl <em>Textfield
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.TextfieldDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getTextfieldDescription()
     * @generated
     */
    int TEXTFIELD_DESCRIPTION = 30;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Textfield Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Textfield Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.CheckboxDescriptionImpl <em>Checkbox
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.CheckboxDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getCheckboxDescription()
     * @generated
     */
    int CHECKBOX_DESCRIPTION = 31;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Checkbox Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Checkbox Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.SelectDescriptionImpl <em>Select
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.SelectDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getSelectDescription()
     * @generated
     */
    int SELECT_DESCRIPTION = 32;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__CANDIDATES_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Candidate Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Select Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of operations of the '<em>Select Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.MultiSelectDescriptionImpl <em>Multi
     * Select Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.MultiSelectDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getMultiSelectDescription()
     * @generated
     */
    int MULTI_SELECT_DESCRIPTION = 33;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Candidate Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Multi Select Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of operations of the '<em>Multi Select Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.TextAreaDescriptionImpl <em>Text Area
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.TextAreaDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getTextAreaDescription()
     * @generated
     */
    int TEXT_AREA_DESCRIPTION = 34;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Text Area Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Text Area Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.RadioDescriptionImpl <em>Radio
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.RadioDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getRadioDescription()
     * @generated
     */
    int RADIO_DESCRIPTION = 35;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__CANDIDATES_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Candidate Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Radio Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of operations of the '<em>Radio Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.ArrowStyle <em>Arrow Style</em>}' enum.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.ArrowStyle
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getArrowStyle()
     * @generated
     */
    int ARROW_STYLE = 36;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.LineStyle <em>Line Style</em>}' enum. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.LineStyle
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLineStyle()
     * @generated
     */
    int LINE_STYLE = 37;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.SynchronizationPolicy <em>Synchronization
     * Policy</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.SynchronizationPolicy
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getSynchronizationPolicy()
     * @generated
     */
    int SYNCHRONIZATION_POLICY = 38;

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.View <em>View</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>View</em>'.
     * @see org.eclipse.sirius.components.view.View
     * @generated
     */
    EClass getView();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.View#getDescriptions <em>Descriptions</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.View#getDescriptions()
     * @see #getView()
     * @generated
     */
    EReference getView_Descriptions();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.RepresentationDescription
     * <em>Representation Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Representation Description</em>'.
     * @see org.eclipse.sirius.components.view.RepresentationDescription
     * @generated
     */
    EClass getRepresentationDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.RepresentationDescription#getName <em>Name</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.RepresentationDescription#getName()
     * @see #getRepresentationDescription()
     * @generated
     */
    EAttribute getRepresentationDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.RepresentationDescription#getDomainType <em>Domain Type</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Domain Type</em>'.
     * @see org.eclipse.sirius.components.view.RepresentationDescription#getDomainType()
     * @see #getRepresentationDescription()
     * @generated
     */
    EAttribute getRepresentationDescription_DomainType();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.RepresentationDescription#getPreconditionExpression <em>Precondition
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @see org.eclipse.sirius.components.view.RepresentationDescription#getPreconditionExpression()
     * @see #getRepresentationDescription()
     * @generated
     */
    EAttribute getRepresentationDescription_PreconditionExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.RepresentationDescription#getTitleExpression <em>Title
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Title Expression</em>'.
     * @see org.eclipse.sirius.components.view.RepresentationDescription#getTitleExpression()
     * @see #getRepresentationDescription()
     * @generated
     */
    EAttribute getRepresentationDescription_TitleExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.DiagramDescription <em>Diagram
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Diagram Description</em>'.
     * @see org.eclipse.sirius.components.view.DiagramDescription
     * @generated
     */
    EClass getDiagramDescription();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.DiagramDescription#getNodeDescriptions <em>Node Descriptions</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.DiagramDescription#getNodeDescriptions()
     * @see #getDiagramDescription()
     * @generated
     */
    EReference getDiagramDescription_NodeDescriptions();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.DiagramDescription#getEdgeDescriptions <em>Edge Descriptions</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Edge Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.DiagramDescription#getEdgeDescriptions()
     * @see #getDiagramDescription()
     * @generated
     */
    EReference getDiagramDescription_EdgeDescriptions();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.DiagramDescription#getOnDrop <em>On Drop</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>On Drop</em>'.
     * @see org.eclipse.sirius.components.view.DiagramDescription#getOnDrop()
     * @see #getDiagramDescription()
     * @generated
     */
    EReference getDiagramDescription_OnDrop();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.DiagramElementDescription
     * <em>Diagram Element Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Diagram Element Description</em>'.
     * @see org.eclipse.sirius.components.view.DiagramElementDescription
     * @generated
     */
    EClass getDiagramElementDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.DiagramElementDescription#getName <em>Name</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.DiagramElementDescription#getName()
     * @see #getDiagramElementDescription()
     * @generated
     */
    EAttribute getDiagramElementDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.DiagramElementDescription#getDomainType <em>Domain Type</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Domain Type</em>'.
     * @see org.eclipse.sirius.components.view.DiagramElementDescription#getDomainType()
     * @see #getDiagramElementDescription()
     * @generated
     */
    EAttribute getDiagramElementDescription_DomainType();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.DiagramElementDescription#getSemanticCandidatesExpression <em>Semantic
     * Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.DiagramElementDescription#getSemanticCandidatesExpression()
     * @see #getDiagramElementDescription()
     * @generated
     */
    EAttribute getDiagramElementDescription_SemanticCandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.DiagramElementDescription#getLabelExpression <em>Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.DiagramElementDescription#getLabelExpression()
     * @see #getDiagramElementDescription()
     * @generated
     */
    EAttribute getDiagramElementDescription_LabelExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.DiagramElementDescription#getDeleteTool <em>Delete Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Delete Tool</em>'.
     * @see org.eclipse.sirius.components.view.DiagramElementDescription#getDeleteTool()
     * @see #getDiagramElementDescription()
     * @generated
     */
    EReference getDiagramElementDescription_DeleteTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.DiagramElementDescription#getLabelEditTool <em>Label Edit Tool</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Label Edit Tool</em>'.
     * @see org.eclipse.sirius.components.view.DiagramElementDescription#getLabelEditTool()
     * @see #getDiagramElementDescription()
     * @generated
     */
    EReference getDiagramElementDescription_LabelEditTool();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.DiagramElementDescription#getSynchronizationPolicy <em>Synchronization
     * Policy</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Synchronization Policy</em>'.
     * @see org.eclipse.sirius.components.view.DiagramElementDescription#getSynchronizationPolicy()
     * @see #getDiagramElementDescription()
     * @generated
     */
    EAttribute getDiagramElementDescription_SynchronizationPolicy();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.NodeDescription <em>Node
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Description</em>'.
     * @see org.eclipse.sirius.components.view.NodeDescription
     * @generated
     */
    EClass getNodeDescription();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.NodeDescription#getChildrenDescriptions <em>Children
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Children Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.NodeDescription#getChildrenDescriptions()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_ChildrenDescriptions();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.NodeDescription#getBorderNodesDescriptions <em>Border Nodes
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Border Nodes Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.NodeDescription#getBorderNodesDescriptions()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_BorderNodesDescriptions();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.NodeDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.NodeDescription#getStyle()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.NodeDescription#getNodeTools <em>Node Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @see org.eclipse.sirius.components.view.NodeDescription#getNodeTools()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_NodeTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.NodeDescription#getConditionalStyles <em>Conditional Styles</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.NodeDescription#getConditionalStyles()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.EdgeDescription <em>Edge
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Description</em>'.
     * @see org.eclipse.sirius.components.view.EdgeDescription
     * @generated
     */
    EClass getEdgeDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.EdgeDescription#getBeginLabelExpression <em>Begin Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Begin Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.EdgeDescription#getBeginLabelExpression()
     * @see #getEdgeDescription()
     * @generated
     */
    EAttribute getEdgeDescription_BeginLabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.EdgeDescription#getEndLabelExpression <em>End Label Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>End Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.EdgeDescription#getEndLabelExpression()
     * @see #getEdgeDescription()
     * @generated
     */
    EAttribute getEdgeDescription_EndLabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.EdgeDescription#isIsDomainBasedEdge <em>Is Domain Based Edge</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Domain Based Edge</em>'.
     * @see org.eclipse.sirius.components.view.EdgeDescription#isIsDomainBasedEdge()
     * @see #getEdgeDescription()
     * @generated
     */
    EAttribute getEdgeDescription_IsDomainBasedEdge();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.EdgeDescription#getSourceNodeDescriptions <em>Source Node
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Source Node Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.EdgeDescription#getSourceNodeDescriptions()
     * @see #getEdgeDescription()
     * @generated
     */
    EReference getEdgeDescription_SourceNodeDescriptions();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.EdgeDescription#getTargetNodeDescriptions <em>Target Node
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Target Node Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.EdgeDescription#getTargetNodeDescriptions()
     * @see #getEdgeDescription()
     * @generated
     */
    EReference getEdgeDescription_TargetNodeDescriptions();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.EdgeDescription#getSourceNodesExpression <em>Source Nodes
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Source Nodes Expression</em>'.
     * @see org.eclipse.sirius.components.view.EdgeDescription#getSourceNodesExpression()
     * @see #getEdgeDescription()
     * @generated
     */
    EAttribute getEdgeDescription_SourceNodesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.EdgeDescription#getTargetNodesExpression <em>Target Nodes
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Target Nodes Expression</em>'.
     * @see org.eclipse.sirius.components.view.EdgeDescription#getTargetNodesExpression()
     * @see #getEdgeDescription()
     * @generated
     */
    EAttribute getEdgeDescription_TargetNodesExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.EdgeDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.EdgeDescription#getStyle()
     * @see #getEdgeDescription()
     * @generated
     */
    EReference getEdgeDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.EdgeDescription#getEdgeTools <em>Edge Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Edge Tools</em>'.
     * @see org.eclipse.sirius.components.view.EdgeDescription#getEdgeTools()
     * @see #getEdgeDescription()
     * @generated
     */
    EReference getEdgeDescription_EdgeTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.EdgeDescription#getConditionalStyles <em>Conditional Styles</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.EdgeDescription#getConditionalStyles()
     * @see #getEdgeDescription()
     * @generated
     */
    EReference getEdgeDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.LabelStyle <em>Label Style</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Label Style</em>'.
     * @see org.eclipse.sirius.components.view.LabelStyle
     * @generated
     */
    EClass getLabelStyle();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.LabelStyle#getFontSize
     * <em>Font Size</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Font Size</em>'.
     * @see org.eclipse.sirius.components.view.LabelStyle#getFontSize()
     * @see #getLabelStyle()
     * @generated
     */
    EAttribute getLabelStyle_FontSize();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.LabelStyle#isItalic
     * <em>Italic</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Italic</em>'.
     * @see org.eclipse.sirius.components.view.LabelStyle#isItalic()
     * @see #getLabelStyle()
     * @generated
     */
    EAttribute getLabelStyle_Italic();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.LabelStyle#isBold
     * <em>Bold</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Bold</em>'.
     * @see org.eclipse.sirius.components.view.LabelStyle#isBold()
     * @see #getLabelStyle()
     * @generated
     */
    EAttribute getLabelStyle_Bold();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.LabelStyle#isUnderline
     * <em>Underline</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Underline</em>'.
     * @see org.eclipse.sirius.components.view.LabelStyle#isUnderline()
     * @see #getLabelStyle()
     * @generated
     */
    EAttribute getLabelStyle_Underline();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.LabelStyle#isStrikeThrough
     * <em>Strike Through</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Strike Through</em>'.
     * @see org.eclipse.sirius.components.view.LabelStyle#isStrikeThrough()
     * @see #getLabelStyle()
     * @generated
     */
    EAttribute getLabelStyle_StrikeThrough();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.BorderStyle <em>Border Style</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Border Style</em>'.
     * @see org.eclipse.sirius.components.view.BorderStyle
     * @generated
     */
    EClass getBorderStyle();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.BorderStyle#getBorderColor
     * <em>Border Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Border Color</em>'.
     * @see org.eclipse.sirius.components.view.BorderStyle#getBorderColor()
     * @see #getBorderStyle()
     * @generated
     */
    EAttribute getBorderStyle_BorderColor();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.BorderStyle#getBorderRadius
     * <em>Border Radius</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Border Radius</em>'.
     * @see org.eclipse.sirius.components.view.BorderStyle#getBorderRadius()
     * @see #getBorderStyle()
     * @generated
     */
    EAttribute getBorderStyle_BorderRadius();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.BorderStyle#getBorderSize
     * <em>Border Size</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Border Size</em>'.
     * @see org.eclipse.sirius.components.view.BorderStyle#getBorderSize()
     * @see #getBorderStyle()
     * @generated
     */
    EAttribute getBorderStyle_BorderSize();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.BorderStyle#getBorderLineStyle <em>Border Line Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Border Line Style</em>'.
     * @see org.eclipse.sirius.components.view.BorderStyle#getBorderLineStyle()
     * @see #getBorderStyle()
     * @generated
     */
    EAttribute getBorderStyle_BorderLineStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.Style <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.Style
     * @generated
     */
    EClass getStyle();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.Style#getColor
     * <em>Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Color</em>'.
     * @see org.eclipse.sirius.components.view.Style#getColor()
     * @see #getStyle()
     * @generated
     */
    EAttribute getStyle_Color();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.NodeStyle <em>Node Style</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Style</em>'.
     * @see org.eclipse.sirius.components.view.NodeStyle
     * @generated
     */
    EClass getNodeStyle();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.NodeStyle#isListMode
     * <em>List Mode</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>List Mode</em>'.
     * @see org.eclipse.sirius.components.view.NodeStyle#isListMode()
     * @see #getNodeStyle()
     * @generated
     */
    EAttribute getNodeStyle_ListMode();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.NodeStyle#getShape
     * <em>Shape</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Shape</em>'.
     * @see org.eclipse.sirius.components.view.NodeStyle#getShape()
     * @see #getNodeStyle()
     * @generated
     */
    EAttribute getNodeStyle_Shape();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.NodeStyle#getLabelColor
     * <em>Label Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Color</em>'.
     * @see org.eclipse.sirius.components.view.NodeStyle#getLabelColor()
     * @see #getNodeStyle()
     * @generated
     */
    EAttribute getNodeStyle_LabelColor();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.NodeStyle#getSizeComputationExpression <em>Size Computation
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Size Computation Expression</em>'.
     * @see org.eclipse.sirius.components.view.NodeStyle#getSizeComputationExpression()
     * @see #getNodeStyle()
     * @generated
     */
    EAttribute getNodeStyle_SizeComputationExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.EdgeStyle <em>Edge Style</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Style</em>'.
     * @see org.eclipse.sirius.components.view.EdgeStyle
     * @generated
     */
    EClass getEdgeStyle();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.EdgeStyle#getLineStyle
     * <em>Line Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Line Style</em>'.
     * @see org.eclipse.sirius.components.view.EdgeStyle#getLineStyle()
     * @see #getEdgeStyle()
     * @generated
     */
    EAttribute getEdgeStyle_LineStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.EdgeStyle#getSourceArrowStyle <em>Source Arrow Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Source Arrow Style</em>'.
     * @see org.eclipse.sirius.components.view.EdgeStyle#getSourceArrowStyle()
     * @see #getEdgeStyle()
     * @generated
     */
    EAttribute getEdgeStyle_SourceArrowStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.EdgeStyle#getTargetArrowStyle <em>Target Arrow Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Target Arrow Style</em>'.
     * @see org.eclipse.sirius.components.view.EdgeStyle#getTargetArrowStyle()
     * @see #getEdgeStyle()
     * @generated
     */
    EAttribute getEdgeStyle_TargetArrowStyle();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.EdgeStyle#getEdgeWidth
     * <em>Edge Width</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Edge Width</em>'.
     * @see org.eclipse.sirius.components.view.EdgeStyle#getEdgeWidth()
     * @see #getEdgeStyle()
     * @generated
     */
    EAttribute getEdgeStyle_EdgeWidth();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.Tool <em>Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Tool</em>'.
     * @see org.eclipse.sirius.components.view.Tool
     * @generated
     */
    EClass getTool();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.Tool#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.Tool#getName()
     * @see #getTool()
     * @generated
     */
    EAttribute getTool_Name();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.Tool#getBody <em>Body</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.Tool#getBody()
     * @see #getTool()
     * @generated
     */
    EReference getTool_Body();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.LabelEditTool <em>Label Edit
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Label Edit Tool</em>'.
     * @see org.eclipse.sirius.components.view.LabelEditTool
     * @generated
     */
    EClass getLabelEditTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.DeleteTool <em>Delete Tool</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Delete Tool</em>'.
     * @see org.eclipse.sirius.components.view.DeleteTool
     * @generated
     */
    EClass getDeleteTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.NodeTool <em>Node Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Tool</em>'.
     * @see org.eclipse.sirius.components.view.NodeTool
     * @generated
     */
    EClass getNodeTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.EdgeTool <em>Edge Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Tool</em>'.
     * @see org.eclipse.sirius.components.view.EdgeTool
     * @generated
     */
    EClass getEdgeTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.DropTool <em>Drop Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Drop Tool</em>'.
     * @see org.eclipse.sirius.components.view.DropTool
     * @generated
     */
    EClass getDropTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.Operation <em>Operation</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Operation</em>'.
     * @see org.eclipse.sirius.components.view.Operation
     * @generated
     */
    EClass getOperation();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.Operation#getChildren <em>Children</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Children</em>'.
     * @see org.eclipse.sirius.components.view.Operation#getChildren()
     * @see #getOperation()
     * @generated
     */
    EReference getOperation_Children();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ChangeContext <em>Change
     * Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Change Context</em>'.
     * @see org.eclipse.sirius.components.view.ChangeContext
     * @generated
     */
    EClass getChangeContext();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.ChangeContext#getExpression
     * <em>Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Expression</em>'.
     * @see org.eclipse.sirius.components.view.ChangeContext#getExpression()
     * @see #getChangeContext()
     * @generated
     */
    EAttribute getChangeContext_Expression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.CreateInstance <em>Create
     * Instance</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Create Instance</em>'.
     * @see org.eclipse.sirius.components.view.CreateInstance
     * @generated
     */
    EClass getCreateInstance();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.CreateInstance#getTypeName
     * <em>Type Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Type Name</em>'.
     * @see org.eclipse.sirius.components.view.CreateInstance#getTypeName()
     * @see #getCreateInstance()
     * @generated
     */
    EAttribute getCreateInstance_TypeName();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.CreateInstance#getReferenceName <em>Reference Name</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Reference Name</em>'.
     * @see org.eclipse.sirius.components.view.CreateInstance#getReferenceName()
     * @see #getCreateInstance()
     * @generated
     */
    EAttribute getCreateInstance_ReferenceName();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.CreateInstance#getVariableName <em>Variable Name</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Variable Name</em>'.
     * @see org.eclipse.sirius.components.view.CreateInstance#getVariableName()
     * @see #getCreateInstance()
     * @generated
     */
    EAttribute getCreateInstance_VariableName();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.SetValue <em>Set Value</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Set Value</em>'.
     * @see org.eclipse.sirius.components.view.SetValue
     * @generated
     */
    EClass getSetValue();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.SetValue#getFeatureName
     * <em>Feature Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Feature Name</em>'.
     * @see org.eclipse.sirius.components.view.SetValue#getFeatureName()
     * @see #getSetValue()
     * @generated
     */
    EAttribute getSetValue_FeatureName();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.SetValue#getValueExpression
     * <em>Value Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.SetValue#getValueExpression()
     * @see #getSetValue()
     * @generated
     */
    EAttribute getSetValue_ValueExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.UnsetValue <em>Unset Value</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Unset Value</em>'.
     * @see org.eclipse.sirius.components.view.UnsetValue
     * @generated
     */
    EClass getUnsetValue();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.UnsetValue#getFeatureName
     * <em>Feature Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Feature Name</em>'.
     * @see org.eclipse.sirius.components.view.UnsetValue#getFeatureName()
     * @see #getUnsetValue()
     * @generated
     */
    EAttribute getUnsetValue_FeatureName();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.UnsetValue#getElementExpression <em>Element Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Element Expression</em>'.
     * @see org.eclipse.sirius.components.view.UnsetValue#getElementExpression()
     * @see #getUnsetValue()
     * @generated
     */
    EAttribute getUnsetValue_ElementExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.DeleteElement <em>Delete
     * Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Delete Element</em>'.
     * @see org.eclipse.sirius.components.view.DeleteElement
     * @generated
     */
    EClass getDeleteElement();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.CreateView <em>Create View</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Create View</em>'.
     * @see org.eclipse.sirius.components.view.CreateView
     * @generated
     */
    EClass getCreateView();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.CreateView#getParentViewExpression <em>Parent View Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Parent View Expression</em>'.
     * @see org.eclipse.sirius.components.view.CreateView#getParentViewExpression()
     * @see #getCreateView()
     * @generated
     */
    EAttribute getCreateView_ParentViewExpression();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.CreateView#getElementDescription <em>Element Description</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Element Description</em>'.
     * @see org.eclipse.sirius.components.view.CreateView#getElementDescription()
     * @see #getCreateView()
     * @generated
     */
    EReference getCreateView_ElementDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.CreateView#getSemanticElementExpression <em>Semantic Element
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Element Expression</em>'.
     * @see org.eclipse.sirius.components.view.CreateView#getSemanticElementExpression()
     * @see #getCreateView()
     * @generated
     */
    EAttribute getCreateView_SemanticElementExpression();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.CreateView#getVariableName
     * <em>Variable Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Variable Name</em>'.
     * @see org.eclipse.sirius.components.view.CreateView#getVariableName()
     * @see #getCreateView()
     * @generated
     */
    EAttribute getCreateView_VariableName();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.DeleteView <em>Delete View</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Delete View</em>'.
     * @see org.eclipse.sirius.components.view.DeleteView
     * @generated
     */
    EClass getDeleteView();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.DeleteView#getViewExpression
     * <em>View Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>View Expression</em>'.
     * @see org.eclipse.sirius.components.view.DeleteView#getViewExpression()
     * @see #getDeleteView()
     * @generated
     */
    EAttribute getDeleteView_ViewExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.Conditional <em>Conditional</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional</em>'.
     * @see org.eclipse.sirius.components.view.Conditional
     * @generated
     */
    EClass getConditional();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.Conditional#getCondition
     * <em>Condition</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Condition</em>'.
     * @see org.eclipse.sirius.components.view.Conditional#getCondition()
     * @see #getConditional()
     * @generated
     */
    EAttribute getConditional_Condition();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ConditionalNodeStyle <em>Conditional
     * Node Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Node Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalNodeStyle
     * @generated
     */
    EClass getConditionalNodeStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ConditionalEdgeStyle <em>Conditional
     * Edge Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Edge Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalEdgeStyle
     * @generated
     */
    EClass getConditionalEdgeStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.FormDescription <em>Form
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Form Description</em>'.
     * @see org.eclipse.sirius.components.view.FormDescription
     * @generated
     */
    EClass getFormDescription();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.FormDescription#getWidgets <em>Widgets</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Widgets</em>'.
     * @see org.eclipse.sirius.components.view.FormDescription#getWidgets()
     * @see #getFormDescription()
     * @generated
     */
    EReference getFormDescription_Widgets();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.WidgetDescription <em>Widget
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Widget Description</em>'.
     * @see org.eclipse.sirius.components.view.WidgetDescription
     * @generated
     */
    EClass getWidgetDescription();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.WidgetDescription#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.WidgetDescription#getName()
     * @see #getWidgetDescription()
     * @generated
     */
    EAttribute getWidgetDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.WidgetDescription#getLabelExpression <em>Label Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.WidgetDescription#getLabelExpression()
     * @see #getWidgetDescription()
     * @generated
     */
    EAttribute getWidgetDescription_LabelExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.TextfieldDescription <em>Textfield
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Textfield Description</em>'.
     * @see org.eclipse.sirius.components.view.TextfieldDescription
     * @generated
     */
    EClass getTextfieldDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.TextfieldDescription#getValueExpression <em>Value Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.TextfieldDescription#getValueExpression()
     * @see #getTextfieldDescription()
     * @generated
     */
    EAttribute getTextfieldDescription_ValueExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.TextfieldDescription#getBody <em>Body</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.TextfieldDescription#getBody()
     * @see #getTextfieldDescription()
     * @generated
     */
    EReference getTextfieldDescription_Body();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.CheckboxDescription <em>Checkbox
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Checkbox Description</em>'.
     * @see org.eclipse.sirius.components.view.CheckboxDescription
     * @generated
     */
    EClass getCheckboxDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.CheckboxDescription#getValueExpression <em>Value Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.CheckboxDescription#getValueExpression()
     * @see #getCheckboxDescription()
     * @generated
     */
    EAttribute getCheckboxDescription_ValueExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.CheckboxDescription#getBody <em>Body</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.CheckboxDescription#getBody()
     * @see #getCheckboxDescription()
     * @generated
     */
    EReference getCheckboxDescription_Body();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.SelectDescription <em>Select
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Select Description</em>'.
     * @see org.eclipse.sirius.components.view.SelectDescription
     * @generated
     */
    EClass getSelectDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.SelectDescription#getValueExpression <em>Value Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.SelectDescription#getValueExpression()
     * @see #getSelectDescription()
     * @generated
     */
    EAttribute getSelectDescription_ValueExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.SelectDescription#getCandidatesExpression <em>Candidates
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.SelectDescription#getCandidatesExpression()
     * @see #getSelectDescription()
     * @generated
     */
    EAttribute getSelectDescription_CandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.SelectDescription#getCandidateLabelExpression <em>Candidate Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidate Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.SelectDescription#getCandidateLabelExpression()
     * @see #getSelectDescription()
     * @generated
     */
    EAttribute getSelectDescription_CandidateLabelExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.SelectDescription#getBody <em>Body</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.SelectDescription#getBody()
     * @see #getSelectDescription()
     * @generated
     */
    EReference getSelectDescription_Body();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.MultiSelectDescription <em>Multi
     * Select Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Multi Select Description</em>'.
     * @see org.eclipse.sirius.components.view.MultiSelectDescription
     * @generated
     */
    EClass getMultiSelectDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.MultiSelectDescription#getValueExpression <em>Value Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.MultiSelectDescription#getValueExpression()
     * @see #getMultiSelectDescription()
     * @generated
     */
    EAttribute getMultiSelectDescription_ValueExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.MultiSelectDescription#getCandidatesExpression <em>Candidates
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.MultiSelectDescription#getCandidatesExpression()
     * @see #getMultiSelectDescription()
     * @generated
     */
    EAttribute getMultiSelectDescription_CandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.MultiSelectDescription#getCandidateLabelExpression <em>Candidate Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidate Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.MultiSelectDescription#getCandidateLabelExpression()
     * @see #getMultiSelectDescription()
     * @generated
     */
    EAttribute getMultiSelectDescription_CandidateLabelExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.MultiSelectDescription#getBody <em>Body</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.MultiSelectDescription#getBody()
     * @see #getMultiSelectDescription()
     * @generated
     */
    EReference getMultiSelectDescription_Body();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.TextAreaDescription <em>Text Area
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Text Area Description</em>'.
     * @see org.eclipse.sirius.components.view.TextAreaDescription
     * @generated
     */
    EClass getTextAreaDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.TextAreaDescription#getValueExpression <em>Value Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.TextAreaDescription#getValueExpression()
     * @see #getTextAreaDescription()
     * @generated
     */
    EAttribute getTextAreaDescription_ValueExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.TextAreaDescription#getBody <em>Body</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.TextAreaDescription#getBody()
     * @see #getTextAreaDescription()
     * @generated
     */
    EReference getTextAreaDescription_Body();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.RadioDescription <em>Radio
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Radio Description</em>'.
     * @see org.eclipse.sirius.components.view.RadioDescription
     * @generated
     */
    EClass getRadioDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.RadioDescription#getValueExpression <em>Value Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.RadioDescription#getValueExpression()
     * @see #getRadioDescription()
     * @generated
     */
    EAttribute getRadioDescription_ValueExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.RadioDescription#getCandidatesExpression <em>Candidates
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.RadioDescription#getCandidatesExpression()
     * @see #getRadioDescription()
     * @generated
     */
    EAttribute getRadioDescription_CandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.RadioDescription#getCandidateLabelExpression <em>Candidate Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidate Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.RadioDescription#getCandidateLabelExpression()
     * @see #getRadioDescription()
     * @generated
     */
    EAttribute getRadioDescription_CandidateLabelExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.RadioDescription#getBody <em>Body</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.RadioDescription#getBody()
     * @see #getRadioDescription()
     * @generated
     */
    EReference getRadioDescription_Body();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.ArrowStyle <em>Arrow Style</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Arrow Style</em>'.
     * @see org.eclipse.sirius.components.view.ArrowStyle
     * @generated
     */
    EEnum getArrowStyle();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.LineStyle <em>Line Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Line Style</em>'.
     * @see org.eclipse.sirius.components.view.LineStyle
     * @generated
     */
    EEnum getLineStyle();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.SynchronizationPolicy
     * <em>Synchronization Policy</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Synchronization Policy</em>'.
     * @see org.eclipse.sirius.components.view.SynchronizationPolicy
     * @generated
     */
    EEnum getSynchronizationPolicy();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ViewFactory getViewFactory();

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
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ViewImpl <em>View</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ViewImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getView()
         * @generated
         */
        EClass VIEW = eINSTANCE.getView();

        /**
         * The meta object literal for the '<em><b>Descriptions</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference VIEW__DESCRIPTIONS = eINSTANCE.getView_Descriptions();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl
         * <em>Representation Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getRepresentationDescription()
         * @generated
         */
        EClass REPRESENTATION_DESCRIPTION = eINSTANCE.getRepresentationDescription();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute REPRESENTATION_DESCRIPTION__NAME = eINSTANCE.getRepresentationDescription_Name();

        /**
         * The meta object literal for the '<em><b>Domain Type</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute REPRESENTATION_DESCRIPTION__DOMAIN_TYPE = eINSTANCE.getRepresentationDescription_DomainType();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute REPRESENTATION_DESCRIPTION__PRECONDITION_EXPRESSION = eINSTANCE.getRepresentationDescription_PreconditionExpression();

        /**
         * The meta object literal for the '<em><b>Title Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION = eINSTANCE.getRepresentationDescription_TitleExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.DiagramDescriptionImpl
         * <em>Diagram Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.DiagramDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDiagramDescription()
         * @generated
         */
        EClass DIAGRAM_DESCRIPTION = eINSTANCE.getDiagramDescription();

        /**
         * The meta object literal for the '<em><b>Auto Layout</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_DESCRIPTION__AUTO_LAYOUT = eINSTANCE.getDiagramDescription_AutoLayout();

        /**
         * The meta object literal for the '<em><b>Node Descriptions</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS = eINSTANCE.getDiagramDescription_NodeDescriptions();

        /**
         * The meta object literal for the '<em><b>Edge Descriptions</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS = eINSTANCE.getDiagramDescription_EdgeDescriptions();

        /**
         * The meta object literal for the '<em><b>On Drop</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_DESCRIPTION__ON_DROP = eINSTANCE.getDiagramDescription_OnDrop();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.DiagramElementDescriptionImpl
         * <em>Diagram Element Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.DiagramElementDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDiagramElementDescription()
         * @generated
         */
        EClass DIAGRAM_ELEMENT_DESCRIPTION = eINSTANCE.getDiagramElementDescription();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_ELEMENT_DESCRIPTION__NAME = eINSTANCE.getDiagramElementDescription_Name();

        /**
         * The meta object literal for the '<em><b>Domain Type</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE = eINSTANCE.getDiagramElementDescription_DomainType();

        /**
         * The meta object literal for the '<em><b>Semantic Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = eINSTANCE.getDiagramElementDescription_SemanticCandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getDiagramElementDescription_LabelExpression();

        /**
         * The meta object literal for the '<em><b>Delete Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_ELEMENT_DESCRIPTION__DELETE_TOOL = eINSTANCE.getDiagramElementDescription_DeleteTool();

        /**
         * The meta object literal for the '<em><b>Label Edit Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EDIT_TOOL = eINSTANCE.getDiagramElementDescription_LabelEditTool();

        /**
         * The meta object literal for the '<em><b>Synchronization Policy</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY = eINSTANCE.getDiagramElementDescription_SynchronizationPolicy();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.NodeDescriptionImpl <em>Node
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.NodeDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getNodeDescription()
         * @generated
         */
        EClass NODE_DESCRIPTION = eINSTANCE.getNodeDescription();

        /**
         * The meta object literal for the '<em><b>Children Descriptions</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS = eINSTANCE.getNodeDescription_ChildrenDescriptions();

        /**
         * The meta object literal for the '<em><b>Border Nodes Descriptions</b></em>' containment reference list
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS = eINSTANCE.getNodeDescription_BorderNodesDescriptions();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__STYLE = eINSTANCE.getNodeDescription_Style();

        /**
         * The meta object literal for the '<em><b>Node Tools</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__NODE_TOOLS = eINSTANCE.getNodeDescription_NodeTools();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getNodeDescription_ConditionalStyles();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.EdgeDescriptionImpl <em>Edge
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.EdgeDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getEdgeDescription()
         * @generated
         */
        EClass EDGE_DESCRIPTION = eINSTANCE.getEdgeDescription();

        /**
         * The meta object literal for the '<em><b>Begin Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION = eINSTANCE.getEdgeDescription_BeginLabelExpression();

        /**
         * The meta object literal for the '<em><b>End Label Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_DESCRIPTION__END_LABEL_EXPRESSION = eINSTANCE.getEdgeDescription_EndLabelExpression();

        /**
         * The meta object literal for the '<em><b>Is Domain Based Edge</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE = eINSTANCE.getEdgeDescription_IsDomainBasedEdge();

        /**
         * The meta object literal for the '<em><b>Source Node Descriptions</b></em>' reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS = eINSTANCE.getEdgeDescription_SourceNodeDescriptions();

        /**
         * The meta object literal for the '<em><b>Target Node Descriptions</b></em>' reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS = eINSTANCE.getEdgeDescription_TargetNodeDescriptions();

        /**
         * The meta object literal for the '<em><b>Source Nodes Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION = eINSTANCE.getEdgeDescription_SourceNodesExpression();

        /**
         * The meta object literal for the '<em><b>Target Nodes Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION = eINSTANCE.getEdgeDescription_TargetNodesExpression();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_DESCRIPTION__STYLE = eINSTANCE.getEdgeDescription_Style();

        /**
         * The meta object literal for the '<em><b>Edge Tools</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_DESCRIPTION__EDGE_TOOLS = eINSTANCE.getEdgeDescription_EdgeTools();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getEdgeDescription_ConditionalStyles();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.LabelStyleImpl <em>Label
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.LabelStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLabelStyle()
         * @generated
         */
        EClass LABEL_STYLE = eINSTANCE.getLabelStyle();

        /**
         * The meta object literal for the '<em><b>Font Size</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_STYLE__FONT_SIZE = eINSTANCE.getLabelStyle_FontSize();

        /**
         * The meta object literal for the '<em><b>Italic</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_STYLE__ITALIC = eINSTANCE.getLabelStyle_Italic();

        /**
         * The meta object literal for the '<em><b>Bold</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_STYLE__BOLD = eINSTANCE.getLabelStyle_Bold();

        /**
         * The meta object literal for the '<em><b>Underline</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_STYLE__UNDERLINE = eINSTANCE.getLabelStyle_Underline();

        /**
         * The meta object literal for the '<em><b>Strike Through</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_STYLE__STRIKE_THROUGH = eINSTANCE.getLabelStyle_StrikeThrough();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.BorderStyle <em>Border
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.BorderStyle
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getBorderStyle()
         * @generated
         */
        EClass BORDER_STYLE = eINSTANCE.getBorderStyle();

        /**
         * The meta object literal for the '<em><b>Border Color</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BORDER_STYLE__BORDER_COLOR = eINSTANCE.getBorderStyle_BorderColor();

        /**
         * The meta object literal for the '<em><b>Border Radius</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BORDER_STYLE__BORDER_RADIUS = eINSTANCE.getBorderStyle_BorderRadius();

        /**
         * The meta object literal for the '<em><b>Border Size</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute BORDER_STYLE__BORDER_SIZE = eINSTANCE.getBorderStyle_BorderSize();

        /**
         * The meta object literal for the '<em><b>Border Line Style</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BORDER_STYLE__BORDER_LINE_STYLE = eINSTANCE.getBorderStyle_BorderLineStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.StyleImpl <em>Style</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.StyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getStyle()
         * @generated
         */
        EClass STYLE = eINSTANCE.getStyle();

        /**
         * The meta object literal for the '<em><b>Color</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute STYLE__COLOR = eINSTANCE.getStyle_Color();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.NodeStyleImpl <em>Node
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.NodeStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getNodeStyle()
         * @generated
         */
        EClass NODE_STYLE = eINSTANCE.getNodeStyle();

        /**
         * The meta object literal for the '<em><b>List Mode</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_STYLE__LIST_MODE = eINSTANCE.getNodeStyle_ListMode();

        /**
         * The meta object literal for the '<em><b>Shape</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_STYLE__SHAPE = eINSTANCE.getNodeStyle_Shape();

        /**
         * The meta object literal for the '<em><b>Label Color</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_STYLE__LABEL_COLOR = eINSTANCE.getNodeStyle_LabelColor();

        /**
         * The meta object literal for the '<em><b>Size Computation Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_STYLE__SIZE_COMPUTATION_EXPRESSION = eINSTANCE.getNodeStyle_SizeComputationExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.EdgeStyleImpl <em>Edge
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.EdgeStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getEdgeStyle()
         * @generated
         */
        EClass EDGE_STYLE = eINSTANCE.getEdgeStyle();

        /**
         * The meta object literal for the '<em><b>Line Style</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_STYLE__LINE_STYLE = eINSTANCE.getEdgeStyle_LineStyle();

        /**
         * The meta object literal for the '<em><b>Source Arrow Style</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_STYLE__SOURCE_ARROW_STYLE = eINSTANCE.getEdgeStyle_SourceArrowStyle();

        /**
         * The meta object literal for the '<em><b>Target Arrow Style</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_STYLE__TARGET_ARROW_STYLE = eINSTANCE.getEdgeStyle_TargetArrowStyle();

        /**
         * The meta object literal for the '<em><b>Edge Width</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_STYLE__EDGE_WIDTH = eINSTANCE.getEdgeStyle_EdgeWidth();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ToolImpl <em>Tool</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ToolImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getTool()
         * @generated
         */
        EClass TOOL = eINSTANCE.getTool();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute TOOL__NAME = eINSTANCE.getTool_Name();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TOOL__BODY = eINSTANCE.getTool_Body();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.LabelEditToolImpl <em>Label
         * Edit Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.LabelEditToolImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLabelEditTool()
         * @generated
         */
        EClass LABEL_EDIT_TOOL = eINSTANCE.getLabelEditTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.DeleteToolImpl <em>Delete
         * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.DeleteToolImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDeleteTool()
         * @generated
         */
        EClass DELETE_TOOL = eINSTANCE.getDeleteTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.NodeToolImpl <em>Node
         * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.NodeToolImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getNodeTool()
         * @generated
         */
        EClass NODE_TOOL = eINSTANCE.getNodeTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.EdgeToolImpl <em>Edge
         * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.EdgeToolImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getEdgeTool()
         * @generated
         */
        EClass EDGE_TOOL = eINSTANCE.getEdgeTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.DropToolImpl <em>Drop
         * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.DropToolImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDropTool()
         * @generated
         */
        EClass DROP_TOOL = eINSTANCE.getDropTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.OperationImpl
         * <em>Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.OperationImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getOperation()
         * @generated
         */
        EClass OPERATION = eINSTANCE.getOperation();

        /**
         * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference OPERATION__CHILDREN = eINSTANCE.getOperation_Children();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ChangeContextImpl <em>Change
         * Context</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ChangeContextImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getChangeContext()
         * @generated
         */
        EClass CHANGE_CONTEXT = eINSTANCE.getChangeContext();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute CHANGE_CONTEXT__EXPRESSION = eINSTANCE.getChangeContext_Expression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.CreateInstanceImpl <em>Create
         * Instance</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.CreateInstanceImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getCreateInstance()
         * @generated
         */
        EClass CREATE_INSTANCE = eINSTANCE.getCreateInstance();

        /**
         * The meta object literal for the '<em><b>Type Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute CREATE_INSTANCE__TYPE_NAME = eINSTANCE.getCreateInstance_TypeName();

        /**
         * The meta object literal for the '<em><b>Reference Name</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CREATE_INSTANCE__REFERENCE_NAME = eINSTANCE.getCreateInstance_ReferenceName();

        /**
         * The meta object literal for the '<em><b>Variable Name</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CREATE_INSTANCE__VARIABLE_NAME = eINSTANCE.getCreateInstance_VariableName();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.SetValueImpl <em>Set
         * Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.SetValueImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getSetValue()
         * @generated
         */
        EClass SET_VALUE = eINSTANCE.getSetValue();

        /**
         * The meta object literal for the '<em><b>Feature Name</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SET_VALUE__FEATURE_NAME = eINSTANCE.getSetValue_FeatureName();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SET_VALUE__VALUE_EXPRESSION = eINSTANCE.getSetValue_ValueExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.UnsetValueImpl <em>Unset
         * Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.UnsetValueImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getUnsetValue()
         * @generated
         */
        EClass UNSET_VALUE = eINSTANCE.getUnsetValue();

        /**
         * The meta object literal for the '<em><b>Feature Name</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute UNSET_VALUE__FEATURE_NAME = eINSTANCE.getUnsetValue_FeatureName();

        /**
         * The meta object literal for the '<em><b>Element Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute UNSET_VALUE__ELEMENT_EXPRESSION = eINSTANCE.getUnsetValue_ElementExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.DeleteElementImpl <em>Delete
         * Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.DeleteElementImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDeleteElement()
         * @generated
         */
        EClass DELETE_ELEMENT = eINSTANCE.getDeleteElement();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.CreateViewImpl <em>Create
         * View</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.CreateViewImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getCreateView()
         * @generated
         */
        EClass CREATE_VIEW = eINSTANCE.getCreateView();

        /**
         * The meta object literal for the '<em><b>Parent View Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CREATE_VIEW__PARENT_VIEW_EXPRESSION = eINSTANCE.getCreateView_ParentViewExpression();

        /**
         * The meta object literal for the '<em><b>Element Description</b></em>' reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CREATE_VIEW__ELEMENT_DESCRIPTION = eINSTANCE.getCreateView_ElementDescription();

        /**
         * The meta object literal for the '<em><b>Semantic Element Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION = eINSTANCE.getCreateView_SemanticElementExpression();

        /**
         * The meta object literal for the '<em><b>Variable Name</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CREATE_VIEW__VARIABLE_NAME = eINSTANCE.getCreateView_VariableName();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.DeleteViewImpl <em>Delete
         * View</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.DeleteViewImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDeleteView()
         * @generated
         */
        EClass DELETE_VIEW = eINSTANCE.getDeleteView();

        /**
         * The meta object literal for the '<em><b>View Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute DELETE_VIEW__VIEW_EXPRESSION = eINSTANCE.getDeleteView_ViewExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ConditionalImpl
         * <em>Conditional</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditional()
         * @generated
         */
        EClass CONDITIONAL = eINSTANCE.getConditional();

        /**
         * The meta object literal for the '<em><b>Condition</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute CONDITIONAL__CONDITION = eINSTANCE.getConditional_Condition();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ConditionalNodeStyleImpl
         * <em>Conditional Node Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalNodeStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalNodeStyle()
         * @generated
         */
        EClass CONDITIONAL_NODE_STYLE = eINSTANCE.getConditionalNodeStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ConditionalEdgeStyleImpl
         * <em>Conditional Edge Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalEdgeStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalEdgeStyle()
         * @generated
         */
        EClass CONDITIONAL_EDGE_STYLE = eINSTANCE.getConditionalEdgeStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.FormDescriptionImpl <em>Form
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.FormDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getFormDescription()
         * @generated
         */
        EClass FORM_DESCRIPTION = eINSTANCE.getFormDescription();

        /**
         * The meta object literal for the '<em><b>Widgets</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference FORM_DESCRIPTION__WIDGETS = eINSTANCE.getFormDescription_Widgets();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.WidgetDescriptionImpl
         * <em>Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.WidgetDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getWidgetDescription()
         * @generated
         */
        EClass WIDGET_DESCRIPTION = eINSTANCE.getWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute WIDGET_DESCRIPTION__NAME = eINSTANCE.getWidgetDescription_Name();

        /**
         * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute WIDGET_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getWidgetDescription_LabelExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.TextfieldDescriptionImpl
         * <em>Textfield Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.TextfieldDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getTextfieldDescription()
         * @generated
         */
        EClass TEXTFIELD_DESCRIPTION = eINSTANCE.getTextfieldDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TEXTFIELD_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getTextfieldDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXTFIELD_DESCRIPTION__BODY = eINSTANCE.getTextfieldDescription_Body();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.CheckboxDescriptionImpl
         * <em>Checkbox Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.CheckboxDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getCheckboxDescription()
         * @generated
         */
        EClass CHECKBOX_DESCRIPTION = eINSTANCE.getCheckboxDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CHECKBOX_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getCheckboxDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CHECKBOX_DESCRIPTION__BODY = eINSTANCE.getCheckboxDescription_Body();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.SelectDescriptionImpl
         * <em>Select Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.SelectDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getSelectDescription()
         * @generated
         */
        EClass SELECT_DESCRIPTION = eINSTANCE.getSelectDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SELECT_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getSelectDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SELECT_DESCRIPTION__CANDIDATES_EXPRESSION = eINSTANCE.getSelectDescription_CandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Candidate Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION = eINSTANCE.getSelectDescription_CandidateLabelExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SELECT_DESCRIPTION__BODY = eINSTANCE.getSelectDescription_Body();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.MultiSelectDescriptionImpl
         * <em>Multi Select Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.MultiSelectDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getMultiSelectDescription()
         * @generated
         */
        EClass MULTI_SELECT_DESCRIPTION = eINSTANCE.getMultiSelectDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getMultiSelectDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION = eINSTANCE.getMultiSelectDescription_CandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Candidate Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION = eINSTANCE.getMultiSelectDescription_CandidateLabelExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_SELECT_DESCRIPTION__BODY = eINSTANCE.getMultiSelectDescription_Body();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.TextAreaDescriptionImpl
         * <em>Text Area Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.TextAreaDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getTextAreaDescription()
         * @generated
         */
        EClass TEXT_AREA_DESCRIPTION = eINSTANCE.getTextAreaDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TEXT_AREA_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getTextAreaDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXT_AREA_DESCRIPTION__BODY = eINSTANCE.getTextAreaDescription_Body();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.RadioDescriptionImpl
         * <em>Radio Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.RadioDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getRadioDescription()
         * @generated
         */
        EClass RADIO_DESCRIPTION = eINSTANCE.getRadioDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute RADIO_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getRadioDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute RADIO_DESCRIPTION__CANDIDATES_EXPRESSION = eINSTANCE.getRadioDescription_CandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Candidate Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute RADIO_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION = eINSTANCE.getRadioDescription_CandidateLabelExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RADIO_DESCRIPTION__BODY = eINSTANCE.getRadioDescription_Body();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.ArrowStyle <em>Arrow Style</em>}'
         * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.ArrowStyle
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getArrowStyle()
         * @generated
         */
        EEnum ARROW_STYLE = eINSTANCE.getArrowStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.LineStyle <em>Line Style</em>}'
         * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.LineStyle
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLineStyle()
         * @generated
         */
        EEnum LINE_STYLE = eINSTANCE.getLineStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.SynchronizationPolicy
         * <em>Synchronization Policy</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.SynchronizationPolicy
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getSynchronizationPolicy()
         * @generated
         */
        EEnum SYNCHRONIZATION_POLICY = eINSTANCE.getSynchronizationPolicy();

    }

} // ViewPackage
