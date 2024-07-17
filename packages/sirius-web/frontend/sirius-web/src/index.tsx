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
import { ExtensionRegistry } from '@eclipse-sirius/sirius-components-core';
import { NodeTypeContribution } from '@eclipse-sirius/sirius-components-diagrams';
import {
  widgetContributionExtensionPoint,
  GQLWidget,
  PropertySectionComponent,
} from '@eclipse-sirius/sirius-components-forms';
import {
  ReferenceIcon,
  ReferencePreview,
  GQLReferenceWidget,
  ReferencePropertySection,
} from '@eclipse-sirius/sirius-components-widget-reference';
import {
  ApolloClientOptionsConfigurer,
  DefaultExtensionRegistryMergeStrategy,
  DiagramRepresentationConfiguration,
  NodeTypeRegistry,
  SiriusWebApplication,
  apolloClientOptionsConfigurersExtensionPoint,
} from '@eclipse-sirius/sirius-web-application';
import { papayaExtensionRegistry } from '@eclipse-sirius/sirius-web-papaya';
import ReactDOM from 'react-dom';
import { httpOrigin, wsOrigin } from './core/URL';
import { ellipseNodeStyleDocumentTransform } from './nodes/ElipseNodeDocumentTransform';
import { EllipseNode } from './nodes/EllipseNode';
import { EllipseNodeConverter } from './nodes/EllipseNodeConverter';
import { EllipseNodeLayoutHandler } from './nodes/EllipseNodeLayoutHandler';
import { referenceWidgetDocumentTransform } from './widgets/ReferenceWidgetDocumentTransform';

import './ReactFlow.css';
import './fonts.css';
import './portals.css';
import './reset.css';
import './variables.css';

if (process.env.NODE_ENV !== 'production') {
  loadDevMessages();
  loadErrorMessages();
}

const registry = new ExtensionRegistry();
registry.addAll(papayaExtensionRegistry, new DefaultExtensionRegistryMergeStrategy());

const nodesApolloClientOptionsConfigurer: ApolloClientOptionsConfigurer = (currentOptions) => {
  const { documentTransform } = currentOptions;

  const newDocumentTransform = documentTransform
    ? documentTransform.concat(ellipseNodeStyleDocumentTransform)
    : ellipseNodeStyleDocumentTransform;
  return {
    ...currentOptions,
    documentTransform: newDocumentTransform,
  };
};

const widgetsApolloClientOptionsConfigurer: ApolloClientOptionsConfigurer = (currentOptions) => {
  const { documentTransform } = currentOptions;

  const newDocumentTransform = documentTransform
    ? documentTransform.concat(referenceWidgetDocumentTransform)
    : referenceWidgetDocumentTransform;
  return {
    ...currentOptions,
    documentTransform: newDocumentTransform,
  };
};

registry.putData(apolloClientOptionsConfigurersExtensionPoint, {
  identifier: `siriusWeb_${apolloClientOptionsConfigurersExtensionPoint.identifier}`,
  data: [nodesApolloClientOptionsConfigurer, widgetsApolloClientOptionsConfigurer],
});

const nodeTypeRegistry: NodeTypeRegistry = {
  nodeLayoutHandlers: [new EllipseNodeLayoutHandler()],
  nodeConverters: [new EllipseNodeConverter()],
  nodeTypeContributions: [<NodeTypeContribution component={EllipseNode} type={'ellipseNode'} />],
};

const isReferenceWidget = (widget: GQLWidget): widget is GQLReferenceWidget => widget.__typename === 'ReferenceWidget';

registry.putData(widgetContributionExtensionPoint, {
  identifier: 'widget_reference',
  data: [
    {
      name: 'ReferenceWidget',
      icon: <ReferenceIcon />,
      previewComponent: ReferencePreview,
      component: (widget: GQLWidget): PropertySectionComponent<GQLWidget> | null => {
        let propertySectionComponent: PropertySectionComponent<GQLWidget> | null = null;

        if (isReferenceWidget(widget)) {
          propertySectionComponent = ReferencePropertySection;
        }
        return propertySectionComponent;
      },
    },
  ],
});

ReactDOM.render(
  <SiriusWebApplication httpOrigin={httpOrigin} wsOrigin={wsOrigin} extensionRegistry={registry}>
    <DiagramRepresentationConfiguration nodeTypeRegistry={nodeTypeRegistry} />
  </SiriusWebApplication>,
  document.getElementById('root')
);
