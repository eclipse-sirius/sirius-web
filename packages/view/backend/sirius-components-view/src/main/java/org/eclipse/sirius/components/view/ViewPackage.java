/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import org.eclipse.emf.ecore.EDataType;
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
    String eNAME = "view";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/sirius-web/view";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "view";

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
     * The feature id for the '<em><b>Palette</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__PALETTE = REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Node Descriptions</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS = REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Edge Descriptions</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS = REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 3;

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
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION = 3;

    /**
     * The feature id for the '<em><b>Synchronization Policy</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY = 4;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION = 5;

    /**
     * The number of structural features of the '<em>Diagram Element Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT = 6;

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
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__PRECONDITION_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Synchronization Policy</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__SYNCHRONIZATION_POLICY = DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Collapsible</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__COLLAPSIBLE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Palette</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__PALETTE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Children Layout Strategy</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__STYLE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__CONDITIONAL_STYLES = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Children Descriptions</b></em>' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Border Nodes Descriptions</b></em>' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Reused Child Node Descriptions</b></em>' reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Reused Border Node Descriptions</b></em>' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>User Resizable</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__USER_RESIZABLE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 9;

    /**
     * The number of structural features of the '<em>Node Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION_FEATURE_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 10;

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
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__PRECONDITION_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Synchronization Policy</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__SYNCHRONIZATION_POLICY = DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION;

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
     * The feature id for the '<em><b>Palette</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__PALETTE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Source Node Descriptions</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Target Node Descriptions</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Source Nodes Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Target Nodes Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__STYLE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 8;

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
     * The meta object id for the '{@link org.eclipse.sirius.components.view.NodeStyleDescription <em>Node Style
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.NodeStyleDescription
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getNodeStyleDescription()
     * @generated
     */
    int NODE_STYLE_DESCRIPTION = 9;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__COLOR = STYLE__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__FONT_SIZE = STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__ITALIC = STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__BOLD = STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__UNDERLINE = STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__BORDER_COLOR = STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__BORDER_RADIUS = STYLE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__BORDER_SIZE = STYLE_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = STYLE_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__LABEL_COLOR = STYLE_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Width Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION = STYLE_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Height Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION = STYLE_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__SHOW_ICON = STYLE_FEATURE_COUNT + 12;

    /**
     * The number of structural features of the '<em>Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION_FEATURE_COUNT = STYLE_FEATURE_COUNT + 13;

    /**
     * The number of operations of the '<em>Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION_OPERATION_COUNT = STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.RectangularNodeStyleDescriptionImpl
     * <em>Rectangular Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.RectangularNodeStyleDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getRectangularNodeStyleDescription()
     * @generated
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION = 10;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__COLOR = NODE_STYLE_DESCRIPTION__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__FONT_SIZE = NODE_STYLE_DESCRIPTION__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__ITALIC = NODE_STYLE_DESCRIPTION__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BOLD = NODE_STYLE_DESCRIPTION__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__UNDERLINE = NODE_STYLE_DESCRIPTION__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BORDER_COLOR = NODE_STYLE_DESCRIPTION__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = NODE_STYLE_DESCRIPTION__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BORDER_SIZE = NODE_STYLE_DESCRIPTION__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__LABEL_COLOR = NODE_STYLE_DESCRIPTION__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Width Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION = NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Height Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION = NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__SHOW_ICON = NODE_STYLE_DESCRIPTION__SHOW_ICON;

    /**
     * The feature id for the '<em><b>With Header</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__WITH_HEADER = NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Rectangular Node Style Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Rectangular Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = NODE_STYLE_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ImageNodeStyleDescriptionImpl
     * <em>Image Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ImageNodeStyleDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getImageNodeStyleDescription()
     * @generated
     */
    int IMAGE_NODE_STYLE_DESCRIPTION = 11;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__COLOR = NODE_STYLE_DESCRIPTION__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__FONT_SIZE = NODE_STYLE_DESCRIPTION__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__ITALIC = NODE_STYLE_DESCRIPTION__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__BOLD = NODE_STYLE_DESCRIPTION__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__UNDERLINE = NODE_STYLE_DESCRIPTION__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR = NODE_STYLE_DESCRIPTION__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = NODE_STYLE_DESCRIPTION__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE = NODE_STYLE_DESCRIPTION__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__LABEL_COLOR = NODE_STYLE_DESCRIPTION__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Width Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION = NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Height Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION = NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__SHOW_ICON = NODE_STYLE_DESCRIPTION__SHOW_ICON;

    /**
     * The feature id for the '<em><b>Shape</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__SHAPE = NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Image Node Style Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Image Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = NODE_STYLE_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl
     * <em>Icon Label Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getIconLabelNodeStyleDescription()
     * @generated
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION = 12;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__COLOR = NODE_STYLE_DESCRIPTION__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__FONT_SIZE = NODE_STYLE_DESCRIPTION__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__ITALIC = NODE_STYLE_DESCRIPTION__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__BOLD = NODE_STYLE_DESCRIPTION__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__UNDERLINE = NODE_STYLE_DESCRIPTION__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR = NODE_STYLE_DESCRIPTION__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = NODE_STYLE_DESCRIPTION__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE = NODE_STYLE_DESCRIPTION__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__LABEL_COLOR = NODE_STYLE_DESCRIPTION__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Width Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION = NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Height Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION = NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__SHOW_ICON = NODE_STYLE_DESCRIPTION__SHOW_ICON;

    /**
     * The number of structural features of the '<em>Icon Label Node Style Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Icon Label Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = NODE_STYLE_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.LayoutStrategyDescription <em>Layout
     * Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.LayoutStrategyDescription
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLayoutStrategyDescription()
     * @generated
     */
    int LAYOUT_STRATEGY_DESCRIPTION = 13;

    /**
     * The number of structural features of the '<em>Layout Strategy Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT = 0;

    /**
     * The number of operations of the '<em>Layout Strategy Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LAYOUT_STRATEGY_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.FreeFormLayoutStrategyDescriptionImpl
     * <em>Free Form Layout Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.FreeFormLayoutStrategyDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getFreeFormLayoutStrategyDescription()
     * @generated
     */
    int FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION = 14;

    /**
     * The number of structural features of the '<em>Free Form Layout Strategy Description</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT = LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Free Form Layout Strategy Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION_OPERATION_COUNT = LAYOUT_STRATEGY_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ListLayoutStrategyDescriptionImpl
     * <em>List Layout Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ListLayoutStrategyDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getListLayoutStrategyDescription()
     * @generated
     */
    int LIST_LAYOUT_STRATEGY_DESCRIPTION = 15;

    /**
     * The number of structural features of the '<em>List Layout Strategy Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT = LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>List Layout Strategy Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_LAYOUT_STRATEGY_DESCRIPTION_OPERATION_COUNT = LAYOUT_STRATEGY_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.EdgeStyleImpl <em>Edge Style</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.EdgeStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getEdgeStyle()
     * @generated
     */
    int EDGE_STYLE = 16;

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
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__SHOW_ICON = STYLE_FEATURE_COUNT + 9;

    /**
     * The number of structural features of the '<em>Edge Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE_FEATURE_COUNT = STYLE_FEATURE_COUNT + 10;

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
    int TOOL = 17;

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
    int LABEL_EDIT_TOOL = 18;

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
     * The feature id for the '<em><b>Initial Direct Edit Label Expression</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_EDIT_TOOL__INITIAL_DIRECT_EDIT_LABEL_EXPRESSION = TOOL_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Label Edit Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_EDIT_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 1;

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
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.DiagramDescription#getPalette <em>Palette</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Palette</em>'.
     * @see org.eclipse.sirius.components.view.DiagramDescription#getPalette()
     * @see #getDiagramDescription()
     * @generated
     */
    EReference getDiagramDescription_Palette();

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
    int DELETE_TOOL = 19;

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
    int NODE_TOOL = 20;

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
    int EDGE_TOOL = 21;

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
     * The feature id for the '<em><b>Target Element Descriptions</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS = TOOL_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Edge Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Edge Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.EdgeReconnectionToolImpl <em>Edge
     * Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.EdgeReconnectionToolImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getEdgeReconnectionTool()
     * @generated
     */
    int EDGE_RECONNECTION_TOOL = 22;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_RECONNECTION_TOOL__NAME = TOOL__NAME;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_RECONNECTION_TOOL__BODY = TOOL__BODY;

    /**
     * The number of structural features of the '<em>Edge Reconnection Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_RECONNECTION_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Edge Reconnection Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_RECONNECTION_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.SourceEdgeEndReconnectionToolImpl
     * <em>Source Edge End Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.SourceEdgeEndReconnectionToolImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getSourceEdgeEndReconnectionTool()
     * @generated
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL = 23;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL__NAME = EDGE_RECONNECTION_TOOL__NAME;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL__BODY = EDGE_RECONNECTION_TOOL__BODY;

    /**
     * The number of structural features of the '<em>Source Edge End Reconnection Tool</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL_FEATURE_COUNT = EDGE_RECONNECTION_TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Source Edge End Reconnection Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL_OPERATION_COUNT = EDGE_RECONNECTION_TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.TargetEdgeEndReconnectionToolImpl
     * <em>Target Edge End Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.TargetEdgeEndReconnectionToolImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getTargetEdgeEndReconnectionTool()
     * @generated
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL = 24;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL__NAME = EDGE_RECONNECTION_TOOL__NAME;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL__BODY = EDGE_RECONNECTION_TOOL__BODY;

    /**
     * The number of structural features of the '<em>Target Edge End Reconnection Tool</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL_FEATURE_COUNT = EDGE_RECONNECTION_TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Target Edge End Reconnection Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL_OPERATION_COUNT = EDGE_RECONNECTION_TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.DropToolImpl <em>Drop Tool</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.DropToolImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDropTool()
     * @generated
     */
    int DROP_TOOL = 25;

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
    int OPERATION = 26;

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
    int CHANGE_CONTEXT = 27;

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
    int CREATE_INSTANCE = 28;

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
    int SET_VALUE = 29;

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
    int UNSET_VALUE = 30;

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
    int DELETE_ELEMENT = 31;

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
    int CREATE_VIEW = 32;

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
     * The feature id for the '<em><b>Containment Kind</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW__CONTAINMENT_KIND = OPERATION_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Create View</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 5;

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
    int DELETE_VIEW = 33;

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
    int CONDITIONAL = 34;

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
    int CONDITIONAL_NODE_STYLE = 35;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__STYLE = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Conditional Node Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 1;

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
    int CONDITIONAL_EDGE_STYLE = 36;

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
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__SHOW_ICON = CONDITIONAL_FEATURE_COUNT + 10;

    /**
     * The number of structural features of the '<em>Conditional Edge Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 11;

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
    int FORM_DESCRIPTION = 37;

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
     * The feature id for the '<em><b>Groups</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION__GROUPS = REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 0;

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
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.GroupDescriptionImpl <em>Group
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.GroupDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getGroupDescription()
     * @generated
     */
    int GROUP_DESCRIPTION = 38;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION__LABEL_EXPRESSION = 1;

    /**
     * The feature id for the '<em><b>Display Mode</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION__DISPLAY_MODE = 2;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = 3;

    /**
     * The feature id for the '<em><b>Toolbar Actions</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION__TOOLBAR_ACTIONS = 4;

    /**
     * The feature id for the '<em><b>Widgets</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION__WIDGETS = 5;

    /**
     * The number of structural features of the '<em>Group Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION_FEATURE_COUNT = 6;

    /**
     * The number of operations of the '<em>Group Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.WidgetDescriptionImpl <em>Widget
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.WidgetDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getWidgetDescription()
     * @generated
     */
    int WIDGET_DESCRIPTION = 39;

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
    int TEXTFIELD_DESCRIPTION = 40;

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
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Textfield Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

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
    int CHECKBOX_DESCRIPTION = 41;

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
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Checkbox Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

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
    int SELECT_DESCRIPTION = 42;

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
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Select Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

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
    int MULTI_SELECT_DESCRIPTION = 43;

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
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Multi Select Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

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
    int TEXT_AREA_DESCRIPTION = 44;

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
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Text Area Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of operations of the '<em>Text Area Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.RichTextDescriptionImpl <em>Rich Text
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.RichTextDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getRichTextDescription()
     * @generated
     */
    int RICH_TEXT_DESCRIPTION = 45;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RICH_TEXT_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RICH_TEXT_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RICH_TEXT_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RICH_TEXT_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Rich Text Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RICH_TEXT_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Rich Text Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RICH_TEXT_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.RadioDescriptionImpl <em>Radio
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.RadioDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getRadioDescription()
     * @generated
     */
    int RADIO_DESCRIPTION = 46;

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
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Radio Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Radio Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.WidgetDescriptionStyleImpl <em>Widget
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.WidgetDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getWidgetDescriptionStyle()
     * @generated
     */
    int WIDGET_DESCRIPTION_STYLE = 52;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.TextfieldDescriptionStyleImpl
     * <em>Textfield Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.TextfieldDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getTextfieldDescriptionStyle()
     * @generated
     */
    int TEXTFIELD_DESCRIPTION_STYLE = 53;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.impl.ConditionalTextfieldDescriptionStyleImpl <em>Conditional
     * Textfield Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalTextfieldDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalTextfieldDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE = 54;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.CheckboxDescriptionStyleImpl
     * <em>Checkbox Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.CheckboxDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getCheckboxDescriptionStyle()
     * @generated
     */
    int CHECKBOX_DESCRIPTION_STYLE = 55;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.impl.ConditionalCheckboxDescriptionStyleImpl <em>Conditional Checkbox
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalCheckboxDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalCheckboxDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE = 56;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.SelectDescriptionStyleImpl <em>Select
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.SelectDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getSelectDescriptionStyle()
     * @generated
     */
    int SELECT_DESCRIPTION_STYLE = 57;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ConditionalSelectDescriptionStyleImpl
     * <em>Conditional Select Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalSelectDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalSelectDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE = 58;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.MultiSelectDescriptionStyleImpl
     * <em>Multi Select Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.MultiSelectDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getMultiSelectDescriptionStyle()
     * @generated
     */
    int MULTI_SELECT_DESCRIPTION_STYLE = 59;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.impl.ConditionalMultiSelectDescriptionStyleImpl <em>Conditional Multi
     * Select Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalMultiSelectDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalMultiSelectDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE = 60;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.TextareaDescriptionStyleImpl
     * <em>Textarea Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.TextareaDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getTextareaDescriptionStyle()
     * @generated
     */
    int TEXTAREA_DESCRIPTION_STYLE = 61;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.impl.ConditionalTextareaDescriptionStyleImpl <em>Conditional Textarea
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalTextareaDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalTextareaDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE = 62;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.RadioDescriptionStyleImpl <em>Radio
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.RadioDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getRadioDescriptionStyle()
     * @generated
     */
    int RADIO_DESCRIPTION_STYLE = 63;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ConditionalRadioDescriptionStyleImpl
     * <em>Conditional Radio Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalRadioDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalRadioDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE = 64;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.BarChartDescriptionImpl <em>Bar Chart
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.BarChartDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getBarChartDescription()
     * @generated
     */
    int BAR_CHART_DESCRIPTION = 47;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Values Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__VALUES_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Keys Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__KEYS_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>YAxis Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Width</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__WIDTH = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Height</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__HEIGHT = WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Bar Chart Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Bar Chart Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.PieChartDescriptionImpl <em>Pie Chart
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.PieChartDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getPieChartDescription()
     * @generated
     */
    int PIE_CHART_DESCRIPTION = 48;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Values Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION__VALUES_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Keys Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION__KEYS_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Pie Chart Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of operations of the '<em>Pie Chart Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.FlexboxContainerDescriptionImpl
     * <em>Flexbox Container Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.FlexboxContainerDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getFlexboxContainerDescription()
     * @generated
     */
    int FLEXBOX_CONTAINER_DESCRIPTION = 49;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FLEXBOX_CONTAINER_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int FLEXBOX_CONTAINER_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Flex Direction</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Flexbox Container Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FLEXBOX_CONTAINER_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Flexbox Container Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FLEXBOX_CONTAINER_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ButtonDescriptionImpl <em>Button
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ButtonDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getButtonDescription()
     * @generated
     */
    int BUTTON_DESCRIPTION = 50;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Button Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__BUTTON_LABEL_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Image Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__IMAGE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Button Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of operations of the '<em>Button Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ImageDescriptionImpl <em>Image
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ImageDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getImageDescription()
     * @generated
     */
    int IMAGE_DESCRIPTION = 51;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Url Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_DESCRIPTION__URL_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Max Width Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Image Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Image Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The number of structural features of the '<em>Widget Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT = 0;

    /**
     * The number of operations of the '<em>Widget Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT = 0;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Textfield Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Textfield Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__FONT_SIZE = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__ITALIC = CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__BOLD = CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__UNDERLINE = CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__STRIKE_THROUGH = CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR = CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR = CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Conditional Textfield Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Conditional Textfield Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE_OPERATION_COUNT = CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION_STYLE__COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Checkbox Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Checkbox Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__COLOR = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Conditional Checkbox Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Conditional Checkbox Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE_OPERATION_COUNT = CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Select Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Select Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__FONT_SIZE = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__ITALIC = CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__BOLD = CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__UNDERLINE = CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH = CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR = CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR = CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Conditional Select Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Conditional Select Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE_OPERATION_COUNT = CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Multi Select Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Multi Select Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__FONT_SIZE = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__ITALIC = CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__BOLD = CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__UNDERLINE = CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH = CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR = CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR = CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Conditional Multi Select Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Conditional Multi Select Description Style</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE_OPERATION_COUNT = CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Textarea Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Textarea Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FONT_SIZE = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__ITALIC = CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BOLD = CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__UNDERLINE = CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__STRIKE_THROUGH = CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR = CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR = CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Conditional Textarea Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Conditional Textarea Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE_OPERATION_COUNT = CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE__COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Radio Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Radio Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE__FONT_SIZE = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE__ITALIC = CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE__BOLD = CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE__UNDERLINE = CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE__STRIKE_THROUGH = CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE__COLOR = CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Conditional Radio Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Conditional Radio Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE_OPERATION_COUNT = CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ButtonDescriptionStyleImpl <em>Button
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ButtonDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getButtonDescriptionStyle()
     * @generated
     */
    int BUTTON_DESCRIPTION_STYLE = 65;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE__BACKGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE__FOREGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Button Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Button Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ConditionalButtonDescriptionStyleImpl
     * <em>Conditional Button Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalButtonDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalButtonDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE = 66;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__FONT_SIZE = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__ITALIC = CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__BOLD = CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__UNDERLINE = CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__STRIKE_THROUGH = CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__BACKGROUND_COLOR = CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__FOREGROUND_COLOR = CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Conditional Button Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Conditional Button Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE_OPERATION_COUNT = CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.BarChartDescriptionStyleImpl <em>Bar
     * Chart Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.BarChartDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getBarChartDescriptionStyle()
     * @generated
     */
    int BAR_CHART_DESCRIPTION_STYLE = 67;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Bars Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Bar Chart Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Bar Chart Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.impl.ConditionalBarChartDescriptionStyleImpl <em>Conditional Bar Chart
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalBarChartDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalBarChartDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE = 68;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__FONT_SIZE = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__ITALIC = CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BOLD = CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__UNDERLINE = CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH = CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Bars Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR = CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Conditional Bar Chart Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Conditional Bar Chart Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE_OPERATION_COUNT = CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.PieChartDescriptionStyleImpl <em>Pie
     * Chart Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.PieChartDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getPieChartDescriptionStyle()
     * @generated
     */
    int PIE_CHART_DESCRIPTION_STYLE = 69;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Colors</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__COLORS = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Stroke Width</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__STROKE_WIDTH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Stroke Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__STROKE_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Pie Chart Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 8;

    /**
     * The number of operations of the '<em>Pie Chart Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.impl.ConditionalPieChartDescriptionStyleImpl <em>Conditional Pie Chart
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalPieChartDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalPieChartDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE = 70;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__FONT_SIZE = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__ITALIC = CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__BOLD = CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__UNDERLINE = CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH = CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Colors</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__COLORS = CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Stroke Width</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__STROKE_WIDTH = CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Stroke Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__STROKE_COLOR = CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Conditional Pie Chart Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 8;

    /**
     * The number of operations of the '<em>Conditional Pie Chart Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE_OPERATION_COUNT = CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.LabelDescriptionImpl <em>Label
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.LabelDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLabelDescription()
     * @generated
     */
    int LABEL_DESCRIPTION = 71;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Label Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Label Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.LabelDescriptionStyleImpl <em>Label
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.LabelDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLabelDescriptionStyle()
     * @generated
     */
    int LABEL_DESCRIPTION_STYLE = 72;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE__COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Label Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Label Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ConditionalLabelDescriptionStyleImpl
     * <em>Conditional Label Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalLabelDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalLabelDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE = 73;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE__FONT_SIZE = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE__ITALIC = CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE__BOLD = CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE__UNDERLINE = CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE__STRIKE_THROUGH = CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE__COLOR = CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Conditional Label Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Conditional Label Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE_OPERATION_COUNT = CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.LinkDescriptionImpl <em>Link
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.LinkDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLinkDescription()
     * @generated
     */
    int LINK_DESCRIPTION = 74;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Link Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Link Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.LinkDescriptionStyleImpl <em>Link
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.LinkDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLinkDescriptionStyle()
     * @generated
     */
    int LINK_DESCRIPTION_STYLE = 75;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE__COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Link Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Link Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ConditionalLinkDescriptionStyleImpl
     * <em>Conditional Link Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalLinkDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalLinkDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE = 76;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE__FONT_SIZE = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE__ITALIC = CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE__BOLD = CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE__UNDERLINE = CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE__STRIKE_THROUGH = CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE__COLOR = CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Conditional Link Description Style</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Conditional Link Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE_OPERATION_COUNT = CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ListDescriptionImpl <em>List
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ListDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getListDescription()
     * @generated
     */
    int LIST_DESCRIPTION = 77;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Display Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__DISPLAY_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Is Deletable Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__IS_DELETABLE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>List Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>List Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ListDescriptionStyleImpl <em>List
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ListDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getListDescriptionStyle()
     * @generated
     */
    int LIST_DESCRIPTION_STYLE = 78;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE__COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>List Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>List Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ConditionalListDescriptionStyleImpl
     * <em>Conditional List Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalListDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalListDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE = 79;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE__CONDITION = CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE__FONT_SIZE = CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE__ITALIC = CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE__BOLD = CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE__UNDERLINE = CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE__STRIKE_THROUGH = CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE__COLOR = CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Conditional List Description Style</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE_FEATURE_COUNT = CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Conditional List Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE_OPERATION_COUNT = CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.DiagramPaletteImpl <em>Diagram
     * Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.DiagramPaletteImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDiagramPalette()
     * @generated
     */
    int DIAGRAM_PALETTE = 80;

    /**
     * The feature id for the '<em><b>Drop Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_PALETTE__DROP_TOOL = 0;

    /**
     * The feature id for the '<em><b>Node Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_PALETTE__NODE_TOOLS = 1;

    /**
     * The number of structural features of the '<em>Diagram Palette</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_PALETTE_FEATURE_COUNT = 2;

    /**
     * The number of operations of the '<em>Diagram Palette</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_PALETTE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.NodePaletteImpl <em>Node
     * Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.NodePaletteImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getNodePalette()
     * @generated
     */
    int NODE_PALETTE = 81;

    /**
     * The feature id for the '<em><b>Delete Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE__DELETE_TOOL = 0;

    /**
     * The feature id for the '<em><b>Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE__LABEL_EDIT_TOOL = 1;

    /**
     * The feature id for the '<em><b>Node Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE__NODE_TOOLS = 2;

    /**
     * The feature id for the '<em><b>Edge Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE__EDGE_TOOLS = 3;

    /**
     * The number of structural features of the '<em>Node Palette</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE_FEATURE_COUNT = 4;

    /**
     * The number of operations of the '<em>Node Palette</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.EdgePaletteImpl <em>Edge
     * Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.EdgePaletteImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getEdgePalette()
     * @generated
     */
    int EDGE_PALETTE = 82;

    /**
     * The feature id for the '<em><b>Delete Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE__DELETE_TOOL = 0;

    /**
     * The feature id for the '<em><b>Center Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL = 1;

    /**
     * The feature id for the '<em><b>Begin Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL = 2;

    /**
     * The feature id for the '<em><b>End Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE__END_LABEL_EDIT_TOOL = 3;

    /**
     * The feature id for the '<em><b>Node Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE__NODE_TOOLS = 4;

    /**
     * The feature id for the '<em><b>Edge Reconnection Tools</b></em>' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE__EDGE_RECONNECTION_TOOLS = 5;

    /**
     * The number of structural features of the '<em>Edge Palette</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE_FEATURE_COUNT = 6;

    /**
     * The number of operations of the '<em>Edge Palette</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.LayoutDirection <em>Layout Direction</em>}'
     * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.LayoutDirection
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLayoutDirection()
     * @generated
     */
    int LAYOUT_DIRECTION = 83;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.ArrowStyle <em>Arrow Style</em>}' enum.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.ArrowStyle
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getArrowStyle()
     * @generated
     */
    int ARROW_STYLE = 84;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.LineStyle <em>Line Style</em>}' enum. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.LineStyle
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLineStyle()
     * @generated
     */
    int LINE_STYLE = 85;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.NodeContainmentKind <em>Node Containment
     * Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.NodeContainmentKind
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getNodeContainmentKind()
     * @generated
     */
    int NODE_CONTAINMENT_KIND = 86;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.SynchronizationPolicy <em>Synchronization
     * Policy</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.SynchronizationPolicy
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getSynchronizationPolicy()
     * @generated
     */
    int SYNCHRONIZATION_POLICY = 87;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.GroupDisplayMode <em>Group Display
     * Mode</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.GroupDisplayMode
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getGroupDisplayMode()
     * @generated
     */
    int GROUP_DISPLAY_MODE = 88;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.FlexDirection <em>Flex Direction</em>}'
     * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.FlexDirection
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getFlexDirection()
     * @generated
     */
    int FLEX_DIRECTION = 89;

    /**
     * The meta object id for the '<em>Identifier</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see java.lang.String
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getIdentifier()
     * @generated
     */
    int IDENTIFIER = 90;

    /**
     * The meta object id for the '<em>Interpreted Expression</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see java.lang.String
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getInterpretedExpression()
     * @generated
     */
    int INTERPRETED_EXPRESSION = 91;

    /**
     * The meta object id for the '<em>Domain Type</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see java.lang.String
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDomainType()
     * @generated
     */
    int DOMAIN_TYPE = 92;

    /**
     * The meta object id for the '<em>Color</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see java.lang.String
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getColor()
     * @generated
     */
    int COLOR = 93;

    /**
     * The meta object id for the '<em>Length</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLength()
     * @generated
     */
    int LENGTH = 94;

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
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.DiagramElementDescription#getPreconditionExpression <em>Precondition
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @see org.eclipse.sirius.components.view.DiagramElementDescription#getPreconditionExpression()
     * @see #getDiagramElementDescription()
     * @generated
     */
    EAttribute getDiagramElementDescription_PreconditionExpression();

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
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.NodeDescription#getReusedChildNodeDescriptions <em>Reused Child Node
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Reused Child Node Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.NodeDescription#getReusedChildNodeDescriptions()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_ReusedChildNodeDescriptions();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.NodeDescription#getReusedBorderNodeDescriptions <em>Reused Border Node
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Reused Border Node Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.NodeDescription#getReusedBorderNodeDescriptions()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_ReusedBorderNodeDescriptions();

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
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.NodeDescription#isUserResizable <em>User Resizable</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>User Resizable</em>'.
     * @see org.eclipse.sirius.components.view.NodeDescription#isUserResizable()
     * @see #getNodeDescription()
     * @generated
     */
    EAttribute getNodeDescription_UserResizable();

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
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.NodeDescription#getChildrenLayoutStrategy <em>Children Layout
     * Strategy</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Children Layout Strategy</em>'.
     * @see org.eclipse.sirius.components.view.NodeDescription#getChildrenLayoutStrategy()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_ChildrenLayoutStrategy();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.NodeDescription#isCollapsible <em>Collapsible</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Collapsible</em>'.
     * @see org.eclipse.sirius.components.view.NodeDescription#isCollapsible()
     * @see #getNodeDescription()
     * @generated
     */
    EAttribute getNodeDescription_Collapsible();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.NodeDescription#getPalette <em>Palette</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Palette</em>'.
     * @see org.eclipse.sirius.components.view.NodeDescription#getPalette()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_Palette();

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
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.EdgeDescription#getPalette <em>Palette</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Palette</em>'.
     * @see org.eclipse.sirius.components.view.EdgeDescription#getPalette()
     * @see #getEdgeDescription()
     * @generated
     */
    EReference getEdgeDescription_Palette();

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
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.NodeStyleDescription <em>Node Style
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Style Description</em>'.
     * @see org.eclipse.sirius.components.view.NodeStyleDescription
     * @generated
     */
    EClass getNodeStyleDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.NodeStyleDescription#getLabelColor <em>Label Color</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Color</em>'.
     * @see org.eclipse.sirius.components.view.NodeStyleDescription#getLabelColor()
     * @see #getNodeStyleDescription()
     * @generated
     */
    EAttribute getNodeStyleDescription_LabelColor();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.NodeStyleDescription#getWidthComputationExpression <em>Width
     * Computation Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Width Computation Expression</em>'.
     * @see org.eclipse.sirius.components.view.NodeStyleDescription#getWidthComputationExpression()
     * @see #getNodeStyleDescription()
     * @generated
     */
    EAttribute getNodeStyleDescription_WidthComputationExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.NodeStyleDescription#getHeightComputationExpression <em>Height
     * Computation Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Height Computation Expression</em>'.
     * @see org.eclipse.sirius.components.view.NodeStyleDescription#getHeightComputationExpression()
     * @see #getNodeStyleDescription()
     * @generated
     */
    EAttribute getNodeStyleDescription_HeightComputationExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.NodeStyleDescription#isShowIcon <em>Show Icon</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Show Icon</em>'.
     * @see org.eclipse.sirius.components.view.NodeStyleDescription#isShowIcon()
     * @see #getNodeStyleDescription()
     * @generated
     */
    EAttribute getNodeStyleDescription_ShowIcon();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.RectangularNodeStyleDescription
     * <em>Rectangular Node Style Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Rectangular Node Style Description</em>'.
     * @see org.eclipse.sirius.components.view.RectangularNodeStyleDescription
     * @generated
     */
    EClass getRectangularNodeStyleDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.RectangularNodeStyleDescription#isWithHeader <em>With Header</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>With Header</em>'.
     * @see org.eclipse.sirius.components.view.RectangularNodeStyleDescription#isWithHeader()
     * @see #getRectangularNodeStyleDescription()
     * @generated
     */
    EAttribute getRectangularNodeStyleDescription_WithHeader();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ImageNodeStyleDescription <em>Image
     * Node Style Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Image Node Style Description</em>'.
     * @see org.eclipse.sirius.components.view.ImageNodeStyleDescription
     * @generated
     */
    EClass getImageNodeStyleDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.ImageNodeStyleDescription#getShape <em>Shape</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Shape</em>'.
     * @see org.eclipse.sirius.components.view.ImageNodeStyleDescription#getShape()
     * @see #getImageNodeStyleDescription()
     * @generated
     */
    EAttribute getImageNodeStyleDescription_Shape();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.IconLabelNodeStyleDescription
     * <em>Icon Label Node Style Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Icon Label Node Style Description</em>'.
     * @see org.eclipse.sirius.components.view.IconLabelNodeStyleDescription
     * @generated
     */
    EClass getIconLabelNodeStyleDescription();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.LayoutStrategyDescription <em>Layout
     * Strategy Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Layout Strategy Description</em>'.
     * @see org.eclipse.sirius.components.view.LayoutStrategyDescription
     * @generated
     */
    EClass getLayoutStrategyDescription();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.FreeFormLayoutStrategyDescription
     * <em>Free Form Layout Strategy Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Free Form Layout Strategy Description</em>'.
     * @see org.eclipse.sirius.components.view.FreeFormLayoutStrategyDescription
     * @generated
     */
    EClass getFreeFormLayoutStrategyDescription();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ListLayoutStrategyDescription
     * <em>List Layout Strategy Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>List Layout Strategy Description</em>'.
     * @see org.eclipse.sirius.components.view.ListLayoutStrategyDescription
     * @generated
     */
    EClass getListLayoutStrategyDescription();

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
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.EdgeStyle#isShowIcon
     * <em>Show Icon</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Show Icon</em>'.
     * @see org.eclipse.sirius.components.view.EdgeStyle#isShowIcon()
     * @see #getEdgeStyle()
     * @generated
     */
    EAttribute getEdgeStyle_ShowIcon();

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
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.LabelEditTool#getInitialDirectEditLabelExpression <em>Initial Direct
     * Edit Label Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Initial Direct Edit Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.LabelEditTool#getInitialDirectEditLabelExpression()
     * @see #getLabelEditTool()
     * @generated
     */
    EAttribute getLabelEditTool_InitialDirectEditLabelExpression();

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
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.EdgeTool#getTargetElementDescriptions <em>Target Element
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Target Element Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.EdgeTool#getTargetElementDescriptions()
     * @see #getEdgeTool()
     * @generated
     */
    EReference getEdgeTool_TargetElementDescriptions();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.EdgeReconnectionTool <em>Edge
     * Reconnection Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Reconnection Tool</em>'.
     * @see org.eclipse.sirius.components.view.EdgeReconnectionTool
     * @generated
     */
    EClass getEdgeReconnectionTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool
     * <em>Source Edge End Reconnection Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Source Edge End Reconnection Tool</em>'.
     * @see org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool
     * @generated
     */
    EClass getSourceEdgeEndReconnectionTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.TargetEdgeEndReconnectionTool
     * <em>Target Edge End Reconnection Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Target Edge End Reconnection Tool</em>'.
     * @see org.eclipse.sirius.components.view.TargetEdgeEndReconnectionTool
     * @generated
     */
    EClass getTargetEdgeEndReconnectionTool();

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
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.CreateView#getContainmentKind <em>Containment Kind</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Containment Kind</em>'.
     * @see org.eclipse.sirius.components.view.CreateView#getContainmentKind()
     * @see #getCreateView()
     * @generated
     */
    EAttribute getCreateView_ContainmentKind();

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
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.ConditionalNodeStyle#getStyle <em>Style</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalNodeStyle#getStyle()
     * @see #getConditionalNodeStyle()
     * @generated
     */
    EReference getConditionalNodeStyle_Style();

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
     * '{@link org.eclipse.sirius.components.view.FormDescription#getGroups <em>Groups</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Groups</em>'.
     * @see org.eclipse.sirius.components.view.FormDescription#getGroups()
     * @see #getFormDescription()
     * @generated
     */
    EReference getFormDescription_Groups();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.GroupDescription <em>Group
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Group Description</em>'.
     * @see org.eclipse.sirius.components.view.GroupDescription
     * @generated
     */
    EClass getGroupDescription();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.GroupDescription#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.GroupDescription#getName()
     * @see #getGroupDescription()
     * @generated
     */
    EAttribute getGroupDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.GroupDescription#getLabelExpression <em>Label Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.GroupDescription#getLabelExpression()
     * @see #getGroupDescription()
     * @generated
     */
    EAttribute getGroupDescription_LabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.GroupDescription#getDisplayMode <em>Display Mode</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Display Mode</em>'.
     * @see org.eclipse.sirius.components.view.GroupDescription#getDisplayMode()
     * @see #getGroupDescription()
     * @generated
     */
    EAttribute getGroupDescription_DisplayMode();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.GroupDescription#getSemanticCandidatesExpression <em>Semantic
     * Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.GroupDescription#getSemanticCandidatesExpression()
     * @see #getGroupDescription()
     * @generated
     */
    EAttribute getGroupDescription_SemanticCandidatesExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.GroupDescription#getToolbarActions <em>Toolbar Actions</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Toolbar Actions</em>'.
     * @see org.eclipse.sirius.components.view.GroupDescription#getToolbarActions()
     * @see #getGroupDescription()
     * @generated
     */
    EReference getGroupDescription_ToolbarActions();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.GroupDescription#getWidgets <em>Widgets</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Widgets</em>'.
     * @see org.eclipse.sirius.components.view.GroupDescription#getWidgets()
     * @see #getGroupDescription()
     * @generated
     */
    EReference getGroupDescription_Widgets();

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
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.TextfieldDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.TextfieldDescription#getStyle()
     * @see #getTextfieldDescription()
     * @generated
     */
    EReference getTextfieldDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.TextfieldDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.TextfieldDescription#getConditionalStyles()
     * @see #getTextfieldDescription()
     * @generated
     */
    EReference getTextfieldDescription_ConditionalStyles();

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
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.CheckboxDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.CheckboxDescription#getStyle()
     * @see #getCheckboxDescription()
     * @generated
     */
    EReference getCheckboxDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.CheckboxDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.CheckboxDescription#getConditionalStyles()
     * @see #getCheckboxDescription()
     * @generated
     */
    EReference getCheckboxDescription_ConditionalStyles();

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
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.SelectDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.SelectDescription#getStyle()
     * @see #getSelectDescription()
     * @generated
     */
    EReference getSelectDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.SelectDescription#getConditionalStyles <em>Conditional Styles</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.SelectDescription#getConditionalStyles()
     * @see #getSelectDescription()
     * @generated
     */
    EReference getSelectDescription_ConditionalStyles();

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
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.MultiSelectDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.MultiSelectDescription#getStyle()
     * @see #getMultiSelectDescription()
     * @generated
     */
    EReference getMultiSelectDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.MultiSelectDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.MultiSelectDescription#getConditionalStyles()
     * @see #getMultiSelectDescription()
     * @generated
     */
    EReference getMultiSelectDescription_ConditionalStyles();

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
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.TextAreaDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.TextAreaDescription#getStyle()
     * @see #getTextAreaDescription()
     * @generated
     */
    EReference getTextAreaDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.TextAreaDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.TextAreaDescription#getConditionalStyles()
     * @see #getTextAreaDescription()
     * @generated
     */
    EReference getTextAreaDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.RichTextDescription <em>Rich Text
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Rich Text Description</em>'.
     * @see org.eclipse.sirius.components.view.RichTextDescription
     * @generated
     */
    EClass getRichTextDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.RichTextDescription#getValueExpression <em>Value Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.RichTextDescription#getValueExpression()
     * @see #getRichTextDescription()
     * @generated
     */
    EAttribute getRichTextDescription_ValueExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.RichTextDescription#getBody <em>Body</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.RichTextDescription#getBody()
     * @see #getRichTextDescription()
     * @generated
     */
    EReference getRichTextDescription_Body();

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
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.RadioDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.RadioDescription#getStyle()
     * @see #getRadioDescription()
     * @generated
     */
    EReference getRadioDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.RadioDescription#getConditionalStyles <em>Conditional Styles</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.RadioDescription#getConditionalStyles()
     * @see #getRadioDescription()
     * @generated
     */
    EReference getRadioDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.WidgetDescriptionStyle <em>Widget
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Widget Description Style</em>'.
     * @see org.eclipse.sirius.components.view.WidgetDescriptionStyle
     * @generated
     */
    EClass getWidgetDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.TextfieldDescriptionStyle
     * <em>Textfield Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Textfield Description Style</em>'.
     * @see org.eclipse.sirius.components.view.TextfieldDescriptionStyle
     * @generated
     */
    EClass getTextfieldDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.TextfieldDescriptionStyle#getBackgroundColor <em>Background
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Background Color</em>'.
     * @see org.eclipse.sirius.components.view.TextfieldDescriptionStyle#getBackgroundColor()
     * @see #getTextfieldDescriptionStyle()
     * @generated
     */
    EAttribute getTextfieldDescriptionStyle_BackgroundColor();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.TextfieldDescriptionStyle#getForegroundColor <em>Foreground
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Foreground Color</em>'.
     * @see org.eclipse.sirius.components.view.TextfieldDescriptionStyle#getForegroundColor()
     * @see #getTextfieldDescriptionStyle()
     * @generated
     */
    EAttribute getTextfieldDescriptionStyle_ForegroundColor();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ConditionalTextfieldDescriptionStyle
     * <em>Conditional Textfield Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Textfield Description Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalTextfieldDescriptionStyle
     * @generated
     */
    EClass getConditionalTextfieldDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.CheckboxDescriptionStyle
     * <em>Checkbox Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Checkbox Description Style</em>'.
     * @see org.eclipse.sirius.components.view.CheckboxDescriptionStyle
     * @generated
     */
    EClass getCheckboxDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.CheckboxDescriptionStyle#getColor <em>Color</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Color</em>'.
     * @see org.eclipse.sirius.components.view.CheckboxDescriptionStyle#getColor()
     * @see #getCheckboxDescriptionStyle()
     * @generated
     */
    EAttribute getCheckboxDescriptionStyle_Color();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ConditionalCheckboxDescriptionStyle
     * <em>Conditional Checkbox Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Checkbox Description Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalCheckboxDescriptionStyle
     * @generated
     */
    EClass getConditionalCheckboxDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.SelectDescriptionStyle <em>Select
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Select Description Style</em>'.
     * @see org.eclipse.sirius.components.view.SelectDescriptionStyle
     * @generated
     */
    EClass getSelectDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.SelectDescriptionStyle#getBackgroundColor <em>Background Color</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Background Color</em>'.
     * @see org.eclipse.sirius.components.view.SelectDescriptionStyle#getBackgroundColor()
     * @see #getSelectDescriptionStyle()
     * @generated
     */
    EAttribute getSelectDescriptionStyle_BackgroundColor();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.SelectDescriptionStyle#getForegroundColor <em>Foreground Color</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Foreground Color</em>'.
     * @see org.eclipse.sirius.components.view.SelectDescriptionStyle#getForegroundColor()
     * @see #getSelectDescriptionStyle()
     * @generated
     */
    EAttribute getSelectDescriptionStyle_ForegroundColor();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle
     * <em>Conditional Select Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Select Description Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle
     * @generated
     */
    EClass getConditionalSelectDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.MultiSelectDescriptionStyle
     * <em>Multi Select Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Multi Select Description Style</em>'.
     * @see org.eclipse.sirius.components.view.MultiSelectDescriptionStyle
     * @generated
     */
    EClass getMultiSelectDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.MultiSelectDescriptionStyle#getBackgroundColor <em>Background
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Background Color</em>'.
     * @see org.eclipse.sirius.components.view.MultiSelectDescriptionStyle#getBackgroundColor()
     * @see #getMultiSelectDescriptionStyle()
     * @generated
     */
    EAttribute getMultiSelectDescriptionStyle_BackgroundColor();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.MultiSelectDescriptionStyle#getForegroundColor <em>Foreground
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Foreground Color</em>'.
     * @see org.eclipse.sirius.components.view.MultiSelectDescriptionStyle#getForegroundColor()
     * @see #getMultiSelectDescriptionStyle()
     * @generated
     */
    EAttribute getMultiSelectDescriptionStyle_ForegroundColor();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.ConditionalMultiSelectDescriptionStyle <em>Conditional Multi Select
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Multi Select Description Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalMultiSelectDescriptionStyle
     * @generated
     */
    EClass getConditionalMultiSelectDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.TextareaDescriptionStyle
     * <em>Textarea Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Textarea Description Style</em>'.
     * @see org.eclipse.sirius.components.view.TextareaDescriptionStyle
     * @generated
     */
    EClass getTextareaDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.TextareaDescriptionStyle#getBackgroundColor <em>Background
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Background Color</em>'.
     * @see org.eclipse.sirius.components.view.TextareaDescriptionStyle#getBackgroundColor()
     * @see #getTextareaDescriptionStyle()
     * @generated
     */
    EAttribute getTextareaDescriptionStyle_BackgroundColor();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.TextareaDescriptionStyle#getForegroundColor <em>Foreground
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Foreground Color</em>'.
     * @see org.eclipse.sirius.components.view.TextareaDescriptionStyle#getForegroundColor()
     * @see #getTextareaDescriptionStyle()
     * @generated
     */
    EAttribute getTextareaDescriptionStyle_ForegroundColor();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle
     * <em>Conditional Textarea Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Textarea Description Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle
     * @generated
     */
    EClass getConditionalTextareaDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.RadioDescriptionStyle <em>Radio
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Radio Description Style</em>'.
     * @see org.eclipse.sirius.components.view.RadioDescriptionStyle
     * @generated
     */
    EClass getRadioDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.RadioDescriptionStyle#getColor <em>Color</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Color</em>'.
     * @see org.eclipse.sirius.components.view.RadioDescriptionStyle#getColor()
     * @see #getRadioDescriptionStyle()
     * @generated
     */
    EAttribute getRadioDescriptionStyle_Color();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle
     * <em>Conditional Radio Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Radio Description Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle
     * @generated
     */
    EClass getConditionalRadioDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ButtonDescriptionStyle <em>Button
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Button Description Style</em>'.
     * @see org.eclipse.sirius.components.view.ButtonDescriptionStyle
     * @generated
     */
    EClass getButtonDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.ButtonDescriptionStyle#getBackgroundColor <em>Background Color</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Background Color</em>'.
     * @see org.eclipse.sirius.components.view.ButtonDescriptionStyle#getBackgroundColor()
     * @see #getButtonDescriptionStyle()
     * @generated
     */
    EAttribute getButtonDescriptionStyle_BackgroundColor();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.ButtonDescriptionStyle#getForegroundColor <em>Foreground Color</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Foreground Color</em>'.
     * @see org.eclipse.sirius.components.view.ButtonDescriptionStyle#getForegroundColor()
     * @see #getButtonDescriptionStyle()
     * @generated
     */
    EAttribute getButtonDescriptionStyle_ForegroundColor();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle
     * <em>Conditional Button Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Button Description Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle
     * @generated
     */
    EClass getConditionalButtonDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.BarChartDescriptionStyle <em>Bar
     * Chart Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Bar Chart Description Style</em>'.
     * @see org.eclipse.sirius.components.view.BarChartDescriptionStyle
     * @generated
     */
    EClass getBarChartDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.BarChartDescriptionStyle#getBarsColor <em>Bars Color</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Bars Color</em>'.
     * @see org.eclipse.sirius.components.view.BarChartDescriptionStyle#getBarsColor()
     * @see #getBarChartDescriptionStyle()
     * @generated
     */
    EAttribute getBarChartDescriptionStyle_BarsColor();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ConditionalBarChartDescriptionStyle
     * <em>Conditional Bar Chart Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Bar Chart Description Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalBarChartDescriptionStyle
     * @generated
     */
    EClass getConditionalBarChartDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.PieChartDescriptionStyle <em>Pie
     * Chart Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Pie Chart Description Style</em>'.
     * @see org.eclipse.sirius.components.view.PieChartDescriptionStyle
     * @generated
     */
    EClass getPieChartDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.PieChartDescriptionStyle#getColors <em>Colors</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Colors</em>'.
     * @see org.eclipse.sirius.components.view.PieChartDescriptionStyle#getColors()
     * @see #getPieChartDescriptionStyle()
     * @generated
     */
    EAttribute getPieChartDescriptionStyle_Colors();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.PieChartDescriptionStyle#getStrokeWidth <em>Stroke Width</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Stroke Width</em>'.
     * @see org.eclipse.sirius.components.view.PieChartDescriptionStyle#getStrokeWidth()
     * @see #getPieChartDescriptionStyle()
     * @generated
     */
    EAttribute getPieChartDescriptionStyle_StrokeWidth();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.PieChartDescriptionStyle#getStrokeColor <em>Stroke Color</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Stroke Color</em>'.
     * @see org.eclipse.sirius.components.view.PieChartDescriptionStyle#getStrokeColor()
     * @see #getPieChartDescriptionStyle()
     * @generated
     */
    EAttribute getPieChartDescriptionStyle_StrokeColor();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ConditionalPieChartDescriptionStyle
     * <em>Conditional Pie Chart Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Pie Chart Description Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalPieChartDescriptionStyle
     * @generated
     */
    EClass getConditionalPieChartDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.LabelDescription <em>Label
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Label Description</em>'.
     * @see org.eclipse.sirius.components.view.LabelDescription
     * @generated
     */
    EClass getLabelDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.LabelDescription#getValueExpression <em>Value Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.LabelDescription#getValueExpression()
     * @see #getLabelDescription()
     * @generated
     */
    EAttribute getLabelDescription_ValueExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.LabelDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.LabelDescription#getStyle()
     * @see #getLabelDescription()
     * @generated
     */
    EReference getLabelDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.LabelDescription#getConditionalStyles <em>Conditional Styles</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.LabelDescription#getConditionalStyles()
     * @see #getLabelDescription()
     * @generated
     */
    EReference getLabelDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.LabelDescriptionStyle <em>Label
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Label Description Style</em>'.
     * @see org.eclipse.sirius.components.view.LabelDescriptionStyle
     * @generated
     */
    EClass getLabelDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.LabelDescriptionStyle#getColor <em>Color</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Color</em>'.
     * @see org.eclipse.sirius.components.view.LabelDescriptionStyle#getColor()
     * @see #getLabelDescriptionStyle()
     * @generated
     */
    EAttribute getLabelDescriptionStyle_Color();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle
     * <em>Conditional Label Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Label Description Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle
     * @generated
     */
    EClass getConditionalLabelDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.LinkDescription <em>Link
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Link Description</em>'.
     * @see org.eclipse.sirius.components.view.LinkDescription
     * @generated
     */
    EClass getLinkDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.LinkDescription#getValueExpression <em>Value Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.LinkDescription#getValueExpression()
     * @see #getLinkDescription()
     * @generated
     */
    EAttribute getLinkDescription_ValueExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.LinkDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.LinkDescription#getStyle()
     * @see #getLinkDescription()
     * @generated
     */
    EReference getLinkDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.LinkDescription#getConditionalStyles <em>Conditional Styles</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.LinkDescription#getConditionalStyles()
     * @see #getLinkDescription()
     * @generated
     */
    EReference getLinkDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.LinkDescriptionStyle <em>Link
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Link Description Style</em>'.
     * @see org.eclipse.sirius.components.view.LinkDescriptionStyle
     * @generated
     */
    EClass getLinkDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.LinkDescriptionStyle#getColor <em>Color</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Color</em>'.
     * @see org.eclipse.sirius.components.view.LinkDescriptionStyle#getColor()
     * @see #getLinkDescriptionStyle()
     * @generated
     */
    EAttribute getLinkDescriptionStyle_Color();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ConditionalLinkDescriptionStyle
     * <em>Conditional Link Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Link Description Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalLinkDescriptionStyle
     * @generated
     */
    EClass getConditionalLinkDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ListDescription <em>List
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>List Description</em>'.
     * @see org.eclipse.sirius.components.view.ListDescription
     * @generated
     */
    EClass getListDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.ListDescription#getValueExpression <em>Value Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.ListDescription#getValueExpression()
     * @see #getListDescription()
     * @generated
     */
    EAttribute getListDescription_ValueExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.ListDescription#getDisplayExpression <em>Display Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Display Expression</em>'.
     * @see org.eclipse.sirius.components.view.ListDescription#getDisplayExpression()
     * @see #getListDescription()
     * @generated
     */
    EAttribute getListDescription_DisplayExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.ListDescription#getIsDeletableExpression <em>Is Deletable
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Deletable Expression</em>'.
     * @see org.eclipse.sirius.components.view.ListDescription#getIsDeletableExpression()
     * @see #getListDescription()
     * @generated
     */
    EAttribute getListDescription_IsDeletableExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.ListDescription#getBody <em>Body</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.ListDescription#getBody()
     * @see #getListDescription()
     * @generated
     */
    EReference getListDescription_Body();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.ListDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.ListDescription#getStyle()
     * @see #getListDescription()
     * @generated
     */
    EReference getListDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.ListDescription#getConditionalStyles <em>Conditional Styles</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.ListDescription#getConditionalStyles()
     * @see #getListDescription()
     * @generated
     */
    EReference getListDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ListDescriptionStyle <em>List
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>List Description Style</em>'.
     * @see org.eclipse.sirius.components.view.ListDescriptionStyle
     * @generated
     */
    EClass getListDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.ListDescriptionStyle#getColor <em>Color</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Color</em>'.
     * @see org.eclipse.sirius.components.view.ListDescriptionStyle#getColor()
     * @see #getListDescriptionStyle()
     * @generated
     */
    EAttribute getListDescriptionStyle_Color();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ConditionalListDescriptionStyle
     * <em>Conditional List Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional List Description Style</em>'.
     * @see org.eclipse.sirius.components.view.ConditionalListDescriptionStyle
     * @generated
     */
    EClass getConditionalListDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.DiagramPalette <em>Diagram
     * Palette</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Diagram Palette</em>'.
     * @see org.eclipse.sirius.components.view.DiagramPalette
     * @generated
     */
    EClass getDiagramPalette();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.DiagramPalette#getDropTool <em>Drop Tool</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Drop Tool</em>'.
     * @see org.eclipse.sirius.components.view.DiagramPalette#getDropTool()
     * @see #getDiagramPalette()
     * @generated
     */
    EReference getDiagramPalette_DropTool();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.DiagramPalette#getNodeTools <em>Node Tools</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @see org.eclipse.sirius.components.view.DiagramPalette#getNodeTools()
     * @see #getDiagramPalette()
     * @generated
     */
    EReference getDiagramPalette_NodeTools();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.NodePalette <em>Node Palette</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Palette</em>'.
     * @see org.eclipse.sirius.components.view.NodePalette
     * @generated
     */
    EClass getNodePalette();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.NodePalette#getDeleteTool <em>Delete Tool</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Delete Tool</em>'.
     * @see org.eclipse.sirius.components.view.NodePalette#getDeleteTool()
     * @see #getNodePalette()
     * @generated
     */
    EReference getNodePalette_DeleteTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.NodePalette#getLabelEditTool <em>Label Edit Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Label Edit Tool</em>'.
     * @see org.eclipse.sirius.components.view.NodePalette#getLabelEditTool()
     * @see #getNodePalette()
     * @generated
     */
    EReference getNodePalette_LabelEditTool();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.NodePalette#getNodeTools <em>Node Tools</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @see org.eclipse.sirius.components.view.NodePalette#getNodeTools()
     * @see #getNodePalette()
     * @generated
     */
    EReference getNodePalette_NodeTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.NodePalette#getEdgeTools <em>Edge Tools</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Edge Tools</em>'.
     * @see org.eclipse.sirius.components.view.NodePalette#getEdgeTools()
     * @see #getNodePalette()
     * @generated
     */
    EReference getNodePalette_EdgeTools();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.EdgePalette <em>Edge Palette</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Palette</em>'.
     * @see org.eclipse.sirius.components.view.EdgePalette
     * @generated
     */
    EClass getEdgePalette();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.EdgePalette#getDeleteTool <em>Delete Tool</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Delete Tool</em>'.
     * @see org.eclipse.sirius.components.view.EdgePalette#getDeleteTool()
     * @see #getEdgePalette()
     * @generated
     */
    EReference getEdgePalette_DeleteTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.EdgePalette#getCenterLabelEditTool <em>Center Label Edit Tool</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Center Label Edit Tool</em>'.
     * @see org.eclipse.sirius.components.view.EdgePalette#getCenterLabelEditTool()
     * @see #getEdgePalette()
     * @generated
     */
    EReference getEdgePalette_CenterLabelEditTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.EdgePalette#getBeginLabelEditTool <em>Begin Label Edit Tool</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Begin Label Edit Tool</em>'.
     * @see org.eclipse.sirius.components.view.EdgePalette#getBeginLabelEditTool()
     * @see #getEdgePalette()
     * @generated
     */
    EReference getEdgePalette_BeginLabelEditTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.EdgePalette#getEndLabelEditTool <em>End Label Edit Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>End Label Edit Tool</em>'.
     * @see org.eclipse.sirius.components.view.EdgePalette#getEndLabelEditTool()
     * @see #getEdgePalette()
     * @generated
     */
    EReference getEdgePalette_EndLabelEditTool();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.EdgePalette#getNodeTools <em>Node Tools</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @see org.eclipse.sirius.components.view.EdgePalette#getNodeTools()
     * @see #getEdgePalette()
     * @generated
     */
    EReference getEdgePalette_NodeTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.EdgePalette#getEdgeReconnectionTools <em>Edge Reconnection
     * Tools</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Edge Reconnection Tools</em>'.
     * @see org.eclipse.sirius.components.view.EdgePalette#getEdgeReconnectionTools()
     * @see #getEdgePalette()
     * @generated
     */
    EReference getEdgePalette_EdgeReconnectionTools();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.LayoutDirection <em>Layout
     * Direction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Layout Direction</em>'.
     * @see org.eclipse.sirius.components.view.LayoutDirection
     * @generated
     */
    EEnum getLayoutDirection();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.BarChartDescription <em>Bar Chart
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Bar Chart Description</em>'.
     * @see org.eclipse.sirius.components.view.BarChartDescription
     * @generated
     */
    EClass getBarChartDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.BarChartDescription#getValuesExpression <em>Values Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Values Expression</em>'.
     * @see org.eclipse.sirius.components.view.BarChartDescription#getValuesExpression()
     * @see #getBarChartDescription()
     * @generated
     */
    EAttribute getBarChartDescription_ValuesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.BarChartDescription#getKeysExpression <em>Keys Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Keys Expression</em>'.
     * @see org.eclipse.sirius.components.view.BarChartDescription#getKeysExpression()
     * @see #getBarChartDescription()
     * @generated
     */
    EAttribute getBarChartDescription_KeysExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.BarChartDescription#getYAxisLabelExpression <em>YAxis Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>YAxis Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.BarChartDescription#getYAxisLabelExpression()
     * @see #getBarChartDescription()
     * @generated
     */
    EAttribute getBarChartDescription_YAxisLabelExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.BarChartDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.BarChartDescription#getStyle()
     * @see #getBarChartDescription()
     * @generated
     */
    EReference getBarChartDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.BarChartDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.BarChartDescription#getConditionalStyles()
     * @see #getBarChartDescription()
     * @generated
     */
    EReference getBarChartDescription_ConditionalStyles();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.BarChartDescription#getWidth
     * <em>Width</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Width</em>'.
     * @see org.eclipse.sirius.components.view.BarChartDescription#getWidth()
     * @see #getBarChartDescription()
     * @generated
     */
    EAttribute getBarChartDescription_Width();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.BarChartDescription#getHeight <em>Height</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Height</em>'.
     * @see org.eclipse.sirius.components.view.BarChartDescription#getHeight()
     * @see #getBarChartDescription()
     * @generated
     */
    EAttribute getBarChartDescription_Height();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.PieChartDescription <em>Pie Chart
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Pie Chart Description</em>'.
     * @see org.eclipse.sirius.components.view.PieChartDescription
     * @generated
     */
    EClass getPieChartDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.PieChartDescription#getValuesExpression <em>Values Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Values Expression</em>'.
     * @see org.eclipse.sirius.components.view.PieChartDescription#getValuesExpression()
     * @see #getPieChartDescription()
     * @generated
     */
    EAttribute getPieChartDescription_ValuesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.PieChartDescription#getKeysExpression <em>Keys Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Keys Expression</em>'.
     * @see org.eclipse.sirius.components.view.PieChartDescription#getKeysExpression()
     * @see #getPieChartDescription()
     * @generated
     */
    EAttribute getPieChartDescription_KeysExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.PieChartDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.PieChartDescription#getStyle()
     * @see #getPieChartDescription()
     * @generated
     */
    EReference getPieChartDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.PieChartDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.PieChartDescription#getConditionalStyles()
     * @see #getPieChartDescription()
     * @generated
     */
    EReference getPieChartDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.FlexboxContainerDescription
     * <em>Flexbox Container Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Flexbox Container Description</em>'.
     * @see org.eclipse.sirius.components.view.FlexboxContainerDescription
     * @generated
     */
    EClass getFlexboxContainerDescription();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.FlexboxContainerDescription#getChildren <em>Children</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Children</em>'.
     * @see org.eclipse.sirius.components.view.FlexboxContainerDescription#getChildren()
     * @see #getFlexboxContainerDescription()
     * @generated
     */
    EReference getFlexboxContainerDescription_Children();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.FlexboxContainerDescription#getFlexDirection <em>Flex
     * Direction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Flex Direction</em>'.
     * @see org.eclipse.sirius.components.view.FlexboxContainerDescription#getFlexDirection()
     * @see #getFlexboxContainerDescription()
     * @generated
     */
    EAttribute getFlexboxContainerDescription_FlexDirection();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ButtonDescription <em>Button
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Button Description</em>'.
     * @see org.eclipse.sirius.components.view.ButtonDescription
     * @generated
     */
    EClass getButtonDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.ButtonDescription#getButtonLabelExpression <em>Button Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Button Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.ButtonDescription#getButtonLabelExpression()
     * @see #getButtonDescription()
     * @generated
     */
    EAttribute getButtonDescription_ButtonLabelExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.ButtonDescription#getBody <em>Body</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.ButtonDescription#getBody()
     * @see #getButtonDescription()
     * @generated
     */
    EReference getButtonDescription_Body();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.ButtonDescription#getImageExpression <em>Image Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Image Expression</em>'.
     * @see org.eclipse.sirius.components.view.ButtonDescription#getImageExpression()
     * @see #getButtonDescription()
     * @generated
     */
    EAttribute getButtonDescription_ImageExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.ButtonDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.ButtonDescription#getStyle()
     * @see #getButtonDescription()
     * @generated
     */
    EReference getButtonDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.ButtonDescription#getConditionalStyles <em>Conditional Styles</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.ButtonDescription#getConditionalStyles()
     * @see #getButtonDescription()
     * @generated
     */
    EReference getButtonDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ImageDescription <em>Image
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Image Description</em>'.
     * @see org.eclipse.sirius.components.view.ImageDescription
     * @generated
     */
    EClass getImageDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.ImageDescription#getUrlExpression <em>Url Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Url Expression</em>'.
     * @see org.eclipse.sirius.components.view.ImageDescription#getUrlExpression()
     * @see #getImageDescription()
     * @generated
     */
    EAttribute getImageDescription_UrlExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.ImageDescription#getMaxWidthExpression <em>Max Width
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Max Width Expression</em>'.
     * @see org.eclipse.sirius.components.view.ImageDescription#getMaxWidthExpression()
     * @see #getImageDescription()
     * @generated
     */
    EAttribute getImageDescription_MaxWidthExpression();

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
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.NodeContainmentKind <em>Node
     * Containment Kind</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Node Containment Kind</em>'.
     * @see org.eclipse.sirius.components.view.NodeContainmentKind
     * @generated
     */
    EEnum getNodeContainmentKind();

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
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.GroupDisplayMode <em>Group Display
     * Mode</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Group Display Mode</em>'.
     * @see org.eclipse.sirius.components.view.GroupDisplayMode
     * @generated
     */
    EEnum getGroupDisplayMode();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.FlexDirection <em>Flex
     * Direction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Flex Direction</em>'.
     * @see org.eclipse.sirius.components.view.FlexDirection
     * @generated
     */
    EEnum getFlexDirection();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Identifier</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for data type '<em>Identifier</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     * @generated
     */
    EDataType getIdentifier();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Interpreted Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for data type '<em>Interpreted Expression</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     * @generated
     */
    EDataType getInterpretedExpression();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Domain Type</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for data type '<em>Domain Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     * @generated
     */
    EDataType getDomainType();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Color</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for data type '<em>Color</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     * @generated
     */
    EDataType getColor();

    /**
     * Returns the meta object for data type '<em>Length</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for data type '<em>Length</em>'.
     * @model instanceClass="int"
     * @generated
     */
    EDataType getLength();

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
         * The meta object literal for the '<em><b>Palette</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_DESCRIPTION__PALETTE = eINSTANCE.getDiagramDescription_Palette();

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
         * The meta object literal for the '<em><b>Synchronization Policy</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY = eINSTANCE.getDiagramElementDescription_SynchronizationPolicy();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION = eINSTANCE.getDiagramElementDescription_PreconditionExpression();

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
         * The meta object literal for the '<em><b>Reused Child Node Descriptions</b></em>' reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS = eINSTANCE.getNodeDescription_ReusedChildNodeDescriptions();

        /**
         * The meta object literal for the '<em><b>Reused Border Node Descriptions</b></em>' reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS = eINSTANCE.getNodeDescription_ReusedBorderNodeDescriptions();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__STYLE = eINSTANCE.getNodeDescription_Style();

        /**
         * The meta object literal for the '<em><b>User Resizable</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_DESCRIPTION__USER_RESIZABLE = eINSTANCE.getNodeDescription_UserResizable();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getNodeDescription_ConditionalStyles();

        /**
         * The meta object literal for the '<em><b>Children Layout Strategy</b></em>' containment reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY = eINSTANCE.getNodeDescription_ChildrenLayoutStrategy();

        /**
         * The meta object literal for the '<em><b>Collapsible</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_DESCRIPTION__COLLAPSIBLE = eINSTANCE.getNodeDescription_Collapsible();

        /**
         * The meta object literal for the '<em><b>Palette</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__PALETTE = eINSTANCE.getNodeDescription_Palette();

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
         * The meta object literal for the '<em><b>Palette</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_DESCRIPTION__PALETTE = eINSTANCE.getEdgeDescription_Palette();

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
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.NodeStyleDescription <em>Node
         * Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.NodeStyleDescription
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getNodeStyleDescription()
         * @generated
         */
        EClass NODE_STYLE_DESCRIPTION = eINSTANCE.getNodeStyleDescription();

        /**
         * The meta object literal for the '<em><b>Label Color</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_STYLE_DESCRIPTION__LABEL_COLOR = eINSTANCE.getNodeStyleDescription_LabelColor();

        /**
         * The meta object literal for the '<em><b>Width Computation Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION = eINSTANCE.getNodeStyleDescription_WidthComputationExpression();

        /**
         * The meta object literal for the '<em><b>Height Computation Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION = eINSTANCE.getNodeStyleDescription_HeightComputationExpression();

        /**
         * The meta object literal for the '<em><b>Show Icon</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_STYLE_DESCRIPTION__SHOW_ICON = eINSTANCE.getNodeStyleDescription_ShowIcon();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.RectangularNodeStyleDescriptionImpl <em>Rectangular Node
         * Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.RectangularNodeStyleDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getRectangularNodeStyleDescription()
         * @generated
         */
        EClass RECTANGULAR_NODE_STYLE_DESCRIPTION = eINSTANCE.getRectangularNodeStyleDescription();

        /**
         * The meta object literal for the '<em><b>With Header</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute RECTANGULAR_NODE_STYLE_DESCRIPTION__WITH_HEADER = eINSTANCE.getRectangularNodeStyleDescription_WithHeader();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ImageNodeStyleDescriptionImpl
         * <em>Image Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ImageNodeStyleDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getImageNodeStyleDescription()
         * @generated
         */
        EClass IMAGE_NODE_STYLE_DESCRIPTION = eINSTANCE.getImageNodeStyleDescription();

        /**
         * The meta object literal for the '<em><b>Shape</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute IMAGE_NODE_STYLE_DESCRIPTION__SHAPE = eINSTANCE.getImageNodeStyleDescription_Shape();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl <em>Icon Label Node Style
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getIconLabelNodeStyleDescription()
         * @generated
         */
        EClass ICON_LABEL_NODE_STYLE_DESCRIPTION = eINSTANCE.getIconLabelNodeStyleDescription();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.LayoutStrategyDescription
         * <em>Layout Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.LayoutStrategyDescription
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLayoutStrategyDescription()
         * @generated
         */
        EClass LAYOUT_STRATEGY_DESCRIPTION = eINSTANCE.getLayoutStrategyDescription();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.FreeFormLayoutStrategyDescriptionImpl <em>Free Form Layout
         * Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.FreeFormLayoutStrategyDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getFreeFormLayoutStrategyDescription()
         * @generated
         */
        EClass FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION = eINSTANCE.getFreeFormLayoutStrategyDescription();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.ListLayoutStrategyDescriptionImpl <em>List Layout Strategy
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ListLayoutStrategyDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getListLayoutStrategyDescription()
         * @generated
         */
        EClass LIST_LAYOUT_STRATEGY_DESCRIPTION = eINSTANCE.getListLayoutStrategyDescription();

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
         * The meta object literal for the '<em><b>Show Icon</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_STYLE__SHOW_ICON = eINSTANCE.getEdgeStyle_ShowIcon();

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
         * The meta object literal for the '<em><b>Initial Direct Edit Label Expression</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_EDIT_TOOL__INITIAL_DIRECT_EDIT_LABEL_EXPRESSION = eINSTANCE.getLabelEditTool_InitialDirectEditLabelExpression();

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
         * The meta object literal for the '<em><b>Target Element Descriptions</b></em>' reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS = eINSTANCE.getEdgeTool_TargetElementDescriptions();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.EdgeReconnectionToolImpl
         * <em>Edge Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.EdgeReconnectionToolImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getEdgeReconnectionTool()
         * @generated
         */
        EClass EDGE_RECONNECTION_TOOL = eINSTANCE.getEdgeReconnectionTool();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.SourceEdgeEndReconnectionToolImpl <em>Source Edge End
         * Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.SourceEdgeEndReconnectionToolImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getSourceEdgeEndReconnectionTool()
         * @generated
         */
        EClass SOURCE_EDGE_END_RECONNECTION_TOOL = eINSTANCE.getSourceEdgeEndReconnectionTool();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.TargetEdgeEndReconnectionToolImpl <em>Target Edge End
         * Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.TargetEdgeEndReconnectionToolImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getTargetEdgeEndReconnectionTool()
         * @generated
         */
        EClass TARGET_EDGE_END_RECONNECTION_TOOL = eINSTANCE.getTargetEdgeEndReconnectionTool();

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
         * The meta object literal for the '<em><b>Containment Kind</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CREATE_VIEW__CONTAINMENT_KIND = eINSTANCE.getCreateView_ContainmentKind();

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
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CONDITIONAL_NODE_STYLE__STYLE = eINSTANCE.getConditionalNodeStyle_Style();

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
         * The meta object literal for the '<em><b>Groups</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference FORM_DESCRIPTION__GROUPS = eINSTANCE.getFormDescription_Groups();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.GroupDescriptionImpl
         * <em>Group Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.GroupDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getGroupDescription()
         * @generated
         */
        EClass GROUP_DESCRIPTION = eINSTANCE.getGroupDescription();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute GROUP_DESCRIPTION__NAME = eINSTANCE.getGroupDescription_Name();

        /**
         * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute GROUP_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getGroupDescription_LabelExpression();

        /**
         * The meta object literal for the '<em><b>Display Mode</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute GROUP_DESCRIPTION__DISPLAY_MODE = eINSTANCE.getGroupDescription_DisplayMode();

        /**
         * The meta object literal for the '<em><b>Semantic Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = eINSTANCE.getGroupDescription_SemanticCandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Toolbar Actions</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference GROUP_DESCRIPTION__TOOLBAR_ACTIONS = eINSTANCE.getGroupDescription_ToolbarActions();

        /**
         * The meta object literal for the '<em><b>Widgets</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference GROUP_DESCRIPTION__WIDGETS = eINSTANCE.getGroupDescription_Widgets();

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
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXTFIELD_DESCRIPTION__STYLE = eINSTANCE.getTextfieldDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXTFIELD_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getTextfieldDescription_ConditionalStyles();

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
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CHECKBOX_DESCRIPTION__STYLE = eINSTANCE.getCheckboxDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CHECKBOX_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getCheckboxDescription_ConditionalStyles();

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
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SELECT_DESCRIPTION__STYLE = eINSTANCE.getSelectDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SELECT_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getSelectDescription_ConditionalStyles();

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
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_SELECT_DESCRIPTION__STYLE = eINSTANCE.getMultiSelectDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getMultiSelectDescription_ConditionalStyles();

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
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXT_AREA_DESCRIPTION__STYLE = eINSTANCE.getTextAreaDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXT_AREA_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getTextAreaDescription_ConditionalStyles();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.RichTextDescriptionImpl
         * <em>Rich Text Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.RichTextDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getRichTextDescription()
         * @generated
         */
        EClass RICH_TEXT_DESCRIPTION = eINSTANCE.getRichTextDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute RICH_TEXT_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getRichTextDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RICH_TEXT_DESCRIPTION__BODY = eINSTANCE.getRichTextDescription_Body();

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
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RADIO_DESCRIPTION__STYLE = eINSTANCE.getRadioDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RADIO_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getRadioDescription_ConditionalStyles();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.WidgetDescriptionStyleImpl
         * <em>Widget Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.WidgetDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getWidgetDescriptionStyle()
         * @generated
         */
        EClass WIDGET_DESCRIPTION_STYLE = eINSTANCE.getWidgetDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.TextfieldDescriptionStyleImpl
         * <em>Textfield Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.TextfieldDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getTextfieldDescriptionStyle()
         * @generated
         */
        EClass TEXTFIELD_DESCRIPTION_STYLE = eINSTANCE.getTextfieldDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Background Color</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR = eINSTANCE.getTextfieldDescriptionStyle_BackgroundColor();

        /**
         * The meta object literal for the '<em><b>Foreground Color</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR = eINSTANCE.getTextfieldDescriptionStyle_ForegroundColor();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.ConditionalTextfieldDescriptionStyleImpl <em>Conditional
         * Textfield Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalTextfieldDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalTextfieldDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE = eINSTANCE.getConditionalTextfieldDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.CheckboxDescriptionStyleImpl
         * <em>Checkbox Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.CheckboxDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getCheckboxDescriptionStyle()
         * @generated
         */
        EClass CHECKBOX_DESCRIPTION_STYLE = eINSTANCE.getCheckboxDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Color</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute CHECKBOX_DESCRIPTION_STYLE__COLOR = eINSTANCE.getCheckboxDescriptionStyle_Color();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.ConditionalCheckboxDescriptionStyleImpl <em>Conditional
         * Checkbox Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalCheckboxDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalCheckboxDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE = eINSTANCE.getConditionalCheckboxDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.SelectDescriptionStyleImpl
         * <em>Select Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.SelectDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getSelectDescriptionStyle()
         * @generated
         */
        EClass SELECT_DESCRIPTION_STYLE = eINSTANCE.getSelectDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Background Color</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR = eINSTANCE.getSelectDescriptionStyle_BackgroundColor();

        /**
         * The meta object literal for the '<em><b>Foreground Color</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR = eINSTANCE.getSelectDescriptionStyle_ForegroundColor();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.ConditionalSelectDescriptionStyleImpl <em>Conditional Select
         * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalSelectDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalSelectDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_SELECT_DESCRIPTION_STYLE = eINSTANCE.getConditionalSelectDescriptionStyle();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.MultiSelectDescriptionStyleImpl <em>Multi Select Description
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.MultiSelectDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getMultiSelectDescriptionStyle()
         * @generated
         */
        EClass MULTI_SELECT_DESCRIPTION_STYLE = eINSTANCE.getMultiSelectDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Background Color</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR = eINSTANCE.getMultiSelectDescriptionStyle_BackgroundColor();

        /**
         * The meta object literal for the '<em><b>Foreground Color</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR = eINSTANCE.getMultiSelectDescriptionStyle_ForegroundColor();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.ConditionalMultiSelectDescriptionStyleImpl <em>Conditional
         * Multi Select Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalMultiSelectDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalMultiSelectDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE = eINSTANCE.getConditionalMultiSelectDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.TextareaDescriptionStyleImpl
         * <em>Textarea Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.TextareaDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getTextareaDescriptionStyle()
         * @generated
         */
        EClass TEXTAREA_DESCRIPTION_STYLE = eINSTANCE.getTextareaDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Background Color</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR = eINSTANCE.getTextareaDescriptionStyle_BackgroundColor();

        /**
         * The meta object literal for the '<em><b>Foreground Color</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR = eINSTANCE.getTextareaDescriptionStyle_ForegroundColor();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.ConditionalTextareaDescriptionStyleImpl <em>Conditional
         * Textarea Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalTextareaDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalTextareaDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE = eINSTANCE.getConditionalTextareaDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.RadioDescriptionStyleImpl
         * <em>Radio Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.RadioDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getRadioDescriptionStyle()
         * @generated
         */
        EClass RADIO_DESCRIPTION_STYLE = eINSTANCE.getRadioDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Color</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute RADIO_DESCRIPTION_STYLE__COLOR = eINSTANCE.getRadioDescriptionStyle_Color();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.ConditionalRadioDescriptionStyleImpl <em>Conditional Radio
         * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalRadioDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalRadioDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_RADIO_DESCRIPTION_STYLE = eINSTANCE.getConditionalRadioDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ButtonDescriptionStyleImpl
         * <em>Button Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ButtonDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getButtonDescriptionStyle()
         * @generated
         */
        EClass BUTTON_DESCRIPTION_STYLE = eINSTANCE.getButtonDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Background Color</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BUTTON_DESCRIPTION_STYLE__BACKGROUND_COLOR = eINSTANCE.getButtonDescriptionStyle_BackgroundColor();

        /**
         * The meta object literal for the '<em><b>Foreground Color</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BUTTON_DESCRIPTION_STYLE__FOREGROUND_COLOR = eINSTANCE.getButtonDescriptionStyle_ForegroundColor();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.ConditionalButtonDescriptionStyleImpl <em>Conditional Button
         * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalButtonDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalButtonDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_BUTTON_DESCRIPTION_STYLE = eINSTANCE.getConditionalButtonDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.BarChartDescriptionStyleImpl
         * <em>Bar Chart Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.BarChartDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getBarChartDescriptionStyle()
         * @generated
         */
        EClass BAR_CHART_DESCRIPTION_STYLE = eINSTANCE.getBarChartDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Bars Color</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR = eINSTANCE.getBarChartDescriptionStyle_BarsColor();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.ConditionalBarChartDescriptionStyleImpl <em>Conditional Bar
         * Chart Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalBarChartDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalBarChartDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE = eINSTANCE.getConditionalBarChartDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.PieChartDescriptionStyleImpl
         * <em>Pie Chart Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.PieChartDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getPieChartDescriptionStyle()
         * @generated
         */
        EClass PIE_CHART_DESCRIPTION_STYLE = eINSTANCE.getPieChartDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Colors</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute PIE_CHART_DESCRIPTION_STYLE__COLORS = eINSTANCE.getPieChartDescriptionStyle_Colors();

        /**
         * The meta object literal for the '<em><b>Stroke Width</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PIE_CHART_DESCRIPTION_STYLE__STROKE_WIDTH = eINSTANCE.getPieChartDescriptionStyle_StrokeWidth();

        /**
         * The meta object literal for the '<em><b>Stroke Color</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PIE_CHART_DESCRIPTION_STYLE__STROKE_COLOR = eINSTANCE.getPieChartDescriptionStyle_StrokeColor();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.ConditionalPieChartDescriptionStyleImpl <em>Conditional Pie
         * Chart Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalPieChartDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalPieChartDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE = eINSTANCE.getConditionalPieChartDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.LabelDescriptionImpl
         * <em>Label Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.LabelDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLabelDescription()
         * @generated
         */
        EClass LABEL_DESCRIPTION = eINSTANCE.getLabelDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getLabelDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LABEL_DESCRIPTION__STYLE = eINSTANCE.getLabelDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LABEL_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getLabelDescription_ConditionalStyles();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.LabelDescriptionStyleImpl
         * <em>Label Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.LabelDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLabelDescriptionStyle()
         * @generated
         */
        EClass LABEL_DESCRIPTION_STYLE = eINSTANCE.getLabelDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Color</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_DESCRIPTION_STYLE__COLOR = eINSTANCE.getLabelDescriptionStyle_Color();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.ConditionalLabelDescriptionStyleImpl <em>Conditional Label
         * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalLabelDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalLabelDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_LABEL_DESCRIPTION_STYLE = eINSTANCE.getConditionalLabelDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.LinkDescriptionImpl <em>Link
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.LinkDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLinkDescription()
         * @generated
         */
        EClass LINK_DESCRIPTION = eINSTANCE.getLinkDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LINK_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getLinkDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LINK_DESCRIPTION__STYLE = eINSTANCE.getLinkDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LINK_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getLinkDescription_ConditionalStyles();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.LinkDescriptionStyleImpl
         * <em>Link Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.LinkDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLinkDescriptionStyle()
         * @generated
         */
        EClass LINK_DESCRIPTION_STYLE = eINSTANCE.getLinkDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Color</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute LINK_DESCRIPTION_STYLE__COLOR = eINSTANCE.getLinkDescriptionStyle_Color();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.ConditionalLinkDescriptionStyleImpl <em>Conditional Link
         * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalLinkDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalLinkDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_LINK_DESCRIPTION_STYLE = eINSTANCE.getConditionalLinkDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ListDescriptionImpl <em>List
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ListDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getListDescription()
         * @generated
         */
        EClass LIST_DESCRIPTION = eINSTANCE.getListDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LIST_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getListDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Display Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LIST_DESCRIPTION__DISPLAY_EXPRESSION = eINSTANCE.getListDescription_DisplayExpression();

        /**
         * The meta object literal for the '<em><b>Is Deletable Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LIST_DESCRIPTION__IS_DELETABLE_EXPRESSION = eINSTANCE.getListDescription_IsDeletableExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LIST_DESCRIPTION__BODY = eINSTANCE.getListDescription_Body();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LIST_DESCRIPTION__STYLE = eINSTANCE.getListDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LIST_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getListDescription_ConditionalStyles();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ListDescriptionStyleImpl
         * <em>List Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ListDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getListDescriptionStyle()
         * @generated
         */
        EClass LIST_DESCRIPTION_STYLE = eINSTANCE.getListDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Color</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute LIST_DESCRIPTION_STYLE__COLOR = eINSTANCE.getListDescriptionStyle_Color();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.ConditionalListDescriptionStyleImpl <em>Conditional List
         * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalListDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditionalListDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_LIST_DESCRIPTION_STYLE = eINSTANCE.getConditionalListDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.DiagramPaletteImpl
         * <em>Diagram Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.DiagramPaletteImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDiagramPalette()
         * @generated
         */
        EClass DIAGRAM_PALETTE = eINSTANCE.getDiagramPalette();

        /**
         * The meta object literal for the '<em><b>Drop Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_PALETTE__DROP_TOOL = eINSTANCE.getDiagramPalette_DropTool();

        /**
         * The meta object literal for the '<em><b>Node Tools</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_PALETTE__NODE_TOOLS = eINSTANCE.getDiagramPalette_NodeTools();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.NodePaletteImpl <em>Node
         * Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.NodePaletteImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getNodePalette()
         * @generated
         */
        EClass NODE_PALETTE = eINSTANCE.getNodePalette();

        /**
         * The meta object literal for the '<em><b>Delete Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_PALETTE__DELETE_TOOL = eINSTANCE.getNodePalette_DeleteTool();

        /**
         * The meta object literal for the '<em><b>Label Edit Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_PALETTE__LABEL_EDIT_TOOL = eINSTANCE.getNodePalette_LabelEditTool();

        /**
         * The meta object literal for the '<em><b>Node Tools</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_PALETTE__NODE_TOOLS = eINSTANCE.getNodePalette_NodeTools();

        /**
         * The meta object literal for the '<em><b>Edge Tools</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_PALETTE__EDGE_TOOLS = eINSTANCE.getNodePalette_EdgeTools();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.EdgePaletteImpl <em>Edge
         * Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.EdgePaletteImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getEdgePalette()
         * @generated
         */
        EClass EDGE_PALETTE = eINSTANCE.getEdgePalette();

        /**
         * The meta object literal for the '<em><b>Delete Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_PALETTE__DELETE_TOOL = eINSTANCE.getEdgePalette_DeleteTool();

        /**
         * The meta object literal for the '<em><b>Center Label Edit Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL = eINSTANCE.getEdgePalette_CenterLabelEditTool();

        /**
         * The meta object literal for the '<em><b>Begin Label Edit Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL = eINSTANCE.getEdgePalette_BeginLabelEditTool();

        /**
         * The meta object literal for the '<em><b>End Label Edit Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_PALETTE__END_LABEL_EDIT_TOOL = eINSTANCE.getEdgePalette_EndLabelEditTool();

        /**
         * The meta object literal for the '<em><b>Node Tools</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_PALETTE__NODE_TOOLS = eINSTANCE.getEdgePalette_NodeTools();

        /**
         * The meta object literal for the '<em><b>Edge Reconnection Tools</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_PALETTE__EDGE_RECONNECTION_TOOLS = eINSTANCE.getEdgePalette_EdgeReconnectionTools();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.LayoutDirection <em>Layout
         * Direction</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.LayoutDirection
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLayoutDirection()
         * @generated
         */
        EEnum LAYOUT_DIRECTION = eINSTANCE.getLayoutDirection();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.BarChartDescriptionImpl
         * <em>Bar Chart Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.BarChartDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getBarChartDescription()
         * @generated
         */
        EClass BAR_CHART_DESCRIPTION = eINSTANCE.getBarChartDescription();

        /**
         * The meta object literal for the '<em><b>Values Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BAR_CHART_DESCRIPTION__VALUES_EXPRESSION = eINSTANCE.getBarChartDescription_ValuesExpression();

        /**
         * The meta object literal for the '<em><b>Keys Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BAR_CHART_DESCRIPTION__KEYS_EXPRESSION = eINSTANCE.getBarChartDescription_KeysExpression();

        /**
         * The meta object literal for the '<em><b>YAxis Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION = eINSTANCE.getBarChartDescription_YAxisLabelExpression();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference BAR_CHART_DESCRIPTION__STYLE = eINSTANCE.getBarChartDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getBarChartDescription_ConditionalStyles();

        /**
         * The meta object literal for the '<em><b>Width</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute BAR_CHART_DESCRIPTION__WIDTH = eINSTANCE.getBarChartDescription_Width();

        /**
         * The meta object literal for the '<em><b>Height</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute BAR_CHART_DESCRIPTION__HEIGHT = eINSTANCE.getBarChartDescription_Height();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.PieChartDescriptionImpl
         * <em>Pie Chart Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.PieChartDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getPieChartDescription()
         * @generated
         */
        EClass PIE_CHART_DESCRIPTION = eINSTANCE.getPieChartDescription();

        /**
         * The meta object literal for the '<em><b>Values Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PIE_CHART_DESCRIPTION__VALUES_EXPRESSION = eINSTANCE.getPieChartDescription_ValuesExpression();

        /**
         * The meta object literal for the '<em><b>Keys Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PIE_CHART_DESCRIPTION__KEYS_EXPRESSION = eINSTANCE.getPieChartDescription_KeysExpression();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PIE_CHART_DESCRIPTION__STYLE = eINSTANCE.getPieChartDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PIE_CHART_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getPieChartDescription_ConditionalStyles();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.impl.FlexboxContainerDescriptionImpl <em>Flexbox Container
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.FlexboxContainerDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getFlexboxContainerDescription()
         * @generated
         */
        EClass FLEXBOX_CONTAINER_DESCRIPTION = eINSTANCE.getFlexboxContainerDescription();

        /**
         * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN = eINSTANCE.getFlexboxContainerDescription_Children();

        /**
         * The meta object literal for the '<em><b>Flex Direction</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION = eINSTANCE.getFlexboxContainerDescription_FlexDirection();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ButtonDescriptionImpl
         * <em>Button Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ButtonDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getButtonDescription()
         * @generated
         */
        EClass BUTTON_DESCRIPTION = eINSTANCE.getButtonDescription();

        /**
         * The meta object literal for the '<em><b>Button Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BUTTON_DESCRIPTION__BUTTON_LABEL_EXPRESSION = eINSTANCE.getButtonDescription_ButtonLabelExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference BUTTON_DESCRIPTION__BODY = eINSTANCE.getButtonDescription_Body();

        /**
         * The meta object literal for the '<em><b>Image Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BUTTON_DESCRIPTION__IMAGE_EXPRESSION = eINSTANCE.getButtonDescription_ImageExpression();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference BUTTON_DESCRIPTION__STYLE = eINSTANCE.getButtonDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference BUTTON_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getButtonDescription_ConditionalStyles();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ImageDescriptionImpl
         * <em>Image Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ImageDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getImageDescription()
         * @generated
         */
        EClass IMAGE_DESCRIPTION = eINSTANCE.getImageDescription();

        /**
         * The meta object literal for the '<em><b>Url Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute IMAGE_DESCRIPTION__URL_EXPRESSION = eINSTANCE.getImageDescription_UrlExpression();

        /**
         * The meta object literal for the '<em><b>Max Width Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION = eINSTANCE.getImageDescription_MaxWidthExpression();

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
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.NodeContainmentKind <em>Node
         * Containment Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.NodeContainmentKind
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getNodeContainmentKind()
         * @generated
         */
        EEnum NODE_CONTAINMENT_KIND = eINSTANCE.getNodeContainmentKind();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.SynchronizationPolicy
         * <em>Synchronization Policy</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.SynchronizationPolicy
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getSynchronizationPolicy()
         * @generated
         */
        EEnum SYNCHRONIZATION_POLICY = eINSTANCE.getSynchronizationPolicy();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.GroupDisplayMode <em>Group Display
         * Mode</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.GroupDisplayMode
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getGroupDisplayMode()
         * @generated
         */
        EEnum GROUP_DISPLAY_MODE = eINSTANCE.getGroupDisplayMode();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.FlexDirection <em>Flex
         * Direction</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.FlexDirection
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getFlexDirection()
         * @generated
         */
        EEnum FLEX_DIRECTION = eINSTANCE.getFlexDirection();

        /**
         * The meta object literal for the '<em>Identifier</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see java.lang.String
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getIdentifier()
         * @generated
         */
        EDataType IDENTIFIER = eINSTANCE.getIdentifier();

        /**
         * The meta object literal for the '<em>Interpreted Expression</em>' data type. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @see java.lang.String
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getInterpretedExpression()
         * @generated
         */
        EDataType INTERPRETED_EXPRESSION = eINSTANCE.getInterpretedExpression();

        /**
         * The meta object literal for the '<em>Domain Type</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see java.lang.String
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDomainType()
         * @generated
         */
        EDataType DOMAIN_TYPE = eINSTANCE.getDomainType();

        /**
         * The meta object literal for the '<em>Color</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see java.lang.String
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getColor()
         * @generated
         */
        EDataType COLOR = eINSTANCE.getColor();

        /**
         * The meta object literal for the '<em>Length</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLength()
         * @generated
         */
        EDataType LENGTH = eINSTANCE.getLength();

    }

} // ViewPackage
