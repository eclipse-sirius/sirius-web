/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
  DiagramRepresentationConfiguration,
  NodeTypeRegistry,
  SiriusWebApplication,
} from '@eclipse-sirius/sirius-web-application';
import { papayaExtensionRegistry } from '@eclipse-sirius/sirius-web-papaya';
import { forkRegistry } from '@eclipse-sirius/sirius-web-view-fork';
import { createRoot } from 'react-dom/client';
import { httpOrigin, wsOrigin } from './core/URL';
import { SiriusWebExtensionRegistryMergeStrategy } from './extension/SiriusWebExtensionRegistryMergerStrategy';
import { EllipseNode } from './nodes/EllipseNode';
import { EllipseNodeConverter } from './nodes/EllipseNodeConverter';
import { EllipseNodeLayoutHandler } from './nodes/EllipseNodeLayoutHandler';

import './fonts.css';
import './portals.css';
import './ReactFlow.css';
import './reset.css';
import './variables.css';

if (process.env.NODE_ENV !== 'production') {
  loadDevMessages();
  loadErrorMessages();
}

const registry = new ExtensionRegistry();
registry.addAll(forkRegistry, new SiriusWebExtensionRegistryMergeStrategy());
registry.addAll(papayaExtensionRegistry, new SiriusWebExtensionRegistryMergeStrategy());

const nodeTypeRegistry: NodeTypeRegistry = {
  nodeLayoutHandlers: [new EllipseNodeLayoutHandler()],
  nodeConverters: [new EllipseNodeConverter()],
  nodeTypeContributions: [<NodeTypeContribution component={EllipseNode} type={'ellipseNode'} />],
};

const container = document.getElementById('root');
const root = createRoot(container!);
root.render(
  <SiriusWebApplication
    httpOrigin={httpOrigin}
    wsOrigin={wsOrigin}
    extensionRegistry={registry}
    extensionRegistryMergeStrategy={new SiriusWebExtensionRegistryMergeStrategy()}>
    <DiagramRepresentationConfiguration nodeTypeRegistry={nodeTypeRegistry} />
  </SiriusWebApplication>
);
