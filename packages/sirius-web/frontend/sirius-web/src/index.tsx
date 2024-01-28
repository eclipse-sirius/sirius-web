/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import { loadDevMessages, loadErrorMessages } from '@apollo/client/dev';
import { NodeTypeContribution } from '@eclipse-sirius/sirius-components-diagrams';
import {
  GQLWidget,
  PropertySectionComponent,
  PropertySectionComponentRegistry,
  PropertySectionContext,
  PropertySectionContextValue,
  WidgetContribution,
} from '@eclipse-sirius/sirius-components-forms';
import {
  GQLReferenceWidget,
  ReferenceIcon,
  ReferencePreview,
  ReferencePropertySection,
} from '@eclipse-sirius/sirius-components-widget-reference';
import {
  DiagramRepresentationConfiguration,
  NodeTypeRegistry,
  SiriusWebApplication,
} from '@eclipse-sirius/sirius-web-application';
import LinearScaleOutlinedIcon from '@material-ui/icons/LinearScaleOutlined';
import ReactDOM from 'react-dom';
import { httpOrigin, wsOrigin } from './core/URL';
import { EllipseNode } from './nodes/EllipseNode';
import { EllipseNodeConverter } from './nodes/EllipseNodeConverter';
import { EllipseNodeLayoutHandler } from './nodes/EllipseNodeLayoutHandler';
import { GQLSlider } from './widgets/SliderFragment.types';
import { SliderPreview } from './widgets/SliderPreview';
import { SliderPropertySection } from './widgets/SliderPropertySection';

import './ReactFlow.css';
import './fonts.css';
import './portals.css';
import './reset.css';
import './variables.css';

if (process.env.NODE_ENV !== 'production') {
  loadDevMessages();
  loadErrorMessages();
}

const isSlider = (widget: GQLWidget): widget is GQLSlider => widget.__typename === 'Slider';
const isReferenceWidget = (widget: GQLWidget): widget is GQLReferenceWidget => widget.__typename === 'ReferenceWidget';

const propertySectionsRegistry: PropertySectionComponentRegistry = {
  getComponent: (widget: GQLWidget): PropertySectionComponent<GQLWidget> | null => {
    if (isSlider(widget)) {
      return SliderPropertySection;
    } else if (isReferenceWidget(widget)) {
      return ReferencePropertySection;
    }
    return null;
  },
  getPreviewComponent: (widget: GQLWidget) => {
    if (widget.__typename === 'Slider') {
      return SliderPreview;
    } else if (widget.__typename === 'ReferenceWidget') {
      return ReferencePreview;
    }
    return null;
  },
  getWidgetContributions: () => {
    const sliderWidgetContribution: WidgetContribution = {
      name: 'Slider',
      fields: `label iconURL minValue maxValue currentValue`,
      icon: <LinearScaleOutlinedIcon />,
    };
    const referenceWidget: WidgetContribution = {
      name: 'ReferenceWidget',
      fields: `label
               iconURL
               ownerId
               descriptionId
               reference {
                 ownerKind
                 referenceKind
                 containment
                 manyValued
               }
               referenceValues {
                 id
                 label
                 kind
                 iconURL
               }
               style {
                 color
                 fontSize
                 italic
                 bold
                 underline
                 strikeThrough
               }`,
      icon: <ReferenceIcon />,
    };
    return [sliderWidgetContribution, referenceWidget];
  },
};

const propertySectionRegistryValue: PropertySectionContextValue = {
  propertySectionsRegistry,
};

const nodeTypeRegistry: NodeTypeRegistry = {
  graphQLNodeStyleFragments: [
    {
      type: 'EllipseNodeStyle',
      fields: `borderColor borderSize borderStyle color`,
    },
  ],
  nodeLayoutHandlers: [new EllipseNodeLayoutHandler()],
  nodeConverters: [new EllipseNodeConverter()],
  nodeTypeContributions: [<NodeTypeContribution component={EllipseNode} type={'ellipseNode'} />],
};

ReactDOM.render(
  <PropertySectionContext.Provider value={propertySectionRegistryValue}>
    <SiriusWebApplication httpOrigin={httpOrigin} wsOrigin={wsOrigin}>
      <DiagramRepresentationConfiguration nodeTypeRegistry={nodeTypeRegistry} />
    </SiriusWebApplication>
  </PropertySectionContext.Provider>,
  document.getElementById('root')
);
