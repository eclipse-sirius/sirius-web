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
  DiagramRepresentationConfiguration,
  NodeTypeRegistry,
  SiriusWebApplication,
} from '@eclipse-sirius/sirius-web-application';
import ReactDOM from 'react-dom';
import { httpOrigin, wsOrigin } from './core/URL';
import { EllipseNode } from './nodes/EllipseNode';
import { EllipseNodeConverter } from './nodes/EllipseNodeConverter';
import { EllipseNodeLayoutHandler } from './nodes/EllipseNodeLayoutHandler';

import './ReactFlow.css';
import './fonts.css';
import './portals.css';
import './reset.css';
import './variables.css';

if (process.env.NODE_ENV !== 'production') {
  loadDevMessages();
  loadErrorMessages();
}

const nodeTypeRegistry: NodeTypeRegistry = {
  graphQLNodeStyleFragments: [
    {
      type: 'EllipseNodeStyle',
      fields: `borderColor borderSize borderStyle background`,
    },
  ],
  nodeLayoutHandlers: [new EllipseNodeLayoutHandler()],
  nodeConverters: [new EllipseNodeConverter()],
  nodeTypeContributions: [<NodeTypeContribution component={EllipseNode} type={'ellipseNode'} />],
};

ReactDOM.render(
  <SiriusWebApplication httpOrigin={httpOrigin} wsOrigin={wsOrigin}>
    <DiagramRepresentationConfiguration nodeTypeRegistry={nodeTypeRegistry} />
  </SiriusWebApplication>,
  document.getElementById('root')
);
